// link: https://leetcode.com/problems/subsets-ii/description/

import java.util.*;

public class SubsetsWithDup {

    private static void helper (int[] nums, int n, List<Integer> runningNums, List<List<Integer>> ans) {

        if (n == nums.length) {
            ans.add(new ArrayList<>(runningNums));
            return;
        }
        // not take
        int i = n;
        while (++i < nums.length && nums[i] == nums[n]);
        helper(nums, i, runningNums, ans);
        // take
        
        runningNums.add(nums[n]);
        helper(nums, n+1, runningNums, ans);
        runningNums.remove(runningNums.size()-1);
        
    }
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        helper(nums, 0, new ArrayList<>(), ans);
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] inp_arr = input.trim().substring(1, input.length()-1).split(", ");
        int i = 0;
        int[] arr = new int[inp_arr.length];
        for (String s: inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        List<List<Integer>> ans = subsetsWithDup(arr);
        System.out.println(ans);
        sc.close();
    }
}
