
// link: https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/

import java.util.Scanner;

public class IsArraySortedRotated {

    // returns tightest right-min
    public static int findRightMin (int[] nums) {
        int idx = nums.length - 1;

        for (int i = nums.length-1; i > 0; i--) {
            if (nums[i] < nums[idx])
                idx = i;
            if (nums[i-1] > nums[i])
                return i;
        }
        return idx;
    }
    public static int findLeftMin(int[] nums) {
        int idx = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < nums[idx])
                idx = i;
        }
        return idx;
    }
    public static boolean check_left_right(int[] nums, int idx) {

        int i = idx, r = 0;
        while (r < nums.length-1) {

            // System.out.println(nums[i]);
            int j = (i+1) % nums.length;
            if (nums[i] > nums[j])
                return false;
            i = (i+1) % nums.length;
            r++;
        }
        return true;
    }

    public static boolean check(int[] nums) {

        int right_min_idx = findRightMin(nums);
        int left_min_idx = findLeftMin(nums);
        // System.out.println("min_idx: " + min_idx);
        
        return check_left_right(nums, left_min_idx) || check_left_right(nums, right_min_idx);
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        input = input.trim().substring(1, input.length()-1);
        
        String[] splitted = input.split(",");
        int[] arr = new int[splitted.length];
        int i = 0;
        for (String str : splitted) {
            arr[i] = Integer.parseInt(str);
            i++;
        }
        System.out.println(check(arr));
    }
}
