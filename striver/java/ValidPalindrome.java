
import java.util.Scanner;



// link: https://leetcode.com/problems/valid-palindrome/description/

public class ValidPalindrome {

    public static boolean checkPal(String s) {

        if (s.length()==0 || s.length()==1)
            return true;
        if (s.charAt(0) != s.charAt(s.length()-1))
            return false;
        return checkPal(s.substring(1, s.length()-1));
    }

    public static boolean isPalindrome(String s) {

        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        s = sb.toString();
        s = s.toLowerCase();

        return checkPal(s);
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
    }
}
