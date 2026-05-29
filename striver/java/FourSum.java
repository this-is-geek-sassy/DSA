
// link: https://leetcode.com/problems/4sum/description/

import java.util.*;
// import java.util.Scanner;

public class FourSum {

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        
        Arrays.sort(nums);
        List <List<Integer>> ans = new ArrayList<>();

        int n = nums.length;

        for (int i=0; i < n-3; i++) {
            // skip duplicates for i
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int  j = i+1; j < n-2; j++) {
                // ski[ duplicates
                if (j > i+1 && nums[j] == nums[j-1])
                    continue;
                int left = j+1, right = n-1;

                while (left < right) {

                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        left++;    right--;
                        while (left < right && nums[left] == nums[left-1])
                            left++;
                        while (left < right && nums[right] == nums[right+1])
                            right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return ans;
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
        int target = sc.nextInt();

        List<List<Integer>> ans = fourSum(nums, target);
        System.out.println(ans);
        sc.close();
    }
}
