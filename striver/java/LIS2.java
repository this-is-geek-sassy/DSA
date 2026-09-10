// https://leetcode.com/problems/number-of-longest-increasing-subsequence/

import java.util.Scanner;

public class LIS2 {
    static class Result {
        int length;
        int count;

        public Result(int length, int count) {
            this.length = length;
            this.count = count;
        }
    }
    private static Result helper(int[] nums, int i, int lastIdx, Result[][] memory) {

        // No more elements to process
        if (i == nums.length)
            return new Result(0, 1);

        // Shift lastIdx by 1 because lastIdx can be -1
        if (memory[i][lastIdx + 1] != null)
            return memory[i][lastIdx + 1];

        // Option 1: skip nums[i]
        Result skip = helper(nums, i + 1, lastIdx, memory);

        // Option 2: take nums[i], if valid
        Result take = new Result(0, 0);

        if (lastIdx == -1 || nums[lastIdx] < nums[i]) {
            Result next = helper(nums, i + 1, i, memory);
            take = new Result(next.length + 1, next.count);
        }

        // memory[i][lastIdx + 1] = Math.max(skip, take);
        if (skip.length > take.length) {
            memory[i][lastIdx+1] = new Result(skip.length, skip.count);
        }
        else if (take.length > skip.length) {
            memory[i][lastIdx+1] = new Result(take.length, take.count);
        }
        else {
            memory[i][lastIdx+1] = new Result(skip.length, skip.count + take.count);
        }

        return memory[i][lastIdx + 1];
    }
    public static int findNumberOfLIS(int[] nums) {
        int n = nums.length;

        // n+1 columns because lastIdx ranges from -1 to n-1
        Result[][] memory = new Result[n][n + 1];

        // for (int[] row : memory)
        //     Arrays.fill(row, -1);

        Result ans = helper(nums, 0, -1, memory);
        return ans.count;
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
