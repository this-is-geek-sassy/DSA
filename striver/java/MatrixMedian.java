
import java.util.Scanner;


// link: https://takeuforward.org/data-structure/median-of-row-wise-sorted-matrix

public class MatrixMedian {
    static int[][] parseMatrix(String input) {

        input = input.trim();

        // Remove the outer [[ and ]]
        input = input.substring(2, input.length() - 2);

        // Split into rows
        String[] rows = input.split("\\], \\[");

        int[][] matrix = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {

            String[] nums = rows[i].split(", ");

            matrix[i] = new int[nums.length];

            for (int j = 0; j < nums.length; j++) {
                matrix[i][j] = Integer.parseInt(nums[j]);
            }
        }

        return matrix;
    }
    private static int findMin (int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for (int i=0; i<matrix.length; i++) {
            if (matrix[i][0] < min)
                min = matrix[i][0];
        }
        return min;
    }
    private static int findMax (int[][] matrix) {
        int max = Integer.MIN_VALUE;
        int n = matrix[0].length - 1;

        for (int i=0; i<matrix.length; i++) {
            if (matrix[i][n] > max)
                max = matrix[i][n];
        }
        return max;
    }
    public static int median(int[][] mat) {
        // code here
        int m = mat.length, n = mat[0].length;
        int low = findMin(mat);
        int high = findMax(mat);
        int mid;

        while (low <= high) {
            mid = low + (high-low)/2;

            // check how many element for each row
            int total = 0;
            for (int i=0; i<m; i++) {
                int rlow = 0;
                int rhigh = n-1, rmid;

                while (rlow <= rhigh) {
                    rmid = rlow + (rhigh - rlow)/2;
                    if (mat[i][rmid] <= mid){
                        total += (rmid - rlow +1);
                        rlow = rmid+1;
                    } else {
                        rhigh = rmid-1;
                    }
                }
            }
            if (total > (m*n)/2) {
                high = mid - 1;
            } else {
                low = mid+1;
            }
        }
        return low;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        int[][] matrix = parseMatrix(input);
        int ans = median(matrix);
        System.out.println(ans);
        sc.close();
    }
}
