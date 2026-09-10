import clsx from 'clsx';
import { useStore } from '../store';
import { neighborsOf, weightAt } from '../lib/graph';

export default function AdjListPanel() {
  const graph = useStore((s) => s.graph);
  const selectedId = useStore((s) => s.selectedId);
  const hoveredId = useStore((s) => s.hoveredId);
  const hoveredEdge = useStore((s) => s.hoveredEdge);
  const select = useStore((s) => s.select);
  const hover = useStore((s) => s.hover);
  const hoverEdge = useStore((s) => s.hoverEdge);

  if (!graph) return null;

  const focus = hoveredId ?? selectedId;

  return (
    <div className="flex min-h-0 flex-1 flex-col border-b border-zinc-800">
      <div className="flex items-center justify-between border-b border-zinc-800 px-4 py-2.5">
        <span className="text-xs font-semibold uppercase tracking-wider text-zinc-500">Adjacency list</span>
        <span className="mono text-[10px] text-zinc-600">{graph.meta.kind === 'adjList' ? 'source' : 'derived'}</span>
      </div>
      <div className="min-h-0 flex-1 overflow-auto p-2">
        {graph.nodes.length === 0 ? (
          <p className="p-3 text-center text-xs text-zinc-600">No vertices.</p>
        ) : (
          graph.nodes.map((u) => {
            const nbrs = neighborsOf(graph, u);
            const rowOn = u === focus || (hoveredEdge != null && (hoveredEdge.u === u || hoveredEdge.v === u));
            return (
              <div
                key={u}
                className={clsx(
                  'mb-0.5 flex flex-wrap items-baseline gap-x-1 rounded-md px-2 py-1 text-xs',
                  rowOn ? 'bg-amber-500/10' : 'hover:bg-zinc-900/80',
                )}
                onMouseEnter={() => hover(u)}
                onMouseLeave={() => hover(null)}
                onClick={() => select(selectedId === u ? null : u)}
                style={{ cursor: 'pointer' }}
              >
                <span className={clsx('mono font-semibold', rowOn ? 'text-amber-200' : 'text-emerald-300')}>{u}</span>
                {nbrs.length === 0 ? (
                  <span className="text-zinc-600">∅</span>
                ) : (
                  nbrs.map((v, i) => {
                    const on = hoveredEdge != null && hoveredEdge.u === u && hoveredEdge.v === v;
                    const w = weightAt(graph, u, v);
                    return (
                      <span
                        key={`${u}-${v}-${i}`}
                        className="inline-flex items-baseline"
                        onMouseEnter={(e) => {
                          e.stopPropagation();
                          hoverEdge({ u, v });
                        }}
                        onMouseLeave={(e) => {
                          e.stopPropagation();
                          hoverEdge(null);
                          hover(u);
                        }}
                      >
                        <span className="text-zinc-500">→</span>
                        <span className={clsx('mono ml-1', on ? 'text-amber-200' : 'text-zinc-200')}>{v}</span>
                        {graph.meta.weighted && <span className="mono ml-0.5 text-[10px] text-zinc-500">({w})</span>}
                      </span>
                    );
                  })
                )}
              </div>
            );
          })
        )}
      </div>
    </div>
  );
}
