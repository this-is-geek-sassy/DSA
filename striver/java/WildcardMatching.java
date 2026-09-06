
import java.util.Arrays;
import java.util.Scanner;

// https://leetcode.com/problems/wildcard-matching/description/

public class WildcardMatching {
    private static boolean helper (String s, String p, int i, int j, char[][] memory) {

        if (j == p.length() && i == s.length()) {
            // fully consumed p & s
            return true;
        }
        if (j == p.length()) {
            // fully consumed p but could not consume s
            return false;
        }
        if (memory[i][j] != (char)2) {
            return memory[i][j] == (char)1;
        }
        if (i == s.length() && j < p.length()) {
            if (p.charAt(j) == '*')
                memory[i][j] = (helper(s, p, i, j+1, memory) == true)? (char)1 : 0;
            else {
                memory[i][j] = 0;
                return false;
            }
        }
        else if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            memory[i][j] = (helper(s, p, i+1, j+1, memory) == true)? (char)1 : 0;
        }
        else if (p.charAt(j) == '*') {
            // for (int k = i; k <= s.length(); k++) {
            //     // skip some characters
            //     boolean letSee = helper(s, p, k, j+1, memory);
            //     memory[i][j] = letSee;
            //     if (letSee == true)
            //         return true;
            // }
            // memory[i][j] = false;
            memory[i][j] = (helper(s, p, i, j+1, memory) == true || helper(s, p, i+1, j, memory) == true)? (char)1 : 0;
        } else {
            memory[i][j] = 0;
        }
        return memory[i][j]==1;
    }

    public static boolean isMatch(String s, String p) {
        char[][] memory = new char[s.length()+1][p.length()+1];
        for (char[] m: memory) {
            Arrays.fill(m, (char)2);
        }
        // System.out.println(memory[0][0]);
        return helper(s, p, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String p = sc.nextLine();
        boolean ans = isMatch(s, p);
        System.out.println(ans);
        sc.close();
    }
}
