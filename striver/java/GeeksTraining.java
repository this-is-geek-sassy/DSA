// link: https://www.geeksforgeeks.org/problems/geeks-training/1

import java.util.Arrays;
import java.util.Scanner;

public class GeeksTraining {

    private static int next(int i) {
        return i%3;
    }
    private static int helper2 (int[][] mat, int[][] memory) {
        
        memory[mat.length-1][0] = mat[mat.length-1][0];
        memory[mat.length-1][1] = mat[mat.length-1][1];
        memory[mat.length-1][2] = mat[mat.length-1][2];

        for (int i=mat.length-2; i>=0; i--) {
            memory[i][0] = mat[i][0] + Math.max(memory[i+1][1], memory[i+1][2]);
            memory[i][1] = mat[i][1] + Math.max(memory[i+1][0], memory[i+1][2]);
            memory[i][2] = mat[i][2] + Math.max(memory[i+1][1], memory[i+1][0]);
        }
        return Math.max(Math.max(memory[0][0], memory[0][1]), memory[0][2]);
    }

    private static int helper (int[][] mat, int colNow, int rowNow, int[][] memory) {

        if (rowNow == mat.length-1) {
            return Math.max(mat[rowNow][next(colNow+1)], mat[rowNow][next(colNow+2)]);
        }
        if (memory[rowNow][colNow] != 0)
            return memory[rowNow][colNow];

        int optionA = mat[rowNow][next(colNow+1)] + helper(mat, next(colNow+1), rowNow+1, memory);
        int optionB = mat[rowNow][next(colNow+2)] + helper(mat, next(colNow+2), rowNow+1, memory);

        memory[rowNow][colNow] = Math.max(optionA, optionB);
        return memory[rowNow][colNow];
    }

    public static int maximumPoints(int[][] mat) {

        int[][] memory = new int[mat.length][3];

        // int optionA = helper(mat, 0, 0, memory);
        // for (int i = 0; i < memory.length; i++) {
        //     Arrays.fill(memory[i], 0);
        // }

        // int optionB = helper(mat, 1, 0, memory);
        // for (int i = 0; i < memory.length; i++) {
        //     Arrays.fill(memory[i], 0);
        // }

        // int optionC = helper(mat, 2, 0, memory);

        // return Math.max(Math.max(optionA, optionB), optionC);

        return helper2(mat, memory);
    }

    public static int[][] parseMatrix(String input) {

        input = input.trim();
        input = input.substring(1, input.length() - 1).trim();

        String[] rows = input.split("\\],\\s*\\[");

        int[][] matrix = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {

            String row = rows[i]
                    .replace("[", "")
                    .replace("]", "")
                    .trim();

            String[] numbers = row.split("\\s*,\\s*");

            matrix[i] = new int[numbers.length];

            for (int j = 0; j < numbers.length; j++) {
                matrix[i][j] = Integer.parseInt(numbers[j]);
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StringBuilder input = new StringBuilder();

        System.out.println("Enter matrix (empty line to finish):");

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            // Empty line = input finished
            if (line.trim().isEmpty()) {
                break;
            }

            input.append(line);
        }

        int[][] matrix = parseMatrix(input.toString());

        System.out.println(Arrays.deepToString(matrix));

        int ans = maximumPoints(matrix);
        System.out.println("ans = " + ans);
        scanner.close();
    }
}
