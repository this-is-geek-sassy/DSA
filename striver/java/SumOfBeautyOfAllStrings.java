// link: https://leetcode.com/problems/sum-of-beauty-of-all-substrings/

import java.util.*;

public class SumOfBeautyOfAllStrings {
    public static int beautySum(String s) {

        int beauty = 0;
        Map<Character, Integer> freq = new HashMap<>();
        TreeMap<Integer, Set<Character>> rev_freq = new TreeMap<>();

        for (int i=0; i<s.length(); i++) {
            for (int j = i; j < s.length(); j++) {

                char ch = s.charAt(j);
                int oldFreq = freq.getOrDefault(ch, 0);
                int newFreq = oldFreq + 1;

                freq.put(ch, newFreq);
                int freq_being_considered = newFreq;
                
                if (oldFreq > 0) {
                    rev_freq.get(oldFreq).remove(ch);
                }
                if (oldFreq > 0 && rev_freq.get(oldFreq).isEmpty()) {
                    rev_freq.remove(oldFreq);
                }
                rev_freq.computeIfAbsent(freq_being_considered, k -> new HashSet<>()).add(s.charAt(j));

                int minFreq = rev_freq.firstKey();
                int maxFreq = rev_freq.lastKey();
                beauty += (maxFreq - minFreq);
            }
            freq.clear();
            rev_freq.clear();
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
