/**
 * Headless verification of the trace pipeline against real generated traces.
 * Run: npx tsx scripts/verify-trace.ts
 *
 * Checks are structural invariants (self-consistency of each trace) plus a few
 * semantic spot checks, so the suite survives edits to the striver source files.
 * Regenerate traces with the recviz CLI before running if sources changed.
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

/** Invariants that must hold for every well-formed trace. */
function checkInvariants(label: string, name: string) {
  const raw = load(name);
  const t = parseTrace(raw);
  check(`${label} node count = enters`, t.nodeCount === raw.events.filter((e) => e.t === 'e').length);
  check(`${label} maxSeq = events-1`, t.maxSeq === raw.events.length - 1, `got ${t.maxSeq}`);
  check(`${label} single root`, t.roots.length === 1);
  const allResolved = [...t.byId.values()].every((n) => n.hasResult && n.exitSeq !== null);
  check(`${label} every call has an exit`, allResolved);
  const doneAll = [...t.byId.values()].every((n) => stateAt(n, t.maxSeq) === 'done');
  check(`${label} all done at end`, doneAll);
  const activeAt0 = [...t.byId.values()].filter((n) => stateAt(n, 0) === 'active');
  check(`${label} only root active at step 0`, activeAt0.length === 1);
  // parent/child referential integrity
  let linksOk = true;
  t.byId.forEach((n) => {
    if (n.parentId != null) {
      const p = t.byId.get(n.parentId);
      if (!p || !p.children.includes(n)) linksOk = false;
    }
  });
  check(`${label} parent/child links consistent`, linksOk);
  return t;
}

// ---- LCS3: recursion with memo table ----------------------------------------
{
  const t = checkInvariants('LCS3', 'LCS3.json');
  const root = t.roots[0];
  check('LCS3 root args', root.args.i === 0 && root.args.j === 0 && root.args.s1 === 'abcde');
  check('LCS3 root result', root.result === 1, `got ${JSON.stringify(root.result)}`);
  check('LCS3 nodes are non-void', [...t.byId.values()].every((n) => !n.isVoid));
  check('LCS3 compact label', compactArgs(root).includes('i=0'), compactArgs(root));
  check('LCS3 memo arg excluded from dupKey', !root.dupKey.includes('memory'), root.dupKey);
}

// ---- LCS2: memoized — overlapping subproblems must be detectable -----------
{
  const t = checkInvariants('LCS2', 'LCS2.json');
  check('LCS2 detects duplicates', t.dupKeys.size > 0, `${t.dupKeys.size} dup keys`);
  check('LCS2 root result', t.roots[0].result === 'ace', `got ${JSON.stringify(t.roots[0].result)}`);
}

// ---- CombinationSum: void backtracking -------------------------------------
{
  const t = checkInvariants('CombinationSum', 'CombinationSum.json');
  const allVoid = [...t.byId.values()].every((n) => n.result === null && n.isVoid);
  check('CombinationSum void returns flagged', allVoid);
  const rootArgs = t.roots[0].args;
  check(
    'CombinationSum collection args serialized',
    Array.isArray(rootArgs.mainArr) && Array.isArray(rootArgs.runningList),
  );
  check('CombinationSum fmtVal on arrays', fmtVal([2, 3, 6, 7]) === '[2,3,6,7]', fmtVal([2, 3, 6, 7]));
}

console.log(failures === 0 ? '\nAll checks passed.' : `\n${failures} check(s) FAILED.`);
process.exit(failures === 0 ? 0 : 1);
