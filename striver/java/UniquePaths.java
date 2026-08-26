
import java.util.Scanner;

// link: https://leetcode.com/problems/unique-paths/

public class UniquePaths {

    private static int cominatoricsSoln (int m, int n) {

        // C(m+n-2, n-1) or C(m+n-2, m-1)
        int N = m + n - 2;
        int K = Math.min(m - 1, n - 1);

        long result = 1;

        for (int i = 1; i <= K; i++) {
            result = result * (N - K + i) / i;
        }

        return (int) result;
    }

    private static int helper (int m, int n, int[][] memory) {

        if (m==0 && n==0) {
            return 1;
        }
        if (memory[m][n] != -1) {
            return memory[m][n];
        }
        if (m==0) {
            return helper(0, n-1, memory);
        }
        if (n==0) {
            return helper(m-1, 0, memory);
        }
        memory[m][n] = helper(m-1, n, memory) + helper(m, n-1, memory);
        return memory[m][n];
    }

    private static int helper2 (int m, int n) {

        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    private static int uniquePaths (int m, int n) {
        // int[][] memory = new int[m][n];
        // for (int[] i: memory) {
        //     Arrays.fill(i, -1);
        // }
        // return helper(m-1, n-1, memory);
        return helper2(m, n);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();


        sc.close();
    }
}
