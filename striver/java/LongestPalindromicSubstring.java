
import java.util.Scanner;

public class LongestPalindromicSubstring {

    private static boolean isPalindrome(String s, int left, int right) {
        
        // int left = 0, right = s.length()-1;
        while (left <= right) {
            if (s.charAt(left) != s.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }

    public static String longestPalindrome(String s) {
        int largest_len = 0, left = 0, right = s.length()-1;
        String sb = "";
        
        while (left <= s.length()-1) {
            while ((right - left + 1) > largest_len) {
                if (isPalindrome(s, left, right)) {
                    largest_len = right - left + 1;
                    sb = s.substring(left, right+1);
                    break;
                }
                right--;
            }
            left++;
            right = s.length()-1;
        }
        return sb;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        String ans = longestPalindrome(input);
        System.out.println(ans);
        sc.close();
    }
}
