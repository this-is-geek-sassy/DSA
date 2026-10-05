package greedy;

// https://leetcode.com/problems/jump-game-ii/description/

import java.util.Scanner;


public class JumpGame2 {
    
    public static int jump(int[] nums) {
        
        if (nums.length == 1)
            return 0;

        int frontier = 0;
        int steps = 0;

        for (int i=0; i<nums.length; i++) {
            
            if (frontier >= nums.length-1)
                return steps;
            int max_val = Integer.MIN_VALUE;
            for (int j=i; j<=frontier; j++) {
                max_val = Math.max(max_val, j + nums[j]);
            }
            if (max_val > frontier) {
                frontier = max_val;
                steps++;
            }
        }
        return steps;
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
        int ans = jump(nums);
        System.out.println(ans);
        sc.close();
    }
}
