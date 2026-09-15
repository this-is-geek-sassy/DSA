import { useMemo } from 'react';
import clsx from 'clsx';
import { useStore } from '../store';
import { fmtCell, stateAt, type Cell } from '../lib/trace';

function Chip({ cell, active, onClick }: { cell: Cell; active: boolean; onClick: () => void }) {
  return (
    <button
      onClick={onClick}
      className={clsx(
        'mono rounded-md border px-1.5 py-0.5 text-[11px]',
        active
          ? 'border-cyan-500/60 bg-cyan-500/15 text-cyan-200'
          : 'border-zinc-700 bg-zinc-900 text-zinc-300 hover:border-zinc-500',
      )}
    >
      {fmtCell(cell)}
    </button>
  );
}

export default function QueuePanel() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);
  const pinned = useStore((s) => s.pinned);
  const pinCell = useStore((s) => s.pinCell);
  const state = useMemo(() => (trace ? stateAt(trace, step) : null), [trace, step]);

  return (
    <div className="flex shrink-0 flex-col border-b border-zinc-800">
      <div className="border-b border-zinc-800 px-4 py-2.5 text-xs font-semibold uppercase tracking-wider text-zinc-500">
        Queues
      </div>
      <div className="space-y-3 px-3 py-3">
        <div>
          <div className="mb-1.5 flex items-baseline justify-between text-[10px] uppercase tracking-wider text-zinc-500">
            <span>Processing this minute</span>
            <span className="mono text-zinc-600">{state?.currentLayer.length ?? 0}</span>
          </div>
          <div className="flex flex-wrap gap-1">
            {(state?.currentLayer.length ?? 0) === 0 ? (
              <span className="text-[11px] text-zinc-600">empty</span>
            ) : (
              state!.currentLayer.map((cell) => (
                <Chip
                  key={`${cell.r},${cell.c}`}
                  cell={cell}
                  active={pinned?.r === cell.r && pinned?.c === cell.c}
                  onClick={() => pinCell(cell)}
                />
              ))
            )}
          </div>
        </div>
        <div>
          <div className="mb-1.5 flex items-baseline justify-between text-[10px] uppercase tracking-wider text-zinc-500">
            <span>Rotting next minute</span>
            <span className="mono text-zinc-600">{state?.nextLayer.length ?? 0}</span>
          </div>
          <div className="flex flex-wrap gap-1">
            {(state?.nextLayer.length ?? 0) === 0 ? (
              <span className="text-[11px] text-zinc-600">empty</span>
            ) : (
              state!.nextLayer.map((cell) => (
                <Chip
                  key={`${cell.r},${cell.c}`}
                  cell={cell}
                  active={pinned?.r === cell.r && pinned?.c === cell.c}
                  onClick={() => pinCell(cell)}
                />
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
