import { create } from 'zustand';
import { parseTrace, type ParsedTrace, type TraceFile } from './lib/trace';

interface VizState {
  trace: ParsedTrace | null;
  traceName: string;
  step: number;
  playing: boolean;
  /** Playback speed in trace events per second. */
  speed: number;
  selectedId: number | null;
  showDupes: boolean;
  /** Node ids whose subtree is collapsed. */
  collapsed: Record<number, true>;
  availableTraces: string[];

  loadTraceFile: (tf: TraceFile, name: string) => void;
  setStep: (n: number) => void;
  setPlaying: (b: boolean) => void;
  setSpeed: (n: number) => void;
  select: (id: number | null) => void;
  toggleDupes: () => void;
  toggleCollapse: (id: number) => void;
  resetCollapsed: () => void;
  setAvailableTraces: (t: string[]) => void;
}

const AUTO_COLLAPSE_BUDGET = 1200;

/**
 * For large trees, pre-collapse the deepest affordable level so the initial render
 * stays interactive. Nodes at the cutoff depth render as collapsed stubs.
 */
function autoCollapse(trace: ParsedTrace): Record<number, true> {
  const collapsed: Record<number, true> = {};
  if (trace.nodeCount <= AUTO_COLLAPSE_BUDGET) return collapsed;

  const byDepth = new Map<number, number>();
  trace.byId.forEach((n) => byDepth.set(n.depth, (byDepth.get(n.depth) ?? 0) + 1));

  let cumulative = 0;
  let cutoff = 0;
  for (const d of [...byDepth.keys()].sort((a, b) => a - b)) {
    const next = cumulative + byDepth.get(d)!;
    if (next > AUTO_COLLAPSE_BUDGET) break;
    cumulative = next;
    cutoff = d;
  }
  trace.byId.forEach((n) => {
    if (n.depth === cutoff && n.children.length > 0) collapsed[n.id] = true;
  });
  return collapsed;
}

export const useStore = create<VizState>((set, get) => ({
  trace: null,
  traceName: '',
  step: 0,
  playing: false,
  speed: 120,
  selectedId: null,
  showDupes: false,
  collapsed: {},
  availableTraces: [],

  loadTraceFile: (tf, name) => {
    const trace = parseTrace(tf);
    set({
      trace,
      traceName: name,
      step: trace.maxSeq, // show the finished tree by default
      playing: false,
      selectedId: null,
      collapsed: autoCollapse(trace),
    });
  },
  setStep: (n) => {
    const { trace } = get();
    const max = trace ? trace.maxSeq : 0;
    set({ step: Math.max(0, Math.min(max, n)) });
  },
  setPlaying: (b) => set({ playing: b }),
  setSpeed: (n) => set({ speed: n }),
  select: (id) => set({ selectedId: id }),
  toggleDupes: () => set((s) => ({ showDupes: !s.showDupes })),
  toggleCollapse: (id) =>
    set((s) => {
      const collapsed = { ...s.collapsed };
      if (collapsed[id]) delete collapsed[id];
      else collapsed[id] = true;
      return { collapsed };
    }),
  resetCollapsed: () => {
    const { trace } = get();
    set({ collapsed: trace ? autoCollapse(trace) : {} });
  },
  setAvailableTraces: (t) => set({ availableTraces: t }),
}));
