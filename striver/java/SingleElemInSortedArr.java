
// link: https://leetcode.com/problems/single-element-in-a-sorted-array/description/

import java.util.Scanner;

public class SingleElemInSortedArr {
    public static int singleNonDuplicate(int[] nums) {
        int n = nums.length, low = 0, high = n-1;

        if (n == 1)
            return nums[0];

        while (low <= high) {
            if (low == high)
                return nums[low];
            int mid = (low + high)/2;
            if (mid == 0)
                return nums[0];
            if (nums[mid] != nums[mid+1] && nums[mid] != nums[mid - 1])
                return nums[mid];
            else if (mid%2 == 1) { 
                if (nums[mid] == nums[mid-1])
                    low = mid + 1;
                else
                    high = mid - 1;
            } else {
                if (nums[mid] == nums[mid-1])
                    high = mid - 1;
                else
                    low = mid + 1;
            }
        }
        return -1;
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
        
        int ans = singleNonDuplicate(arr);
        System.out.println(ans);
        System.out.println("nums: ");
        for (int e : arr) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
