
// link: https://leetcode.com/problems/find-peak-element/description/

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class FindPeakElement {
    public static int findPeakElement(int[] nums) {
        int n = nums.length, low = 0, high = n - 1;

        if (n == 1)
            return 0;
        if (n == 2) {
            return (nums[0] > nums[1]) ? 0 : 1;
        }

        while (low <= high) {
            int mid = (low + high) / 2;
            // System.out.println("low: " + low + " high: " + high + " mid: " + mid);
            if (mid ==0)
            {
                if (nums[0] > nums[1])
                    return 0;
                else
                    return 1;
            }
            if (mid == n-1){
                return (nums[n-1] > nums[n-2]) ? n-1 : n-2;
            }
            if (nums[mid] > nums[mid-1] && nums[mid] > nums[mid+1])
                return mid;
            else if (nums[mid] < nums[mid-1])
                high = mid-1;
            else
                low = mid+1;
        }
        return -1;
    }

    public static int[] generateRandomArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be non-negative");
        }

        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(0, 30);
        }

        return arr;
    }
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int choice = sc.nextInt();
            sc.nextLine();
            int[] arr;

            if (choice == 0) {
                arr = generateRandomArray(10);
            } else {
                String input = sc.nextLine();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Expected input like [1,2,3] after the choice");
                }
                input = input.trim().substring(1, input.length()-1);
                String[] ip_arr = input.split(",");
                arr = new int[ip_arr.length];

                for (int i = 0; i < ip_arr.length; i++) {
                    arr[i] = Integer.parseInt(ip_arr[i]);
                }
            }
            int ans = findPeakElement(arr);
            System.out.println(ans);
            System.out.println("nums: ");
            for (int e : arr) {
                System.out.print(e + " ");
            }
        }
    }
}
