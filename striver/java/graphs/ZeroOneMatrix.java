package graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;


public class ZeroOneMatrix {

    private static int getNumber (int i, int j, int m, int n) {
        return i*n + j;
    }
    // private static boolean checkIfZero (int node, int[][] mat) {
    //     int m = mat.length, n = mat[0].length;

    //     int i = node / n;
    //     int j = node % n;

    //     if (mat[i][j] == 0) {
    //         return true;
    //     }
    //     else
    //         return false;
    // }
    // private static boolean putValue (int node, int[][] mat, int val) {
    //     int m = mat.length, n = mat[0].length;

    //     int i = node / n;
    //     int j = node % n;

    //     mat[i][j] = val;
    //     return true;
    // }

    public static int[][] updateMatrix(int[][] mat) {
        
        // build adj list
        int v = mat.length * mat[0].length, m = mat.length, n = mat[0].length;

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i=0; i<v; i++) {
            graph.add(new ArrayList<>());
        }
        Deque<Integer> q = new ArrayDeque<>();
        int [] visited = new int[v];
        int[] distance = new int[v];


        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int node = getNumber(i, j, m, n);
                if (mat[i][j] == 0) {
                    q.offerLast(node);
                    visited[node] = 1;
                }
                int up = -1, down = -1, left = -1, right = -1;
                if (i-1 >=0 ) {
                    up = getNumber(i-1, j, m, n);
                    graph.get(node).add(up);
                }
                if (i+1 < m) {
                    down = getNumber(i+1, j, m, n);
                    graph.get(node).add(down);
                }
                if (j-1 >= 0) {
                    left = getNumber(i, j-1, m, n);
                    graph.get(node).add(left);
                }
                if (j+1 < n) {
                    right = getNumber(i, j+1, m, n);
                    graph.get(node).add(right);
                }
            }
        }
        // Adj list formation complete
        int[][] finalDistance = new int[m][n];

        while (!q.isEmpty()) {
            int node = q.pollFirst();

            int i = node / n;
            int j = node % n;
            finalDistance[i][j] = distance[node];

            for (Integer neighbour : graph.get(node)) {
                if (visited[neighbour] == 0) {
                    visited[neighbour] = 1;
                    distance[neighbour] = distance[node] + 1;
                    q.offerLast(neighbour);
                }
            }
        }
        return finalDistance;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        int[][] mat = GraphParser.parseGrid(input);

        int[][] ans = updateMatrix(mat);
        for (int[] m: ans) {
            for (int n: m) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}
