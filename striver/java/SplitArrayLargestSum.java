
import java.util.Scanner;

// link: https://leetcode.com/problems/split-array-largest-sum/description/


public class SplitArrayLargestSum {
    private static int numberOfSplits (int[] nums, long maxSum) {
        // returns the minimum #parts required to maintain the maxSum limit
        long part = 1, sum = 0;
        for (int i=0; i<nums.length; i++) {
            if (sum + nums[i] <= maxSum) {
                sum += nums[i];
            } else {
                part++;
                sum = nums[i];
            }
        }
        return (int) part;
    }
    public static int splitArray(int[] nums, int k) {
        long low = Integer.MIN_VALUE, high = 0, mid=999;

        for (int num: nums) {
            low = Math.max(low, num);
            high += num;
        }
        while (low <= high) {
            mid = low + (high - low)/2;
            if (numberOfSplits(nums, mid) <= k) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println("Low: " + low + " high: " + high + " mid: " + mid);
        return (int) low;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String ip = sc.nextLine();
        String[] ip_arr = ip.trim().substring(1, ip.length()-1).split(",");
        int[] nums = new int[ip_arr.length];
        int i = 0;

        for (String s: ip_arr) {
            nums[i++] = Integer.parseInt(s);
        }
        int k = sc.nextInt();
        int ans = splitArray(nums, k);
        System.out.println(ans);
        sc.close();
    }
}
