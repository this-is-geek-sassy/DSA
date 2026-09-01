// https://www.geeksforgeeks.org/problems/rod-cutting0840/1

import java.util.Arrays;
import java.util.Scanner;

public class RodCutting {

    private static int helper (int[] price, int remainingLen, int i, int[][] memory) {

        if (remainingLen == 0) {
            return 0;
        }
        if (remainingLen < 0 || i == price.length+1) {
            return Integer.MIN_VALUE;
        }
        if (memory[remainingLen][i] != -1)
            return memory[remainingLen][i];
        
        memory[remainingLen][i] = Math.max(price[i-1] + helper(price, remainingLen-i, i, memory), helper(price, remainingLen, i+1, memory));
        return memory[remainingLen][i];
    }

    public static int cutRod (int[] price) {
        int[][] memory = new int[price.length+1][price.length+1];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(price, price.length, 1, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        // int k = sc.nextInt();

        int i = 0;
        int[] price = new int[inp_arr.length];
        for (String s : inp_arr) {
            price[i++] = Integer.parseInt(s);
        }
        int ans = cutRod(price);
        System.out.println(ans);
        sc.close();
    }
}
