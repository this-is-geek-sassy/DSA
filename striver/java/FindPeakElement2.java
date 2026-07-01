
import java.util.Scanner;


// link: https://leetcode.com/problems/find-a-peak-element-ii/description/

public class FindPeakElement2 {

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
    public static int[] findPeakGrid(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[] ans = {-1, -1};
        for (int i=0; i < m; i++) {
            int low = 0, high = n-1, mid;
            System.out.println("i = " + i);
            while (low <= high) {
                mid = low + (high-low)/2; 
                if (mid == 0) {
                    
                    if (i == 0) {
                        if (matrix[i][mid] > matrix[i][mid+1] && matrix[i][mid] > matrix[i+1][mid])
                        {
                            ans[0] = i;
                            ans[1] = mid;
                            return ans;
                        }
                        // low = mid+1;
                    }
                    else if (i == m-1) {
                        if (matrix[i][mid] > matrix[i][mid+1] && matrix[i][mid] > matrix[i-1][mid]) {
                            ans[0] = i;
                            ans[1] = mid;
                            return ans;
                        }
                        // low = mid+1;
                    }
                    else if (matrix[i][mid] > matrix[i][mid+1] && matrix[i][mid] > matrix[i+1][mid] && matrix[i][mid] > matrix[i-1][mid]) {
                        ans[0] = i;
                        ans[1] = mid;
                        return ans;
                    }
                    low = mid+1;
                } else if (mid == n-1) {

                    if (i == 0) {
                        if (matrix[i][mid] > matrix[i][mid-1] && matrix[i][mid] > matrix[i+1][mid]) {
                            ans[0] = i;
                            ans[1] = mid;
                            return ans;
                        }
                    }
                    else if (i==m-1) {
                        if (matrix[i][mid] > matrix[i][mid-1] && matrix[i][mid] > matrix[i-1][mid]) {
                            ans[0] = i;
                            ans[1] = mid;
                            return ans;
                        }
                    }
                    else if (matrix[i][mid] > matrix[i][mid-1] && matrix[i][mid] > matrix[i+1][mid] && matrix[i][mid] > matrix[i-1][mid]) {
                        ans[0] = i;
                        ans[1] = mid;
                        return ans;
                    }
                    high = mid-1;
                }
                
                else if (i==0) {
                    if (matrix[i][mid] > matrix[i][mid+1] && matrix[i][mid] > matrix[i][mid-1] && matrix[i][mid] > matrix[i+1][mid]) {
                        ans[0] = i;
                        ans[1] = mid;
                        return ans;
                    }
                    else if (matrix[i][mid-1] > matrix[i][mid] && (matrix[i][mid-1] - matrix[i-1][mid-1]) > 0)
                        high = mid - 1;
                    else
                        low = mid + 1;
                }
                else if (i==m-1) {
                    if (matrix[i][mid] > matrix[i][mid+1] && matrix[i][mid] > matrix[i][mid-1] && matrix[i][mid] > matrix[i-1][mid]) {
                        ans[0] = i;
                        ans[1] = mid;
                        return ans;
                    }
                    else if (matrix[i][mid-1] > matrix[i][mid] && (matrix[i][mid-1] - matrix[i-1][mid-1]) > 0)
                        high = mid - 1;
                    else
                        low = mid + 1;
                }
                else if (matrix[i][mid] > matrix[i][mid+1] && matrix[i][mid] > matrix[i][mid-1] && matrix[i][mid] > matrix[i-1][mid] && matrix[i][mid] > matrix[i+1][mid]) {
                    ans[0] = i;
                    ans[1] = mid;
                    return ans;
                } else if (matrix[i][mid-1] > matrix[i][mid] && (matrix[i][mid-1] - matrix[i-1][mid-1]) > 0)
                    high = mid - 1;
                else
                    low = mid + 1;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        int[][] matrix = parseMatrix(input);
        int[] ans = findPeakGrid(matrix);
        System.out.println(ans[0] + " " + ans[1]);
        sc.close();
    }
}
