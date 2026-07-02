
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


// link: https://leetcode.com/problems/isomorphic-strings/description/

public class IsomorphicStrings {

    public boolean isIsomorphicOptimal(String s, String t) {
        char[] map = new char[256];
        boolean[] used = new boolean[256];

        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);

            if (map[a] == 0) {           // 'a' hasn't been mapped yet
                if (used[b])             // 'b' is already mapped from another char
                    return false;

                map[a] = b;
                used[b] = true;
            } else if (map[a] != b) {    // Existing mapping is inconsistent
                return false;
            }
        }

        return true;
    }
    
    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length())
            return false;
        HashMap<Character, Character> hmp = new HashMap<>();
        HashSet<Character> hs = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i), b = t.charAt(i);
            Character mapped = hmp.get(a);
            if (mapped != null && mapped != b) {
                return false;
            }
            else if (hs.contains(b) && !hmp.containsKey(a)) {
                return false;
            }
            hmp.put(s.charAt(i), t.charAt(i));
            hs.add(b);
            // System.out.println(hmp);
            // System.out.println(hs);
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String t = sc.nextLine();
        boolean ans = isIsomorphic(s, t);
        System.out.println(ans);
        sc.close();
    }
}
