// link: https://leetcode.com/problems/sum-of-beauty-of-all-substrings/

import java.util.*;

public class SumOfBeautyOfAllStrings {
    public static int beautySum(String s) {
        // int beauty = 0;

        // for (int i = 0; i < s.length(); i++) {
        //     int[] freq = new int[26];

        //     for (int j = i; j < s.length(); j++) {
        //         freq[s.charAt(j) - 'a']++;

        //         int minFreq = Integer.MAX_VALUE;
        //         int maxFreq = 0;

        //         for (int f : freq) {
        //             if (f == 0) continue;      // Ignore characters not present

        //             minFreq = Math.min(minFreq, f);
        //             maxFreq = Math.max(maxFreq, f);
        //         }

        //         beauty += (maxFreq - minFreq);
        //     }
        // }

        // return beauty;

        int beauty = 0;
        Map<Character, Integer> freq = new HashMap<>();
        int minFreq = Integer.MAX_VALUE, maxFreq = Integer.MIN_VALUE;


        for (int i=0; i<s.length(); i++) {
            for (int j = i; j < s.length(); j++) {

                char ch = s.charAt(j);
                int oldFreq = freq.getOrDefault(ch, 0);
                int newFreq = oldFreq + 1;

                freq.put(ch, newFreq);
                maxFreq = Math.max(maxFreq, newFreq);
                int min = Integer.MAX_VALUE;
                for (Map.Entry<Character, Integer> e : freq.entrySet()) {

                    min = Math.min(e.getValue(), min);
                }
                minFreq = min;
                beauty += (maxFreq - minFreq);
            }
            freq.clear();
            maxFreq = Integer.MIN_VALUE;
            // rev_freq.clear();
            System.out.println("beauty after " + i + "th iteration = " + beauty);
        }
        return beauty;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        int ans = beautySum(input);
        System.out.println(ans);
        sc.close();
    }
}
