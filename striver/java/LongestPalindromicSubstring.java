
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

    public static String longestPalindromeAlt(String s) {

        int largest_len=0;
        String ans = "";
        // odd centers
        for (int i=0; i<s.length(); i++) {
            Character center = s.charAt(i);
            int left = i-1, right = i+1;
            // StringBuilder palin = new StringBuilder(center.toString());

            while (left>=0 && right<=s.length()-1) {
                if (s.charAt(left) == s.charAt(right)) {
                    // palin.append(s.charAt(right));
                    // palin.insert(0, s.charAt(left));
                    left--;
                    right++;
                } else {
                    break;
                }
            }
            if ((--right-++left+1) > largest_len && right <= s.length()-1 && left>=0) {
                ans = s.substring(left, right+1);
                largest_len = (right-left+1);
            }
            // System.out.println("i = " + i);
        }
        // System.out.println("largest_len: " + largest_len);
        // even centers
        for (int i=0; i<s.length()-1; i++) {
            int left = i, right = i+1;
            // Character leftchar = s.charAt(left), rightchar = s.charAt(right);
            // StringBuilder palin = new StringBuilder();
            while (left>=0 && right<=s.length()-1) {
                if (s.charAt(left) != s.charAt(right))
                    break;
                // palin.append(s.charAt(right));
                // palin.insert(0, s.charAt(left));
                left--;
                right++;
            }
            if ((--right-++left+1) > largest_len && right <= s.length()-1 && left>=0) {
                ans = s.substring(left, right+1);
                largest_len = (right-left+1);
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        String ans = longestPalindromeAlt(input);
        System.out.println(ans);
        sc.close();
    }
}
