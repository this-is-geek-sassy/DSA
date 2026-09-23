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

    // private static int merge_paths (HashMap<String, String> parent_side, HashMap<String, String> child_side, String curentWord, String neighbourWord, String beginWord, String endWord, boolean fromParentSide) {

    //     String nextWord;
    //     if (fromParentSide) {
    //         parent_side.put(curentWord, neighbourWord);
    //         curentWord = neighbourWord;
    //         nextWord = child_side.get(neighbourWord);
    //     } else {
    //         parent_side.put(neighbourWord, curentWord);
    //         nextWord = child_side.get(curentWord);
    //     }
    //     while (!nextWord.equals(endWord)) {
    //         parent_side.put(curentWord, nextWord);
    //         nextWord = child_side.get(nextWord);
    //     }
    //     parent_side.put(curentWord, endWord);

    //     String this_string = beginWord;
    //     String next_string = parent_side.get(this_string);
    //     int length = 0;
    //     while (!next_string.equals(endWord)) { 
    //         ++length;
    //         this_string = next_string;
    //         next_string = parent_side.get(next_string);
    //     }
    //     return length+1;
    // }
    // private static int merge_paths_from_child (HashMap<String, String> parent_side, HashMap<String, String> child_side, String curentWord, String neighbourWord, String beginWord, String endWord) {

    //     parent_side.put(neighbourWord, curentWord);
    //     String nextWord = child_side.get(curentWord);
    // }
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        
        int[] visited_s = new int[wordList.size()];
        int[] visited_t = new int[wordList.size()];

        Deque<String> q_s = new ArrayDeque<>();
        Deque<String> q_t = new ArrayDeque<>();

        q_s.offerLast(beginWord);
        q_t.offerFirst(endWord);
        
        try {
            visited_t[wordList.indexOf(endWord)] = 1;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 0;
        }
        HashMap<String, Integer> distS = new HashMap<>();
        HashMap<String, Integer> distT = new HashMap<>();
        distS.put(beginWord, 1);
        distT.put(endWord, 1);

        // int lengthOfTransformation = 0;
        while (!q_s.isEmpty() && !q_t.isEmpty()) {

            int sourceLevelSize = q_s.size();
        
            for (int k = 0; k < sourceLevelSize; k++) {
        
                String current_s = q_s.pollFirst();
        
                for (int i = 0; i < wordList.size(); i++) {
        
                    String word = wordList.get(i);
        
                    if (!isNeighbour(current_s, word))
                        continue;
        
                    // First check whether target BFS has already reached it
                    if (visited_t[i] == 1) {
                        return distS.get(current_s) + distT.get(word);
                    }
        
                    // Otherwise add to source BFS
                    if (visited_s[i] == 0) {
                        visited_s[i] = 1;
                        q_s.offerLast(word);
                        distS.put(word, distS.get(current_s) + 1);
                    }
                }
            }
        
        
            int targetLevelSize = q_t.size();
        
            for (int k = 0; k < targetLevelSize; k++) {
        
                String current_t = q_t.pollFirst();
        
                for (int i = 0; i < wordList.size(); i++) {
        
                    String word = wordList.get(i);
        
                    if (!isNeighbour(current_t, word))
                        continue;
        
                    // First check whether source BFS has already reached it
                    if (visited_s[i] == 1) {
                        return distT.get(current_t) + distS.get(word);
                    }
        
                    // Otherwise add to target BFS
                    if (visited_t[i] == 0) {
                        visited_t[i] = 1;
                        q_t.offerLast(word);
                        distT.put(word, distT.get(current_t) + 1);
                    }
                }
            }
        }
        
        return 0;
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
