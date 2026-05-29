
// link: https://www.geeksforgeeks.org/problems/largest-subarray-with-0-sum/1

import java.util.*;


public class LargestSubArrayWithSumZero {

    public static int maxLength(int arr[]) {

        Map<Integer, Integer> map = new HashMap<>();
        int prefixSum = 0, longestLen = 0;
        map.put(prefixSum, -1);
        for (int i=0; i < arr.length; i++) {
            prefixSum += arr[i];

            // System.out.println("prefixSum: " + prefixSum);

            if (map.containsKey(prefixSum)) {
                // hint that I got a subarray sum with 0
                int len = i - (map.get(prefixSum));
                if (len > longestLen)
                    longestLen = len;
            } else {
                map.put(prefixSum, i);
            }
        }
        return longestLen;
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
        // int target = sc.nextInt();

        int ans = maxLength(nums);
        System.out.println(ans);
        sc.close();
    }
}
