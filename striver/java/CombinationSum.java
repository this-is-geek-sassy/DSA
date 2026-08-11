// https://leetcode.com/problems/combination-sum/description/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CombinationSum {

    private static void helper (int[] mainArr, int k, int n, int runningSum, List<Integer> runningList, List<List<Integer>> mainList) {

        // if (n == mainArr.length) {
            // return (runningSum == k) ? 1 : 0;
        if (runningSum == k) {
            mainList.add(new ArrayList<>(runningList));
            return;
        }
        if (n == mainArr.length || runningSum > k)
            return;
        // }
        // if (runningSum == k) {
        //     count++;
        //     // return count;
        // }

        // notTake
        helper(mainArr, k, n+1, runningSum, runningList, mainList);
        // count = helper(mainArr, k, n+1, runningSum, count);

        runningList.add(mainArr[n]);
        //take = 
        helper(mainArr, k, n, runningSum + mainArr[n], runningList, mainList);
        runningList.remove(runningList.size() - 1);
        // count = helper(mainArr, k, n+1, runningSum + mainArr[n], count);
        // return notTake + take;
        // notTake.addAll(take);
        // return take;
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        
        List<List<Integer>> mainList = new ArrayList<>();
        helper(candidates, target, 0, 0, new ArrayList<>(), mainList);
        return new ArrayList<>(mainList);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");
        int k = sc.nextInt();

        int i = 0;
        int[] arr = new int[inp_arr.length];
        for (String s : inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        List<List<Integer>> ans = combinationSum(arr, k);
        System.out.println(ans);
    }
}
