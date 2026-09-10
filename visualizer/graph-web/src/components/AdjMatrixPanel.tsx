import clsx from 'clsx';
import { useStore } from '../store';
import { displayMatrix, edgesMatch } from '../lib/graph';

export default function AdjMatrixPanel() {
  const graph = useStore((s) => s.graph);
  const selectedId = useStore((s) => s.selectedId);
  const hoveredId = useStore((s) => s.hoveredId);
  const hoveredEdge = useStore((s) => s.hoveredEdge);
  const select = useStore((s) => s.select);
  const hover = useStore((s) => s.hover);
  const hoverEdge = useStore((s) => s.hoverEdge);

  if (!graph) return null;

  const { ids, cells } = displayMatrix(graph);
  const focus = hoveredId ?? selectedId;

  return (
    <div className="flex min-h-0 flex-1 flex-col">
      <div className="flex items-center justify-between border-b border-zinc-800 px-4 py-2.5">
        <span className="text-xs font-semibold uppercase tracking-wider text-zinc-500">Adjacency matrix</span>
        <span className="mono text-[10px] text-zinc-600">{graph.meta.kind === 'adjMatrix' ? 'source' : 'derived'}</span>
      </div>
      <div className="min-h-0 flex-1 overflow-auto p-2">
        {ids.length === 0 ? (
          <p className="p-3 text-center text-xs text-zinc-600">No vertices.</p>
        ) : (
          <table className="mono border-collapse text-[11px]">
            <thead>
              <tr>
                <th className="sticky left-0 top-0 z-20 bg-zinc-950 px-2 py-1 text-zinc-600" />
                {ids.map((v) => (
                  <th
                    key={v}
                    className={clsx(
                      'sticky top-0 z-10 bg-zinc-950 px-2 py-1 font-medium',
                      v === focus ? 'text-amber-300' : 'text-zinc-500',
                    )}
                    onMouseEnter={() => hover(v)}
                    onMouseLeave={() => hover(null)}
                    onClick={() => select(selectedId === v ? null : v)}
                    style={{ cursor: 'pointer' }}
                  >
                    {v}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {ids.map((u, i) => (
                <tr key={u}>
                  <th
                    className={clsx(
                      'sticky left-0 z-10 bg-zinc-950 px-2 py-1 text-left font-medium',
                      u === focus ? 'text-amber-300' : 'text-zinc-500',
                    )}
                    onMouseEnter={() => hover(u)}
                    onMouseLeave={() => hover(null)}
                    onClick={() => select(selectedId === u ? null : u)}
                    style={{ cursor: 'pointer' }}
                  >
                    {u}
                  </th>
                  {cells[i].map((val, j) => {
                    const v = ids[j];
                    const edgeOn =
                      hoveredEdge != null && edgesMatch(hoveredEdge, u, v, graph.meta.directed);
                    const rowCol = u === focus || v === focus;
                    const nz = val !== 0;
                    return (
                      <td
                        key={`${u}-${v}`}
                        className={clsx(
                          'px-2 py-1 text-center',
                          edgeOn
                            ? 'bg-amber-500/25 text-amber-100'
                            : rowCol
                              ? 'bg-zinc-900 text-zinc-200'
                              : nz
                                ? 'text-emerald-300'
                                : 'text-zinc-700',
                        )}
                        onMouseEnter={() => {
                          hover(u);
                          hoverEdge({ u, v });
                        }}
                        onMouseLeave={() => {
                          hover(null);
                          hoverEdge(null);
                        }}
                        onClick={() => select(selectedId === u ? null : u)}
                        style={{ cursor: 'pointer' }}
                      >
                        {val}
                      </td>
                    );
                  })}
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
