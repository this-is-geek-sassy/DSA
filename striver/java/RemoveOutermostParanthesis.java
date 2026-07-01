
// link: https://leetcode.com/problems/remove-outermost-parentheses/description/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class RemoveOutermostParanthesis {
    public static String removeOuterParentheses(String s) {
        ArrayList<String> decomposition = new ArrayList<>();
        Deque<Character> st = new ArrayDeque<>();
        String part = "";

        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == '(') {
                st.push('(');
                part += "(";
            } else {
                st.pop();
                part += ")";
            }
            if (st.isEmpty()) {
                decomposition.add(part);
                part = "";
            }
        }
        // int i = 0;
        String ans = "";
        for (String str : decomposition) {
            // System.out.print(str + " ");
            String temp = str.substring(1, str.length()-1);
            ans += temp;
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();

        String ans = removeOuterParentheses(input);
        System.out.println(ans);
        sc.close();
    }
}
