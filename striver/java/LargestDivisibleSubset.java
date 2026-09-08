// https://leetcode.com/problems/largest-divisible-subset/description/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
// import java.util.Scanner;

public class LargestDivisibleSubset {

    private static int helper (int[] nums, int i, int lastIndex, int[][] memory) {
        
        if (i == -1) {
            return 0;
        }
        if (memory[i][lastIndex] != -1) {
            return memory[i][lastIndex];
        }
        if (lastIndex == nums.length || nums[lastIndex] % nums[i] == 0) {
            int dontTake = helper(nums, i-1, lastIndex, memory);
            // ArrayList<Integer> takeList = new ArrayList<>(li);
            // takeList.add(nums[i]);
            
            int take = 1 + helper(nums, i-1, i, memory);
            // return (take.size() > dontTake.size()) ? take : dontTake;
            memory[i][lastIndex] = Math.max(dontTake, take);
        }
        else {
            int dontTake = helper(nums, i-1, lastIndex, memory);
            memory[i][lastIndex] = dontTake;
        }
        return memory[i][lastIndex];
    }

    public static List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        int[][] memory = new int[nums.length][nums.length+1];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        // return helper(nums, nums.length-1, nums.length, new ArrayList<>(), memory).reversed();
        // System.out.println("largest len = " + helper(nums, nums.length-1, nums.length, memory));
        int largestLen = helper(nums, nums.length-1, nums.length, memory);
        int lastIndex = nums.length;
        List<Integer> ans = new ArrayList<>();

        for (int i = nums.length-1; i>=0; i--) {
            
            if (lastIndex == nums.length || nums[lastIndex] % nums[i] == 0) {
                int take = 1 + helper(nums, i-1, i, memory);
                int dontTake = helper(nums, i-1, lastIndex, memory);

                if (take >= dontTake) {
                    ans.add(nums[i]);
                    lastIndex = i;
                }
            }
        }
        // if (nums[lastIndex] % nums[0] == 0) {
        //     ans.add(nums[0]);
        // }
        // for (int i = 0; i < memory.length; i++) {
        //     for (int j = 0; j <= memory.length; j++) {
        //         System.out.print(memory[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        return ans.reversed();
    }
    public static List<Integer> largestDivisibleSubsetAlt(int[] nums) {
        Arrays.sort(nums);
        int[] dp = new int[nums.length];
        int[] present = new int[nums.length];
        Arrays.fill(dp, 1);
        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            int max = 1, max_i = i;
            for (int j = i-1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0 && (1 + dp[j]) > max) {
                    max =  1 + dp[j];
                    max_i = j;
                }
            }
            dp[i] = max;
            present[i] = max_i;
        }
        // System.out.println("max len= " + dp[nums.length-1]);
        // for (int p: present) {
        //     System.out.print(p + " ");
        // }
        int maxIndex = 0;

        for (int i = 1; i < nums.length; i++) {
            if (dp[i] > dp[maxIndex]) {
                maxIndex = i;
            }
        }

        List<Integer> ans = new ArrayList<>();

        while (true) {
            ans.add(nums[maxIndex]);

            if (present[maxIndex] == maxIndex) {
                break;
            }

            maxIndex = present[maxIndex];
        }

        return ans.reversed();
    }
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        
        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(", ");

        int[] prices = new int[inpArr.length];
        int i = 0;
        for (String s: inpArr) {
            prices[i++] = Integer.parseInt(s);
        }
        List<Integer> ans = largestDivisibleSubsetAlt(prices);
        System.out.println(ans);
        sc.close();
    }
}
