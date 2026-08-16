
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// link: https://leetcode.com/problems/palindrome-partitioning/description/

public class PalindromePartitioning {

    private static boolean isPlaindrome (String s) {
        if (s.length() == 0 || s.length() == 1)
            return true;
        int n = s.length();
        for (int i = 0; i <= (n-1)/2; i++) {

            if (s.charAt(i) != s.charAt(n-i-1))
                return false;
        }
        return true;
    }

    private static void helper (String s, int start, int end, List<List<String>> list,
        List<String> runningList
    ) {
        if (start+1 == end)
        {
            runningList.add(s.substring(start, end));
            return;
        }
        for (int i=start; i< s.length()-1; i++) {
            String left = s.substring(start, i+1);
            // String right = s.substring(i+1, end);
            if (isPlaindrome(left)) {
                runningList.add(left);
                helper(s, i+1, end, list, runningList);
                
                list.add(new ArrayList<>(runningList));
                runningList.clear();
            }
        }
    }

    public List<List<String>> partition(String s) {
        
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        sc.close();
    }
}