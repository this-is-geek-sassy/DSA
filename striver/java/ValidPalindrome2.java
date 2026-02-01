
import java.util.Scanner;



// link: https://leetcode.com/problems/valid-palindrome/description/

public class ValidPalindrome2 {

    public static boolean checkPal(String s) {

        if (s.length()==0 || s.length()==1)
            return true;
        if (s.charAt(0) != s.charAt(s.length()-1))
            return false;
        return checkPal(s.substring(1, s.length()-1));
    }

    public static boolean isPalindrome(String s) {

        s = s.replaceAll("[^a-zA-Z0-9]", "");
        s = s.toLowerCase();

        return checkPal(s);
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        System.out.println(isPalindrome(input));
        s.close();
    }
}
