
import java.util.HashMap;
import java.util.Scanner;


// link: https://leetcode.com/problems/valid-anagram/description/

public class ValidAnagram {
    public boolean isAnagramUnicode (String s, String t) {
        if (s.codePointCount(0, s.length()) !=
            t.codePointCount(0, t.length())) {
            return false;
        }

        HashMap<Integer, Integer> freq = new HashMap<>();

        s.codePoints().forEach(cp ->
            freq.merge(cp, 1, Integer::sum)
        );

        for (int cp : t.codePoints().toArray()) {
            Integer count = freq.get(cp);

            if (count == null || count == 0) {
                return false;
            }

            if (count == 1)
                freq.remove(cp);
            else
                freq.put(cp, count - 1);
        }

        return freq.isEmpty();
    }
    public static boolean isAnagram(String s, String t) {
        int[] dist1 = new int[26];
        int[] dist2 = new int[26];

        for (int i=0; i<s.length(); i++) {
            dist1[s.charAt(i) - 'a']++;
        }
        for (int i=0; i<t.length(); i++) {
            dist2[t.charAt(i) - 'a']++;
        }
        for (int i=0; i<26; i++) {
            if (dist1[i] != dist2[i])
                return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine(), t = sc.nextLine();
        // System.out.println('z' - 'a');
        boolean ans = isAnagram(s, t);
        System.out.println(ans);
        sc.close();
    }
}
