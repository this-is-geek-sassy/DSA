import { useCallback, useEffect, useRef } from 'react';
import { FileUp, Grid3x3, TriangleAlert } from 'lucide-react';
import { useStore } from './store';
import GridCanvas from './components/GridCanvas';
import PlaybackBar from './components/PlaybackBar';
import VarsPanel from './components/VarsPanel';
import QueuePanel from './components/QueuePanel';
import EventLog from './components/EventLog';

export default function App() {
  const trace = useStore((s) => s.trace);
  const traceName = useStore((s) => s.traceName);
  const availableTraces = useStore((s) => s.availableTraces);
  const loadTraceFile = useStore((s) => s.loadTraceFile);
  const setAvailableTraces = useStore((s) => s.setAvailableTraces);
  const fileInput = useRef<HTMLInputElement>(null);

  const loadNamed = useCallback(
    async (name: string) => {
      try {
        const r = await fetch(`grids/${name}`);
        if (!r.ok) throw new Error(String(r.status));
        loadTraceFile(await r.json(), name);
      } catch (e) {
        console.error('failed to load grid trace', name, e);
      }
    },
    [loadTraceFile],
  );

  useEffect(() => {
    (async () => {
      try {
        const params = new URLSearchParams(window.location.search);
        const r = await fetch('grids/manifest.json');
        if (!r.ok) return;
        const m = (await r.json()) as { grids?: string[] };
        const names = m.grids ?? [];
        setAvailableTraces(names);
        if (names.length > 0 && !useStore.getState().trace) {
          const want = params.get('trace');
          const name = want && names.includes(want) ? want : names[0];
          void loadNamed(name).then(() => {
            const st = useStore.getState();
            const stepParam = params.get('step');
            if (stepParam !== null) st.setStep(Number(stepParam));
            if (params.get('mode') === 'step') st.setMode('step');
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
      .then((txt) => loadTraceFile(JSON.parse(txt), file.name))
      .catch((e) => console.error('invalid grid trace file', e));
  };

  const picker = (
    <select
      value={traceName}
      onChange={(e) => e.target.value && void loadNamed(e.target.value)}
      className="max-w-52 rounded-md border border-zinc-700 bg-zinc-900 px-2 py-1.5 text-xs text-zinc-300"
      title="Pick a generated grid trace"
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
        title="Open a grid trace JSON file"
        className="flex items-center gap-1.5 rounded-md border border-zinc-700 bg-zinc-900 px-2.5 py-1.5 text-xs text-zinc-300 hover:bg-zinc-800 hover:text-white"
      >
        <FileUp size={14} /> Open JSON…
      </button>
    </>
  );

  return (
    <div className="flex h-full flex-col bg-zinc-950 text-zinc-200">
      <header className="flex h-12 shrink-0 items-center gap-3 border-b border-zinc-800 px-4">
        <div className="flex items-center gap-2">
          <Grid3x3 size={17} className="text-emerald-400" />
          <span className="text-sm font-semibold tracking-tight text-zinc-100">gridview</span>
        </div>
        {trace && (
          <span className="mono text-xs text-zinc-500">
            {trace.meta.program} · {trace.meta.rows}×{trace.meta.cols} · {trace.events.length} events
          </span>
        )}
        {trace && trace.meta.result != null && (
          <span className="rounded-full bg-emerald-500/10 px-2 py-0.5 text-[10px] text-emerald-300">
            result {trace.meta.result}
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
      </header>

      {trace ? (
        <div className="flex min-h-0 flex-1">
          <main className="relative min-w-0 flex-1">
            <GridCanvas />
          </main>
          <aside className="flex w-[22rem] shrink-0 flex-col border-l border-zinc-800">
            <VarsPanel />
            <QueuePanel />
            <EventLog />
          </aside>
        </div>
      ) : (
        <div className="flex flex-1 items-center justify-center">
          <div className="w-full max-w-md rounded-xl border border-zinc-800 bg-zinc-900/40 p-8 text-center">
            <Grid3x3 size={36} className="mx-auto mb-4 text-emerald-400" />
            <h1 className="mb-1 text-lg font-semibold text-zinc-100">Grid BFS visualizer</h1>
            <p className="mb-5 text-sm text-zinc-500">
              Trace a Java grid BFS, then step through the frontier minute by minute with live variables.
            </p>
            <div className="mono mb-5 rounded-md border border-zinc-800 bg-zinc-950 px-3 py-2 text-left text-[11px] leading-5 text-zinc-400">
              javac striver/java/graphs/*.java
              <br />
              echo &apos;[[2,1,1],[1,1,0],[0,1,1]]&apos; | java -cp striver/java graphs.RottenOranges
              <br />
              {'cd visualizer/grid-web && npm run dev'}
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
