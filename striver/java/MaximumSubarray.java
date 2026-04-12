
// link: https://leetcode.com/problems/maximum-subarray/description/

import java.util.Scanner;

public class MaximumSubarray {

    public static int maxSubArraySum(int[] nums) {
        
        int sum = 0, max_sum = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sum > max_sum) {
                max_sum = sum;
            }
            if (sum < 0) {
                sum = 0;
            }
            
        }
        return max_sum;
    }
    public static int[] maxSubArray (int[] nums) {

        int sum = 0, max_sum = Integer.MIN_VALUE;

        int start = 0, ansStart = -1, ansEnd = -1;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sum > max_sum) {
                max_sum = sum;
                ansStart = start;
                ansEnd = i;
            }
            if (sum < 0) {
                sum = 0;
                start = i+1;
            }
            
        }
        return new int[]{max_sum, ansStart, ansEnd};
    }
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);

        String ip = s.nextLine();
        ip = ip.trim().substring(1, ip.length()-1);
        String[] str_arr = ip.split(",");

        int[] numbers = new int[str_arr.length];
        int i=0;

        for (String str : str_arr) {
            int a = (int)Integer.parseInt(str);
            numbers[i] = a;
            i++;
        }
        
        s.close();

        int[] res = maxSubArray(numbers);
        System.out.println("maxSum=" + res[0] + ", start=" + res[1] + ", end=" + res[2]);
    }
}
