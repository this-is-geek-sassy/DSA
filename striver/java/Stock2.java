// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/

import java.util.Arrays;
import java.util.Scanner;

public class Stock2 {

    private static int helper (int[] prices, int i, boolean holding, int[][] memory) {

        if (i == prices.length)
            return 0;

        int a = holding? 1 : 0;
        if (memory[a][i] != Integer.MIN_VALUE) {
            return memory[a][i];
        }
        if (holding) {
            memory[a][i] = Math.max(prices[i] + helper(prices, i, false, memory), helper(prices, i+1, holding, memory));
        } else {
            memory[a][i] = Math.max(helper(prices, i+1, holding, memory), -prices[i] + helper(prices, i+1, true, memory));
        }
        return memory[a][i];
    }

    public static int maxProfit(int[] prices) {
        int[][] memory = new int[2][prices.length];
        for (int[] m: memory) {
            Arrays.fill(m, Integer.MIN_VALUE);
        }
        return helper(prices, 0, false, memory);
    }

    public static int maxProfitBottomUp(int[] prices) {

        int[][] memory = new int[2][prices.length];
        memory[0][prices.length-1] = 0;
        memory[1][prices.length-1] = prices[prices.length-1];

        for (int i = prices.length-2; i>=0; i--) {
            memory[0][i] = Math.max(memory[0][i+1], -prices[i] + memory[1][i+1]);
            memory[1][i] = Math.max(memory[1][i+1], prices[i] + memory[0][i+1]);
        }
        return memory[0][0];
    }
    private static int getNext (int k) {
        return (k+1)%2;
    }
    public static int maxProfitBottomUpSpaceOptim(int[] prices) {

        int[][] memory = new int[2][2];
        memory[0][1] = 0;
        memory[1][1] = prices[prices.length-1];

        int k = 0;
        for (int i = prices.length-2; i>=0; i--) {
            memory[0][k] = Math.max(memory[0][getNext(k)], -prices[i] + memory[1][getNext(k)]);
            memory[1][k] = Math.max(memory[1][getNext(k)], prices[i] + memory[0][getNext(k)]);
            k = getNext(k);
        }
        return memory[0][getNext(k)];
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
