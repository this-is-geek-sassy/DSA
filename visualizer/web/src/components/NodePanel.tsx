import { MousePointerClick, ArrowDownToDot, ArrowUpFromDot } from 'lucide-react';
import { useStore } from '../store';
import { fmtVal, stateAt } from '../lib/trace';
import clsx from 'clsx';

const stateChip: Record<string, string> = {
  waiting: 'bg-zinc-800 text-zinc-400',
  active: 'bg-amber-500/15 text-amber-300',
  done: 'bg-emerald-500/15 text-emerald-300',
};

export default function NodePanel() {
  const trace = useStore((s) => s.trace);
  const selectedId = useStore((s) => s.selectedId);
  const step = useStore((s) => s.step);
  const setStep = useStore((s) => s.setStep);

  const node = trace && selectedId != null ? trace.byId.get(selectedId) : undefined;

  return (
    <div className="flex min-h-0 flex-1 flex-col border-b border-zinc-800">
      <div className="border-b border-zinc-800 px-4 py-2.5 text-xs font-semibold uppercase tracking-wider text-zinc-500">
        Node inspector
      </div>
      {!node ? (
        <div className="flex flex-1 flex-col items-center justify-center gap-2 p-6 text-center text-zinc-600">
          <MousePointerClick size={22} />
          <p className="text-xs">Click any node in the tree to inspect its variables and return value.</p>
        </div>
      ) : (
        <div className="min-h-0 flex-1 overflow-y-auto p-4">
          <div className="mb-3 flex items-center gap-2">
            <span className="mono text-sm font-semibold text-zinc-100">{node.method}()</span>
            <span className={clsx('rounded-full px-2 py-0.5 text-[10px] font-medium', stateChip[stateAt(node, step)])}>
              {stateAt(node, step)}
            </span>
          </div>

          <div className="mb-1 text-[10px] font-semibold uppercase tracking-wider text-zinc-500">Arguments</div>
          <div className="mb-4 overflow-hidden rounded-md border border-zinc-800">
            {Object.entries(node.args).map(([k, v], i) => (
              <div key={k} className={clsx('flex gap-2 px-2.5 py-1.5 text-xs', i % 2 === 0 && 'bg-zinc-900/60')}>
                <span className="mono shrink-0 text-zinc-400">{k}</span>
                <span className="mono break-all text-zinc-100">{fmtVal(v, 400)}</span>
              </div>
            ))}
          </div>

          <div className="mb-1 text-[10px] font-semibold uppercase tracking-wider text-zinc-500">Return value</div>
          <div className="mono mb-4 rounded-md border border-zinc-800 bg-zinc-900/60 px-2.5 py-1.5 text-xs text-emerald-300">
            {node.isVoid
              ? 'void'
              : node.hasResult && node.exitSeq !== null && step >= node.exitSeq
                ? fmtVal(node.result, 400)
                : '…'}
          </div>

          <div className="mono mb-4 grid grid-cols-2 gap-x-4 gap-y-1 text-xs text-zinc-500">
            <span>depth</span>
            <span className="text-right text-zinc-300">{node.depth}</span>
            <span>children</span>
            <span className="text-right text-zinc-300">{node.children.length}</span>
            <span>enter step</span>
            <span className="text-right text-zinc-300">{node.enterSeq}</span>
            <span>exit step</span>
            <span className="text-right text-zinc-300">{node.exitSeq ?? '…'}</span>
          </div>

          <div className="flex gap-2">
            <button
              onClick={() => setStep(node.enterSeq)}
              className="flex items-center gap-1.5 rounded-md border border-zinc-700 px-2.5 py-1.5 text-xs text-zinc-300 hover:bg-zinc-800"
            >
              <ArrowDownToDot size={13} /> Jump to call
            </button>
            <button
              onClick={() => node.exitSeq !== null && setStep(node.exitSeq)}
              disabled={node.exitSeq === null}
              className="flex items-center gap-1.5 rounded-md border border-zinc-700 px-2.5 py-1.5 text-xs text-zinc-300 hover:bg-zinc-800 disabled:opacity-40"
            >
              <ArrowUpFromDot size={13} /> Jump to return
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
