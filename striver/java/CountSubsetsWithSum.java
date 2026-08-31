// https://www.geeksforgeeks.org/problems/perfect-sum-problem5633/1

import java.util.Arrays;
import java.util.Scanner;

public class CountSubsetsWithSum {

    private static int helper (int[] arr, int target, int runningSum, int runningIdx, int[][] memory) {

        if (runningIdx == arr.length && runningSum == target)
            return 1;

        if (runningIdx == arr.length)
            return 0;

        // if (runningSum == target)
        //     return 1;
        if (memory[runningIdx][runningSum] != -1)
            return memory[runningIdx][runningSum];

        memory[runningIdx][runningSum] = helper(arr, target, runningSum + arr[runningIdx], runningIdx + 1, memory) + helper(arr, target, runningSum, runningIdx + 1, memory);
        return memory[runningIdx][runningSum];
    }
    public static int perfectSum (int[] arr, int target) {
        int total = 0;
        for (int n: arr) {
            total += n;
        }
        int[][] memory = new int[arr.length][total+1];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(arr, target, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        // int k = sc.nextInt();

        int i = 0;
        int[] arr = new int[inp_arr.length];
        for (String s : inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        int target = sc.nextInt();
        System.out.println(perfectSum(arr, target));
        sc.close();
    }
}
