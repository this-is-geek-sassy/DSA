
// link: https://leetcode.com/problems/maximum-product-subarray/description/

import java.util.Scanner;

public class MaximumProductSubArray {
    public static int maxProduct(int[] nums) {
        int res = nums[0];
        int maxProd = nums[0];
        int minProd = nums[0];

        for (int i=1; i < nums.length; i++) {
            int curr = nums[i];

            if (curr < 0) {
                int temp = maxProd;
                maxProd = minProd;
                minProd = temp;
            }
            maxProd = Math.max(curr, maxProd*curr);
            minProd = Math.min(curr, curr*minProd);

            res = Math.max(res, maxProd);
        }
        return res;
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
        
        int ans = maxProduct(arr);
        System.out.println(ans);
        System.out.println("nums: ");
        for (int e : arr) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
