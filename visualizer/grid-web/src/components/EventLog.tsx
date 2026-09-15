import { useEffect, useMemo, useRef } from 'react';
import clsx from 'clsx';
import { useStore } from '../store';
import { eventLabel, eventsForCell } from '../lib/trace';

const TONE: Record<string, string> = {
  start: 'text-zinc-300',
  pop: 'text-cyan-300',
  skip: 'text-zinc-500',
  infect: 'text-orange-300',
  tick: 'text-emerald-300',
  done: 'text-violet-300',
};

export default function EventLog() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);
  const pinned = useStore((s) => s.pinned);
  const setStep = useStore((s) => s.setStep);
  const listRef = useRef<HTMLDivElement>(null);
  const activeRef = useRef<HTMLButtonElement>(null);

  const events = useMemo(() => {
    if (!trace) return [];
    if (pinned) return eventsForCell(trace, pinned.r, pinned.c);
    return trace.events;
  }, [trace, pinned]);

  useEffect(() => {
    activeRef.current?.scrollIntoView({ block: 'nearest' });
  }, [step, events]);

  return (
    <div className="flex min-h-0 flex-1 flex-col">
      <div className="flex items-center justify-between border-b border-zinc-800 px-4 py-2.5">
        <span className="text-xs font-semibold uppercase tracking-wider text-zinc-500">Events</span>
        {pinned && (
          <span className="mono text-[10px] text-zinc-500">
            cell ({pinned.r}, {pinned.c})
          </span>
        )}
      </div>
      <div ref={listRef} className="min-h-0 flex-1 overflow-y-auto">
        {events.length === 0 ? (
          <p className="p-4 text-xs text-zinc-600">No events{pinned ? ' for this cell' : ''}.</p>
        ) : (
          events.map((ev) => {
            const active = ev.s === step;
            return (
              <button
                key={ev.s}
                ref={active ? activeRef : undefined}
                onClick={() => setStep(ev.s)}
                className={clsx(
                  'flex w-full items-baseline gap-2 px-3 py-1 text-left text-[11px] hover:bg-zinc-800/80',
                  active && 'bg-emerald-500/10',
                )}
              >
                <span className="mono w-8 shrink-0 text-zinc-600">{ev.s}</span>
                <span className={clsx('mono', TONE[ev.t] ?? 'text-zinc-300')}>{eventLabel(ev)}</span>
              </button>
            );
          })
        )}
      </div>
    </div>
  );
}
