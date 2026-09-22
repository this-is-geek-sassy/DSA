package graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class WrodLadder {

    private static boolean isNeighbour (String s1, String s2) {
        int i = 0;
        for (int j=0; j<s1.length(); j++) {
            if (s1.charAt(j) != s2.charAt(j) && i==1)
                return false;

            if (s1.charAt(j) != s2.charAt(j))
                i++;
        }
        return i==1;
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int[] visited = new int[wordList.size()];

        Deque<String> q = new ArrayDeque<>();
        q.offerLast(beginWord);
        HashMap<String, String> prev = new HashMap<>();

        // int lengthOfTransformation = 0;
        while (!q.isEmpty()) {
            String current = q.pollFirst();
            for (int i=0; i<wordList.size(); i++) {
                String word = wordList.get(i);
                if (isNeighbour(current, word) && visited[i] == 0) {
                    visited[i] = 1;
                    q.offerLast(word);
                    prev.put(word, current);
                }
            }
            // lengthOfTransformation++;
            // System.out.println("handled node : " + current);
            // System.out.println("lengthOfTransformation = " + lengthOfTransformation);
        }
        // System.out.println(prev);

        // reconstruct path and length
        if (!prev.containsKey(endWord))
            return 0;
        
        String this_string = prev.get(endWord);
        int length = 1;
        while (!this_string.equalsIgnoreCase(beginWord)) {
            this_string = prev.get(this_string);
            length++;
        }
        return length+1;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String beginWord = sc.nextLine();
        String endWord = sc.nextLine();
        String listInp = sc.nextLine();

        String[] listAsItIs = listInp.trim().substring(1, listInp.length()-1).split(",");
        List<String> realList = new ArrayList<>();

        for (String s: listAsItIs) {
            String temp = s.substring(1, s.length()-1);
            // System.out.println(realList[i-1] + " ");
            realList.add(temp);
        }
        int ans = ladderLength(beginWord, endWord, realList);
        System.out.println(ans);
        sc.close();
    }
}
