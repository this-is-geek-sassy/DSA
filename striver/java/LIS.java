// https://takeuforward.org/data-structure/longest-increasing-subsequence-binary-search-dp-43

import java.util.*;

public class LIS {

    private static int helper(int[] nums, int i, int lastIdx, int[][] memory) {

        // No more elements to process
        if (i == nums.length)
            return 0;

        // Shift lastIdx by 1 because lastIdx can be -1
        if (memory[i][lastIdx + 1] != -1)
            return memory[i][lastIdx + 1];

        // Option 1: skip nums[i]
        int skip = helper(nums, i + 1, lastIdx, memory);

        // Option 2: take nums[i], if valid
        int take = 0;

        if (lastIdx == -1 || nums[lastIdx] < nums[i]) {
            take = 1 + helper(nums, i + 1, i, memory);
        }

        memory[i][lastIdx + 1] = Math.max(take, skip);

        return memory[i][lastIdx + 1];
    }
    public static int maxLength (int[] nums) {
        int n = nums.length;

        // n+1 columns because lastIdx ranges from -1 to n-1
        int[][] memory = new int[n][n + 1];

        for (int[] row : memory)
            Arrays.fill(row, -1);

        return helper(nums, 0, -1, memory);
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
        int ans = maxLength(prices);
        System.out.println(ans);
        sc.close();
    }
}
