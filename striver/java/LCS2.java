
import java.util.Scanner;

// https://takeuforward.org/data-structure/print-longest-common-subsequence-dp-26

public class LCS2 {
    private static String helper (String text1, String text2, int i, int j, String[][] memory) {

        if (i >= text1.length() || j >= text2.length())
            return "";
        if (memory[i][j] != null) {
            return memory[i][j];
        }
        if (text1.charAt(i) == text2.charAt(j)) {
            memory[i][j] = text1.charAt(i) + helper(text1, text2, i+1, j+1, memory);
        } else {
            // memory[i][j] =
            String s1 = helper(text1, text2, i+1, j, memory);
            String s2 = helper(text1, text2, i, j+1, memory);
            memory[i][j] = (s1.length() >= s2.length())? s1 : s2;
        }
        return memory[i][j];
    }

    public static String longestCommonSubsequence (String text1, String text2) {
        String[][] memory = new String[text1.length()][text2.length()];
        return helper(text1, text2, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text1 = sc.nextLine();
        String text2 = sc.nextLine();
        String ans = longestCommonSubsequence(text1, text2);
        System.out.println(ans);
        sc.close();
    }
}
