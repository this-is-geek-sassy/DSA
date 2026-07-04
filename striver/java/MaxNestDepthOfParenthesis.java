
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


// link: https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/description/

public class MaxNestDepthOfParenthesis {
    public static int maxDepth(String s) {
        int depth = 0;
        Deque<Character> d = new ArrayDeque<>();
        for (int i=0; i<s.length(); i++) {

            if (s.charAt(i) == '(') {
                d.push('(');
                depth = Math.max(depth, d.size());
            }
            else if (s.charAt(i) == ')')
                d.pop();
        }
        return depth;
    }
    public static int maxDepth2(String s) {
        int depth = 0;
        int count = 0;
        for (int i=0; i<s.length(); i++) {

            if (s.charAt(i) == '(') {
                count++;
                depth = Math.max(depth, count);
            }
            else if (s.charAt(i) == ')')
                count--;
        }
        return depth;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        sc.close();
    }
}
