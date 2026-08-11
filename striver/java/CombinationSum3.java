
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// link: https://leetcode.com/problems/combination-sum-iii/


public class CombinationSum3 {

    private static void helper (List<Integer> nums, int k, int n, int idx, int runningSum, int runningCount, List<Integer> runningList, List<List<Integer>> ans) {

        if (runningSum == n && runningCount == k) {
            ans.add(new ArrayList<>(runningList));
            return;
        }
        if (idx == nums.size() || runningCount > k || runningSum > n)
            return;

        //not take
        helper(nums, k, n, idx+1, runningSum, runningCount, runningList, ans);

        // take
        runningList.add(nums.get(idx));
        helper(nums, k, n, idx+1, runningSum + nums.get(idx), ++runningCount, runningList, ans);
        runningList.remove(runningList.size()-1);
    }
    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<Integer> nums = List.of(1,2,3,4,5,6,7,8,9);
        List<List<Integer>> ans = new ArrayList<>();
        helper(nums, k, n, 0, 0, 0, new ArrayList<>(), ans);
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int k = sc.nextInt();
        int n = sc.nextInt();
        List<List<Integer>> ans = combinationSum3(k, n);
        System.out.println(ans);
        sc.close();
    }
}
