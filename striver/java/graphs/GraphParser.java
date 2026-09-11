package graphs;

import java.util.ArrayList;

public class GraphParser {
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
}
