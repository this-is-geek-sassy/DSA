// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/description/

import java.util.Arrays;
import java.util.Scanner;

public class Stock3 {

    private static int helper (int[] prices, int i, boolean holding, int[][][] memory, int txnCount) {

        if (i == prices.length)
            return 0;
        if (txnCount == 2) {
            return 0;
        }
        int a = holding? 1 : 0;
        if (memory[a][txnCount][i] != Integer.MIN_VALUE) {
            return memory[a][txnCount][i];
        }
        if (holding) {
            memory[a][txnCount][i] = Math.max(prices[i] + helper(prices, i+1, false, memory, txnCount+1), helper(prices, i+1, holding, memory, txnCount));
        } else {
            memory[a][txnCount][i] = Math.max(helper(prices, i+1, holding, memory, txnCount), -prices[i] + helper(prices, i+1, true, memory, txnCount));
        }
        return memory[a][txnCount][i];
    }
    public static int maxProfit(int[] prices) {
        int[][][] memory = new int[2][2][prices.length];
        for (int[][] _m: memory) {
            for (int[] m: _m)
                Arrays.fill(m, Integer.MIN_VALUE);
        }
        return helper(prices, 0, false, memory, 0);
    }

    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(",");

        int[] prices = new int[inpArr.length];
        int i=0;
        for (String s: inpArr) {
            prices[i++] = Integer.parseInt(s);
        }
        int ans = maxProfit(prices);
        System.out.println(ans);
        sc.close();
    }
}
