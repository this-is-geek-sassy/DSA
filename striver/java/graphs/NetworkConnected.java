// https://leetcode.com/problems/number-of-operations-to-make-network-connected/

package graphs;

import java.util.ArrayList;
import java.util.Scanner;

public class NetworkConnected {

    private static void dfs (ArrayList<ArrayList<Integer>> graph, boolean[] visited, int node) {

        if (visited[node])
            return;
        visited[node] = true;
        ArrayList<Integer> neighbours = graph.get(node);
        // int nodeCount = 1;

        for (Integer neighbour: neighbours) {
            if (!visited[neighbour])
                dfs(graph, visited, neighbour);
        }
        // return;
    }

    public static int makeConnected(int n, int[][] connections) {
        // adj list gen
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        int edgeCount = connections.length;
        if (edgeCount < n-1)
            return -1;

        for (int[] connection: connections) {
            graph.get(connection[0]).add(connection[1]);
            graph.get(connection[1]).add(connection[0]);
        }
        boolean[] visited = new boolean[n];
        int componentCount = 0;
        // int edgeCountsForComponents = 0;

        for (int i=0; i<n; i++) {
            if (!visited[i]) {
                componentCount++;
                dfs(graph, visited, i);
            }
        }

        return componentCount-1;
        // return -1;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        String input = sc.nextLine().trim();

        String[] edgeStrings = input.substring(1, input.length()-1).split("],");
        int[][] connections = new int[edgeStrings.length][2];

        int i = 0;
        for (String edgeString: edgeStrings) {
            if (edgeString.endsWith("]"))
                edgeString = edgeString.substring(1, edgeString.length()-1);
            else
                edgeString = edgeString.substring(1);
            String[] edgeComponents = edgeString.split(",");
            connections[i][0] = Integer.parseInt(edgeComponents[0]);
            connections[i][1] = Integer.parseInt(edgeComponents[1]);
            // connections[i][2] = Integer.parseInt(edgeComponents[2]);
            i++;
        }
        int ans = makeConnected(n, connections);
        System.out.println(ans);
        sc.close();
    }
}
