import { useEffect } from 'react';
import {
  Pause,
  Play,
  SkipBack,
  SkipForward,
  StepBack,
  StepForward,
  ChevronsLeft,
  ChevronsRight,
} from 'lucide-react';
import clsx from 'clsx';
import { useStore } from '../store';
import { sliceIndexFor } from '../lib/trace';

const STEP_SPEEDS = [4, 15, 30, 120];
const SLICE_SPEEDS = [1, 2, 4, 8];

const btn =
  'rounded-md p-2 text-zinc-300 hover:bg-zinc-800 hover:text-white disabled:opacity-30 disabled:hover:bg-transparent disabled:hover:text-zinc-300';

export default function PlaybackBar() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);
  const playing = useStore((s) => s.playing);
  const speed = useStore((s) => s.speed);
  const mode = useStore((s) => s.mode);
  const setStep = useStore((s) => s.setStep);
  const setPlaying = useStore((s) => s.setPlaying);
  const setSpeed = useStore((s) => s.setSpeed);
  const setMode = useStore((s) => s.setMode);
  const stepBy = useStore((s) => s.stepBy);
  const jumpSlice = useStore((s) => s.jumpSlice);

  useEffect(() => {
    if (!playing) return;
    let raf = 0;
    let last = performance.now();
    let acc = 0;
    const tick = (now: number) => {
      const dt = now - last;
      last = now;
      const st = useStore.getState();
      acc += (st.speed * dt) / 1000;
      if (acc >= 1) {
        const advance = Math.floor(acc);
        acc -= advance;
        for (let i = 0; i < advance; i++) {
          const cur = useStore.getState();
          if (!cur.trace) break;
          const max = cur.trace.maxSeq;
          if (cur.step >= max) {
            cur.setPlaying(false);
            break;
          }
          const before = cur.step;
          cur.stepBy(1);
          const after = useStore.getState().step;
          if (after === before || after >= max) {
            useStore.getState().setPlaying(false);
            break;
          }
        }
      }
      raf = requestAnimationFrame(tick);
    };
    raf = requestAnimationFrame(tick);
    return () => cancelAnimationFrame(raf);
  }, [playing]);

  useEffect(() => {
    const onKey = (e: KeyboardEvent) => {
      const el = e.target as HTMLElement | null;
      if (el && (el.tagName === 'TEXTAREA' || (el.tagName === 'INPUT' && (el as HTMLInputElement).type !== 'range'))) {
        return;
      }
      const st = useStore.getState();
      if (!st.trace) return;
      const amount = e.shiftKey ? 25 : 1;
      switch (e.key) {
        case 'ArrowLeft':
          if (st.mode === 'slice' || amount === 1) st.stepBy(-1);
          else st.setStep(st.step - amount);
          e.preventDefault();
          break;
        case 'ArrowRight':
          if (st.mode === 'slice' || amount === 1) st.stepBy(1);
          else st.setStep(st.step + amount);
          e.preventDefault();
          break;
        case '[':
          st.jumpSlice(-1);
          e.preventDefault();
          break;
        case ']':
          st.jumpSlice(1);
          e.preventDefault();
          break;
        case ' ':
          st.setPlaying(!st.playing);
          e.preventDefault();
          break;
        case 'Home':
          st.setStep(0);
          e.preventDefault();
          break;
        case 'End':
          st.setStep(st.trace.maxSeq);
          e.preventDefault();
          break;
      }
    };
    window.addEventListener('keydown', onKey);
    return () => window.removeEventListener('keydown', onKey);
  }, []);

  const max = trace?.maxSeq ?? 0;
  const disabled = !trace;
  const speeds = mode === 'slice' ? SLICE_SPEEDS : STEP_SPEEDS;
  const sliceIdx = trace ? sliceIndexFor(trace, step) : 0;
  const sliceMax = trace ? Math.max(0, trace.sliceSteps.length - 1) : 0;

  return (
    <div className="flex h-14 items-center gap-2 border-t border-zinc-800 bg-zinc-950/95 px-4">
      <div className="flex rounded-md border border-zinc-800 p-0.5 text-[11px]">
        <button
          className={clsx(
            'rounded px-2 py-1',
            mode === 'slice' ? 'bg-emerald-500/15 text-emerald-300' : 'text-zinc-400 hover:text-zinc-200',
          )}
          disabled={disabled}
          onClick={() => setMode('slice')}
          title="Jump minute by minute"
        >
          Slice
        </button>
        <button
          className={clsx(
            'rounded px-2 py-1',
            mode === 'step' ? 'bg-emerald-500/15 text-emerald-300' : 'text-zinc-400 hover:text-zinc-200',
          )}
          disabled={disabled}
          onClick={() => setMode('step')}
          title="Every event"
        >
          Step
        </button>
      </div>

      <button className={btn} disabled={disabled} onClick={() => setStep(0)} title="Jump to start (Home)">
        <SkipBack size={16} />
      </button>
      <button className={btn} disabled={disabled} onClick={() => jumpSlice(-1)} title="Previous slice ([)">
        <ChevronsLeft size={16} />
      </button>
      <button className={btn} disabled={disabled} onClick={() => stepBy(-1)} title="Step back (←)">
        <StepBack size={16} />
      </button>
      <button
        className={clsx(btn, 'bg-emerald-500/10 text-emerald-300 hover:bg-emerald-500/20 hover:text-emerald-200')}
        disabled={disabled}
        onClick={() => {
          if (!playing && step >= max) setStep(0);
          setPlaying(!playing);
        }}
        title="Play / pause (Space)"
      >
        {playing ? <Pause size={16} /> : <Play size={16} />}
      </button>
      <button className={btn} disabled={disabled} onClick={() => stepBy(1)} title="Step forward (→)">
        <StepForward size={16} />
      </button>
      <button className={btn} disabled={disabled} onClick={() => jumpSlice(1)} title="Next slice (])">
        <ChevronsRight size={16} />
      </button>
      <button className={btn} disabled={disabled} onClick={() => setStep(max)} title="Jump to end (End)">
        <SkipForward size={16} />
      </button>

      <input
        type="range"
        min={0}
        max={mode === 'slice' ? sliceMax : max}
        value={mode === 'slice' ? sliceIdx : step}
        disabled={disabled}
        onChange={(e) => {
          const n = Number(e.target.value);
          if (mode === 'slice' && trace) {
            setStep(trace.sliceSteps[n] ?? 0);
          } else {
            setStep(n);
          }
        }}
        className="mx-2 h-1 flex-1 cursor-pointer appearance-none rounded-full bg-zinc-800"
      />

      <span className="mono w-40 text-right text-xs text-zinc-400">
        {trace
          ? mode === 'slice'
            ? `slice ${sliceIdx} / ${sliceMax} · ${step}`
            : `${step} / ${max}`
          : 'no trace'}
      </span>
      <select
        value={speeds.includes(speed) ? speed : speeds[0]}
        disabled={disabled}
        onChange={(e) => setSpeed(Number(e.target.value))}
        className="rounded-md border border-zinc-700 bg-zinc-900 px-2 py-1 text-xs text-zinc-300"
        title={mode === 'slice' ? 'Slices per second' : 'Events per second'}
      >
        {speeds.map((s) => (
          <option key={s} value={s}>
            {s}/s
          </option>
        ))}
      </select>
    </div>
  );
}
