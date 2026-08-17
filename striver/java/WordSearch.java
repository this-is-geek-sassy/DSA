
import java.util.*;

// link: https://leetcode.com/problems/word-search/description/

public class WordSearch {

    private static boolean helper (char[][] board, String word, int runningIdx, int i, int j, Deque<List<Integer>> startingMemory,
        Set<List<Integer>> visitedCelList
    ) {

        char lastChar;

        // logging:
        System.out.println("runningIdx = " + runningIdx);
        System.out.println("i = " + i + " j = " + j);
        // base case 
        if (runningIdx == word.length()) {
            // check if last character has any presence in the neighbouring cells of running i and j
            // lastChar = word.charAt(runningIdx);
            // if ((i-1 >= 0 && board[i-1][j] == lastChar) || (i+1 < board.length && board[i+1][j] == lastChar) 
            //     || (j-1 >= 0 && board[i][j-1] == lastChar) || (j+1 < board[0].length && board[i][j+1] == lastChar)) {
            //     return true;
            // }
            return true;
        }
        if (runningIdx == 1) {
            if (startingMemory.isEmpty() == true)
                return false;
            List<Integer> startingPointNow = startingMemory.pop();
            i = startingPointNow.get(0);
            j = startingPointNow.get(1);
            // runningIdx++;
        }
        lastChar = word.charAt(runningIdx);
        visitedCelList.add(List.of(i, j));
        boolean choice1, choice2, choice3, choice4;
        if (i-1 >= 0 && !visitedCelList.contains(List.of(i-1, j)) && board[i-1][j] == lastChar) {
            // visitedCelList.add(List.of(i-1, j));
            choice1 = helper(board, word, runningIdx+1, i-1, j, startingMemory, visitedCelList);
            if (choice1 == true){
                return true;
            }
        }
        if (i+1 < board.length && !visitedCelList.contains(List.of(i+1, j)) && board[i+1][j] == lastChar) {
            // visitedCelList.add(List.of(i+1, j));
            choice2 = helper(board, word, runningIdx+1, i+1, j, startingMemory, visitedCelList);
            if (choice2 == true) {
                return true;
            }
        }
        if (j-1 >= 0 && !visitedCelList.contains(List.of(i, j-1)) && board[i][j-1] == lastChar) {
            // visitedCelList.add(List.of(i, j-1));
            choice3 = helper(board, word, runningIdx+1, i, j-1, startingMemory, visitedCelList);
            if (choice3 == true)
                return true;
        }
        if (j+1 < board[0].length && !visitedCelList.contains(List.of(i, j+1)) && board[i][j+1] == lastChar) {
            // visitedCelList.add(new ArrayList<>(List.of(i, j+1)));
            choice4 = helper(board, word, runningIdx+1, i, j+1, startingMemory, visitedCelList);
            if (choice4 == true)
                return true;
        }
        visitedCelList.remove(List.of(i, j));
        if (runningIdx == 1) {
            return helper(board, word, runningIdx, i, j, startingMemory, visitedCelList);
        }
        return false;
    }
    
    public static boolean exist(char[][] board, String word) {

        if (word.length() > board.length*board[0].length)
            return false;
        char startingChar = word.charAt(0);
        Deque<List<Integer>> startingMemory = new ArrayDeque<>();

        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[0].length; j++) {
                if (board[i][j] == startingChar) {
                    startingMemory.push(List.of(i, j));
                }
            }
        }
        // System.out.println("HERE_1");
        if (startingMemory.isEmpty())
            return false;
        // System.out.println("HERE_2");
        for (List<Integer> l : startingMemory) {
            System.out.println(l.get(0) + " " + l.get(1));
        }
        // System.out.println("HERE_3");
        return helper(board, word, 1, 0, 0, startingMemory, new HashSet<>());
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String grid_input = sc.nextLine();
        String word = sc.nextLine().trim().replace("\"", "");

        String[] gridStringArr = grid_input.trim().substring(1, grid_input.length()-2).split("],");
        
        char[][] grid = new char[gridStringArr.length][];
        int i =0;
        for (String s: gridStringArr)
        {
            String[] intermediateArr = s.substring(1).split(",");
            int j = 0;
            grid[i] = new char[intermediateArr.length];
            for (String t: intermediateArr) {
                t = t.trim().replace("\"", "");
                grid[i][j++] = t.charAt(0);
            }
            i++;
        }

        // for (char[] s : grid) {
        //     for (char t: s) {
        //         System.out.print(t +" ");
        //     }
        //     System.out.println();
        // }
        // System.out.println("BOARD:");

        // for (char[] row : grid) {
        //     for (char c : row) {
        //         System.out.print("[" + c + "]");
        //     }
        //     System.out.println();
        // }

        // System.out.println("WORD = [" + word + "]");
        // System.out.println("STARTING CHAR = [" + word.charAt(0) + "]");

        boolean ans = exist(grid, word);
        System.out.println(ans);
        sc.close();
    }
}

// cleaner version:
// class Solution {

//     private boolean helper(char[][] board, String word,
//                            int idx, int i, int j) {

//         // All characters have been matched
//         if (idx == word.length())
//             return true;

//         // Out of bounds
//         if (i < 0 || i >= board.length ||
//             j < 0 || j >= board[0].length)
//             return false;

//         // Wrong character or already visited
//         if (board[i][j] != word.charAt(idx))
//             return false;

//         // Mark as visited
//         char original = board[i][j];
//         board[i][j] = '#';

//         boolean found =
//             helper(board, word, idx + 1, i - 1, j) ||
//             helper(board, word, idx + 1, i + 1, j) ||
//             helper(board, word, idx + 1, i, j - 1) ||
//             helper(board, word, idx + 1, i, j + 1);

//         // Backtrack
//         board[i][j] = original;

//         return found;
//     }

//     public boolean exist(char[][] board, String word) {

//         if (word.length() > board.length * board[0].length)
//             return false;

//         for (int i = 0; i < board.length; i++) {
//             for (int j = 0; j < board[0].length; j++) {

//                 if (board[i][j] == word.charAt(0)) {

//                     if (helper(board, word, 0, i, j))
//                         return true;
//                 }
//             }
//         }

//         return false;
//     }
// }