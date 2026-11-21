package graphs;

import java.util.ArrayList;
import java.util.Scanner;

public class DFS {

    private static void dfsHelper (ArrayList<ArrayList<Integer>> graph, int[] visited, int[] prev, int vertex) {

        visited[vertex] = 1;
        System.out.println("visiting node = " + vertex);

        ArrayList<Integer> neighbours = graph.get(vertex);
        for (Integer neighbour: neighbours) {
            if (visited[neighbour] == 0) {
                prev[neighbour] = vertex;
                dfsHelper(graph, visited, prev, neighbour);
            }
        }
    }

    public static int[] dfs (int v, ArrayList<ArrayList<Integer>> graph, int sourceVertex) {

        int[] visited = new int[v];
        int[] prev = new int[v];
        dfsHelper(graph, visited, prev, sourceVertex);
        return prev;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        sc.nextLine();

        String input = sc.nextLine();
        ArrayList<ArrayList<Integer>> graph = GraphParser.parseUndirectedGraph(input, v);
        int[] prevList = dfs(v, graph, 0);

        for (int i=0; i<prevList.length; i++) {
            System.out.println("prev of " + i + " is = " + prevList[i]);
        }
        sc.close();
    }
}
