import { useMemo } from 'react';
import clsx from 'clsx';
import { useStore } from '../store';
import { compactArgs, stateAt, type RecNode } from '../lib/trace';

const MAX_ROWS = 300;

export default function CallStackPanel() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);
  const selectedId = useStore((s) => s.selectedId);
  const select = useStore((s) => s.select);

  const active = useMemo(() => {
    if (!trace) return [] as RecNode[];
    const out: RecNode[] = [];
    trace.byId.forEach((n) => {
      if (stateAt(n, step) === 'active') out.push(n);
    });
    out.sort((a, b) => a.depth - b.depth);
    return out;
  }, [trace, step]);

  const overflow = active.length - MAX_ROWS;
  const rows = overflow > 0 ? active.slice(overflow) : active;

  return (
    <div className="flex min-h-0 flex-1 flex-col">
      <div className="flex items-center justify-between border-b border-zinc-800 px-4 py-2.5">
        <span className="text-xs font-semibold uppercase tracking-wider text-zinc-500">Call stack</span>
        <span className="mono text-[10px] text-zinc-600">{active.length || ''}</span>
      </div>
      <div className="min-h-0 flex-1 overflow-y-auto p-2">
        {!trace || active.length === 0 ? (
          <p className="p-3 text-center text-xs text-zinc-600">
            {trace ? 'No active calls at this step.' : 'Load a trace to see the live call stack.'}
          </p>
        ) : (
          <>
            {overflow > 0 && (
              <div className="px-2 py-1 text-center text-[10px] text-zinc-600">… {overflow} earlier frames …</div>
            )}
            {rows.map((n, i) => {
              const isCurrent = i === rows.length - 1;
              return (
                <button
                  key={n.id}
                  onClick={() => select(n.id)}
                  className={clsx(
                    'mono flex w-full items-center gap-2 rounded px-2 py-1 text-left text-xs',
                    isCurrent ? 'bg-amber-500/10 text-amber-200' : 'text-zinc-400 hover:bg-zinc-900',
                    n.id === selectedId && 'outline outline-1 outline-zinc-500',
                  )}
                >
                  <span className="w-8 shrink-0 text-right text-zinc-600">{n.depth}</span>
                  <span className="truncate">
                    {n.method}({compactArgs(n, 20)})
                  </span>
                </button>
              );
            })}
          </>
        )}
      </div>
    </div>
  );
}
