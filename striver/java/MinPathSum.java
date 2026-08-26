
// link: https://leetcode.com/problems/minimum-path-sum/

import java.util.Scanner;

public class MinPathSum {

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
    public static int minPathSum(int[][] grid) {
        int[][] memory = new int[grid.length][grid[0].length];
        memory[0][0] = grid[0][0];

        for (int i=1; i<grid.length; i++) {
            memory[i][0] = memory[i-1][0] + grid[i][0];
        }
        for (int i=1; i<grid[0].length; i++) {
            memory[0][i] = memory[0][i-1] + grid[0][i-1];
        }

        for (int i=1; i<grid.length; i++) {
            for (int j=1; j<grid[0].length; j++) {

                memory[i][j] = Math.min(memory[i-1][j], memory[i][j-1]) + grid[i][j];
            }
        }
        return memory[grid.length-1][grid[0].length-1];
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter matrix: ");
        String input = scanner.nextLine();

        int[][] matrix = parseMatrix(input);

        // System.out.println(Arrays.deepToString(matrix));
        int ans = minPathSum(matrix);
        System.out.println(ans);
        scanner.close();
    }
}
