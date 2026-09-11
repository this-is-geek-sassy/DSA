package graphs;

// // https://www.geeksforgeeks.org/problems/number-of-provinces/1

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConnectedCompoents {

    private static void connectedComponentHelper (int v, ArrayList<ArrayList<Integer>> edges, int[] visited, int vertex, int[] components, int colour) {

        if (visited[vertex] == 1)
            return;

        visited[vertex] = 1;
        components[vertex] = colour;
        System.out.println("Visiting = " + vertex);
        System.out.println("color = " + colour);

        ArrayList<Integer> neighbours = edges.get(vertex);
        for (Integer neighbour : neighbours) {
            connectedComponentHelper(v, edges, visited, neighbour, components, colour);
        }
    }

    private static void dfsHelper (int v, ArrayList<ArrayList<Integer>> edges, int[] visited, int vertex) {

        if (visited[vertex] == 1)
            return;

        visited[vertex] = 1;
        System.out.println("Visiting = " + vertex);
        ArrayList<Integer> neighbours = edges.get(vertex);

        for (Integer neighbour : neighbours) {
            dfsHelper(v, edges, visited, neighbour);
        }
    }

    public static void dfs (int v, ArrayList<ArrayList<Integer>> edges) {

        int[] visited = new int[v];
        dfsHelper(v, edges, visited, 0);
    }

    public static int connectedComponents (int v, ArrayList<ArrayList<Integer>> edgeList) {

        // build adj list
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i=0; i<v; i++) {
            graph.add(new ArrayList<>());
        }

        for (List<Integer> edge: edgeList) {
            int a = edge.get(0), b = edge.get(1);
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        int[] visited = new int[v];
        int[] components = new int[v];
        int colour = 0;

        for (int vertex=0; vertex < v; vertex++) {
            if (visited[vertex] == 0) {
                ++colour;
                connectedComponentHelper(v, graph, visited, vertex, components, colour);
            }
        }
        return colour;
    }

    public static int findCircleNum(int[][] isConnected) {
        
        // build adj list
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        int v = isConnected.length;
        for (int i=0; i<v; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i=0; i<v; i++) {
            for (int j=0; j<v; j++) {
                if (isConnected[i][j] == 1) {
                    graph.get(i).add(j);
                }
            }
        }
        int[] visited = new int[v];
        int[] components = new int[v];
        int colour = 0;

        for (int vertex=0; vertex < v; vertex++) {
            if (visited[vertex] == 0) {
                ++colour;
                connectedComponentHelper(v, graph, visited, vertex, components, colour);
            }
        }
        return colour;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // int v = sc.nextInt();
        // sc.nextLine();

        String input = sc.nextLine();
        // ArrayList<ArrayList<Integer>> edgeList = GraphParser.parseEdgeList(input);
        int[][] isConnected = GraphParser.parseAdjMatrix(input);
        
        // dfs(v, edges);
        // int noOfConnComponents = connectedComponents(v, edgeList);
        int noOfConnComponents = findCircleNum(isConnected);
        System.out.println("# Connected components = " + noOfConnComponents);
        sc.close();
    }
}
