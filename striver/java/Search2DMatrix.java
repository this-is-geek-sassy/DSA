
import java.util.Scanner;


// link: https://leetcode.com/problems/search-a-2d-matrix/description/

public class Search2DMatrix {
    
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
    public static boolean searchMatrix(int[][] matrix, int target) {
        
        int low = 0, high = matrix.length - 1, mid;

        if (matrix.length == 1 && matrix[0].length == 1) {
            return matrix[0][0] == target;
        }
        if (matrix.length == 1) {
            int rlow = 0, rhigh = matrix[0].length - 1, rmid;

            while (rlow <= rhigh) {
                    rmid = rlow + (rhigh - rlow)/2;
                    
                    if (matrix[0][rmid] == target)
                        return true;
                    else if (target < matrix[0][rmid])
                        rhigh = rmid - 1;
                    else
                        rlow = rmid + 1;
                }
                return false;
        }

        while (low <= high) {
            mid = low + (high - low)/2;

            if (matrix[mid][0] == target)
                return true;
            else if (target < matrix[mid][0] && mid >=1 && target >= matrix[mid-1][0]) {
                // search in the upper row
                int rlow = 0, rhigh = matrix[mid-1].length - 1, rmid;
                
                while (rlow <= rhigh) {
                    rmid = rlow + (rhigh - rlow)/2;
                    
                    if (matrix[mid-1][rmid] == target)
                        return true;
                    else if (target < matrix[mid-1][rmid])
                        rhigh = rmid - 1;
                    else
                        rlow = rmid + 1;
                }
                return false;
            }
            else if (target > matrix[mid][0] && target <= matrix[mid][matrix[mid].length-1]) {
                // search in the mid row
                int rlow = 0, rhigh = matrix[mid].length - 1, rmid;
                
                while (rlow <= rhigh) {
                    rmid = rlow + (rhigh - rlow)/2;
                    
                    if (matrix[mid][rmid] == target)
                        return true;
                    else if (target < matrix[mid][rmid])
                        rhigh = rmid - 1;
                    else
                        rlow = rmid + 1;
                }
                return false;
            }
            else if (target < matrix[mid][0]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        int target = sc.nextInt();
        int[][] matrix = parseMatrix(input);

        boolean ans = searchMatrix(matrix, target);
        System.out.println(ans);
        // for (int[] arr : matrix) {
        //     for (int num : arr) {
        //         System.out.print(num + " ");
        //     }
        //     System.out.println();
        // }
    }
}
