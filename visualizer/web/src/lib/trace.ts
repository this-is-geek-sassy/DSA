// Trace types and parsing: flat enter/exit events -> recursion tree + step timeline.

export interface TraceMeta {
  program: string;
  createdAt: string;
  events?: number;
  maxEvents?: number;
  truncated?: boolean;
}

export interface RawEvent {
  t: 'e' | 'x';
  id: number;
  s: number;
  p?: number | null;
  m?: string;
  d?: number;
  args?: Record<string, unknown>;
  r?: unknown;
  /** 1 when the method is void (distinguishes void returns from null results). */
  v?: number;
}

export interface TraceFile {
  meta: TraceMeta;
  events: RawEvent[];
}

export interface RecNode {
  id: number;
  parentId: number | null;
  method: string;
  args: Record<string, unknown>;
  depth: number;
  enterSeq: number;
  exitSeq: number | null;
  result: unknown;
  hasResult: boolean;
  /** True for void methods (result is meaningless). */
  isVoid: boolean;
  children: RecNode[];
  /** Key used for duplicate-subproblem detection. */
  dupKey: string;
}

export interface ParsedTrace {
  meta: TraceMeta;
  roots: RecNode[];
  byId: Map<number, RecNode>;
  maxSeq: number;
  nodeCount: number;
  /** dupKeys occurring more than once = overlapping subproblems. */
  dupKeys: Set<string>;
}

function isPrimitiveJson(v: unknown): boolean {
  return v === null || typeof v === 'number' || typeof v === 'boolean' || typeof v === 'string';
}

/**
 * Duplicate key built from primitive (scalar) args only. Memo tables / accumulator
 * collections are container args that mutate as the run progresses, so including them
 * would defeat duplicate detection in memoized DP (e.g. LCS2's String[][] memory).
 */
function buildDupKey(method: string, args: Record<string, unknown>): string {
  const parts = Object.entries(args)
    .filter(([, v]) => isPrimitiveJson(v))
    .map(([k, v]) => `${k}=${String(v)}`);
  return `${method}(${parts.join(',')})`;
}

export function parseTrace(tf: TraceFile): ParsedTrace {
  const byId = new Map<number, RecNode>();
  const roots: RecNode[] = [];
  const keyCount = new Map<string, number>();
  const events = [...tf.events].sort((a, b) => a.s - b.s);
  let maxSeq = 0;

  for (const ev of events) {
    if (ev.s > maxSeq) maxSeq = ev.s;
    if (ev.t === 'e') {
      const args = ev.args ?? {};
      const node: RecNode = {
        id: ev.id,
        parentId: ev.p ?? null,
        method: ev.m ?? '?',
        args,
        depth: ev.d ?? 0,
        enterSeq: ev.s,
        exitSeq: null,
        result: null,
        hasResult: false,
        isVoid: false,
        children: [],
        dupKey: buildDupKey(ev.m ?? '?', args),
      };
      byId.set(node.id, node);
      const parent = node.parentId != null ? byId.get(node.parentId) : undefined;
      if (parent) parent.children.push(node);
      else roots.push(node);
      keyCount.set(node.dupKey, (keyCount.get(node.dupKey) ?? 0) + 1);
    } else {
      const node = byId.get(ev.id);
      if (node) {
        node.exitSeq = ev.s;
        node.result = ev.r;
        node.hasResult = true;
        if (ev.v === 1) node.isVoid = true;
      }
    }
  }

  const dupKeys = new Set<string>();
  keyCount.forEach((count, key) => {
    if (count > 1) dupKeys.add(key);
  });

  return { meta: tf.meta, roots, byId, maxSeq, nodeCount: byId.size, dupKeys };
}

export type NodeState = 'waiting' | 'active' | 'done';

/** O(1) visual state of a node at a given playback step. */
export function stateAt(n: RecNode, step: number): NodeState {
  if (step < n.enterSeq) return 'waiting';
  if (n.exitSeq === null || step < n.exitSeq) return 'active';
  return 'done';
}

/** Deterministic pastel hue for a duplicate-subproblem key. */
export function dupHue(key: string): number {
  let h = 0;
  for (let i = 0; i < key.length; i++) {
    h = (h * 31 + key.charCodeAt(i)) | 0;
  }
  return ((h % 360) + 360) % 360;
}

export function fmtVal(v: unknown, max = 48): string {
  let s: string;
  if (v === null || v === undefined) s = 'null';
  else if (typeof v === 'string') s = JSON.stringify(v.length > max ? v.slice(0, max) + '…' : v);
  else if (typeof v === 'number' || typeof v === 'boolean') s = String(v);
  else s = JSON.stringify(v) ?? String(v);
  if (s.length > max + 4) s = s.slice(0, max + 4) + '…';
  return s;
}

/** One-line label for a tree node: scalar args only, e.g. "i=2 j=1". */
export function compactArgs(n: RecNode, max = 26): string {
  const parts = Object.entries(n.args)
    .filter(([, v]) => isPrimitiveJson(v))
    .map(([k, v]) => {
      const vs =
        typeof v === 'string'
          ? v.length > 6
            ? JSON.stringify(v.slice(0, 6) + '…')
            : JSON.stringify(v)
          : String(v);
      return `${k}=${vs}`;
    });
  let s = parts.join(' ');
  if (s.length > max) s = s.slice(0, max - 1) + '…';
  return s || n.method;
}
