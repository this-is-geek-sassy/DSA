
// link: https://takeuforward.org/data-structure/merge-two-sorted-arrays-without-extra-space

import java.util.Scanner;

public class MergeArraysWoSpace {

    public static void merge (int[] nums1, int[] nums2) {
        
        int n = nums2.length-1, m = nums1.length - n - 2;
        int i, j, k;
        for (i=m, j=n, k=nums1.length-1; i>=0 && j>=0; k--) {
            if (nums1[i] >= nums2[j]) {
                nums1[k] = nums1[i];
                // nums1[i] = Integer.MIN_VALUE;
                i--;
            } else {
                nums1[k] = nums2[j];
                // nums2[j] = Integer.MIN_VALUE;
                j--;
            }
        }
        if (i == -1 && j >= 0) {
            while (j >= 0) {
                nums1[k--] = nums2[j--];
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(", ");
        int[] nums1 = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums1[i] = Integer.parseInt(input_arr[i]);
        }
        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        input_arr = input.split(", ");
        int[] nums2 = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums2[i] = Integer.parseInt(input_arr[i]);
        }
        merge(nums1, nums2);
        for (int e : nums1) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
