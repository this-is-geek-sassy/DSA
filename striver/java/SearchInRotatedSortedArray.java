
// main: https://leetcode.com/problems/search-in-rotated-sorted-array/

import java.util.*;

public class SearchInRotatedSortedArray {

    public static int normalBinSearch(int[] nums, int target) {

        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (target == nums[mid]) {
                return mid;
            } else if (target >= nums[mid]) {
                low = mid +1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
    public static int normalLinearSearch(int[] nums, int target) {

        for (int i=0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }
    public static int positiveModulo (int a, int m) {

        return ((a % m) + m) % m;
    }
    public static int search(int[] nums, int target) {
        
        int low = 0, high = nums.length - 1;
        int mid = (low + high) / 2;

        if (nums[low] < nums[high]) {
            // unroated, normal bin search
            return normalBinSearch(nums, target);
        } 
        // suspecious case, could be rotated or unrotated
        if (nums[low] == nums[mid] && nums[mid] == nums[high]) {
            // frustrated case, just fo liear search
            return normalLinearSearch(nums, target);
        }
        // definitely roatated at this point]
        int k_cand = 0;
        while (low <= high) {
            if (nums[low] < nums[k_cand])
                k_cand = low;
            mid = (low + high) / 2;

            if (nums[mid] >= nums[low]) {
                low = mid + 1;
            } else {
                if (nums[mid] < nums[k_cand])
                    k_cand = mid;
                high = mid - 1;
            }
        }
        int pivot = nums.length - k_cand;
        System.out.println("pivot: " + pivot);
        int n = nums.length;
        low = 0;
        high = n - 1;

        while (low <= high) {
            mid = low + (high - low) / 2;

            int realMid = (mid + pivot) % n;

            if (nums[realMid] == target)
                return realMid;

            if (nums[realMid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return -1;
    }
    public static int search2 (int[] nums, int target) {

        int n = nums.length, low = 0, high = n - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            else if (nums[low] <= nums[mid]) {
                // left half is sorted
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                }
                else {
                    low = mid + 1;
                }
            } else {
                // right half is sorted
                if (nums[high] >= target && nums[mid] < target) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");
        int[] nums = new int[inp_arr.length];

        for (int i = 0; i < inp_arr.length; i++) {
            nums[i] = Integer.parseInt(inp_arr[i]);
        }
        int target = sc.nextInt();
        int ans = search2(nums, target);
        System.out.println(ans);
        sc.close();
    }
}
