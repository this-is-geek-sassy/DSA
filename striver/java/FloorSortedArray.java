

// link: https://www.geeksforgeeks.org/problems/floor-in-a-sorted-array-1587115620/1

import java.util.Scanner;

public class FloorSortedArray {

    public static int findFloor(int[] arr, int target) {
        // code here
        int low = 0, high = arr.length - 1;
        int cand = -1;

        // if (target > arr[high - 1])
        //     return high;
        // if (high == 1) {
        //     if (arr[low] <= target)
        //         return low;
        // }
        while (low <= high) {

            int mid = (low + high) / 2;

            if (target >= arr[mid]) {
                // return mid;     // typical bin search casre 
                cand = mid;
                low = mid+1;
            }
            else if (target < arr[mid]) {
                // cand = mid;
                high = mid - 1;
            } 
            // else {
            //     low = mid + 1;
            // }
        }
        return cand;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(", ");
        int[] nums = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums[i] = Integer.parseInt(input_arr[i]);
        }
        int target = sc.nextInt();

        int ans = findFloor(nums, target);
        System.out.println(ans);
        sc.close();
    }
}
