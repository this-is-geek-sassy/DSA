// https://leetcode.com/problems/longest-palindromic-subsequence/

import java.util.Arrays;
import java.util.Scanner;

public class LPS {

    private static int helper (String s, int i, int j, int[][] memory) {
        if (i > j) {
            return 0;
        }
        if (i == j && s.charAt(i) == s.charAt(j)) {
            return 1;
        }
        if (i == j) {
            return 0;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (s.charAt(i) == s.charAt(j)) {
            memory[i][j] = 2 + helper(s, i+1, j-1, memory);
        }
        else
            memory[i][j] = Math.max(helper(s, i+1, j, memory), helper(s, i, j-1, memory));
        return memory[i][j];
    }
    public static int longestPalindromeSubseq(String s) {
        int[][] memory = new int[s.length()][s.length()];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(s, 0, s.length()-1, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text1 = sc.nextLine();
        // String text2 = sc.nextLine();
        int ans = longestPalindromeSubseq(text1);
        System.out.println(ans);
        sc.close();
    }
}
