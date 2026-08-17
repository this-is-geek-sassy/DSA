
import java.util.*;

// link: https://leetcode.com/problems/n-queens/description/

public class NQueens {
    private static boolean checkIfPlacemnetPossible (int[][] board, int n, int row, int col) {

        // bottom (trivially false)
        // for (int i = row+1; i < n; i++) {
        //     if (board[i][col] == 1)
        //         return false;
        // }
        // top
        for (int i=row-1; i>=0; i--) {
            if (board[i][col] == 1)
                return false;
        }
        // right (trivialy false)
        // for (int i=col+1; i < n; i++) {
        //     if (board[row][i] == 1)
        //         return false;
        // }
        // left (trivially false)
        // for (int i=col-1; i >= 0; i--) {
        //     if (board[row][i] == 1)
        //         return false;
        // }
        // cross cells now
        // NW
        for (int i=row-1, j=col-1; i>=0 && j>=0; i--, j--) {
            if (board[i][j] == 1)
                return false;
        }
        // NE
        for (int i=row-1, j=col+1; i>=0 && j<n; i--, j++) {
            if (board[i][j] == 1)
                return false;
        }
        // SW (trivially false)
        // for (int i=row+1, j=col-1; i<n && j>=0; i++, j--) {
        //     if (board[i][j] == 1)
        //         return false;
        // }
        // SE (trivially false)
        // for (int i=row+1, j=col+1; i<n && j<n; i++, j++) {
        //     if (board[i][j] == 1)
        //         return false;
        // }
        return true;
    }
    private static List<String> boardStateToString (int[][] board, int n) {
        List<String> boardState = new ArrayList<>();
        
        for (int i=0; i<n; i++) {
            StringBuilder sb = new StringBuilder(".".repeat(n));
            for (int j=0; j<n; j++) {
                if (board[i][j] == 1) {
                    sb.setCharAt(j, 'Q');
                }
                // else {
                //     sb.setCharAt(j, 'Q');
                // }
            }
            boardState.add(sb.toString());
        }
        return boardState;
    }
    private static void helper (int[][] board, int n, int rowNow, List<List<String>> state) {

        if (rowNow == n) {
            // all rows have passed, now convert the board into a List<String>
            state.add(boardStateToString(board, n));
            return;
        }
        for (int i=0; i<n; i++) {
            if (board[rowNow][i] == 0 && checkIfPlacemnetPossible(board, n, rowNow, i)) {
                board[rowNow][i] = 1;
                helper(board, n, rowNow+1, state);
                board[rowNow][i] = 0;
            }
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        
        int[][] board = new int[n][n];
        List<List<String>> state = new ArrayList<>();
        
        helper(board, n, 0, state);
        
        return state;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        //
        List<List<String>> ans = solveNQueens(n);
        System.out.println(ans);
        sc.close();
    }
}
