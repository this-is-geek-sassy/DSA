
// link: https://www.geeksforgeeks.org/problems/implement-upper-bound/1

import java.util.Scanner;

public class UpperBound {

    public static int upperBound(int[] arr, int target) {

        int low = 0, high = arr.length-1;
        int cand = -1;

        if (target >= arr[high])
            return high + 1;
        if (high == 0) {
            if (arr[low] < target)
                return low;
        }
        while (low <= high) {

            int mid = (low + high) / 2;

            if (target >= arr[mid]) {
                // return mid;     // typical bin search casre 
                // cand = mid;
                low = mid + 1;
            }
            else if (target < arr[mid]) {
                cand = mid;
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
        // input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(" ");
        int[] nums = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums[i] = Integer.parseInt(input_arr[i]);
        }
        int target = sc.nextInt();

        int ans = upperBound(nums, target);
        System.out.println(ans);
        sc.close();
    }
}
