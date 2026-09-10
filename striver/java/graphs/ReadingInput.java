package graphs;

import java.util.ArrayList;
import java.util.Scanner;

public class ReadingInput {

    private static int[][] adjGraphBuilder (int n, int m, Scanner sc) {
        
        int[][] graph = new int[n+1][n+1];

        for (int i = 1; i<=m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            graph[u][v] = 1;
            graph[v][u] = 1;
        }
        return graph;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        // System.out.println("n = " + n + " m = " + m);
        
        // int[][] graph = adjGraphBuilder(n, m, sc);
        // // printing the adj matrix
        // for (int[] row: graph) {
        //     for (int cell: row) {
        //         System.out.print(cell + " ");
        //     }
        //     System.out.println();
        // }

        ArrayList<Integer>[] adj = new ArrayList[n+1];

        for (int i = 0; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i=1; i<=m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        GraphDump.dump(adj);

        // printing
        for (int i=1; i<=n; i++) {
            int u = i;
            System.out.print(u);
            for (int v: adj[u]) {
                System.out.print("->" + v + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}
