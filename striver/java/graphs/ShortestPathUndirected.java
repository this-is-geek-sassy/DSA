package graphs;

// https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph-having-unit-distance/1

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;


public class ShortestPathUndirected {

    public static int shortestPath(int V, int[][] edges, int src, int dest) {
        // code here
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        
        for (int i=0; i<V; i++)
            graph.add(new ArrayList<>());

        for (int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        // adj list ready
        int[] visited = new int[V];
        int[] distance = new int[V];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[src] = 0;

        Deque<Integer> q = new ArrayDeque<>();
        q.offerLast(src);
        visited[src] = 1;

        while (!q.isEmpty()) {
            int vertex = q.pollFirst();
            
            ArrayList<Integer> neighbours = graph.get(vertex);
            for (Integer neighbour: neighbours) {
                if (visited[neighbour] == 0) {
                    visited[neighbour] = 1;
                    distance[neighbour] = distance[vertex] + 1;
                    q.offerLast(neighbour);
                }
                // q.offerLast(neighbour);
                // distance[neighbour] = Math.min(distance[vertex]+1, distance[neighbour]);
            }
        }
        // for (int d: distance) {
        //     System.out.print(d + " ");
        // }
        System.out.println();

        if (distance[dest] == Integer.MAX_VALUE)
            return -1;
        return distance[dest];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        sc.nextLine();

        String edgesInp = sc.nextLine().trim();
        String[] edgesInpArr = edgesInp.substring(1, edgesInp.length()-1).split("], ");

        int[][] edgeList = new int[edgesInpArr.length][2];
        int i = 0;

        for (String eachEdge: edgesInpArr) {
            if (eachEdge.endsWith("]"))
                eachEdge = eachEdge.substring(1, eachEdge.length()-1);
            else
                eachEdge = eachEdge.substring(1);

            String[] vertices = eachEdge.split(", ");
            int u = Integer.parseInt(vertices[0]);
            int v = Integer.parseInt(vertices[1]);
            edgeList[i][0] = u;
            edgeList[i][1] = v;
            i++;
        }

        int src = sc.nextInt();
        int dest = sc.nextInt();

        int ans = shortestPath(V, edgeList, src, dest);
        System.out.println(ans);
        sc.close();
    }
}
