
import java.util.Arrays;
import java.util.Scanner;

// link: https://leetcode.com/problems/unique-paths-ii/description/

public class UniquePaths2 {

    public static int[][] parseMatrix(String input) {
        // Remove all whitespace
        input = input.replaceAll("\\s+", "");

        // Remove outer [[ ]]
        input = input.substring(2, input.length() - 2);

        // Split rows: "0,0,0],[0,1,0],[0,0,0"
        String[] rows = input.split("\\],\\[");

        int[][] matrix = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split(",");

            matrix[i] = new int[values.length];

            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Integer.parseInt(values[j]);
            }
        }
        return matrix;
    }

    private static int helper (int m, int n, int[][] memory, int[][] obstacleGrid) {

        if (m==0 && n==0 && obstacleGrid[0][0] == 0) {
            return 1;
        }
        else if (m==0 && n==0) {
            return 0;
        }
        if (obstacleGrid[m][n] == 1) {
            return 0;
        }
        if (memory[m][n] != -1) {
            return memory[m][n];
        }
        if (m==0) {
            return helper(0, n-1, memory, obstacleGrid);
        }
        if (n==0) {
            return helper(m-1, 0, memory, obstacleGrid);
        }
        memory[m][n] = helper(m-1, n, memory, obstacleGrid) + helper(m, n-1, memory, obstacleGrid);
        return memory[m][n];
    }

    private static int uniquePaths (int[][] obstacleGrid) {

        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] memory = new int[m][n];
        for (int[] i: memory) {
            Arrays.fill(i, -1);
        }
        return helper(m-1, n-1, memory, obstacleGrid);
        // return helper2(m, n);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter matrix: ");
        String input = scanner.nextLine();

        int[][] matrix = parseMatrix(input);

        // System.out.println(Arrays.deepToString(matrix));
        int ans = uniquePaths(matrix);
        System.out.println(ans);
        scanner.close();
    }
}
