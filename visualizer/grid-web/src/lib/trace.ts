// Grid BFS trace types: event stream -> reconstructed grid / queue / vars at a step.

export type EventType = 'start' | 'pop' | 'skip' | 'infect' | 'tick' | 'done';

export interface Cell {
  r: number;
  c: number;
}

export interface TraceMeta {
  program: string;
  kind?: string;
  rows: number;
  cols: number;
  legend?: Record<string, string>;
  result?: number | null;
  createdAt?: string;
  events?: number;
  maxEvents?: number;
  truncated?: boolean;
}

export interface GridEvent {
  t: EventType;
  s: number;
  r?: number;
  c?: number;
  from?: [number, number];
  why?: string;
  minute?: number;
  frontier?: [number, number][];
  rotted?: [number, number][];
  vars?: Record<string, unknown>;
  result?: number;
}

export interface TraceFile {
  meta: TraceMeta;
  initialGrid: number[][];
  events: GridEvent[];
}

export interface ParsedTrace {
  meta: TraceMeta;
  initialGrid: number[][];
  events: GridEvent[];
  bySeq: Map<number, GridEvent>;
  maxSeq: number;
  /** Sequence numbers that bound a time slice: start, each tick, done. */
  sliceSteps: number[];
}

export interface TraceState {
  grid: number[][];
  minute: number;
  current: Cell | null;
  probe: Cell | null;
  probeFrom: Cell | null;
  probeAction: 'skip' | 'infect' | null;
  skipWhy: string | null;
  vars: Record<string, unknown>;
  currentLayer: Cell[];
  nextLayer: Cell[];
  rottedThisSlice: Cell[];
  result: number | null;
  event: GridEvent | null;
}

export function cellKey(r: number, c: number): string {
  return `${r},${c}`;
}

export function sameCell(a: Cell | null, r: number, c: number): boolean {
  return a != null && a.r === r && a.c === c;
}

function asCell(p: [number, number] | number[] | undefined): Cell | null {
  if (!p || p.length < 2) return null;
  return { r: p[0], c: p[1] };
}

function asCells(pairs: [number, number][] | number[][] | undefined): Cell[] {
  if (!pairs) return [];
  return pairs.map((p) => ({ r: p[0], c: p[1] }));
}

function cloneGrid(grid: number[][]): number[][] {
  return grid.map((row) => row.slice());
}

function removeCell(list: Cell[], r: number, c: number): Cell[] {
  const i = list.findIndex((x) => x.r === r && x.c === c);
  if (i < 0) return list;
  return [...list.slice(0, i), ...list.slice(i + 1)];
}

function eventTouches(ev: GridEvent, r: number, c: number): boolean {
  if (ev.r === r && ev.c === c) return true;
  if (ev.from && ev.from[0] === r && ev.from[1] === c) return true;
  if (ev.frontier?.some((p) => p[0] === r && p[1] === c)) return true;
  if (ev.rotted?.some((p) => p[0] === r && p[1] === c)) return true;
  return false;
}

const EVENT_TYPES = new Set<EventType>(['start', 'pop', 'skip', 'infect', 'tick', 'done']);

export function parseTrace(raw: unknown): ParsedTrace {
  if (!raw || typeof raw !== 'object') {
    throw new Error('trace is not an object');
  }
  const obj = raw as Record<string, unknown>;
  const metaRaw = (obj.meta ?? {}) as Record<string, unknown>;
  const initialGrid = Array.isArray(obj.initialGrid) ? (obj.initialGrid as number[][]) : [];
  const rows = Number(metaRaw.rows ?? initialGrid.length ?? 0);
  const cols = Number(metaRaw.cols ?? initialGrid[0]?.length ?? 0);
  const eventsIn = Array.isArray(obj.events) ? (obj.events as GridEvent[]) : [];
  const events = eventsIn
    .filter((ev) => ev && EVENT_TYPES.has(ev.t) && typeof ev.s === 'number')
    .slice()
    .sort((a, b) => a.s - b.s);

  const bySeq = new Map<number, GridEvent>();
  let maxSeq = 0;
  const sliceSteps: number[] = [];
  for (const ev of events) {
    bySeq.set(ev.s, ev);
    if (ev.s > maxSeq) maxSeq = ev.s;
    if (ev.t === 'start' || ev.t === 'tick' || ev.t === 'done') {
      sliceSteps.push(ev.s);
    }
  }
  if (sliceSteps.length === 0 || sliceSteps[0] !== 0) {
    sliceSteps.unshift(0);
  }

  const meta: TraceMeta = {
    program: String(metaRaw.program ?? 'grid'),
    kind: metaRaw.kind != null ? String(metaRaw.kind) : undefined,
    rows,
    cols,
    legend: (metaRaw.legend as Record<string, string> | undefined) ?? {
      '0': 'empty',
      '1': 'fresh',
      '2': 'rotten',
    },
    result: typeof metaRaw.result === 'number' ? metaRaw.result : null,
    createdAt: metaRaw.createdAt != null ? String(metaRaw.createdAt) : undefined,
    events: typeof metaRaw.events === 'number' ? metaRaw.events : events.length,
    maxEvents: typeof metaRaw.maxEvents === 'number' ? metaRaw.maxEvents : undefined,
    truncated: Boolean(metaRaw.truncated),
  };

  return { meta, initialGrid, events, bySeq, maxSeq, sliceSteps };
}

/** Reconstruct grid, queues, and variables at playback step `step` (inclusive). */
export function stateAt(trace: ParsedTrace, step: number): TraceState {
  const grid = cloneGrid(trace.initialGrid);
  let minute = 0;
  let current: Cell | null = null;
  let probe: Cell | null = null;
  let probeFrom: Cell | null = null;
  let probeAction: 'skip' | 'infect' | null = null;
  let skipWhy: string | null = null;
  let vars: Record<string, unknown> = {};
  let currentLayer: Cell[] = [];
  let nextLayer: Cell[] = [];
  let rottedThisSlice: Cell[] = [];
  let result: number | null = trace.meta.result ?? null;
  let event: GridEvent | null = null;

  for (const ev of trace.events) {
    if (ev.s > step) break;
    event = ev;
    if (ev.vars) vars = { ...vars, ...ev.vars };
    if (ev.t !== 'tick' && typeof ev.minute === 'number') minute = ev.minute;

    switch (ev.t) {
      case 'start':
        currentLayer = asCells(ev.frontier);
        nextLayer = [];
        rottedThisSlice = [];
        break;
      case 'pop':
        current = { r: ev.r ?? 0, c: ev.c ?? 0 };
        probe = null;
        probeFrom = null;
        probeAction = null;
        skipWhy = null;
        currentLayer = removeCell(currentLayer, current.r, current.c);
        break;
      case 'skip':
        probe = { r: ev.r ?? 0, c: ev.c ?? 0 };
        probeFrom = asCell(ev.from);
        probeAction = 'skip';
        skipWhy = ev.why ?? null;
        break;
      case 'infect': {
        const cell = { r: ev.r ?? 0, c: ev.c ?? 0 };
        if (cell.r >= 0 && cell.r < grid.length && cell.c >= 0 && cell.c < (grid[cell.r]?.length ?? 0)) {
          grid[cell.r][cell.c] = 2;
        }
        probe = cell;
        probeFrom = asCell(ev.from);
        probeAction = 'infect';
        skipWhy = null;
        nextLayer = [...nextLayer, cell];
        rottedThisSlice = [...rottedThisSlice, cell];
        break;
      }
      case 'tick':
        minute = ev.minute ?? minute;
        rottedThisSlice = asCells(ev.rotted);
        currentLayer = nextLayer;
        nextLayer = [];
        current = null;
        probe = null;
        probeFrom = null;
        probeAction = null;
        skipWhy = null;
        break;
      case 'done':
        if (typeof ev.result === 'number') result = ev.result;
        probe = null;
        probeFrom = null;
        probeAction = null;
        skipWhy = null;
        break;
    }
  }

  return {
    grid,
    minute,
    current,
    probe,
    probeFrom,
    probeAction,
    skipWhy,
    vars,
    currentLayer,
    nextLayer,
    rottedThisSlice,
    result,
    event,
  };
}

export function eventsForCell(trace: ParsedTrace, r: number, c: number): GridEvent[] {
  return trace.events.filter((ev) => eventTouches(ev, r, c));
}

export function sliceIndexFor(trace: ParsedTrace, step: number): number {
  const steps = trace.sliceSteps;
  let idx = 0;
  for (let i = 0; i < steps.length; i++) {
    if (steps[i] <= step) idx = i;
    else break;
  }
  return idx;
}

export function nextSliceStep(trace: ParsedTrace, step: number): number {
  for (const s of trace.sliceSteps) {
    if (s > step) return s;
  }
  return trace.maxSeq;
}

export function prevSliceStep(trace: ParsedTrace, step: number): number {
  let prev = 0;
  for (const s of trace.sliceSteps) {
    if (s < step) prev = s;
    else break;
  }
  return prev;
}

export function fmtVal(v: unknown, max = 48): string {
  let s: string;
  if (v === null || v === undefined) s = 'null';
  else if (typeof v === 'string') s = JSON.stringify(v.length > max ? v.slice(0, max) + '…' : v);
  else if (typeof v === 'number' || typeof v === 'boolean') s = String(v);
  else s = JSON.stringify(v) ?? String(v);
  if (s.length > max + 4) s = s.slice(0, max + 4) + '…';
  return s;
}

export function fmtCell(cell: Cell): string {
  return `(${cell.r}, ${cell.c})`;
}

export function eventLabel(ev: GridEvent): string {
  switch (ev.t) {
    case 'start':
      return `start · ${(ev.frontier?.length ?? 0)} rotten`;
    case 'pop':
      return `pop (${ev.r}, ${ev.c})`;
    case 'skip':
      return `skip (${ev.r}, ${ev.c}) ${ev.why ?? ''}`.trim();
    case 'infect':
      return `infect (${ev.r}, ${ev.c})`;
    case 'tick':
      return `tick · minute ${ev.minute ?? '?'}`;
    case 'done':
      return `done · ${ev.result ?? ''}`;
  }
}

const PINNED_VAR_KEYS = [
  'minute',
  'moveCount',
  'noFreshOranges',
  'nodeLeftInLayer',
  'nodesInNextLayer',
  'r',
  'c',
];

/** Vars for the inspector: pinned BFS counters first, then the rest. */
export function orderedVars(state: TraceState): [string, unknown][] {
  const merged: Record<string, unknown> = {
    minute: state.minute,
    ...state.vars,
  };
  if (state.current) {
    if (merged.r === undefined) merged.r = state.current.r;
    if (merged.c === undefined) merged.c = state.current.c;
  }
  const seen = new Set<string>();
  const out: [string, unknown][] = [];
  for (const k of PINNED_VAR_KEYS) {
    if (k in merged) {
      out.push([k, merged[k]]);
      seen.add(k);
    }
  }
  for (const [k, v] of Object.entries(merged)) {
    if (!seen.has(k)) out.push([k, v]);
  }
  return out;
}
