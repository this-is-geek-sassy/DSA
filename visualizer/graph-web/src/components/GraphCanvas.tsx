import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import {
  forceCenter,
  forceCollide,
  forceLink,
  forceManyBody,
  forceSimulation,
  type Simulation,
  type SimulationLinkDatum,
  type SimulationNodeDatum,
} from 'd3-force';
import { select } from 'd3-selection';
import { zoom as d3zoom, zoomIdentity, type ZoomBehavior } from 'd3-zoom';
import { Maximize2 } from 'lucide-react';
import { useStore } from '../store';
import { edgeKey, edgesMatch, type GraphEdge, type GraphFile } from '../lib/graph';

const NODE_R = 18;

interface SimNode extends SimulationNodeDatum {
  id: number;
}

interface SimLink extends SimulationLinkDatum<SimNode> {
  u: number;
  v: number;
  w: number;
}

function shorten(
  x1: number,
  y1: number,
  x2: number,
  y2: number,
  startPad: number,
  endPad: number,
): { x1: number; y1: number; x2: number; y2: number } {
  const dx = x2 - x1;
  const dy = y2 - y1;
  const len = Math.hypot(dx, dy) || 1;
  const ux = dx / len;
  const uy = dy / len;
  return {
    x1: x1 + ux * startPad,
    y1: y1 + uy * startPad,
    x2: x2 - ux * endPad,
    y2: y2 - uy * endPad,
  };
}

function offsetLine(
  x1: number,
  y1: number,
  x2: number,
  y2: number,
  offset: number,
): { x1: number; y1: number; x2: number; y2: number } {
  const dx = x2 - x1;
  const dy = y2 - y1;
  const len = Math.hypot(dx, dy) || 1;
  const ox = (-dy / len) * offset;
  const oy = (dx / len) * offset;
  return { x1: x1 + ox, y1: y1 + oy, x2: x2 + ox, y2: y2 + oy };
}

function selfLoopPath(x: number, y: number): string {
  const r = NODE_R + 10;
  return `M ${x} ${y - NODE_R} C ${x + r * 1.6} ${y - r * 2.2}, ${x + r * 1.6} ${y + r * 0.4}, ${x} ${y + NODE_R * 0.2}`;
}

function pairCount(graph: GraphFile): Map<string, number> {
  const m = new Map<string, number>();
  for (const e of graph.edges) {
    const k = edgeKey(e.u, e.v, false);
    m.set(k, (m.get(k) ?? 0) + 1);
  }
  return m;
}

export default function GraphCanvas() {
  const graph = useStore((s) => s.graph);
  const graphName = useStore((s) => s.graphName);
  const selectedId = useStore((s) => s.selectedId);
  const hoveredId = useStore((s) => s.hoveredId);
  const hoveredEdge = useStore((s) => s.hoveredEdge);
  const selectNode = useStore((s) => s.select);
  const hover = useStore((s) => s.hover);
  const hoverEdge = useStore((s) => s.hoverEdge);

  const svgRef = useRef<SVGSVGElement>(null);
  const zoomRef = useRef<ZoomBehavior<SVGSVGElement, unknown> | null>(null);
  const simRef = useRef<Simulation<SimNode, SimLink> | null>(null);
  const nodesRef = useRef<Map<number, SimNode>>(new Map());
  const dragId = useRef<number | null>(null);
  const dragged = useRef(false);
  const [t, setT] = useState({ x: 0, y: 0, k: 1 });
  const [, setTick] = useState(0);

  const directed = graph?.meta.directed ?? false;
  const weighted = graph?.meta.weighted ?? false;
  const pairs = useMemo(() => (graph ? pairCount(graph) : new Map<string, number>()), [graph]);

  useEffect(() => {
    if (!svgRef.current) return;
    const svg = svgRef.current;
    const z = d3zoom<SVGSVGElement, unknown>()
      .scaleExtent([0.15, 4])
      .filter((event) => {
        if (event.type === 'mousedown' || event.type === 'touchstart') {
          const target = event.target as Element | null;
          if (target?.closest?.('.graph-node')) return false;
        }
        return true;
      })
      .on('zoom', (e) => setT({ x: e.transform.x, y: e.transform.y, k: e.transform.k }));
    zoomRef.current = z;
    select(svg).call(z);
    return () => {
      select(svg).on('.zoom', null);
    };
  }, []);

  useEffect(() => {
    simRef.current?.stop();
    simRef.current = null;
    nodesRef.current = new Map();
    if (!graph || graph.nodes.length === 0) {
      setTick((n) => n + 1);
      return;
    }

    const nodes: SimNode[] = graph.nodes.map((id) => ({ id }));
    const byId = new Map(nodes.map((n) => [n.id, n]));
    nodesRef.current = byId;

    const links: SimLink[] = graph.edges.map((e) => ({
      u: e.u,
      v: e.v,
      w: e.w,
      source: e.u,
      target: e.v,
    }));

    const sim = forceSimulation(nodes)
      .force(
        'link',
        forceLink<SimNode, SimLink>(links)
          .id((d) => d.id)
          .distance(weighted ? 110 : 90)
          .strength(0.7),
      )
      .force('charge', forceManyBody().strength(-280))
      .force('center', forceCenter(0, 0))
      .force('collide', forceCollide(NODE_R + 10))
      .on('tick', () => setTick((n) => n + 1));

    simRef.current = sim;
    return () => {
      sim.stop();
      if (simRef.current === sim) simRef.current = null;
    };
  }, [graph, graphName, weighted]);

  const toGraphPoint = useCallback(
    (clientX: number, clientY: number) => {
      const svg = svgRef.current;
      if (!svg) return { x: 0, y: 0 };
      const rect = svg.getBoundingClientRect();
      return {
        x: (clientX - rect.left - t.x) / t.k,
        y: (clientY - rect.top - t.y) / t.k,
      };
    },
    [t],
  );

  const fit = useCallback(() => {
    const svg = svgRef.current;
    const zoom = zoomRef.current;
    if (!svg || !zoom) return;
    const nodes = [...nodesRef.current.values()];
    if (nodes.length === 0) return;
    let minX = Infinity;
    let minY = Infinity;
    let maxX = -Infinity;
    let maxY = -Infinity;
    for (const n of nodes) {
      const x = n.x ?? 0;
      const y = n.y ?? 0;
      minX = Math.min(minX, x);
      minY = Math.min(minY, y);
      maxX = Math.max(maxX, x);
      maxY = Math.max(maxY, y);
    }
    const rect = svg.getBoundingClientRect();
    if (rect.width === 0) return;
    const pad = 80;
    const w = Math.max(1, maxX - minX + pad * 2);
    const h = Math.max(1, maxY - minY + pad * 2);
    const k = Math.min(1.4, Math.min(rect.width / w, rect.height / h));
    const cx = (minX + maxX) / 2;
    const cy = (minY + maxY) / 2;
    const x = rect.width / 2 - cx * k;
    const y = rect.height / 2 - cy * k;
    select(svg).call(zoom.transform, zoomIdentity.translate(x, y).scale(k));
  }, []);

  useEffect(() => {
    if (!graph) return;
    const id = window.setTimeout(() => fit(), 400);
    return () => window.clearTimeout(id);
  }, [graph, graphName, fit]);

  const onNodePointerDown = (event: React.PointerEvent<SVGGElement>, id: number) => {
    event.preventDefault();
    event.stopPropagation();
    dragId.current = id;
    dragged.current = false;
    (event.currentTarget as Element).setPointerCapture(event.pointerId);
    const node = nodesRef.current.get(id);
    if (!node) return;
    const p = toGraphPoint(event.clientX, event.clientY);
    node.fx = p.x;
    node.fy = p.y;
    simRef.current?.alphaTarget(0.25).restart();
  };

  const onNodePointerMove = (event: React.PointerEvent<SVGGElement>, id: number) => {
    if (dragId.current !== id) return;
    dragged.current = true;
    const node = nodesRef.current.get(id);
    if (!node) return;
    const p = toGraphPoint(event.clientX, event.clientY);
    node.fx = p.x;
    node.fy = p.y;
    simRef.current?.alpha(0.2).restart();
  };

  const onNodePointerUp = (_event: React.PointerEvent<SVGGElement>, id: number) => {
    if (dragId.current !== id) return;
    dragId.current = null;
    simRef.current?.alphaTarget(0);
  };

  const focusId = hoveredId ?? selectedId;
  const nodes = [...nodesRef.current.values()];

  const edgeActive = (e: GraphEdge) => {
    if (hoveredEdge && edgesMatch(hoveredEdge, e.u, e.v, directed)) return true;
    if (focusId == null) return false;
    return e.u === focusId || e.v === focusId;
  };

  const nodeActive = (id: number) => {
    if (id === hoveredId || id === selectedId) return true;
    if (hoveredEdge && (hoveredEdge.u === id || hoveredEdge.v === id)) return true;
    return false;
  };

  const dimmed = focusId != null || hoveredEdge != null;

  return (
    <div className="relative h-full w-full">
      <svg ref={svgRef} className="h-full w-full touch-none">
        <defs>
          <pattern id="gview-grid" width={26} height={26} patternUnits="userSpaceOnUse">
            <circle cx={1} cy={1} r={1} fill="#1c1c22" />
          </pattern>
          <marker
            id="gview-arrow"
            viewBox="0 0 10 10"
            refX="8"
            refY="5"
            markerWidth="7"
            markerHeight="7"
            orient="auto-start-reverse"
          >
            <path d="M 0 0 L 10 5 L 0 10 z" fill="#34d399" />
          </marker>
          <marker
            id="gview-arrow-muted"
            viewBox="0 0 10 10"
            refX="8"
            refY="5"
            markerWidth="7"
            markerHeight="7"
            orient="auto-start-reverse"
          >
            <path d="M 0 0 L 10 5 L 0 10 z" fill="#3f3f46" />
          </marker>
          <marker
            id="gview-arrow-hot"
            viewBox="0 0 10 10"
            refX="8"
            refY="5"
            markerWidth="7"
            markerHeight="7"
            orient="auto-start-reverse"
          >
            <path d="M 0 0 L 10 5 L 0 10 z" fill="#fbbf24" />
          </marker>
        </defs>
        <rect width="100%" height="100%" fill="url(#gview-grid)" onClick={() => selectNode(null)} />
        <g transform={`translate(${t.x},${t.y}) scale(${t.k})`}>
          {graph?.edges.map((e, i) => {
            const su = nodesRef.current.get(e.u);
            const sv = nodesRef.current.get(e.v);
            if (!su || !sv) return null;
            const x1 = su.x ?? 0;
            const y1 = su.y ?? 0;
            const x2 = sv.x ?? 0;
            const y2 = sv.y ?? 0;
            const active = edgeActive(e);
            const muted = dimmed && !active;
            const stroke = active ? '#fbbf24' : muted ? '#27272a' : '#34d399';
            const marker = directed
              ? active
                ? 'url(#gview-arrow-hot)'
                : muted
                  ? 'url(#gview-arrow-muted)'
                  : 'url(#gview-arrow)'
              : undefined;

            if (e.u === e.v) {
              return (
                <g
                  key={`loop-${e.u}-${i}`}
                  onMouseEnter={() => hoverEdge({ u: e.u, v: e.v })}
                  onMouseLeave={() => hoverEdge(null)}
                >
                  <path
                    d={selfLoopPath(x1, y1)}
                    fill="none"
                    stroke={stroke}
                    strokeWidth={active ? 2.4 : 1.4}
                    markerEnd={marker}
                    opacity={muted ? 0.4 : 1}
                  />
                  {weighted && (
                    <text x={x1 + NODE_R + 16} y={y1 - NODE_R - 4} fontSize={10} fill="#a1a1aa" className="mono">
                      {e.w}
                    </text>
                  )}
                </g>
              );
            }

            const undirectedPair = edgeKey(e.u, e.v, false);
            const bothWays = directed && (pairs.get(undirectedPair) ?? 0) > 1;
            const shifted = bothWays
              ? offsetLine(x1, y1, x2, y2, e.u < e.v ? 6 : -6)
              : { x1, y1, x2, y2 };
            const line = shorten(shifted.x1, shifted.y1, shifted.x2, shifted.y2, NODE_R, NODE_R + (directed ? 2 : 0));
            const mx = (line.x1 + line.x2) / 2;
            const my = (line.y1 + line.y2) / 2;

            return (
              <g
                key={`${e.u}-${e.v}-${i}`}
                onMouseEnter={() => hoverEdge({ u: e.u, v: e.v })}
                onMouseLeave={() => hoverEdge(null)}
                style={{ cursor: 'pointer' }}
              >
                <line
                  x1={line.x1}
                  y1={line.y1}
                  x2={line.x2}
                  y2={line.y2}
                  stroke={stroke}
                  strokeWidth={active ? 2.6 : 1.6}
                  markerEnd={marker}
                  opacity={muted ? 0.4 : 1}
                />
                {weighted && (
                  <text
                    x={mx}
                    y={my - 6}
                    textAnchor="middle"
                    fontSize={10}
                    fill={active ? '#fde68a' : '#a1a1aa'}
                    className="mono"
                    opacity={muted ? 0.4 : 1}
                  >
                    {e.w}
                  </text>
                )}
              </g>
            );
          })}

          {nodes.map((n) => {
            const id = n.id;
            const active = nodeActive(id);
            const muted = dimmed && !active;
            const selected = id === selectedId;
            const x = n.x ?? 0;
            const y = n.y ?? 0;
            return (
              <g
                key={id}
                className="graph-node"
                transform={`translate(${x},${y})`}
                style={{ cursor: 'grab' }}
                opacity={muted ? 0.35 : 1}
                onPointerDown={(e) => onNodePointerDown(e, id)}
                onPointerMove={(e) => onNodePointerMove(e, id)}
                onPointerUp={(e) => onNodePointerUp(e, id)}
                onPointerCancel={(e) => onNodePointerUp(e, id)}
                onDoubleClick={(e) => {
                  e.stopPropagation();
                  const node = nodesRef.current.get(id);
                  if (node) {
                    node.fx = null;
                    node.fy = null;
                    simRef.current?.alpha(0.4).restart();
                  }
                }}
                onClick={(e) => {
                  e.stopPropagation();
                  if (dragged.current) return;
                  selectNode(selected ? null : id);
                }}
                onMouseEnter={() => hover(id)}
                onMouseLeave={() => hover(null)}
              >
                {selected && <circle r={NODE_R + 5} fill="none" stroke="#fafafa" strokeWidth={1.5} />}
                <circle
                  r={NODE_R}
                  fill={active ? '#451a03' : '#022c22'}
                  stroke={active ? '#f59e0b' : '#10b981'}
                  strokeWidth={active ? 2.4 : 1.6}
                />
                <text y={4} textAnchor="middle" fontSize={12} fill={active ? '#fde68a' : '#a7f3d0'} className="mono">
                  {id}
                </text>
              </g>
            );
          })}
        </g>
      </svg>
      {graph && graph.nodes.length === 0 && (
        <div className="absolute inset-0 flex items-center justify-center">
          <p className="text-sm text-zinc-500">This dump has no nodes.</p>
        </div>
      )}
      {graph && (
        <div className="absolute right-3 top-3">
          <button
            onClick={fit}
            title="Fit graph to view"
            className="rounded-md border border-zinc-700 bg-zinc-900/90 p-2 text-zinc-300 hover:bg-zinc-800 hover:text-white"
          >
            <Maximize2 size={15} />
          </button>
        </div>
      )}
    </div>
  );
}
