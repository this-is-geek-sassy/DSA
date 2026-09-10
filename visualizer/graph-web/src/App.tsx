import { useCallback, useEffect, useRef } from 'react';
import { FileUp, Share2 } from 'lucide-react';
import { useStore } from './store';
import GraphCanvas from './components/GraphCanvas';
import AdjListPanel from './components/AdjListPanel';
import AdjMatrixPanel from './components/AdjMatrixPanel';

export default function App() {
  const graph = useStore((s) => s.graph);
  const graphName = useStore((s) => s.graphName);
  const availableGraphs = useStore((s) => s.availableGraphs);
  const loadGraphFile = useStore((s) => s.loadGraphFile);
  const setAvailableGraphs = useStore((s) => s.setAvailableGraphs);
  const fileInput = useRef<HTMLInputElement>(null);

  const loadNamed = useCallback(
    async (name: string) => {
      try {
        const r = await fetch(`graphs/${name}`);
        if (!r.ok) throw new Error(String(r.status));
        loadGraphFile(await r.json(), name);
      } catch (e) {
        console.error('failed to load graph', name, e);
      }
    },
    [loadGraphFile],
  );

  useEffect(() => {
    (async () => {
      try {
        const params = new URLSearchParams(window.location.search);
        const r = await fetch('graphs/manifest.json');
        if (!r.ok) return;
        const m = (await r.json()) as { graphs?: string[] };
        const names = m.graphs ?? [];
        setAvailableGraphs(names);
        if (names.length > 0 && !useStore.getState().graph) {
          const want = params.get('graph');
          const name = want && names.includes(want) ? want : names[0];
          void loadNamed(name);
        }
      } catch {
        // no manifest yet — the user can drop a dump file manually
      }
    })();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const onUpload = (file: File) => {
    file
      .text()
      .then((txt) => loadGraphFile(JSON.parse(txt), file.name))
      .catch((e) => console.error('invalid graph file', e));
  };

  const picker = (
    <select
      value={graphName}
      onChange={(e) => e.target.value && void loadNamed(e.target.value)}
      className="max-w-52 rounded-md border border-zinc-700 bg-zinc-900 px-2 py-1.5 text-xs text-zinc-300"
      title="Pick a dumped graph"
    >
      {!graphName && <option value="">Select graph…</option>}
      {availableGraphs.map((n) => (
        <option key={n} value={n}>
          {n}
        </option>
      ))}
      {graphName && !availableGraphs.includes(graphName) && <option value={graphName}>{graphName}</option>}
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
        title="Open a graph JSON file"
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
          <Share2 size={17} className="text-emerald-400" />
          <span className="text-sm font-semibold tracking-tight text-zinc-100">graphview</span>
        </div>
        {graph && (
          <span className="mono text-xs text-zinc-500">
            {graph.meta.program} · {graph.meta.n} nodes · {graph.edges.length} edges
          </span>
        )}
        {graph?.meta.directed && (
          <span className="rounded-full bg-sky-500/10 px-2 py-0.5 text-[10px] text-sky-300">directed</span>
        )}
        {graph?.meta.weighted && (
          <span className="rounded-full bg-violet-500/10 px-2 py-0.5 text-[10px] text-violet-300">weighted</span>
        )}
        {graph && !graph.meta.directed && (
          <span className="rounded-full bg-zinc-800 px-2 py-0.5 text-[10px] text-zinc-400">undirected</span>
        )}
        <div className="flex-1" />
        {picker}
        {uploadBtn}
      </header>

      {graph ? (
        <div className="flex min-h-0 flex-1">
          <main className="relative min-w-0 flex-1">
            <GraphCanvas />
          </main>
          <aside className="flex w-[22rem] shrink-0 flex-col border-l border-zinc-800">
            <AdjListPanel />
            <AdjMatrixPanel />
          </aside>
        </div>
      ) : (
        <div className="flex flex-1 items-center justify-center">
          <div className="w-full max-w-md rounded-xl border border-zinc-800 bg-zinc-900/40 p-8 text-center">
            <Share2 size={36} className="mx-auto mb-4 text-emerald-400" />
            <h1 className="mb-1 text-lg font-semibold text-zinc-100">Graph visualizer</h1>
            <p className="mb-5 text-sm text-zinc-500">
              Dump an adjacency list or matrix from a Java solution, then inspect it as a real graph.
            </p>
            <div className="mono mb-5 rounded-md border border-zinc-800 bg-zinc-950 px-3 py-2 text-left text-[11px] leading-5 text-zinc-400">
              GraphDump.dump(adj);
              <br />
              javac striver/java/graphs/*.java
              <br />
              java -cp striver/java graphs.ReadingInput
              <br />
              {'cd visualizer/graph-web && npm run dev'}
            </div>
            <div className="flex items-center justify-center gap-2">
              {picker}
              {uploadBtn}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
