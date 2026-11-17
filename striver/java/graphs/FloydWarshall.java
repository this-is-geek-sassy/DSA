package graphs;

import java.util.Scanner;

public class FloydWarshall {

    public static int[][] shortestDistance (int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix.length];

        // setup
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                if (matrix[i][j] != -1)
                    dp[i][j] = matrix[i][j];
                else
                    dp[i][j] = 1_000_000;
            }
        }
        int n = matrix.length;

        for (int k=0; k<n; k++) {
            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++) {
                    if (dp[i][k] + dp[k][j] < dp[i][j])
                        dp[i][j] = dp[i][k] + dp[k][j];
                }
            }
        }
        return dp;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] rowStrings = input.substring(1, input.length()-1).split("],");

        int[][] adjMatrix = new int[rowStrings.length][rowStrings.length];

        int i=0, j=0;
        for (String rowString: rowStrings) {
            if (rowString.endsWith("]"))
                rowString = rowString.substring(1, rowString.length()-1);
            else
                rowString = rowString.substring(1);
            String[] row = rowString.split(", ");
            // System.out.println(rowString);
            for (String r: row) {
                adjMatrix[i][j++] = Integer.valueOf(r);
            }
            i++;
            j = 0;
        }
        int[][] ans = shortestDistance(adjMatrix);
        // sanity check
        for (int[] row: ans) {
            for (int r: row)
                System.out.print(r + " ");
            System.out.println();
        }
        sc.close();
    }
}
