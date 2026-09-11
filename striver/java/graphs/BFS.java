package graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class BFS {

    public static int[] bfs (int v, ArrayList<ArrayList<Integer>> graph) {

        int[] visited = new int[v];
        int[] prev = new int[v];
        Deque<Integer> q = new ArrayDeque<>();
        q.offerLast(0);
        visited[0] = 1;

        System.out.println("visiting node = 0");

        while (!q.isEmpty()) {
            int node = q.pollFirst();

            System.out.println("visiting node = " + node);

            ArrayList<Integer> neighbours = graph.get(node);
            for (Integer neighbour: neighbours) {
                if (visited[neighbour] == 0) {
                    q.offerLast(neighbour);
                    visited[neighbour] = 1;
                    prev[neighbour] = node;
                }
            }
        }
        return prev;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        sc.nextLine();

        String input = sc.nextLine();
        ArrayList<ArrayList<Integer>> graph = GraphParser.parseUndirectedGraph(input, v);
        int[] prevList = bfs(v, graph);

        for (int i=0; i<prevList.length; i++) {
            System.out.println("prev of " + i + " is = " + prevList[i]);
        }
        sc.close();
    }
}
