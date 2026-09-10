// https://leetcode.com/problems/number-of-longest-increasing-subsequence/

import java.util.Arrays;
import java.util.Scanner;

public class LIS2 {
    // static class Result {
    //     int length;
    //     int count;

    //     public Result(int length, int count) {
    //         this.length = length;
    //         this.count = count;
    //     }
    // }
    private static int helper(int[] nums, int i, int lastIdx, int[][] length, int[][] count) {

        // No more elements to process
        if (i == nums.length) {
            count[i][lastIdx+1] = 1;
            return 0;
        }

        // Shift lastIdx by 1 because lastIdx can be -1
        if (length[i][lastIdx + 1] != -1)
            return length[i][lastIdx + 1];

        // Option 1: skip nums[i]
        int skip = helper(nums, i + 1, lastIdx, length, count);
        int skipCount = count[i+1][lastIdx+1];

        // Option 2: take nums[i], if valid
        int take = -1, takeCount = 0;

        if (lastIdx == -1 || nums[lastIdx] < nums[i]) {
            take = 1 + helper(nums, i + 1, i, length, count);
            // take = new Result(next.length + 1, next.count);
            takeCount = count[i+1][i+1];
        }

        // memory[i][lastIdx + 1] = Math.max(skip, take);
        if (skip > take) {
            length[i][lastIdx+1] = skip;
            count[i][lastIdx+1] = skipCount;
        }
        else if (take > skip) {
            length[i][lastIdx+1] = take;
            count[i][lastIdx+1] = takeCount;
        }
        else {
            length[i][lastIdx+1] = skip;
            count[i][lastIdx+1] = skipCount + takeCount;
        }

        return length[i][lastIdx + 1];
    }
    public static int findNumberOfLIS(int[] nums) {
        int n = nums.length;

        // n+1 columns because lastIdx ranges from -1 to n-1
        int[][] length = new int[n+1][n + 1];
        int[][] count = new int[n+1][n + 1];

        for (int[] row : length)
            Arrays.fill(row, -1);

        for (int[] row: count) {
            Arrays.fill(row, -1);
        }
        helper(nums, 0, -1, length, count);
        return count[0][0];
    }
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        
        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(",");

        int[] prices = new int[inpArr.length];
        int i = 0;
        for (String s: inpArr) {
            prices[i++] = Integer.parseInt(s);
        }
        int ans = findNumberOfLIS(prices);
        System.out.println(ans);
        sc.close();
    }
}
