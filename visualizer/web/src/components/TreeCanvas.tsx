import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { hierarchy, tree, type HierarchyNode } from 'd3-hierarchy';
import { select } from 'd3-selection';
import { zoom as d3zoom, zoomIdentity, type ZoomBehavior } from 'd3-zoom';
import { Expand, Maximize2 } from 'lucide-react';
import { useStore } from '../store';
import { compactArgs, dupHue, fmtVal, stateAt, type NodeState, type RecNode } from '../lib/trace';

const X_SPACING = 128;
const Y_SPACING = 68;
const NODE_W = 114;
const NODE_H = 38;

interface HNode {
  node: RecNode;
  children?: HNode[];
}

function virtualRoot(children: RecNode[]): RecNode {
  return {
    id: -1,
    parentId: null,
    method: 'main',
    args: {},
    depth: -1,
    enterSeq: -1,
    exitSeq: null,
    result: null,
    hasResult: false,
    isVoid: false,
    children,
    dupKey: '',
  };
}

function toHierarchy(n: RecNode, collapsed: Record<number, true>): HNode {
  if (collapsed[n.id]) return { node: n };
  const kids = n.children.map((c) => toHierarchy(c, collapsed));
  return kids.length ? { node: n, children: kids } : { node: n };
}

interface Colors {
  fill: string;
  stroke: string;
  text: string;
  result: string;
  opacity: number;
}

function colorsFor(state: NodeState, isDup: boolean, showDupes: boolean, dupKey: string): Colors {
  if (showDupes) {
    if (isDup) {
      const hue = dupHue(dupKey);
      return {
        fill: `hsl(${hue} 45% 13%)`,
        stroke: `hsl(${hue} 85% 60%)`,
        text: `hsl(${hue} 90% 82%)`,
        result: `hsl(${hue} 90% 72%)`,
        opacity: 1,
      };
    }
    return { fill: '#18181b', stroke: '#3f3f46', text: '#71717a', result: '#71717a', opacity: 0.35 };
  }
  switch (state) {
    case 'waiting':
      return { fill: '#18181b', stroke: '#3f3f46', text: '#a1a1aa', result: '#71717a', opacity: 0.45 };
    case 'active':
      return { fill: '#451a03', stroke: '#f59e0b', text: '#fde68a', result: '#fcd34d', opacity: 1 };
    case 'done':
      return { fill: '#022c22', stroke: '#10b981', text: '#a7f3d0', result: '#34d399', opacity: 1 };
  }
}

function edgeColor(state: NodeState): string {
  switch (state) {
    case 'waiting':
      return '#27272a';
    case 'active':
      return '#f59e0b';
    case 'done':
      return '#065f46';
  }
}

export default function TreeCanvas() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);
  const selectedId = useStore((s) => s.selectedId);
  const showDupes = useStore((s) => s.showDupes);
  const collapsed = useStore((s) => s.collapsed);
  const selectNode = useStore((s) => s.select);
  const toggleCollapse = useStore((s) => s.toggleCollapse);
  const resetCollapsed = useStore((s) => s.resetCollapsed);

  const svgRef = useRef<SVGSVGElement>(null);
  const zoomRef = useRef<ZoomBehavior<SVGSVGElement, unknown> | null>(null);
  const [t, setT] = useState({ x: 60, y: 50, k: 1 });

  useEffect(() => {
    if (!svgRef.current) return;
    const z = d3zoom<SVGSVGElement, unknown>()
      .scaleExtent([0.03, 4])
      .on('zoom', (e) => setT({ x: e.transform.x, y: e.transform.y, k: e.transform.k }));
    zoomRef.current = z;
    select(svgRef.current).call(z);
  }, []);

  const subtreeSize = useMemo(() => {
    const m = new Map<number, number>();
    if (!trace) return m;
    const dfs = (n: RecNode): number => {
      let s = 1;
      for (const c of n.children) s += dfs(c);
      m.set(n.id, s);
      return s;
    };
    trace.roots.forEach(dfs);
    return m;
  }, [trace]);

  const layout = useMemo(() => {
    if (!trace) return null;
    const roots = trace.roots;
    const rootData: HNode =
      roots.length === 1
        ? toHierarchy(roots[0], collapsed)
        : { node: virtualRoot(roots), children: roots.map((r) => toHierarchy(r, collapsed)) };
    const hier = hierarchy(rootData, (d) => d.children);
    tree<HNode>().nodeSize([X_SPACING, Y_SPACING])(hier);
    const nodes = hier.descendants();
    const links = hier.links();
    let minX = Infinity;
    let maxX = -Infinity;
    let maxY = 0;
    for (const n of nodes) {
      minX = Math.min(minX, n.x ?? 0);
      maxX = Math.max(maxX, n.x ?? 0);
      maxY = Math.max(maxY, n.y ?? 0);
    }
    return { nodes, links, minX, maxX, maxY };
  }, [trace, collapsed]);

  const fit = useCallback(() => {
    if (!layout || !svgRef.current || !zoomRef.current) return;
    const rect = svgRef.current.getBoundingClientRect();
    if (rect.width === 0) return;
    const w = Math.max(1, layout.maxX - layout.minX + 2 * 140);
    const h = Math.max(1, layout.maxY + 2 * 90);
    const k = Math.min(1.2, Math.min(rect.width / w, rect.height / h));
    const x = rect.width / 2 - ((layout.minX + layout.maxX) / 2) * k;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    (select(svgRef.current) as any).call(
      zoomRef.current.transform,
      zoomIdentity.translate(x, 50).scale(k),
    );
  }, [layout]);

  // Refit only when a different trace is loaded (not on collapse changes).
  const lastTraceName = useRef<string | null>(null);
  const traceName = useStore((s) => s.traceName);
  useEffect(() => {
    if (trace && traceName !== lastTraceName.current) {
      lastTraceName.current = traceName;
      const id = requestAnimationFrame(fit);
      return () => cancelAnimationFrame(id);
    }
  }, [trace, traceName, fit]);

  // Which nodes are currently on the call stack (for "current call" highlighting)?
  const currentId = useMemo(() => {
    if (!trace) return null;
    let best: RecNode | null = null;
    trace.byId.forEach((n) => {
      if (stateAt(n, step) === 'active' && (!best || n.depth > best.depth)) best = n;
    });
    return best ? (best as RecNode).id : null;
  }, [trace, step]);

  const edgePath = (s: { x: number; y: number }, d: { x: number; y: number }) =>
    `M${s.x},${s.y + NODE_H / 2} C${s.x},${(s.y + d.y) / 2} ${d.x},${(s.y + d.y) / 2} ${d.x},${d.y - NODE_H / 2}`;

  const renderNode = (h: HierarchyNode<HNode>) => {
    const n = h.data.node;
    if (n.id === -1) return null; // virtual root: edges only
    const x = h.x ?? 0;
    const y = h.y ?? 0;
    const state = stateAt(n, step);
    const isDup = trace!.dupKeys.has(n.dupKey);
    const c = colorsFor(state, isDup, showDupes, n.dupKey);
    const isCurrent = n.id === currentId;
    const isSelected = n.id === selectedId;
    const isCollapsed = !!collapsed[n.id];
    const hidden = isCollapsed ? (subtreeSize.get(n.id) ?? 1) - 1 : 0;
    const showResult = n.hasResult && !n.isVoid && n.exitSeq !== null && step >= n.exitSeq;

    return (
      <g
        key={n.id}
        transform={`translate(${x},${y})`}
        opacity={c.opacity}
        className="node"
        style={{ cursor: 'pointer', filter: isCurrent ? 'url(#recviz-glow)' : undefined }}
        onClick={() => selectNode(isSelected ? null : n.id)}
      >
        {isSelected && (
          <rect
            x={-NODE_W / 2 - 4}
            y={-NODE_H / 2 - 4}
            width={NODE_W + 8}
            height={NODE_H + 8}
            rx={12}
            fill="none"
            stroke="#fafafa"
            strokeWidth={1.5}
          />
        )}
        <rect
          className="node-rect"
          x={-NODE_W / 2}
          y={-NODE_H / 2}
          width={NODE_W}
          height={NODE_H}
          rx={9}
          fill={c.fill}
          stroke={c.stroke}
          strokeWidth={isCurrent ? 2.5 : 1.5}
        />
        <text y={showResult ? -4 : 3} textAnchor="middle" fontSize={11} fill={c.text} className="mono">
          {compactArgs(n)}
        </text>
        {showResult && (
          <text y={11} textAnchor="middle" fontSize={10} fill={c.result} className="mono">
            → {fmtVal(n.result, 16)}
          </text>
        )}
        {n.children.length > 0 && (
          <g
            transform={`translate(${NODE_W / 2 + 2},0)`}
            onClick={(e) => {
              e.stopPropagation();
              toggleCollapse(n.id);
            }}
          >
            <circle r={8} fill="#27272a" stroke="#52525b" strokeWidth={1} />
            <text y={3.5} textAnchor="middle" fontSize={10} fill="#d4d4d8" className="mono">
              {isCollapsed ? '+' : '–'}
            </text>
          </g>
        )}
        {isCollapsed && hidden > 0 && (
          <text x={NODE_W / 2 + 14} y={3.5} fontSize={9} fill="#71717a" className="mono">
            {hidden}
          </text>
        )}
      </g>
    );
  };

  return (
    <div className="relative h-full w-full">
      <svg ref={svgRef} className="h-full w-full touch-none">
        <defs>
          <pattern id="recviz-grid" width={26} height={26} patternUnits="userSpaceOnUse">
            <circle cx={1} cy={1} r={1} fill="#1c1c22" />
          </pattern>
          <filter id="recviz-glow" x="-80%" y="-80%" width="260%" height="260%">
            <feDropShadow dx={0} dy={0} stdDeviation={7} floodColor="#fbbf24" floodOpacity={0.75} />
          </filter>
        </defs>
        <rect width="100%" height="100%" fill="url(#recviz-grid)" />
        <g transform={`translate(${t.x},${t.y}) scale(${t.k})`}>
          {layout?.links.map((l) => {
            const target = l.target.data.node;
            const state = stateAt(target, step);
            return (
              <path
                key={target.id}
                d={edgePath(
                  { x: l.source.x ?? 0, y: l.source.y ?? 0 },
                  { x: l.target.x ?? 0, y: l.target.y ?? 0 },
                )}
                fill="none"
                stroke={showDupes ? (trace!.dupKeys.has(target.dupKey) ? '#52525b' : '#27272a') : edgeColor(state)}
                strokeWidth={state === 'active' && !showDupes ? 1.8 : 1.1}
                opacity={showDupes && !trace!.dupKeys.has(target.dupKey) ? 0.4 : 1}
              />
            );
          })}
          {layout?.nodes.map(renderNode)}
        </g>
      </svg>
      {trace && (
        <div className="absolute right-3 top-3 flex gap-2">
          <button
            onClick={fit}
            title="Fit tree to view"
            className="rounded-md border border-zinc-700 bg-zinc-900/90 p-2 text-zinc-300 hover:bg-zinc-800 hover:text-white"
          >
            <Maximize2 size={15} />
          </button>
          <button
            onClick={resetCollapsed}
            title="Re-expand / re-collapse to default"
            className="rounded-md border border-zinc-700 bg-zinc-900/90 p-2 text-zinc-300 hover:bg-zinc-800 hover:text-white"
          >
            <Expand size={15} />
          </button>
        </div>
      )}
    </div>
  );
}
