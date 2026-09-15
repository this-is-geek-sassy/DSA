import { useMemo } from 'react';
import clsx from 'clsx';
import { useStore } from '../store';
import { fmtVal, orderedVars, stateAt } from '../lib/trace';

export default function VarsPanel() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);

  const { rows, changed } = useMemo(() => {
    if (!trace) return { rows: [] as [string, unknown][], changed: new Set<string>() };
    const cur = stateAt(trace, step);
    const prev = step > 0 ? stateAt(trace, step - 1) : null;
    const rows = orderedVars(cur);
    const changed = new Set<string>();
    if (prev) {
      const prevMap = Object.fromEntries(orderedVars(prev));
      for (const [k, v] of rows) {
        if (fmtVal(prevMap[k], 80) !== fmtVal(v, 80)) changed.add(k);
      }
    }
    return { rows, changed };
  }, [trace, step]);

  return (
    <div className="flex min-h-0 flex-1 flex-col border-b border-zinc-800">
      <div className="border-b border-zinc-800 px-4 py-2.5 text-xs font-semibold uppercase tracking-wider text-zinc-500">
        Variables
      </div>
      <div className="min-h-0 flex-1 overflow-y-auto">
        {rows.length === 0 ? (
          <p className="p-4 text-xs text-zinc-600">No variables at this step.</p>
        ) : (
          <div className="overflow-hidden">
            {rows.map(([k, v], i) => (
              <div
                key={k}
                className={clsx(
                  'flex gap-2 px-3 py-1.5 text-xs',
                  i % 2 === 0 && 'bg-zinc-900/60',
                  changed.has(k) && 'bg-amber-500/10',
                )}
              >
                <span className={clsx('mono shrink-0', changed.has(k) ? 'text-amber-300' : 'text-zinc-400')}>
                  {k}
                </span>
                <span className={clsx('mono ml-auto break-all text-right', changed.has(k) ? 'text-amber-100' : 'text-zinc-100')}>
                  {fmtVal(v, 400)}
                </span>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
