// https://leetcode.com/problems/longest-string-chain/description/

import java.util.Arrays;
import java.util.Scanner;

public class LongestStringChain {

    private static boolean canBeDerived(String a, String b) {
        if (a.length() + 1 != b.length()) {
            return false;
        }

        int i = 0;
        int j = 0;
        boolean skipped = false;

        while (i < a.length() && j < b.length()) {
            if (a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            } else {
                // b[j] is the extra character
                if (skipped) {
                    return false;
                }

                skipped = true;
                j++;
            }
        }

        return true;
    }

    public static int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b)->a.length() - b.length());
        int[] dp = new int[words.length];
        Arrays.fill(dp, 1);
        int largest = 0;

        for (int i=1; i<words.length; i++) {
            int max = 1;
            for (int j = i-1; j>=0; j--) {
                if (canBeDerived(words[j], words[i]) && 1 + dp[j] > max) {
                    max = 1 + dp[j];
                }
            }
            dp[i] = max;
            largest = Math.max(largest, max);
        }
        return largest;
    }
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        
        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(",");
        int i = 0;
        for (String s: inpArr) {
            inpArr[i++] = s.substring(1, s.length()-1);
        }
        int ans = longestStrChain(inpArr);
        System.out.println(ans);
        sc.close();
    }
}
