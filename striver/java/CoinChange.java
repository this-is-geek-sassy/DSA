
import java.util.Scanner;

// https://leetcode.com/problems/coin-change/description/

public class CoinChange {

    private static int helper (int[] coins, int amount, int remainingAmount, int[] memory) {

        if (remainingAmount < 0) {
            return Integer.MAX_VALUE;
        }
        if (remainingAmount <= 0) {
            return 0;
        }
        if (memory[remainingAmount] != 0)
            return memory[remainingAmount];

        int minAns = Integer.MAX_VALUE;
        for (int i=0; i < coins.length; i++) {
            // minAns = Math.min(minAns, 1 + helper(coins, amount, remainingAmount - coins[i]));
            int result = helper(coins, amount, remainingAmount - coins[i], memory);
            if (result != Integer.MAX_VALUE) {
                result++;
            }
            minAns = Math.min(result, minAns);
        }
        memory[remainingAmount] = minAns;
        return memory[remainingAmount];
    }
    public static int coinChange(int[] coins, int amount) {
        
        int[] memory = new int[amount+1];
        int ans = helper(coins, amount, amount, memory);
        if (ans == Integer.MAX_VALUE) {
            return -1;
        } else {
            return ans;
        }
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
        int ans = coinChange(coins, amount);
        System.out.println(ans);
        sc.close();
    }
}
