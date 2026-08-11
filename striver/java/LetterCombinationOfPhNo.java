
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// link: https://leetcode.com/problems/letter-combinations-of-a-phone-number/

public class LetterCombinationOfPhNo {
    
    private static String[] helper (String digits, List<String> phoneBook, int idx) {
        if (idx == digits.length()-1) {
            // base condition that ensures we are standing at the last index of input string
            int digit = digits.charAt(idx) - '0';
            String temp = phoneBook.get(digit);
            String[] temp_arr = temp.split("");
            return temp_arr;
        }
        String[] intermediate = helper(digits, phoneBook, idx+1);
        int digit = digits.charAt(idx) - '0';
        String temp = phoneBook.get(digit);
        String[] temp_arr = temp.split("");

        // logging
        // for (String t: intermediate)
        //     System.out.print(t + " ");
        // System.out.println();
        String[] ans = new String[intermediate.length * temp_arr.length];
        int i=0, j=0, k=0;
        for (i=0; i<temp_arr.length; i++) {
            for (j=0; j<intermediate.length; j++)
                ans[k++] = temp_arr[i] + intermediate[j];
        }
        return ans;
    }
    public static List<String> letterCombinations(String digits) {
        
        List<String> phoneBook = List.of(
            "",    // 0
            "",    // 1
            "abc", // 2
            "def", // 3
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz" // 9
        );
        String[] ans_ = helper(digits, phoneBook, 0);
        return Arrays.asList(ans_);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        List<String> ans = letterCombinations(input);
        System.out.println(ans);
    }
}
