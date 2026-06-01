
// link: https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/

import java.util.Scanner;

public class LastOccurrenceInSortedArray {

    public static int[] searchRange(int[] nums, int target) {
        
        int[] ans = new int[2];
        ans[0] = -1;
        ans[1] = -1;
        
        int low = 0, high = nums.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (target == nums[mid]) {

                int i = mid, j = mid;
                while (i >= 0 && nums[i] == nums[mid]) {
                    i--;
                }
                ans[0] = i+1;

                while (j < nums.length && nums[j] == nums[mid]) {
                    j++;
                }
                ans[1] = j-1;
                return ans;
            } else if (target < nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(",");
        int[] nums = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums[i] = Integer.parseInt(input_arr[i]);
        }
        int target = sc.nextInt();

        int[] ans = searchRange(nums, target);
        System.out.println(ans[0] + " " 
            + ans[1]);
        sc.close();
    }
}
