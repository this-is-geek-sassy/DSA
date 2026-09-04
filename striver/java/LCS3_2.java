// https://www.geeksforgeeks.org/problems/longest-common-substring1452/1
// https://takeuforward.org/data-structure/longest-common-substring-dp-27

import java.util.Arrays;
import java.util.Scanner;

public class LCS3_2 {

    private static int helper (String s1, String s2, int i, int j, int[][] memory) {
        // return longest common substring of s1, s2 ending exactly at s1[i] & s2[j]

        if (i<0 || j<0) {
            return 0;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (s1.charAt(i) == s2.charAt(j)) {
            memory[i][j] = 1 + helper(s1, s2, i-1, j-1, memory);
        } else {
            memory[i][j] = 0;
        }
        return memory[i][j];
    }
    private static int findMax (String s1, String s2, int i, int j, int[][] memory) {

        // if (i < 0 || j < 0) {
        //     return 0;
        // }
        // int currentLen = helper(s1, s2, i, j, memory);

        // return Math.max(currentLen, 
        //     Math.max(findMax(s1, s2, i-1, j, memory), findMax(s1, s2, i, j-1, memory))
        // );
        int maxVal = Integer.MIN_VALUE;
        for (int a = 0; a < s1.length(); a++) {
            for (int b = 0; b < s2.length(); b++) {
                memory[a][b] = helper(s1, s2, a, b, memory);
                maxVal = Math.max(maxVal, memory[a][b]);
            }
        }
        return maxVal;
    }
    public static int longestCommonSubstring (String s1, String s2) {
        int[][] memory = new int[s1.length()][s2.length()];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return findMax(s1, s2, s1.length()-1, s2.length()-1, memory);
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
