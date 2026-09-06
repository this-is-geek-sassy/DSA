// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/

import java.util.Arrays;
import java.util.Scanner;

public class Stock4 {

    private static int helper (int[] prices, int i, boolean holding, int[][][] memory, int txnCount, int k) {

        if (i == prices.length)
            return 0;
        if (txnCount == k) {
            return 0;
        }
        int a = holding? 1 : 0;
        if (memory[a][txnCount][i] != Integer.MIN_VALUE) {
            return memory[a][txnCount][i];
        }
        if (holding) {
            memory[a][txnCount][i] = Math.max(prices[i] + helper(prices, i+1, false, memory, txnCount+1, k), helper(prices, i+1, holding, memory, txnCount, k));
        } else {
            memory[a][txnCount][i] = Math.max(helper(prices, i+1, holding, memory, txnCount, k), -prices[i] + helper(prices, i+1, true, memory, txnCount, k));
        }
        return memory[a][txnCount][i];
    }

    public static int maxProfit(int[] prices, int k) {
        int[][][] memory = new int[2][k][prices.length];
        for (int[][] _m: memory) {
            for (int[] m: _m)
                Arrays.fill(m, Integer.MIN_VALUE);
        }
        return helper(prices, 0, false, memory, 0, k);
    }
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        
        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(",");

        int[] prices = new int[inpArr.length];
        int i = 0;
        for (String s: inpArr) {
            prices[i++] = Integer.parseInt(s);
        }
        int k = sc.nextInt();
        int ans = maxProfit(prices, k);
        System.out.println(ans);
        sc.close();
    }
}
