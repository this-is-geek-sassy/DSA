package graphs;

// https://www.geeksforgeeks.org/problems/topological-sort/1?utm_source=chatgpt.com

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class TopologicalSort {

    private static int dfs (int i, int start, int V, boolean[] visited, int[] ordering, ArrayList<ArrayList<Integer>> adjList) {

        visited[start] = true;

        ArrayList<Integer> neighbours = adjList.get(start);

        for (Integer neighbour: neighbours) {
            if (visited[neighbour] == false) {
                i = dfs(i, neighbour, V, visited, ordering, adjList);
            }
        }
        ordering[i] = start;
        return i-1;
    }
    
    public static ArrayList<Integer> topoSort(int V, int[][] edges) {
        // code here
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        for (int i=0; i<V; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i=0; i<edges.length; i++) {
            adjList.get(edges[i][0]).add(edges[i][1]);
        }
        // adj list creation done
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);

        int[] ordering = new int[V];
        int i = V-1;

        for (int at = 0; at < V; at++) {
            if (visited[at] == false) {
                i = dfs (i, at, V, visited, ordering, adjList);
            }
            // ordering[i] = at;
            // i--;
        }
        ArrayList<Integer> finalOrdering = new ArrayList<>();

        for (int value : ordering) {
            finalOrdering.add(value);
        }

        return finalOrdering;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int v = sc.nextInt();
        int e = sc.nextInt();
        sc.nextLine();
        String edgelistInput = sc.nextLine().trim();
        String[] edgelistInputArr = edgelistInput.substring(1, edgelistInput.length()-1).split("], ");
        int[][] edges = new int[e][2];
        int i = 0;

        for (String edge: edgelistInputArr) {
            
            if (edge.endsWith("]")) {
                edge = edge.substring(1, edge.length()-1);
            }
            else {
                edge = edge.substring(1);
            }
            String[] oneEdge = edge.split(", ");
            edges[i][0] = Integer.parseInt(oneEdge[0]);
            edges[i][1] = Integer.parseInt(oneEdge[1]);
            i++;
        }

        // for (int[] edge: edges) {
        //     System.out.println(edge[0] + " " + edge[1]);
        // }
        // System.out.println();
        ArrayList<Integer> ans = topoSort(v, edges);
        System.out.println(ans);
        sc.close();
    }
}