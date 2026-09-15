import { useEffect, useMemo, useRef, useState } from 'react';
import { useStore } from '../store';
import { cellKey, sameCell, stateAt, type Cell } from '../lib/trace';

const FILL: Record<number, string> = {
  0: '#27272a',
  1: '#14532d',
  2: '#7c2d12',
};

const LABEL: Record<string, string> = {
  '0': 'empty',
  '1': 'fresh',
  '2': 'rotten',
};

function inSet(list: Cell[], r: number, c: number): boolean {
  return list.some((x) => x.r === r && x.c === c);
}

export default function GridCanvas() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);
  const pinned = useStore((s) => s.pinned);
  const pinCell = useStore((s) => s.pinCell);
  const wrap = useRef<HTMLDivElement>(null);
  const [box, setBox] = useState({ w: 480, h: 480 });

  useEffect(() => {
    const el = wrap.current;
    if (!el) return;
    const ro = new ResizeObserver((entries) => {
      const cr = entries[0]?.contentRect;
      if (cr) setBox({ w: cr.width, h: cr.height });
    });
    ro.observe(el);
    return () => ro.disconnect();
  }, []);

  const state = useMemo(() => (trace ? stateAt(trace, step) : null), [trace, step]);

  if (!trace || !state) return null;

  const rows = trace.meta.rows;
  const cols = trace.meta.cols;
  const legend = trace.meta.legend ?? LABEL;
  const pad = 36;
  const gap = 4;
  const usableW = Math.max(40, box.w - pad * 2);
  const usableH = Math.max(40, box.h - pad * 2 - 36);
  const cell = Math.max(
    18,
    Math.min(72, Math.floor(Math.min(usableW / Math.max(cols, 1), usableH / Math.max(rows, 1)) - gap)),
  );
  const gridW = cols * (cell + gap) - gap;
  const gridH = rows * (cell + gap) - gap;
  const ox = (box.w - gridW) / 2;
  const oy = Math.max(28, (box.h - 36 - gridH) / 2);

  const cellOrigin = (r: number, c: number) => ({
    x: ox + c * (cell + gap),
    y: oy + r * (cell + gap),
  });
  const cellCenter = (r: number, c: number) => {
    const o = cellOrigin(r, c);
    return { x: o.x + cell / 2, y: o.y + cell / 2 };
  };

  const probe = state.probe;
  const from = state.probeFrom;
  let arrow: { x1: number; y1: number; x2: number; y2: number } | null = null;
  if (probe && from) {
    const a = cellCenter(from.r, from.c);
    const b = cellCenter(probe.r, probe.c);
    arrow = { x1: a.x, y1: a.y, x2: b.x, y2: b.y };
  }

  return (
    <div ref={wrap} className="relative h-full w-full min-h-0">
      <div className="pointer-events-none absolute left-4 top-3 flex items-center gap-2">
        <span className="rounded-md border border-zinc-800 bg-zinc-950/80 px-2 py-1 font-mono text-[11px] text-zinc-400">
          minute {state.minute}
        </span>
        {state.event && (
          <span className="rounded-md border border-zinc-800 bg-zinc-950/80 px-2 py-1 font-mono text-[11px] text-zinc-300">
            {state.event.t}
            {state.probeAction === 'skip' && state.skipWhy ? ` · ${state.skipWhy}` : ''}
          </span>
        )}
      </div>
      <svg width={box.w} height={box.h} className="block h-full w-full">
        <defs>
          <marker id="probe-arrow" viewBox="0 0 10 10" refX="8" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
            <path d="M 0 0 L 10 5 L 0 10 z" fill="#facc15" />
          </marker>
        </defs>
        {Array.from({ length: rows }, (_, r) =>
          Array.from({ length: cols }, (_, c) => {
            const v = state.grid[r]?.[c] ?? 0;
            const { x, y } = cellOrigin(r, c);
            const isCurrent = sameCell(state.current, r, c);
            const isProbe = sameCell(state.probe, r, c);
            const isPinned = pinned != null && pinned.r === r && pinned.c === c;
            const inCurrent = inSet(state.currentLayer, r, c);
            const inNext = inSet(state.nextLayer, r, c);
            const justRotted = inSet(state.rottedThisSlice, r, c);
            let stroke = '#3f3f46';
            let strokeW = 1;
            let dash: string | undefined;
            if (inCurrent) {
              stroke = '#fb923c';
              strokeW = 2;
            }
            if (inNext) {
              stroke = '#fdba74';
              strokeW = 2;
              dash = '4 3';
            }
            if (justRotted) {
              stroke = '#fdba74';
              strokeW = 3;
            }
            if (isProbe) {
              stroke = '#facc15';
              strokeW = 3;
              dash = state.probeAction === 'skip' ? '3 3' : undefined;
            }
            if (isCurrent) {
              stroke = '#22d3ee';
              strokeW = 3;
              dash = undefined;
            }
            if (isPinned) {
              stroke = '#fafafa';
              strokeW = 2;
              dash = '5 3';
            }
            const fill = isCurrent ? '#155e75' : (FILL[v] ?? FILL[0]);
            const label = legend[String(v)] ?? String(v);
            return (
              <g
                key={cellKey(r, c)}
                onClick={() => pinCell({ r, c })}
                className="cursor-pointer"
              >
                <rect
                  x={x}
                  y={y}
                  width={cell}
                  height={cell}
                  rx={6}
                  fill={fill}
                  stroke={stroke}
                  strokeWidth={strokeW}
                  strokeDasharray={dash}
                />
                <text
                  x={x + cell / 2}
                  y={y + cell / 2 - (cell >= 36 ? 6 : 0)}
                  textAnchor="middle"
                  dominantBaseline="central"
                  fill={v === 1 ? '#86efac' : v === 2 ? '#fed7aa' : '#a1a1aa'}
                  fontSize={cell >= 40 ? 13 : 10}
                  fontFamily="ui-monospace, SFMono-Regular, Menlo, monospace"
                >
                  {cell >= 28 ? label : v}
                </text>
                {cell >= 36 && (
                  <text
                    x={x + cell / 2}
                    y={y + cell / 2 + 10}
                    textAnchor="middle"
                    fill="#71717a"
                    fontSize={9}
                    fontFamily="ui-monospace, SFMono-Regular, Menlo, monospace"
                  >
                    {r},{c}
                  </text>
                )}
              </g>
            );
          }),
        )}
        {arrow && (
          <line
            x1={arrow.x1}
            y1={arrow.y1}
            x2={arrow.x2}
            y2={arrow.y2}
            stroke="#facc15"
            strokeWidth={2}
            markerEnd="url(#probe-arrow)"
            pointerEvents="none"
            opacity={0.9}
          />
        )}
      </svg>
      <div className="pointer-events-none absolute bottom-3 left-0 right-0 flex justify-center gap-3 text-[10px] text-zinc-500">
        <span className="flex items-center gap-1.5">
          <span className="inline-block h-2.5 w-2.5 rounded-sm" style={{ background: FILL[0] }} /> empty
        </span>
        <span className="flex items-center gap-1.5">
          <span className="inline-block h-2.5 w-2.5 rounded-sm" style={{ background: FILL[1] }} /> fresh
        </span>
        <span className="flex items-center gap-1.5">
          <span className="inline-block h-2.5 w-2.5 rounded-sm" style={{ background: FILL[2] }} /> rotten
        </span>
        <span className="flex items-center gap-1.5">
          <span className="inline-block h-2.5 w-2.5 rounded-sm border-2 border-cyan-400" /> current
        </span>
        <span className="flex items-center gap-1.5">
          <span className="inline-block h-2.5 w-2.5 rounded-sm border-2 border-orange-400" /> layer
        </span>
        <span className="flex items-center gap-1.5">
          <span className="inline-block h-2.5 w-2.5 rounded-sm border-2 border-dashed border-orange-300" /> next
        </span>
      </div>
    </div>
  );
}
