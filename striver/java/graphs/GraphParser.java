package graphs;

import java.util.ArrayList;

/**
 * Parsers for common LeetCode / Striver graph input strings.
 *
 * Pick a method by the shape of the problem input, not by the algorithm:
 * - {@link #parseUndirectedGraph}: flat edge pairs → undirected adjacency list (needs V)
 * - {@link #parseEdgeList}: same flat edge pairs → list of [u, v] edges (no V)
 * - {@link #parseEdgeMatrix}: nested [[u,v],...] pairs → int[][] (LeetCode prerequisites)
 * - {@link #parseAdjMatrix}: nested [[...],[...]] square matrix → int[][]
 * - {@link #parseGrid}: nested [[...],[...]] rectangular grid → int[][]
 *
 * {@code parseAdjMatrix} and {@code parseGrid} accept the same nested-array string form;
 * use the matrix method when the structure is a graph (square, vertex↔vertex), and the grid
 * method when it is a board / image (rows×cols cells). Use {@link #parseEdgeMatrix} when the
 * nested array is a list of fixed-width pairs (e.g. Course Schedule prerequisites).
 */
public class GraphParser {

    /**
     * Builds an undirected adjacency list from a flat edge-pair string.
     *
     * Use when you need an adjacency-list of neighbors for BFS/DFS/cycle detection and the
     * input is edges like {@code "[[0,1],[0,2],[1,3]]"} (or the flattened
     * {@code "0,1,0,2,1,3"} after bracket stripping).
     *
     * Not for directed graphs (adds both u→v and v→u), adjacency matrices, or cell grids —
     * use {@link #parseEdgeList}, {@link #parseAdjMatrix}, or {@link #parseGrid} instead.
     *
     * @param input edge pairs as a bracketed or comma-separated string of integers in
     *              u,v,u,v,... order
     * @param V     number of vertices; creates indices 0 .. V-1
     * @return adjacency list of size V; each edge appears in both directions
     */
    public static ArrayList<ArrayList<Integer>> parseUndirectedGraph(
            String input, int V) {

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        // Create V empty adjacency lists
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        // Remove [, ], and spaces
        input = input.replaceAll("[\\[\\]\\s]", "");

        // Now:
        // "0,1,2,1,3,4"
        String[] values = input.split(",");

        // Every edge consists of 2 integers
        for (int i = 0; i < values.length; i += 2) {
            int u = Integer.parseInt(values[i]);
            int v = Integer.parseInt(values[i + 1]);

            // Undirected graph
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        return graph;
    }

    /**
     * Parses a flat edge-pair string into a list of individual edges (no adjacency list).
     *
     * Use when the algorithm wants a raw edge list (e.g. Kruskal, Union-Find on edges, or
     * you will build the graph yourself) and the input looks like {@code "[[0,1],[2,1],[3,4]]"}.
     *
     * Differs from {@link #parseUndirectedGraph}: does not need V, does not expand into
     * neighbor lists, and does not add reverse edges — each pair becomes one [u, v] entry only.
     *
     * @param input edge pairs as a bracketed or comma-separated string of integers in
     *              u,v,u,v,... order; empty after stripping yields an empty list
     * @return list of 2-element lists [u, v] in input order
     */
    public static ArrayList<ArrayList<Integer>> parseEdgeList(String input) {

        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

        // Remove [, ], and whitespace
        input = input.replaceAll("[\\[\\]\\s]", "");

        if (input.isEmpty()) {
            return edges;
        }

        // "0,1,2,1,3,4"
        String[] values = input.split(",");

        for (int i = 0; i < values.length; i += 2) {

            ArrayList<Integer> edge = new ArrayList<>();

            edge.add(Integer.parseInt(values[i]));
            edge.add(Integer.parseInt(values[i + 1]));

            edges.add(edge);
        }

        return edges;
    }

    /**
     * Parses a nested edge-pair string into an {@code int[][]} (LeetCode-style prerequisites).
     *
     * Use when the problem gives pairs as {@code "[[1,0],[0,1]]"} and you need
     * {@code int[][] prerequisites} (each row {@code [a, b]}). Handles empty {@code "[]"}.
     *
     * Differs from {@link #parseEdgeList}: returns a primitive {@code int[][]} instead of
     * {@code ArrayList}. Differs from {@link #parseAdjMatrix}/{@link #parseGrid}: meant for
     * a list of edges/pairs, not a square connectivity matrix or a board.
     *
     * @param input nested array string {@code [[a,b],[c,d],...]} with whitespace allowed
     * @return {@code m×k} matrix of pairs (typically {@code m×2}); empty input → {@code new int[0][]}
     */
    public static int[][] parseEdgeMatrix(String input) {

        input = input.replaceAll("\\s", "");

        if (input.isEmpty() || input.equals("[]")) {
            return new int[0][];
        }

        // Remove outer [[ and ]]
        input = input.substring(2, input.length() - 2);

        String[] rows = input.split("\\],\\[");
        int m = rows.length;
        int k = rows[0].split(",").length;
        int[][] edges = new int[m][k];

        for (int i = 0; i < m; i++) {
            String[] values = rows[i].split(",");
            for (int j = 0; j < k; j++) {
                edges[i][j] = Integer.parseInt(values[j]);
            }
        }

        return edges;
    }

    /**
     * Parses a nested square matrix string into an n×n adjacency matrix.
     *
     * Use when the problem gives a connectivity / adjacency matrix such as
     * {@code "[[1,1,0],[1,1,0],[0,0,1]]"} (e.g. number of provinces / connected components
     * on an isConnected matrix).
     *
     * Differs from {@link #parseGrid}: assumes a square n×n graph matrix (vertex i to
     * vertex j). Prefer {@link #parseGrid} for rectangular boards where rows and columns
     * are spatial cells, not vertices.
     *
     * @param input nested array string [[row0],[row1],...] with whitespace allowed
     * @return n×n int[][] where result[i][j] is the parsed entry
     */
    public static int[][] parseAdjMatrix(String input) {

        // Remove whitespace
        input = input.replaceAll("\\s", "");

        // Remove the outer [[ and ]]
        input = input.substring(2, input.length() - 2);

        // Split rows
        String[] rows = input.split("\\],\\[");

        int n = rows.length;
        int[][] isConnected = new int[n][n];

        for (int i = 0; i < n; i++) {

            String[] values = rows[i].split(",");

            for (int j = 0; j < n; j++) {
                isConnected[i][j] = Integer.parseInt(values[j]);
            }
        }

        return isConnected;
    }

    /**
     * Parses a nested rectangular grid string into an m×n cell matrix.
     *
     * Use when the problem is on a board / image / map (flood fill, rotten oranges, island
     * counting) and the input looks like {@code "[[0,0,0],[0,1,0]]"} — rows of cell values,
     * not graph edges.
     *
     * Differs from {@link #parseAdjMatrix}: allows m ≠ n; treat indices as (row, col) cells.
     * Do not use this for vertex adjacency matrices.
     *
     * @param input nested array string [[row0],[row1],...] with whitespace allowed
     * @return m×n int[][] grid; m = number of rows, n = cols
     */
    public static int[][] parseGrid(String input) {

    // Remove whitespace
    input = input.replaceAll("\\s", "");

    // Remove the outer [[ and ]]
    input = input.substring(2, input.length() - 2);

    // Split individual rows
    String[] rows = input.split("\\],\\[");

    int m = rows.length;
    int n = rows[0].split(",").length;

    int[][] grid = new int[m][n];

    for (int i = 0; i < m; i++) {

        String[] values = rows[i].split(",");

        for (int j = 0; j < n; j++) {
            grid[i][j] = Integer.parseInt(values[j]);
        }
    }

    return grid;
}
}
