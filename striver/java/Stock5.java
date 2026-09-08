// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/

import java.util.Arrays;
import java.util.Scanner;

public class Stock5 {

    private static int helper (int[] prices, int i, boolean holding, int[][] memory, int k) {

        if (i >= prices.length)
            return 0;
        // if (txnCount == k) {
        //     return 0;
        // }
        int a = holding? 1 : 0;
        if (memory[a][i] != Integer.MIN_VALUE) {
            return memory[a][i];
        }
        if (holding) {
            memory[a][i] = Math.max(prices[i] + helper(prices, i+2, false, memory, k), helper(prices, i+1, holding, memory, k));
        } else {
            memory[a][i] = Math.max(helper(prices, i+1, holding, memory, k), -prices[i] + helper(prices, i+1, true, memory, k));
        }
        return memory[a][i];
    }
    public static int maxProfit(int[] prices) {
        int[][] memory = new int[2][prices.length];
        for (int[] m: memory) {
            Arrays.fill(m, Integer.MIN_VALUE);
        }
        return helper(prices, 0, false, memory, 0);
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
        // int k = sc.nextInt();
        int ans = maxProfit(prices);
        System.out.println(ans);
        sc.close();
    }
}
