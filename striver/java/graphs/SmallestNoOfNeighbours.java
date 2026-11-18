package graphs;

import java.util.Arrays;
import java.util.Scanner;

public class SmallestNoOfNeighbours {

    public static int findTheCity(int n, int[][] edges, int distanceThreshold) {
        
        // create adj matrix
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(matrix[i], 1_000_000_000);
            matrix[i][i] = 0;
        }
        for (int i=0; i<edges.length; i++) {
            int from = edges[i][0], to = edges[i][1], wt = edges[i][2];

            matrix[from][to] = wt;
            matrix[to][from] = wt;
        }
        // adj matrix complete

        int[][] dp = new int[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                dp[i][j] = matrix[i][j];
            }
        }

        // main part
        for (int k=0; k<n; k++) {
            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++) {
                    if (dp[i][k] + dp[k][j] < dp[i][j])
                        dp[i][j] = dp[i][k] + dp[k][j];
                }
            }
        }
        int[] counter = new int[n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                // System.out.print(dp[i][j] + " ");
                if (dp[i][j] <= distanceThreshold)
                    counter[i]++;
            }
            // System.out.println();
        }
        int city = 0;
        for (int i=1; i<n; i++) {
            if (counter[i] <= counter[city])
                city = i;
        }
        return city;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        String input = sc.nextLine().trim();
        int distanceThreshold = sc.nextInt();

        String[] edgeInput = input.substring(1, input.length()-1).split("],");

        int[][] edges = new int[edgeInput.length][3];
        int i = 0;

        for (String edge: edgeInput) {
            if (edge.endsWith("]"))
                edge = edge.substring(1, edge.length()-1);
            else
                edge = edge.substring(1);
            String[] components = edge.split(",");
            edges[i][0] = Integer.parseInt(components[0]);
            edges[i][1] = Integer.parseInt(components[1]);
            edges[i++][2] = Integer.parseInt(components[2]);
        }
        int ans = findTheCity(n, edges, distanceThreshold);
        System.out.println(ans);
        sc.close();
    }
}
