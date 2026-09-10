export interface GraphMeta {
  program: string;
  kind: 'adjList' | 'adjMatrix' | string;
  directed: boolean;
  weighted: boolean;
  oneIndexed: boolean;
  n: number;
}

export interface GraphEdge {
  u: number;
  v: number;
  w: number;
}

export interface GraphFile {
  meta: GraphMeta;
  nodes: number[];
  edges: GraphEdge[];
  adjList: Record<string, number[]>;
  adjMatrix: number[][];
}

export interface HoveredEdge {
  u: number;
  v: number;
}

function asNumber(v: unknown, fallback = 0): number {
  return typeof v === 'number' && Number.isFinite(v) ? v : fallback;
}

function asBool(v: unknown, fallback = false): boolean {
  return typeof v === 'boolean' ? v : fallback;
}

function asString(v: unknown, fallback: string): string {
  return typeof v === 'string' && v.length > 0 ? v : fallback;
}

function asNumberArray(v: unknown): number[] {
  if (!Array.isArray(v)) return [];
  return v.map((x) => asNumber(x)).filter((x) => Number.isFinite(x));
}

function asMatrix(v: unknown): number[][] {
  if (!Array.isArray(v)) return [];
  return v.map((row) => asNumberArray(row));
}

function asAdjList(v: unknown): Record<string, number[]> {
  if (!v || typeof v !== 'object' || Array.isArray(v)) return {};
  const out: Record<string, number[]> = {};
  for (const [k, val] of Object.entries(v as Record<string, unknown>)) {
    out[k] = asNumberArray(val);
  }
  return out;
}

export function parseGraphFile(raw: unknown): GraphFile {
  const obj = raw && typeof raw === 'object' ? (raw as Record<string, unknown>) : {};
  const metaRaw = obj.meta && typeof obj.meta === 'object' ? (obj.meta as Record<string, unknown>) : {};

  const nodes = asNumberArray(obj.nodes);
  const edges: GraphEdge[] = Array.isArray(obj.edges)
    ? obj.edges.flatMap((e) => {
        if (!e || typeof e !== 'object') return [];
        const rec = e as Record<string, unknown>;
        return [{ u: asNumber(rec.u), v: asNumber(rec.v), w: asNumber(rec.w, 1) }];
      })
    : [];
  const adjList = asAdjList(obj.adjList);
  const adjMatrix = asMatrix(obj.adjMatrix);

  const oneIndexed = asBool(metaRaw.oneIndexed, nodes.length > 0 && !nodes.includes(0));
  const n = asNumber(metaRaw.n, oneIndexed ? Math.max(0, ...nodes) : nodes.length);

  return {
    meta: {
      program: asString(metaRaw.program, 'graph'),
      kind: asString(metaRaw.kind, 'adjList'),
      directed: asBool(metaRaw.directed),
      weighted: asBool(metaRaw.weighted),
      oneIndexed,
      n,
    },
    nodes,
    edges,
    adjList,
    adjMatrix,
  };
}

export function matrixValue(graph: GraphFile, u: number, v: number): number {
  const row = graph.adjMatrix[u];
  if (!row) return 0;
  return row[v] ?? 0;
}

export function neighborsOf(graph: GraphFile, u: number): number[] {
  return graph.adjList[String(u)] ?? [];
}

export function displayMatrix(graph: GraphFile): { ids: number[]; cells: number[][] } {
  const ids = graph.nodes;
  const cells = ids.map((u) => ids.map((v) => matrixValue(graph, u, v)));
  return { ids, cells };
}

export function edgeKey(u: number, v: number, directed: boolean): string {
  if (directed || u === v) return `${u}->${v}`;
  return u < v ? `${u}--${v}` : `${v}--${u}`;
}

export function edgesMatch(a: HoveredEdge, u: number, v: number, directed: boolean): boolean {
  if (directed) return a.u === u && a.v === v;
  return (a.u === u && a.v === v) || (a.u === v && a.v === u);
}

export function weightAt(graph: GraphFile, u: number, v: number): number {
  const cell = matrixValue(graph, u, v);
  if (cell !== 0) return cell;
  if (!graph.meta.directed) {
    const back = matrixValue(graph, v, u);
    if (back !== 0) return back;
  }
  const hit = graph.edges.find((e) => edgesMatch({ u: e.u, v: e.v }, u, v, graph.meta.directed));
  return hit?.w ?? 0;
}
