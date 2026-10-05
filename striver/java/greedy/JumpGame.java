package greedy;

// https://leetcode.com/problems/jump-game/description/

import java.util.Scanner;


public class JumpGame {

    public static boolean canJump(int[] nums) {

        if (nums.length == 1)
            return true;
        
        int frontier = 0;
        for (int i=0; i<nums.length; i++) {
            frontier = Math.max(frontier, i+nums[i]);
            System.out.println("frontier at i(" + i + ") = " + frontier);

            if (frontier == i && nums[i] == 0)
                break;
            if (frontier >= nums.length-1)
                return true;
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String numsIp = sc.nextLine().trim();
        String[] numsArr = numsIp.substring(1, numsIp.length()-1).split(",");

        int i = 0;
        int[] nums = new int[numsArr.length];

        for (String s: numsArr) {
            nums[i++] = Integer.parseInt(s);
        }
        boolean ans = canJump(nums);
        System.out.println(ans);
        sc.close();
    }
}
