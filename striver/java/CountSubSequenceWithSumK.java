
import java.util.Scanner;

// link: https://takeuforward.org/data-structure/count-all-subsequences-with-sum-k

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

    public static int checkSubsequenceSum (int[] arr, int k) {

        int count = helper(arr, k, 0, 0, 0);
        return count;
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
        int ans = checkSubsequenceSum(arr, k);
        System.out.println(ans);
    }
}
