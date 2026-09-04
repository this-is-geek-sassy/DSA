/**
 * Headless verification of the trace pipeline against real generated traces.
 * Run: npx tsx scripts/verify-trace.ts
 */
import { readFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { dirname, join } from 'node:path';
import { parseTrace, stateAt, compactArgs, fmtVal, type TraceFile } from '../src/lib/trace';

const here = dirname(fileURLToPath(import.meta.url));
const tracesDir = join(here, '..', 'public', 'traces');

let failures = 0;
function check(label: string, cond: boolean, detail = '') {
  console.log(`${cond ? 'PASS' : 'FAIL'}  ${label}${detail ? ' — ' + detail : ''}`);
  if (!cond) failures++;
}

function load(name: string): TraceFile {
  return JSON.parse(readFileSync(join(tracesDir, name), 'utf8')) as TraceFile;
}

// ---- LCS3: plain recursion -------------------------------------------------
{
  const t = parseTrace(load('LCS3.json'));
  check('LCS3 node count', t.nodeCount === 15, `got ${t.nodeCount}`);
  check('LCS3 single root', t.roots.length === 1);
  check('LCS3 maxSeq = events-1', t.maxSeq === 29, `got ${t.maxSeq}`);
  const allResolved = [...t.byId.values()].every((n) => n.hasResult && n.exitSeq !== null);
  check('LCS3 every call has a result', allResolved);
  const root = t.roots[0];
  check('LCS3 root args', root.args.i === 0 && root.args.j === 0 && root.args.s1 === 'abcde');
  check('LCS3 root result', root.result === 1, `got ${JSON.stringify(root.result)}`);
  check('LCS3 nodes are non-void', [...t.byId.values()].every((n) => !n.isVoid));
  check('LCS3 state transitions', stateAt(root, 0) !== 'waiting' && stateAt(root, t.maxSeq) === 'done');
  check('LCS3 compact label', compactArgs(root).includes('i=0'), compactArgs(root));
  // at final step every node is done; at step 0 only the root is active
  const doneAll = [...t.byId.values()].every((n) => stateAt(n, t.maxSeq) === 'done');
  const activeAt0 = [...t.byId.values()].filter((n) => stateAt(n, 0) === 'active');
  check('LCS3 all done at end', doneAll);
  check('LCS3 only root active at step 0', activeAt0.length === 1);
}

// ---- LCS2: memoized — overlapping subproblems must be detectable -----------
{
  const t = parseTrace(load('LCS2.json'));
  check('LCS2 node count', t.nodeCount === 12, `got ${t.nodeCount}`);
  check('LCS2 detects duplicates', t.dupKeys.size > 0, `${t.dupKeys.size} dup keys`);
  const root = t.roots[0];
  check('LCS2 root result', root.result === 'ace', `got ${JSON.stringify(root.result)}`);
  // memo table arg (String[][] memory) must be excluded from dupKey: verify via a
  // repeated (i,j) pair sharing the same key even though memory mutated in between.
  const byI = new Map<string, number>();
  t.byId.forEach((n) => {
    const k = `${n.method}@${n.args.i},${n.args.j}`;
    byI.set(k, (byI.get(k) ?? 0) + 1);
  });
  const repeated = [...byI.values()].filter((c) => c > 1).length;
  check('LCS2 has repeated (i,j) subproblems', repeated > 0, `${repeated} repeated`);
}

// ---- CombinationSum: void backtracking -------------------------------------
{
  const t = parseTrace(load('CombinationSum.json'));
  check('CombinationSum node count', t.nodeCount === 55, `got ${t.nodeCount}`);
  const allVoid = [...t.byId.values()].every((n) => n.hasResult && n.result === null);
  check('CombinationSum void returns serialized as null', allVoid);
  const allFlagged = [...t.byId.values()].every((n) => n.isVoid);
  check('CombinationSum void flag set on all nodes', allFlagged);
  const withList = t.roots[0];
  check(
    'CombinationSum collection args serialized',
    Array.isArray(withList.args.mainArr) && Array.isArray(withList.args.runningList),
  );
  check('CombinationSum fmtVal on arrays', fmtVal([2, 3, 6, 7]) === '[2,3,6,7]', fmtVal([2, 3, 6, 7]));
}

console.log(failures === 0 ? '\nAll checks passed.' : `\n${failures} check(s) FAILED.`);
process.exit(failures === 0 ? 0 : 1);
