// https://leetcode.com/problems/largest-divisible-subset/description/

import java.util.*;
import java.util.Scanner;

public class LargestDivisibleSubset {

    private static List<Integer> helper (int[] nums, int i) {
        
        if (i == nums.length-1) {
            return List.of(nums[i]);
        }
    }

    public static List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
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
    }
}
