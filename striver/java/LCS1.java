
import java.util.*;

// https://leetcode.com/problems/longest-common-subsequence/description/

public class LCS1 {

    private static int helper (String text1, String text2, int i, int j, int[][] memory) {

        if (i >= text1.length() || j >= text2.length())
            return 0;
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (text1.charAt(i) == text2.charAt(j)) {
            memory[i][j] = 1 + helper(text1, text2, i+1, j+1, memory);
        } else {
            memory[i][j] = Math.max(helper(text1, text2, i+1, j, memory), helper(text1, text2, i, j+1, memory));
        }
        return memory[i][j];
    }
    public static int longestCommonSubsequence(String text1, String text2) {
        int[][] memory = new int[text1.length()][text2.length()];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(text1, text2, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text1 = sc.nextLine();
        String text2 = sc.nextLine();
        int ans = longestCommonSubsequence(text1, text2);
        System.out.println(ans);
        sc.close();
    }
}
