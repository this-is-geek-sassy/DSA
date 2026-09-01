// https://leetcode.com/problems/target-sum/description/

import java.util.Scanner;

public class TargetSum {

    private static int helper (int[] nums, int target, int runningIdx, int runningSum, Integer[][] memory) {

        if (nums.length == runningIdx && target == runningSum) {
            return 1;
        }
        if (nums.length == runningIdx)
            return 0;
        
        if (memory[runningIdx][1000 + runningSum] != null) {
            return memory[runningIdx][1000 + runningSum];
        }
        memory[runningIdx][1000 + runningSum] = helper(nums, target, runningIdx+1, runningSum + nums[runningIdx], memory) + helper(nums, target, runningIdx+1, runningSum - nums[runningIdx], memory);
        return memory[runningIdx][1000 + runningSum];
    }

    public static int findTargetSumWays(int[] nums, int target) {
        
        int total = 0;
        for (int n: nums) {
            total += n;
        }
        
        Integer[][] memory = new Integer[nums.length][1000 + total+1];
        return helper(nums, target, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");
        // int k = sc.nextInt();

        int i = 0;
        int[] nums = new int[inp_arr.length];
        for (String s : inp_arr) {
            nums[i++] = Integer.parseInt(s);
        }
        int target = sc.nextInt();
        int ans = findTargetSumWays(nums, target);
        System.out.println(ans);
        sc.close();
    }
}
