import { useEffect } from 'react';
import { Pause, Play, SkipBack, SkipForward, StepBack, StepForward } from 'lucide-react';
import clsx from 'clsx';
import { useStore } from '../store';

const SPEEDS = [30, 120, 600, 3000];

const btn =
  'rounded-md p-2 text-zinc-300 hover:bg-zinc-800 hover:text-white disabled:opacity-30 disabled:hover:bg-transparent disabled:hover:text-zinc-300';

export default function PlaybackBar() {
  const trace = useStore((s) => s.trace);
  const step = useStore((s) => s.step);
  const playing = useStore((s) => s.playing);
  const speed = useStore((s) => s.speed);
  const setStep = useStore((s) => s.setStep);
  const setPlaying = useStore((s) => s.setPlaying);
  const setSpeed = useStore((s) => s.setSpeed);

  // requestAnimationFrame playback loop with a fractional-step accumulator.
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
        const max = st.trace?.maxSeq ?? 0;
        const next = Math.min(max, st.step + advance);
        st.setStep(next);
        if (next >= max) st.setPlaying(false);
      }
      raf = requestAnimationFrame(tick);
    };
    raf = requestAnimationFrame(tick);
    return () => cancelAnimationFrame(raf);
  }, [playing]);

  // Global keyboard shortcuts.
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
          st.setStep(st.step - amount);
          e.preventDefault();
          break;
        case 'ArrowRight':
          st.setStep(st.step + amount);
          e.preventDefault();
          break;
        case ' ':
          st.setPlaying(!st.playing);
          e.preventDefault();
          break;
        case 'Home':
          st.setStep(0);
          break;
        case 'End':
          st.setStep(st.trace.maxSeq);
          break;
      }
    };
    window.addEventListener('keydown', onKey);
    return () => window.removeEventListener('keydown', onKey);
  }, []);

  const max = trace?.maxSeq ?? 0;
  const disabled = !trace;

  return (
    <div className="flex h-14 items-center gap-2 border-t border-zinc-800 bg-zinc-950/95 px-4">
      <button className={btn} disabled={disabled} onClick={() => setStep(0)} title="Jump to start (Home)">
        <SkipBack size={16} />
      </button>
      <button className={btn} disabled={disabled} onClick={() => setStep(step - 1)} title="Step back (←)">
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
      <button className={btn} disabled={disabled} onClick={() => setStep(step + 1)} title="Step forward (→)">
        <StepForward size={16} />
      </button>
      <button className={btn} disabled={disabled} onClick={() => setStep(max)} title="Jump to end (End)">
        <SkipForward size={16} />
      </button>

      <input
        type="range"
        min={0}
        max={max}
        value={step}
        disabled={disabled}
        onChange={(e) => setStep(Number(e.target.value))}
        className="mx-2 h-1 flex-1 cursor-pointer appearance-none rounded-full bg-zinc-800"
      />

      <span className="mono w-36 text-right text-xs text-zinc-400">
        {trace ? `${step} / ${max}` : 'no trace'}
      </span>
      <select
        value={speed}
        disabled={disabled}
        onChange={(e) => setSpeed(Number(e.target.value))}
        className="rounded-md border border-zinc-700 bg-zinc-900 px-2 py-1 text-xs text-zinc-300"
        title="Playback speed (events/sec)"
      >
        {SPEEDS.map((s) => (
          <option key={s} value={s}>
            {s}/s
          </option>
        ))}
      </select>
    </div>
  );
}
