
import java.util.Scanner;

// link: https://takeuforward.org/data-structure/subset-sum-equal-to-target-dp-14

public class SubsetSum1 {
    
    private static boolean helper (int[] arr, int k, int runningSum, int runningIdx, int[][] memory) {

        if (runningIdx == arr.length-1) {
            if (runningSum + arr[runningIdx] == k)
                return true;
            else if (runningSum == k)
                return true;
            else
                return false;
        }
        if (runningSum > k)
            return false;

        if (runningSum <= k && memory[runningIdx][runningSum] != 0)
            return memory[runningIdx][runningSum] == 1;

        boolean optionA = helper(arr, k, runningSum + arr[runningIdx], runningIdx+1, memory);
        boolean optionB = helper(arr, k, runningSum, runningIdx+1, memory);

        if (optionA == true || optionB == true)
            memory[runningIdx][runningSum] = 1;
        else
            memory[runningIdx][runningSum] = -1;
        return memory[runningIdx][runningSum] == 1;
    }
    static boolean isSubsetSum(int arr[], int sum) {
        // code here
        // int allSum = 0;
        // for (int a: arr) {
        //     allSum += a;
        // }
        int[][] memory = new int[arr.length][sum+1];

        return helper (arr, sum, 0, 0, memory);
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] inpArr = input.trim()
                                // .substring(1, input.length()-1)
                                .split(" ");

        int[] arr = new int[inpArr.length];
        int i=0;
        for (String s: inpArr) {
            arr[i++] = Integer.parseInt(s);
        }
        int k = sc.nextInt();
        boolean ans = isSubsetSum(arr, k);
        System.out.println(ans);
        sc.close();
    }
}
