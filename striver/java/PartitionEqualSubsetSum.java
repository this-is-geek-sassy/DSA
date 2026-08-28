
import java.util.Scanner;

// link: https://leetcode.com/problems/partition-equal-subset-sum/description/

public class PartitionEqualSubsetSum {

    public static boolean helper (int[] nums, int runningIdx, int runningSum, int[][] memory, int target) {

        if (runningIdx >= nums.length && runningSum == target) {
            return true;
        }
        if (runningIdx >= nums.length)
            return false;
        if (runningSum > target)
            return false;

        if (memory[runningIdx][runningSum] != 0) {
            return memory[runningIdx][runningSum] == 1;
        }
        if (helper(nums, runningIdx+1, runningSum + nums[runningIdx], memory, target) == true || helper(nums, runningIdx+1, runningSum, memory, target) == true)
            memory[runningIdx][runningSum] = 1;
        else
            memory[runningIdx][runningSum] = -1;

        return memory[runningIdx][runningSum] == 1;
    }
    public static boolean canPartition(int[] nums) {
        int allSum = 0;
        for (int a: nums) {
            allSum += a;
        }
        if (allSum%2 != 0)
            return false;
        int target = allSum/2;
        int[][] memory = new int[nums.length][target+1];
        return helper(nums, 1, 0, memory, target);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(",");
        int[] nums = new int[inpArr.length];
        int i = 0;
        for (String s: inpArr) {
            nums[i++] = Integer.parseInt(s);
        }
        boolean ans = canPartition(nums);
        System.out.println(ans);
    }
}
