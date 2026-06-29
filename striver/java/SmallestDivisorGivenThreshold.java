
// link: https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/

import java.util.Scanner;

public class SmallestDivisorGivenThreshold {
    private static boolean checkIfSatifies (int[] nums, int threshold, int divisor) {
        int sum = 0;
        for (int num : nums) {
            sum += (num + divisor - 1)/divisor;
        }
        return sum <= threshold;
    }
    public static int smallestDivisor(int[] nums, int threshold) {
        int max = 1;
        for (int num : nums) {
            max = Math.max(num, max);
        }
        int low =1, high = max, n = nums.length, mid = -1;

        while (low <= high) {
            mid = low + (high - low)/2;
            System.out.println("low = " + low + " high = " + high + " mid = " + mid);
            if (checkIfSatifies(nums, threshold, mid)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
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
        int threshold = sc.nextInt();
        int ans = smallestDivisor(nums, threshold);
        System.out.println("ans = " + ans);
        for (int e : nums) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
