
// link: https://leetcode.com/problems/two-sum/description/

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {

        Map<Integer, Integer> mp = new HashMap<>();
        int recedue;

        for (int i = 0; i < nums.length; i++) {
            recedue = target - nums[i];
            if (mp.containsKey(recedue)) {
                int target_idx = mp.get(recedue);
                int[] sum_idx = new int[2];
                sum_idx[0] = target_idx;
                sum_idx[1] = i;
                return sum_idx;
            } else {
                mp.put(nums[i], i);
            }
        }
        return new int[]{-1, -1};
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        int target = s.nextInt();
        input = input.trim().substring(1, input.length()-1);

        String[] arr = input.split(",");

        int[] nums = new int[arr.length];

        int i = 0;
        for (String elem : arr) {
            nums[i] = Integer.parseInt(elem);
            i++;
        }
        int[] idxs = twoSum(nums, target);
        for (int e : idxs) {
            System.out.print(e + " ");
        }
        System.out.println();
        s.close();
    }
}
