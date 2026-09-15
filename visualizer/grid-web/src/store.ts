import { create } from 'zustand';
import {
  parseTrace,
  nextSliceStep,
  prevSliceStep,
  type ParsedTrace,
} from './lib/trace';

const STEP_SPEEDS = [4, 15, 30, 120];
const SLICE_SPEEDS = [1, 2, 4, 8];

export type PlaybackMode = 'step' | 'slice';

interface VizState {
  trace: ParsedTrace | null;
  traceName: string;
  step: number;
  playing: boolean;
  /** Playback speed in steps (or slices) per second. */
  speed: number;
  mode: PlaybackMode;
  pinned: { r: number; c: number } | null;
  availableTraces: string[];

  loadTraceFile: (raw: unknown, name: string) => void;
  setStep: (n: number) => void;
  setPlaying: (b: boolean) => void;
  setSpeed: (n: number) => void;
  setMode: (m: PlaybackMode) => void;
  pinCell: (cell: { r: number; c: number } | null) => void;
  stepBy: (dir: 1 | -1) => void;
  jumpSlice: (dir: 1 | -1) => void;
  setAvailableTraces: (t: string[]) => void;
}

export const useStore = create<VizState>((set, get) => ({
  trace: null,
  traceName: '',
  step: 0,
  playing: false,
  speed: 4,
  mode: 'slice',
  pinned: null,
  availableTraces: [],

  loadTraceFile: (raw, name) => {
    const trace = parseTrace(raw);
    set({
      trace,
      traceName: name,
      step: 0,
      playing: false,
      pinned: null,
    });
  },
  setStep: (n) => {
    const { trace } = get();
    const max = trace ? trace.maxSeq : 0;
    set({ step: Math.max(0, Math.min(max, n)) });
  },
  setPlaying: (b) => set({ playing: b }),
  setSpeed: (n) => set({ speed: n }),
  setMode: (m) => {
    const { trace, step, speed } = get();
    const speeds = m === 'slice' ? SLICE_SPEEDS : STEP_SPEEDS;
    const nextSpeed = speeds.includes(speed) ? speed : speeds[0];
    if (!trace) {
      set({ mode: m, speed: nextSpeed });
      return;
    }
    if (m === 'slice') {
      const idx = trace.sliceSteps.reduce((acc, s, i) => (s <= step ? i : acc), 0);
      set({ mode: m, speed: nextSpeed, step: trace.sliceSteps[idx] ?? 0 });
    } else {
      set({ mode: m, speed: nextSpeed });
    }
  },
  pinCell: (cell) =>
    set((s) => {
      if (cell && s.pinned && s.pinned.r === cell.r && s.pinned.c === cell.c) {
        return { pinned: null };
      }
      return { pinned: cell };
    }),
  stepBy: (dir) => {
    const { trace, step, mode } = get();
    if (!trace) return;
    if (mode === 'slice') {
      const next = dir === 1 ? nextSliceStep(trace, step) : prevSliceStep(trace, step);
      set({ step: next });
      return;
    }
    set({ step: Math.max(0, Math.min(trace.maxSeq, step + dir)) });
  },
  jumpSlice: (dir) => {
    const { trace, step } = get();
    if (!trace) return;
    const next = dir === 1 ? nextSliceStep(trace, step) : prevSliceStep(trace, step);
    set({ step: next });
  },
  setAvailableTraces: (t) => set({ availableTraces: t }),
}));
