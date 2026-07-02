
// link: https://leetcode.com/problems/reverse-words-in-a-string/

import java.util.Scanner;

public class ReverseWordsInString {
    public static String reverseWords(String s) {
        s = s.trim();
        StringBuilder ans = new StringBuilder();
        String[] words = s.split(" +");
        // for (String word : words) {
        //     System.out.println("<"+word+">");
        // }
        for (int i = words.length-1; i>=0; i--) {
            // if (words[i].isEmpty())
            //     continue;
            ans.append(words[i]).append(" ");
        }
        return ans.toString().trim();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String ans = reverseWords(input);
        System.out.println(ans);
        sc.close();
    }
}
