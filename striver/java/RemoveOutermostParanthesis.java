
// link: https://leetcode.com/problems/remove-outermost-parentheses/description/

import java.util.Scanner;

public class RemoveOutermostParanthesis {
    public static String removeOuterParentheses(String s) {
        // ArrayList<StringBuilder> decomposition = new ArrayList<>();
        // Deque<Character> st = new ArrayDeque<>();
        int count = 0;
        StringBuilder ans = new StringBuilder();
        StringBuilder part = new StringBuilder();

        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == '(') {
                // st.push('(');
                count++;
                part.append("(");
            } else {
                // st.pop();
                count--;
                part.append(")");
            }
            if (count == 0) {
                // decomposition.add(part);
                part.deleteCharAt(0);
                part.deleteCharAt(part.length()-1);
                ans.append(part);
                part = new StringBuilder();
            }
        }
        return ans.toString();
        // int i = 0;
        // String ans = "";
        // for (StringBuilder str : decomposition) {
        //     // System.out.print(str + " ");
        //     String temp = str.substring(1, str.length()-1);
        //     ans += temp;
        // }
        // return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();

        String ans = removeOuterParentheses(input);
        System.out.println(ans);
        sc.close();
    }
}
