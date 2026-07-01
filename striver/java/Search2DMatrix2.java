
// link: https://leetcode.com/problems/search-a-2d-matrix-ii/description/

import java.util.Scanner;

public class Search2DMatrix2 {
    static int[][] parseMatrix(String input) {

        input = input.trim();

        // Remove the outer [[ and ]]
        input = input.substring(2, input.length() - 2);

        // Split into rows
        String[] rows = input.split("\\],\\[");

        int[][] matrix = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {

            String[] nums = rows[i].split(",");

            matrix[i] = new int[nums.length];

            for (int j = 0; j < nums.length; j++) {
                matrix[i][j] = Integer.parseInt(nums[j]);
            }
        }

        return matrix;
    }
    
    public static boolean thoroughSearch (int[][] matrix, int rlow, int rhigh, int clow, int chigh, int target) {
        int i, j=-1;
        for (i = clow; i <= chigh; i++) {
            for (j = rlow; j <= rhigh; j++) {
                // System.out.println(matrix[i][j]);
                if (matrix[i][j] == target)
                    return true;
            }
        }
        System.out.println("thorough search happened. i = " + i + " j = " + j);
        return false;
    }
    public static boolean searchMatrixRec (int[][] matrix, int rlow, int rhigh, int clow, int chigh, int target) {
        int rmid, cmid;
        while (rlow <= rhigh && clow <= chigh) {
            rmid = rlow + (rhigh - rlow)/2;
            cmid = clow + (chigh - clow)/2;

            if (rlow+1 >= rhigh || clow+1 >= chigh) {
                return thoroughSearch (matrix, rlow, rhigh, clow, chigh, target);
            }

            if (matrix[cmid][rmid] == target)
                return true;
            else if (target < matrix[cmid][rmid]) {
                return searchMatrixRec(matrix, rlow, rmid-1, clow, cmid-1, target) || searchMatrixRec(matrix, rlow, rmid-1, cmid, chigh, target) || searchMatrixRec(matrix, rmid, rhigh, clow, cmid-1, target);
            } else {
                return searchMatrixRec(matrix, rlow, rmid-1, cmid, chigh, target) || searchMatrixRec(matrix, rmid, rhigh, clow, cmid-1, target) || searchMatrixRec(matrix, rmid, rhigh, cmid, chigh, target);
            }
        }
        return false;
    }
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int rlow = 0, rhigh = n-1, clow = 0, chigh = m-1, rmid = 0, cmid = 0;

        
        return searchMatrixRec(matrix, rlow, rhigh, clow, chigh, target);
    }
    // attmpt 2
    public static boolean searchMatrixAttempt2(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length-1, n = matrix.length, m = matrix[0].length-1;

        while (row < n && col >= 0) {
            if (target == matrix[row][col])
                return true;
            else if (target < matrix[row][col])
                col--;
            else
                row++;
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        int target = sc.nextInt();
        int[][] matrix = parseMatrix(input);

        boolean ans = searchMatrixAttempt2(matrix, target);
        // boolean ans = thoroughSearch(matrix, 1, 2, 1, 2, target);
        System.out.println(ans);
        // for (int[] arr : matrix) {
        //     for (int num : arr) {
        //         System.out.print(num + " ");
        //     }
        //     System.out.println();
        // }
        sc.close();
    }
}