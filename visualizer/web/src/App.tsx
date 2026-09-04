import { useCallback, useEffect, useRef } from 'react';
import { FileUp, Layers, Network, TriangleAlert } from 'lucide-react';
import clsx from 'clsx';
import { useStore } from './store';
import type { TraceFile } from './lib/trace';
import TreeCanvas from './components/TreeCanvas';
import PlaybackBar from './components/PlaybackBar';
import NodePanel from './components/NodePanel';
import CallStackPanel from './components/CallStackPanel';

export default function App() {
  const trace = useStore((s) => s.trace);
  const traceName = useStore((s) => s.traceName);
  const availableTraces = useStore((s) => s.availableTraces);
  const showDupes = useStore((s) => s.showDupes);
  const loadTraceFile = useStore((s) => s.loadTraceFile);
  const toggleDupes = useStore((s) => s.toggleDupes);
  const setAvailableTraces = useStore((s) => s.setAvailableTraces);
  const fileInput = useRef<HTMLInputElement>(null);

  const loadNamed = useCallback(
    async (name: string) => {
      try {
        const r = await fetch(`traces/${name}`);
        if (!r.ok) throw new Error(String(r.status));
        loadTraceFile((await r.json()) as TraceFile, name);
      } catch (e) {
        console.error('failed to load trace', name, e);
      }
    },
    [loadTraceFile],
  );

  // Discover generated traces and auto-open one. Supports deep links:
  // ?trace=LCS3.json&step=42&dupes=1
  useEffect(() => {
    (async () => {
      try {
        const params = new URLSearchParams(window.location.search);
        const r = await fetch('traces/manifest.json');
        if (!r.ok) return;
        const m = (await r.json()) as { traces?: string[] };
        const names = m.traces ?? [];
        setAvailableTraces(names);
        if (names.length > 0 && !useStore.getState().trace) {
          const want = params.get('trace');
          const name = want && names.includes(want) ? want : names[0];
          void loadNamed(name).then(() => {
            const st = useStore.getState();
            const stepParam = params.get('step');
            if (stepParam !== null) st.setStep(Number(stepParam));
            if (params.get('dupes') === '1' && !st.showDupes) st.toggleDupes();
          });
        }
      } catch {
        // no manifest yet — the user can drop a trace file manually
      }
    })();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const onUpload = (file: File) => {
    file
      .text()
      .then((txt) => loadTraceFile(JSON.parse(txt) as TraceFile, file.name))
      .catch((e) => console.error('invalid trace file', e));
  };

  const picker = (
    <select
      value={traceName}
      onChange={(e) => e.target.value && void loadNamed(e.target.value)}
      className="max-w-52 rounded-md border border-zinc-700 bg-zinc-900 px-2 py-1.5 text-xs text-zinc-300"
      title="Pick a generated trace"
    >
      {!traceName && <option value="">Select trace…</option>}
      {availableTraces.map((n) => (
        <option key={n} value={n}>
          {n}
        </option>
      ))}
      {traceName && !availableTraces.includes(traceName) && <option value={traceName}>{traceName}</option>}
    </select>
  );

  const uploadBtn = (
    <>
      <input
        ref={fileInput}
        type="file"
        accept=".json,application/json"
        className="hidden"
        onChange={(e) => {
          const f = e.target.files?.[0];
          if (f) onUpload(f);
          e.target.value = '';
        }}
      />
      <button
        onClick={() => fileInput.current?.click()}
        title="Open a trace JSON file"
        className="flex items-center gap-1.5 rounded-md border border-zinc-700 bg-zinc-900 px-2.5 py-1.5 text-xs text-zinc-300 hover:bg-zinc-800 hover:text-white"
      >
        <FileUp size={14} /> Open trace…
      </button>
    </>
  );

  return (
    <div className="flex h-full flex-col bg-zinc-950 text-zinc-200">
      <header className="flex h-12 shrink-0 items-center gap-3 border-b border-zinc-800 px-4">
        <div className="flex items-center gap-2">
          <Network size={17} className="text-emerald-400" />
          <span className="text-sm font-semibold tracking-tight text-zinc-100">recviz</span>
        </div>
        {trace && (
          <span className="mono text-xs text-zinc-500">
            {trace.meta.program} · {trace.nodeCount.toLocaleString()} calls
          </span>
        )}
        {trace?.meta.truncated && (
          <span className="flex items-center gap-1 rounded-full bg-amber-500/10 px-2 py-0.5 text-[10px] text-amber-300">
            <TriangleAlert size={11} /> truncated
          </span>
        )}
        <div className="flex-1" />
        {picker}
        {uploadBtn}
        <button
          onClick={toggleDupes}
          disabled={!trace}
          title="Highlight duplicate subproblems (same arguments) — overlapping subproblems heat map"
          className={clsx(
            'flex items-center gap-1.5 rounded-md border px-2.5 py-1.5 text-xs disabled:opacity-40',
            showDupes
              ? 'border-fuchsia-500/50 bg-fuchsia-500/10 text-fuchsia-300'
              : 'border-zinc-700 bg-zinc-900 text-zinc-300 hover:bg-zinc-800',
          )}
        >
          <Layers size={14} /> Duplicates
        </button>
      </header>

      {trace ? (
        <div className="flex min-h-0 flex-1">
          <main className="relative min-w-0 flex-1">
            <TreeCanvas />
          </main>
          <aside className="flex w-80 shrink-0 flex-col border-l border-zinc-800">
            <NodePanel />
            <CallStackPanel />
          </aside>
        </div>
      ) : (
        <div className="flex flex-1 items-center justify-center">
          <div className="w-full max-w-md rounded-xl border border-zinc-800 bg-zinc-900/40 p-8 text-center">
            <Network size={36} className="mx-auto mb-4 text-emerald-400" />
            <h1 className="mb-1 text-lg font-semibold text-zinc-100">Java recursion visualizer</h1>
            <p className="mb-5 text-sm text-zinc-500">
              Generate a trace from any recursive solution, then step through its call tree with variables as
              node state.
            </p>
            <div className="mono mb-5 rounded-md border border-zinc-800 bg-zinc-950 px-3 py-2 text-left text-[11px] leading-5 text-zinc-400">
              $ java -jar visualizer/tracer/target/recviz.jar \
              <br />
              &nbsp;&nbsp;run striver/java/LCS3.java
            </div>
            <div className="flex items-center justify-center gap-2">
              {picker}
              {uploadBtn}
            </div>
          </div>
        </div>
      )}

      <PlaybackBar />
    </div>
  );
}
