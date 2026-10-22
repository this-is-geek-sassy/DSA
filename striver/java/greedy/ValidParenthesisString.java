// https://leetcode.com/problems/valid-parenthesis-string/description/

package greedy;

import java.util.Scanner;

public class ValidParenthesisString {

    public static boolean checkValidString(String s) {
        
        int low = 0, high = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                low++;
                high++;
            }
            else if (s.charAt(i) == ')') {
                low--;
                high--;
            } else {
                low--;
                high++;
            }
            if (high < 0) {
                return false;
            }
            if (low < 0)
                low = 0;
        }
        return low == 0;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        boolean ans = checkValidString(s);
        System.out.println(s);
        sc.close();
    }
}
