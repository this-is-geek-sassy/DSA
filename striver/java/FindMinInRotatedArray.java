
// link: https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/

import java.util.Scanner;

public class FindMinInRotatedArray {
    public static int findMin(int[] nums) {
        int n = nums.length, low = 0, high = n - 1, min = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = (low + high) / 2;
            System.out.println("low: " + low + " high: " + high + " mid: " + mid);
            if (nums[mid] < min) {
                min = nums[mid];
            }
            if (nums[low] <= nums[mid]) {
                // left half is sorted
                // if (nums[low] <= target && target < nums[mid]) {
                    // high = mid - 1;
                    // if (nums[mid] < min)
                    //     min = nums[mid];
                    if (nums[low] < min)
                        min = nums[low];
                    low = mid + 1;
                // }
                // else {
                //     low = mid + 1;
                // }
            } else {
                // right half is sorted
                // if (nums[high] >= target && nums[mid] < target) {
                //     low = mid + 1;
                high = mid - 1;
                // } else {
                //     high = mid - 1;
                // }
            }
            
        }
        if (nums[0] < min)
            min = nums[0];
        return min;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] ip_arr = input.split(",");
        int[] arr = new int[ip_arr.length];

        for (int i=0; i<ip_arr.length; i++) {
            arr[i] = Integer.parseInt(ip_arr[i]);
        }
        
        int ans = findMin(arr);
        System.out.println(ans);
        System.out.println("nums: ");
        for (int e : arr) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
