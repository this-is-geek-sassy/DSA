
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

// link: https://leetcode.com/problems/sort-characters-by-frequency/description/

public class SortCharactersByFreq {

    public static String frequencySort(String s) {
        Map<Character, Integer> mp = new HashMap<>();

        for (int i = 0; i<s.length(); i++) {
            mp.put(s.charAt(i), mp.getOrDefault(s.charAt(i), 0) + 1);
        }
        // System.out.println(mp);
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue()-a.getValue());
        pq.addAll(mp.entrySet());
        System.out.println(pq);

        StringBuilder ans = new StringBuilder(s.length());
        
        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> e = pq.poll();
            char c = e.getKey();
            int n = e.getValue();

            int j = n;
            while (j > 0) {
                ans.append(c);
                j--;
            }
        }
        return ans.toString();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String ans = frequencySort(input);
        System.out.println(ans);
        sc.close();
    }
}
