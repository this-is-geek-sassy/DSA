// https://leetcode.com/problems/coin-change-ii/description/

import java.util.*;

public class CoinChange2 {

    private static int helper (int[] coins, int amount, int remainingAmount, int index, int[][] memory) {

        if (remainingAmount == 0) {
            return 1;
        }
        if (remainingAmount < 0) {
            return 0;
        }
        if (index == coins.length) 
            return 0;

        if (memory[remainingAmount][index] != -1) {
            return memory[remainingAmount][index];
        }
        // int ways = 0;
        // for (int i=0; i<coins.length; i++) {
        //     ways += helper(coins, amount, remainingAmount - coins[i]);
        // }
        // return ways;
        memory[remainingAmount][index] = helper(coins, amount, remainingAmount - coins[index], index, memory) + helper(coins, amount, remainingAmount, index+1, memory);
        return memory[remainingAmount][index];
    }

    public static int change(int amount, int[] coins) {
        
        int[][] memory = new int[amount+1][coins.length];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(coins, amount, amount, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");
        // int k = sc.nextInt();

        int i = 0;
        int[] coins = new int[inp_arr.length];
        for (String s : inp_arr) {
            coins[i++] = Integer.parseInt(s);
        }
        int amount = sc.nextInt();
        int ans = change(amount, coins);
        System.out.println(ans);
        sc.close();
    }
}
