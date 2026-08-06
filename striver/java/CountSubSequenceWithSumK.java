
import java.util.Scanner;

// link: https://takeuforward.org/data-structure/count-all-subsequences-with-sum-k
// https://www.geeksforgeeks.org/problems/check-if-there-exists-a-subsequence-with-sum-k/1

public class CountSubSequenceWithSumK {

    private static int helper (int[] mainArr, int k, int n, int runningSum, int count) {

        if (n == mainArr.length) {
            // return (runningSum == k) ? 1 : 0;
            return (runningSum == k) ? ++count : count;
        }
        // if (runningSum == k) {
        //     count++;
        //     // return count;
        // }

        // int notTake = helper(mainArr, k, n+1, runningSum);
        count = helper(mainArr, k, n+1, runningSum, count);
        // int take = helper(mainArr, k, n+1, runningSum + mainArr[n]);
        count = helper(mainArr, k, n+1, runningSum + mainArr[n], count);
        // return notTake + take;
        return count;
    }

    public static int countSubsequenceSum (int[] arr, int k) {

        int count = helper(arr, k, 0, 0, 0);
        return count;
    }
    private static boolean helper2 (int[] mainArr, int k, int n, int runningSum) {

        if (k == runningSum)
            return true;
        if (n == mainArr.length)
            return false;

        return helper2(mainArr, k, n+1, runningSum) || helper2(mainArr, k, n+1, runningSum + mainArr[n]);
    }
    public static boolean checkSubsequenceSum(int[] arr, int k) {
        // code here
        return helper2(arr, k, 0, 0);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        int k = sc.nextInt();

        int i = 0;
        int[] arr = new int[inp_arr.length];
        for (String s : inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        boolean ans = checkSubsequenceSum(arr, k);
        System.out.println(ans);
    }
}
