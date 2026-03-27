
// link: https://leetcode.com/problems/max-consecutive-ones/description/

import java.util.Scanner;

public class MaxConsecOnes {

    public static int findMaxConsecutiveOnes(int[] nums) {

        int streak=0, max_streak=0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                streak++;
            } else {
                if (streak > max_streak)
                    max_streak = streak;
                streak = 0;
            }
        }
        if (streak > max_streak)
            max_streak = streak;
        streak = 0;
        return max_streak;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        input = input.trim().substring(1, input.length()-1);

        String[] arr = input.split(",");

        int[] nums = new int[arr.length];

        int i = 0;
        for (String elem : arr) {
            nums[i] = Integer.parseInt(elem);
            i++;
        }

        System.out.println(findMaxConsecutiveOnes(nums));
    }
}
