package graphs;

import java.util.Arrays;
import java.util.Scanner;

// https://leetcode.com/problems/is-graph-bipartite/description/

public class BipartiteGraph {

    private static boolean DFS (int[][] graph, int node, int[] colors, int parentColor) {

        if (colors[node] == parentColor)
            return false;
        int[] neighbours = graph[node];
        for (int neighbour: neighbours) {
            if (colors[neighbour] == colors[node])
                return false;

            if (colors[neighbour] == -1) {

                colors[neighbour] = (colors[node]+1)%2;
                boolean isOddCyclic = DFS(graph, neighbour, colors, colors[node]);
                if (isOddCyclic == false)
                    return false;
            }
        }
        return true;
    }

    public static boolean isBipartite(int[][] graph) {
        
        int[] colors = new int[graph.length];
        Arrays.fill(colors, -1);

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == -1) {
                // call DFS
                colors[i] = 0;
                if (DFS(graph, i, colors, 1) == false)
                    return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();

        String[] inpArr = input.substring(1, input.length()-1).split("],");
        int[][] grid = new int[inpArr.length][];
        int i = 0;

        for (String row: inpArr) {
            if (row.endsWith("]"))
                row = row.substring(1, row.length()-1);
            else
                row = row.substring(1);
            String[] rowArr = row.split(",");
            grid[i] = new int[rowArr.length];
            int j = 0;

            for (String s: rowArr) {
                grid[i][j++] = Integer.parseInt(s);
            }
            i++;
            // System.out.println(row);
        }
        boolean ans = isBipartite(grid);
        System.out.println(ans);
        sc.close();
    }
}
