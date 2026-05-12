
import java.util.Arrays;
import java.util.Scanner;

// Link: https://leetcode.com/problems/next-permutation/description/

public class NextPermutation {

    static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    public static void nextPermutation(int[] nums) {

        int break_point = -1;
        for (int i = nums.length-1; i >= 1; i--) {
            if (nums[i] > nums[i-1]) {
                break_point = i-1;
                break;
            }
        }
        if (break_point == -1) {
            Arrays.sort(nums);
            return;
        }
        int second_idx = -1;
        for (int i = nums.length-1; i >= 0; i--) {
            if (nums[i] > nums[break_point]) {
                second_idx = i;
                break;
            }
        }
        // swap break_point & second_idx entry
        int temp = nums[second_idx];
        nums[second_idx] = nums[break_point];
        nums[break_point] = temp;

        // reverse nums[break_point+1 ... nums.length-1]
        reverse(nums, break_point+1, nums.length-1);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] inp_arr = input.split(",");
        int[] nums = new int[inp_arr.length];
        int i = 0;
        for (String e: inp_arr) {
            nums[i++] = Integer.parseInt(e);
        }
        nextPermutation(nums);
        for (int e : nums) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
