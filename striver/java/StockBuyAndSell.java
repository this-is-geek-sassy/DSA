
// link: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/

import java.util.Scanner;

public class StockBuyAndSell {

    // ============ 1D DP Approach (bottom-up iterative) ============
    // DP state: dp[i] = best profit considering prices up to day i
    // Space: O(n), Time: O(n)
    // No recursion = no stack overflow!
    public static int maxProfit1DDP(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        int n = prices.length;
        
        // dp[i] = max profit we can get by day i
        int[] dp = new int[n];
        dp[0] = 0; // No profit on day 0 (need to buy first)
        
        int minPrice = prices[0];
        
        // For each day, decide: sell today or skip today?
        for (int i = 1; i < n; i++) {
            // Option 1: Sell on day i (profit = price[i] - min price before i)
            int sellToday = prices[i] - minPrice;
            
            // Option 2: Skip day i (use best profit from day i-1)
            int skipDay = dp[i - 1];
            
            // Take the best option
            dp[i] = Math.max(sellToday, skipDay);
            
            // Update minimum price seen so far
            minPrice = Math.min(minPrice, prices[i]);
        }
        
        return dp[n - 1];
    }

    public static int maxProfitRec (int[] prices, int start, int end) {

        if (prices.length == 1)
            return 0;
        
        int profit = prices[end] - prices[start];
        if (end - start == 1) {
            if (profit > 0)
                return profit;
            else
                return 0;
        }
        int profit_wo_start = maxProfitRec(prices, start+1, end);
        int profit_wo_end = maxProfitRec(prices, start, end-1);
        // if (profit < 0) {
        //     return profit_wo_start;
        // }
        if (profit > profit_wo_start && profit > profit_wo_end)
            return profit;
        else if (profit_wo_end > profit && profit_wo_end > profit_wo_start)
            return profit_wo_end;
        else
            return profit_wo_start;
    }

    public static int maxProfitDriver (int[] prices, int start, int end, int[][] memo) {

        if (start == end) {
            return memo[start][end];
        }
        if (memo[start][end] != 0)
            return memo[start][end];

        int profit = prices[end] - prices[start];
        
        if (end - start == 1) {
            int max_profit = (memo[start][end] > profit) ? memo[start][end] : profit;
            memo[start][end] = max_profit;
            return max_profit;
        }
        int profit_wo_start = maxProfitDriver(prices, start+1, end, memo);
        int profit_wo_end = maxProfitDriver(prices, start, end-1, memo);

        memo[start+1][end] = profit_wo_start;
        memo[start][end-1] = profit_wo_end;

        if (profit > profit_wo_start && profit > profit_wo_end)
            memo[start][end] = profit;
        else if (profit_wo_end > profit && profit_wo_end > profit_wo_start)
            memo[start][end] = profit_wo_end;
        else
            memo[start][end] = profit_wo_start;

        return memo[start][end];
    }

    public static int maxProfitDyn (int[] prices, int start, int end) {

        if (prices.length == 1) {
            return 0;
        }
        int[][] memo = new int[prices.length][prices.length];

        // do something
        memo[start][end] = maxProfitDriver(prices, start, end, memo);

        return memo[start][end];
    }

    public static int maxProfit(int[] prices) {
        
        int[] profits = new int[prices.length];

        int min_price_so_far = Integer.MAX_VALUE, max_profit = Integer.MIN_VALUE;

        for (int i = 0; i < prices.length; i++) {
            
            if (prices[i] < min_price_so_far) {
                min_price_so_far = prices[i];
                profits[i] = 0;
            }
            else {
                profits[i] = prices[i] - min_price_so_far;
            }
            if (profits[i] > max_profit)
                max_profit = profits[i];
        }
        return max_profit;
    }
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);

        String input = s.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] stocks = input.split(",");
        int[] prices = new int[stocks.length];

        int i = 0;
        for (String n : stocks) {
            prices[i] = Integer.parseInt(n);
            i++;
            
        }
        System.out.println(prices.length);
        System.out.println(maxProfit1DDP(prices));
        s.close();
    }
}
