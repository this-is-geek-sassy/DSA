
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// link: https://leetcode.com/problems/generate-parentheses/description/

public class GenerateParanthesis2 {

    private static void helper (int open, int close, int n, List<String> ans, String s) {

        if (open == n && close == n) {
            ans.add(s);
            return;
        }
        if (open < n) {
            helper(open + 1, close, n, ans, s + "(");
        }
        if (close < open) {
            helper(open, close+1, n, ans, s + ")");
        }
    }
    public static List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        helper(0, 0, n, ans, "");
        return ans;
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        List<String> ans = generateParenthesis(n);
        // System.out.println(new StringBuilder().toString().getClass());
        System.out.println(ans);
        sc.close();
    }
}
