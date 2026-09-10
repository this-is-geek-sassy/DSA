package graphs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Snapshot an adjacency list or matrix to JSON for the graphview UI.
 *
 * <pre>
 *   GraphDump.dump(adj);
 *   GraphDump.dump(matrix);
 *   GraphDump.dump(adj, true);          // directed
 *   GraphDump.dumpWeighted(adjW);       // ArrayList&lt;int[]&gt;[] of {v, w}
 * </pre>
 *
 * Writes {@code visualizer/graph-web/public/graphs/&lt;MainClass&gt;.json} and refreshes
 * {@code manifest.json}. No dependencies beyond the JDK.
 */
public final class GraphDump {

    private GraphDump() {}

    public static void dump(ArrayList<Integer>[] adj) {
        dump(adj, false);
    }

    public static void dump(ArrayList<Integer>[] adj, boolean directed) {
        write(fromAdjList(adj, directed, false), "adjList");
    }

    public static void dump(int[][] matrix) {
        dump(matrix, false);
    }

    public static void dump(int[][] matrix, boolean directed) {
        write(fromMatrix(matrix, directed), "adjMatrix");
    }

    public static void dumpWeighted(ArrayList<int[]>[] adjW) {
        dumpWeighted(adjW, false);
    }

    public static void dumpWeighted(ArrayList<int[]>[] adjW, boolean directed) {
        write(fromAdjList(adjW, directed, true), "adjList");
    }

    // ------------------------------------------------------------------
    // Build a snapshot from list / matrix
    // ------------------------------------------------------------------

    private static Snapshot fromAdjList(Object[] adj, boolean directed, boolean weightedHint) {
        int dim = adj == null ? 0 : adj.length;
        boolean oneIndexed = detectOneIndexedList(adj);
        int n = oneIndexed ? Math.max(0, dim - 1) : dim;
        int start = oneIndexed ? 1 : 0;

        int[][] matrix = new int[dim][dim];
        boolean weighted = weightedHint;
        Map<Integer, List<int[]>> lists = new LinkedHashMap<>();

        for (int u = start; u < dim; u++) {
            List<int[]> neighbors = parseNeighbors(adj == null ? null : adj[u]);
            lists.put(u, neighbors);
            for (int[] vw : neighbors) {
                int v = vw[0];
                int w = vw[1];
                if (v >= 0 && v < dim) {
                    matrix[u][v] = w;
                }
                if (w != 0 && w != 1) {
                    weighted = true;
                }
            }
        }

        List<int[]> edges = edgesFromMatrix(matrix, start, dim, directed);
        if (!weighted) {
            for (int[] e : edges) {
                if (e[2] != 0 && e[2] != 1) {
                    weighted = true;
                    break;
                }
            }
        }

        return new Snapshot(directed, weighted, oneIndexed, n, nodes(start, dim), edges, lists, matrix);
    }

    private static Snapshot fromMatrix(int[][] matrix, boolean directed) {
        int dim = 0;
        if (matrix != null) {
            dim = matrix.length;
            for (int[] row : matrix) {
                if (row != null) {
                    dim = Math.max(dim, row.length);
                }
            }
        }
        int[][] square = new int[dim][dim];
        boolean weighted = false;
        if (matrix != null) {
            for (int i = 0; i < matrix.length; i++) {
                int[] row = matrix[i];
                if (row == null) {
                    continue;
                }
                for (int j = 0; j < row.length && j < dim; j++) {
                    square[i][j] = row[j];
                    if (row[j] != 0 && row[j] != 1) {
                        weighted = true;
                    }
                }
            }
        }

        boolean oneIndexed = detectOneIndexedMatrix(square);
        int n = oneIndexed ? Math.max(0, dim - 1) : dim;
        int start = oneIndexed ? 1 : 0;

        Map<Integer, List<int[]>> lists = new LinkedHashMap<>();
        for (int u = start; u < dim; u++) {
            List<int[]> neighbors = new ArrayList<>();
            for (int v = start; v < dim; v++) {
                int w = square[u][v];
                if (w != 0) {
                    neighbors.add(new int[] {v, w});
                }
            }
            lists.put(u, neighbors);
        }

        List<int[]> edges = edgesFromMatrix(square, start, dim, directed);
        return new Snapshot(directed, weighted, oneIndexed, n, nodes(start, dim), edges, lists, square);
    }

    private static List<int[]> edgesFromMatrix(int[][] matrix, int start, int dim, boolean directed) {
        List<int[]> edges = new ArrayList<>();
        if (directed) {
            for (int u = start; u < dim; u++) {
                for (int v = start; v < dim; v++) {
                    int w = matrix[u][v];
                    if (w != 0) {
                        edges.add(new int[] {u, v, w});
                    }
                }
            }
            return edges;
        }
        for (int u = start; u < dim; u++) {
            for (int v = u; v < dim; v++) {
                int w = matrix[u][v] != 0 ? matrix[u][v] : matrix[v][u];
                if (w != 0) {
                    edges.add(new int[] {u, v, w});
                }
            }
        }
        return edges;
    }

    private static int[] nodes(int start, int dim) {
        int[] nodes = new int[Math.max(0, dim - start)];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = start + i;
        }
        return nodes;
    }

    private static boolean detectOneIndexedList(Object[] adj) {
        if (adj == null || adj.length <= 1) {
            return false;
        }
        if (!isEmptySlot(adj[0])) {
            return false;
        }
        for (int u = 1; u < adj.length; u++) {
            for (int[] vw : parseNeighbors(adj[u])) {
                if (vw[0] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean detectOneIndexedMatrix(int[][] matrix) {
        int dim = matrix.length;
        if (dim <= 1) {
            return false;
        }
        for (int j = 0; j < dim; j++) {
            if (matrix[0][j] != 0) {
                return false;
            }
        }
        for (int i = 0; i < dim; i++) {
            if (matrix[i][0] != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isEmptySlot(Object slot) {
        if (slot == null) {
            return true;
        }
        if (slot instanceof Collection<?> c) {
            return c.isEmpty();
        }
        return false;
    }

    private static List<int[]> parseNeighbors(Object slot) {
        List<int[]> out = new ArrayList<>();
        if (!(slot instanceof Collection<?> c)) {
            return out;
        }
        for (Object item : c) {
            int[] vw = parseNeighbor(item);
            if (vw != null) {
                out.add(vw);
            }
        }
        return out;
    }

    private static int[] parseNeighbor(Object item) {
        if (item == null) {
            return null;
        }
        if (item instanceof Number n) {
            return new int[] {n.intValue(), 1};
        }
        if (item instanceof int[] a && a.length >= 1) {
            return new int[] {a[0], a.length >= 2 ? a[1] : 1};
        }
        if (item instanceof Integer[] a && a.length >= 1 && a[0] != null) {
            int w = a.length >= 2 && a[1] != null ? a[1] : 1;
            return new int[] {a[0], w};
        }
        return null;
    }

    // ------------------------------------------------------------------
    // Write JSON + manifest
    // ------------------------------------------------------------------

    private static void write(Snapshot snap, String kind) {
        String program = callerProgram();
        Path dir = findGraphsDir();
        Path out = dir.resolve(program + ".json");
        String json = toJson(snap, kind, program);
        try {
            Files.createDirectories(dir);
            Files.writeString(out, json, StandardCharsets.UTF_8);
            updateManifest(dir);
            System.err.println("[graphview] wrote " + out.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("[graphview] failed to write dump: " + e.getMessage());
        }
    }

    private static String toJson(Snapshot s, String kind, String program) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"meta\": {\n");
        sb.append("    \"program\": ").append(quote(program)).append(",\n");
        sb.append("    \"kind\": ").append(quote(kind)).append(",\n");
        sb.append("    \"directed\": ").append(s.directed).append(",\n");
        sb.append("    \"weighted\": ").append(s.weighted).append(",\n");
        sb.append("    \"oneIndexed\": ").append(s.oneIndexed).append(",\n");
        sb.append("    \"n\": ").append(s.n).append('\n');
        sb.append("  },\n");
        sb.append("  \"nodes\": [");
        for (int i = 0; i < s.nodes.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(s.nodes[i]);
        }
        sb.append("],\n");
        sb.append("  \"edges\": [\n");
        for (int i = 0; i < s.edges.size(); i++) {
            int[] e = s.edges.get(i);
            sb.append("    {\"u\": ").append(e[0]).append(", \"v\": ").append(e[1]).append(", \"w\": ").append(e[2]).append('}');
            if (i + 1 < s.edges.size()) {
                sb.append(',');
            }
            sb.append('\n');
        }
        sb.append("  ],\n");
        sb.append("  \"adjList\": {\n");
        int li = 0;
        for (Map.Entry<Integer, List<int[]>> e : s.adjList.entrySet()) {
            sb.append("    ").append(quote(String.valueOf(e.getKey()))).append(": [");
            List<int[]> nbrs = e.getValue();
            for (int i = 0; i < nbrs.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(nbrs.get(i)[0]);
            }
            sb.append(']');
            if (++li < s.adjList.size()) {
                sb.append(',');
            }
            sb.append('\n');
        }
        sb.append("  },\n");
        sb.append("  \"adjMatrix\": [\n");
        for (int i = 0; i < s.matrix.length; i++) {
            sb.append("    [");
            for (int j = 0; j < s.matrix[i].length; j++) {
                if (j > 0) {
                    sb.append(", ");
                }
                sb.append(s.matrix[i][j]);
            }
            sb.append(']');
            if (i + 1 < s.matrix.length) {
                sb.append(',');
            }
            sb.append('\n');
        }
        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static Path findGraphsDir() {
        Path dir = Paths.get("").toAbsolutePath();
        while (dir != null) {
            Path candidate = dir.resolve(Paths.get("visualizer", "graph-web", "public", "graphs"));
            if (Files.isDirectory(candidate)) {
                return candidate;
            }
            if (Files.isDirectory(dir.resolve("visualizer"))
                    || Files.isDirectory(dir.resolve(Paths.get("visualizer", "graph-web")))) {
                try {
                    Files.createDirectories(candidate);
                    return candidate;
                } catch (IOException ignored) {
                    // keep walking
                }
            }
            dir = dir.getParent();
        }
        Path fallback = Paths.get("").toAbsolutePath().resolve("graphs-out");
        try {
            Files.createDirectories(fallback);
        } catch (IOException ignored) {
            // write will surface the error
        }
        return fallback;
    }

    private static void updateManifest(Path graphsDir) {
        if (graphsDir == null || !Files.isDirectory(graphsDir)) {
            return;
        }
        List<String> names = new ArrayList<>();
        try (Stream<Path> stream = Files.list(graphsDir)) {
            stream.map(p -> p.getFileName().toString())
                    .filter(n -> n.endsWith(".json"))
                    .filter(n -> !n.equals("manifest.json"))
                    .sorted(Comparator.naturalOrder())
                    .forEach(names::add);
        } catch (IOException e) {
            System.err.println("[graphview] could not list graphs dir: " + e.getMessage());
            return;
        }
        StringBuilder sb = new StringBuilder("{\"graphs\":[");
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(quote(names.get(i)));
        }
        sb.append("]}\n");
        try {
            Files.writeString(graphsDir.resolve("manifest.json"), sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("[graphview] could not update manifest: " + e.getMessage());
        }
    }

    private static String callerProgram() {
        for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
            String cn = e.getClassName();
            if (cn.equals("java.lang.Thread") || cn.equals("graphs.GraphDump")) {
                continue;
            }
            int dot = cn.lastIndexOf('.');
            return dot >= 0 ? cn.substring(dot + 1) : cn;
        }
        return "graph";
    }

    private static String quote(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 2);
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    private record Snapshot(
            boolean directed,
            boolean weighted,
            boolean oneIndexed,
            int n,
            int[] nodes,
            List<int[]> edges,
            Map<Integer, List<int[]>> adjList,
            int[][] matrix) {}
}
