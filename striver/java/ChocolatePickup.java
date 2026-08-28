
import java.util.Scanner;

// link: https://www.naukri.com/code360/problems/ninja-and-his-friends_3125885

public class ChocolatePickup {

    private static int helper (int[][] matrix, int arow, int acol, int brow, int bcol, int[][][] memory) {

        if (acol < 0 || bcol < 0 || acol >= matrix[0].length || bcol >= matrix[0].length
            || arow >= matrix.length || brow >= matrix.length)
            return 0;

        if (arow == matrix.length-1 && brow == matrix.length-1 && acol == bcol) {
            return matrix[arow][acol];
        }
        if (arow == matrix.length-1 && brow == matrix.length-1) {
            return matrix[arow][acol] + matrix[brow][bcol];
        }

        if (memory[arow][acol][bcol] != 0)
            return memory[arow][acol][bcol];

        int best = 0;

        for (int moveA = -1; moveA <= 1; moveA++) {
            for (int moveB = -1; moveB <= 1; moveB++) {

                int candidate = helper(
                    matrix,
                    arow + 1,
                    acol + moveA,
                    brow + 1,
                    bcol + moveB,
                    memory
                );

                best = Math.max(best, candidate);
            }
        }

        int current = 0;
        if (arow == brow && acol == bcol) {
            current = matrix[arow][acol];
        }
        else
            current = matrix[arow][acol] + matrix[brow][bcol];

        memory[arow][acol][bcol] = current + best;
        return memory[arow][acol][bcol];
    }
    
    public static int maximumChocolates(int r, int c, int[][] grid) {
		// Write your code here.
        int[][][] memory = new int[r][c][c];
		return helper(grid, 0, 0, 0, grid[0].length-1, memory);
	}
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        String[] rows = input.substring(1, input.length() - 1).split("\\],\\s*\\[");
        int[][] matrix = new int[rows.length][];
        int j = 0;

        for (String row: rows) {
            row = row.replace("[", "").replace("]", "");

            String[] numbers = row.split(",");
            matrix[j] = new int[numbers.length];
            int i = 0;
            for (String number: numbers) {
                matrix[j][i++] = Integer.parseInt(number.trim());
            }
            j++;
        }

        int ans = maximumChocolates(matrix.length, matrix[0].length, matrix);
        System.out.println(ans);
    }
}
