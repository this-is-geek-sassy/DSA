
import java.util.Arrays;
import java.util.Scanner;

// https://leetcode.com/problems/distinct-subsequences/

public class DistinctSubseq {
    private static int helper (String s, String t, int i, int j, int[][] memory) {

        if (j == t.length()) {
            // fully consumed string t
            return 1;
        }
        if (i == s.length() && j < t.length()) {
            // consumed string s but could not consume t
            return 0;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (s.charAt(i) != t.charAt(j)) {
            memory[i][j] = helper(s, t, i+1, j, memory);
        }
        else {
            memory[i][j] = helper(s, t, i+1, j+1, memory) + helper(s, t, i+1, j, memory);
        }
        return memory[i][j];
    }
    public static int numDistinct(String s, String t) {
        if (t.length() > s.length())
            return 0;
        int[][] memory = new int[s.length()][t.length()];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(s, t, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String t = sc.nextLine();
        int ans = numDistinct(s, t);
        System.out.println(ans);
        sc.close();
    }
}
