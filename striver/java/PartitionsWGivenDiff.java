// https://www.geeksforgeeks.org/problems/partitions-with-given-difference/1

import java.util.Arrays;
import java.util.Scanner;

public class PartitionsWGivenDiff {

    private static int helper (int[] arr, int diff, int runningIdx, int runningSum, int total, int[][] memory) {

        if (runningIdx == arr.length && runningSum == (total + diff)/2) {
            return 1;
        }
        if (runningIdx == arr.length) {
            return 0;
        }
        if (memory[runningIdx][runningSum] != -1) {
            return memory[runningIdx][runningSum];
        }
        memory[runningIdx][runningSum] = helper(arr, diff, runningIdx + 1, runningSum + arr[runningIdx], total, memory) + helper(arr, diff, runningIdx+1, runningSum, total, memory);
        return memory[runningIdx][runningSum];
    }
    public static int countPartitions (int[] arr, int diff) {
        int total=0;
        for (int n: arr) {
            total += n;
        }
        if ((total + diff) % 2 != 0)
            return 0;
        int[][] memory = new int[arr.length][total+1];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(arr, diff, 0, 0, total, memory);
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
        System.out.println(countPartitions(arr, target));
        sc.close();
    }
}
