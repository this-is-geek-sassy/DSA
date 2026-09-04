
import java.util.Arrays;
import java.util.Scanner;

// https://leetcode.com/problems/edit-distance/

public class EditDistance {

    private static int helper (String word1, String word2, int i, int j, int[][] memory) {
        
        if (i >= word1.length()) {
            return word2.length() - j;
        }

        if (j >= word2.length()) {
            return word1.length() - i;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (word1.charAt(i) == word2.charAt(j)) {
            memory[i][j] = helper(word1, word2, i+1, j+1, memory);
        } else {
            memory[i][j] = 1 + Math.min(helper(word1, word2, i, j+1, memory), // insert a char
                    Math.min(helper(word1, word2, i+1, j, memory),  // delete a char
                    helper(word1, word2, i+1, j+1, memory)  // update a char
                ));
        }
        return memory[i][j];
    }
    public static int minDistance(String word1, String word2) {
        int[][] memory = new int[word1.length()][word2.length()];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(word1, word2, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String word1 = sc.nextLine();
        String word2 = sc.nextLine();
        int ans = minDistance(word1, word2);
        System.out.println(ans);
        sc.close();
    }
}
