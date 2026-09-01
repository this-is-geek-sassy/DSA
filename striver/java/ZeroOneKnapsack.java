// https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

import java.util.Arrays;
import java.util.Scanner;

public class ZeroOneKnapsack {

    private static int helper (int[] val, int[] wt, int remainingCapacity, int idx, int[][] memory) {

        if (remainingCapacity < 0) {
            return Integer.MIN_VALUE;
        }
        if (remainingCapacity == 0) {
            return 0;
        }
        if (idx == val.length) {
            return 0;
        }
        if (memory[remainingCapacity][idx] != -1) {
            return memory[remainingCapacity][idx];
        }
        memory[remainingCapacity][idx] = Math.max(val[idx] + helper(val, wt, remainingCapacity - wt[idx], idx + 1, memory), helper(val, wt, remainingCapacity, idx+1, memory));

        return memory[remainingCapacity][idx];
    }

    public static int knapSack(int val[], int wt[], int capacity) {
        // code here
        // int totalPossibleProfit = 0;

        // for (int v: val) {
        //     totalPossibleProfit += v;
        // }
        int[][] memory = new int[capacity+1][val.length+1];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        // int[] memory = new int[val.length+1];
        // Arrays.fill(memory, -1);
        return helper(val, wt, capacity, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        // int k = sc.nextInt();

        int i = 0;
        int[] val = new int[inp_arr.length];
        for (String s : inp_arr) {
            val[i++] = Integer.parseInt(s);
        }
        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        inp_arr = input.split(", ");
        i = 0;
        int[] wt = new int[inp_arr.length];
        for (String s : inp_arr) {
            wt[i++] = Integer.parseInt(s);
        }
        int capacity = sc.nextInt();

        int ans = knapSack(val, wt, capacity);
        System.out.println(ans);
        sc.close();
    }
}
