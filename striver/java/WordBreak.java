
import java.util.*;

// link: https://leetcode.com/problems/word-break/description/

public class WordBreak {

    // trivial (no-use) function
    // private static boolean doesExistInDict (String s, List<String> wordDict) {

    //     for (int i=0; i<wordDict.size(); i++) {
    //         if (s.equals(wordDict.get(i))) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    private static boolean helper (String s, Set<String> wordDict, int maxWordLength, Boolean[] memory, int runnningIdx) {

        // System.out.println("runnningIdx = " + runningIdx);
        // System.out.println("String = " + s);
        // base case:
        if (runnningIdx == s.length()) {
            // successfully broken into dictionary elements, so return true
            return true;
        }
        if (memory[runnningIdx] != null) {
            return memory[runnningIdx];
        }
        // if (memory[runnningIdx] == false) {
        //     return false;
        // }
        for (int i=runnningIdx+1; i <= Math.min(s.length(), runnningIdx + maxWordLength); i++) {
            String chunk = s.substring(runnningIdx, i);
            if (wordDict.contains(chunk)) {
                boolean result = helper(s, wordDict, maxWordLength, memory, i);
                if (result == true) {
                    memory[runnningIdx] = true;
                    return true;
                } 
                // else {
                //     memory[i] = -1;
                // }
            }
        }
        memory[runnningIdx] = false;
        return false;
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int maxWordLength = 0;

        for (String t: wordDict) {
            if (t.length() > maxWordLength) {
                maxWordLength = t.length();
            }
        }
        Boolean[] memory = new Boolean[s.length()];
        return helper(s, dict, maxWordLength, memory, 0);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String dict_line = sc.nextLine();
        String[] dict = dict_line.trim().substring(1, dict_line.length()-1).split(",");

        // System.out.println("s = " + s);
        // System.out.println("dict = ");
        for (int i = 0; i < dict.length; i++) {
            dict[i] = dict[i].substring(1, dict[i].length()-1);
        }
        // System.out.println("hello".substring(0,0));
        boolean ans = wordBreak(s, Arrays.asList(dict));
        System.out.println(ans);
        sc.close();
    }
}
