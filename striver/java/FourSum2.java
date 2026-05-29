
// link: https://leetcode.com/problems/4sum-ii/

import java.util.*;

public class FourSum2 {

    public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        
        Map<Integer, Integer> map = new HashMap<>();

        for (int a : nums1) {
            for (int b : nums2) {
                int sum = a + b;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        int count = 0;

        for (int c : nums3) {
            for (int d : nums4) {
                int target = -(c + d);

                count += map.getOrDefault(target, 0);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(",");
        int[] nums1 = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums1[i] = Integer.parseInt(input_arr[i]);
        }
        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        input_arr = input.split(",");
        int[] nums2 = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            nums2[i] = Integer.parseInt(input_arr[i]);
        }
        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        input_arr = input.split(",");

        int[] nums3 = new int[input_arr.length];
        for (int i = 0; i < input_arr.length; i++) {
            nums3[i] = Integer.parseInt(input_arr[i]);
        }

        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        input_arr = input.split(",");

        int[] nums4 = new int[input_arr.length];
        for (int i = 0; i < input_arr.length; i++) {
            nums4[i] = Integer.parseInt(input_arr[i]);
        }

        int ans = fourSumCount(nums1, nums2, nums3, nums4);
        System.out.println(ans);
        sc.close();
    }
}
