
// link: https://leetcode.com/problems/merge-sorted-array/description/

import java.util.Scanner;

public class MergeSortedarray {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        int[] nums = new int[m+n];
        int j=0, k=0, i;

        for (i=0; (i < nums.length) && (j < m) && (k < n); i++) {
            if (nums1[j] <= nums2[k]) {
                nums[i] = nums1[j];
                j++;
            } else {
                nums[i] = nums2[k];
                k++;
            }
        }
        if (j < m) {
            for (; i < nums.length; i++) {
                nums[i] = nums1[j];
                j++;
            }
        } else if (k < n) {
            for (; i < nums.length; i++) {
                nums[i] = nums2[k];
                k++;
            }
        }
        
        // Copy the merged result back into nums1
        System.arraycopy(nums, 0, nums1, 0, m + n); // System.out.println("Array: ");
        // for (Integer elem : nums1) {
        //     System.out.print(elem + " ");
        // }
        // System.out.println();
    }
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        input = input.trim().substring(1, input.length()-1);

        String[] arr = input.split(",");

        int[] nums1 = new int[arr.length];

        int i = 0;
        for (String elem : arr) {
            nums1[i] = Integer.parseInt(elem);
            i++;
        }

        input = s.nextLine();
        int[] nums2;

        if (input.equals(new String("[]"))) {
            nums2 = new int[0];
            arr = new String[0];
        } else {
            input = input.trim().substring(1, input.length()-1);

            arr = input.split(",");

            nums2 = new int[arr.length];
        }

        i = 0;
        for (String elem : arr) {
            nums2[i] = Integer.parseInt(elem);
            i++;
        }

        input = s.nextLine();
        int m = Integer.parseInt(input);
        input = s.nextLine();
        int n = Integer.parseInt(input);

        merge(nums1, m, nums2, n);
    }
}
