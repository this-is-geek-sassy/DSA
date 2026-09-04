// https://takeuforward.org/data-structure/longest-common-substring-dp-27
// https://www.geeksforgeeks.org/problems/longest-common-substring1452/1

import java.util.Arrays;
import java.util.Scanner;

public class LCS3 {

    private static int helper (String s1, String s2, int i, int j, int globalMax, int currentLen, int[][] memory) {

        if (i >= s1.length() || j >= s2.length()) {
            return globalMax;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (s1.charAt(i) == s2.charAt(j)) {
            currentLen++;
            globalMax = (currentLen > globalMax) ? currentLen : globalMax;
            int altLen = Math.max(helper(s1, s2, i+1, j, globalMax, currentLen-1, memory), helper(s1, s2, i, j+1, globalMax, currentLen-1, memory));
            currentLen = helper(s1, s2, i+1, j+1, globalMax, currentLen, memory);
            
            memory[i][j] = Math.max(globalMax, Math.max(altLen, currentLen));
        }
        else {
            // maxLengthFoundTillNow = 0;
            currentLen = 0;
            memory[i][j] = Math.max(globalMax, Math.max(helper(s1, s2, i, j+1, globalMax, currentLen, memory), helper(s1, s2, i+1, j, globalMax, currentLen, memory)));
        }
        return memory[i][j];
    }
    public static int longestCommonSubstring (String s1, String s2) {
        int[][] memory = new int[s1.length()][s2.length()];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(s1, s2, 0, 0, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text1 = sc.nextLine();
        String text2 = sc.nextLine();
        int ans = longestCommonSubstring(text1, text2);
        System.out.println(ans);
        sc.close();
    }
}
