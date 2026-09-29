package graphs;

// https://www.geeksforgeeks.org/problems/shortest-path-in-directed-acyclic-graph/1

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class ShortestPathInDAG {

    record Edge(int to, int weight) {}

    private static int dfs (ArrayList<ArrayList<Edge>> graph, int[] visited, int[] ordering, int start, int i) {

        visited[start] = 1;
        
        ArrayList<Edge> neighbours = graph.get(start);
        for (Edge e: neighbours) {
            if (visited[e.to()] == 0) {
                i = dfs(graph, visited, ordering, e.to(), i);
            }
        }
        ordering[i] = start;
        return i-1;
    }

    public static ArrayList<Integer> shortestPath(int V, int[][] edges) {
        // Code here

        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();

        for (int i=0; i<V; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i=0; i<edges.length; i++) {
            
            int[] edge = edges[i];
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];
            graph.get(u).add(new Edge(v, wt));
        }
        for (ArrayList<Edge> e: graph) {
            e.sort(Comparator.comparingInt(Edge::weight));
        }

        // Toposort
        int[] visited = new int[V];
        int[] ordering = new int[V];
        int i = V-1;

        for (int at = 0; at < V; at++) {
            if (visited[at] == 0) {
                i = dfs(graph, visited, ordering, at, i);
            }
        }
        i++;
        int[] distance = new int[V];
        Arrays.fill(distance, Integer.MAX_VALUE);

        int start_vertex = 0;
        int j = 0;
        for (j=0; j<ordering.length; j++) {
            if (ordering[j] == 0)
                break;
        }
        // j holds the start (source) index 0 now
        
        distance[start_vertex] = 0;

        while (j < V) {
            start_vertex = ordering[j];
            ArrayList<Edge> neighbours = graph.get(start_vertex);
            
            for (Edge neighbour: neighbours) {
                if ((long)distance[start_vertex] + neighbour.weight() < distance[neighbour.to()]) {
                    distance[neighbour.to()] = distance[start_vertex] + neighbour.weight();
                }
            }
            j++;
        }
        ArrayList<Integer> finalDist = new ArrayList<>();
        for (int d: distance) {
            if (d == Integer.MAX_VALUE)
                finalDist.add(-1);
            else
                finalDist.add(d);
        }
        return finalDist;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int v = sc.nextInt();
        sc.nextLine();
        String edgelistInp = sc.nextLine().trim();
        String[] edgesList = edgelistInp.substring(1, edgelistInp.length()-1).split("], ");

        int[][] edgesList_graph = new int[edgesList.length][3];
        int i = 0;

        for (String edges: edgesList) {
            if (edges.endsWith("]"))
                edges = edges.substring(1, edges.length()-1);
            else
                edges = edges.substring(1);
            // System.err.println(edges);
            String[] edgeComponents = edges.split(",");   // [u, v, wt]
            edgesList_graph[i][0] = Integer.parseInt(edgeComponents[0]);
            edgesList_graph[i][1] = Integer.parseInt(edgeComponents[1]);
            edgesList_graph[i][2] = Integer.parseInt(edgeComponents[2]);
            i++;
        }
        ArrayList<Integer> ans = shortestPath(v, edgesList_graph);
        System.out.println(ans);
        sc.close();
    }
}