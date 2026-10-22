// https://leetcode.com/problems/jump-game-ii/

package greedy;

import java.util.Scanner;

public class JumpGame2Better {

    public static int jump(int[] nums) {

        if (nums.length == 1) {
            return 0;
        }

        int frontier = 0, steps = 0, prevJumpBoundary = 0;
        
        for (int i=0; i<nums.length-1; i++) {
            int frontierNow = i + nums[i];

            if (frontierNow > frontier) {
                frontier = frontierNow;
            }
            if (i == prevJumpBoundary) {
                steps++;
                prevJumpBoundary = frontier;
            }
            // if (frontier >= nums.length - 1)
            //     return steps+1;
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
