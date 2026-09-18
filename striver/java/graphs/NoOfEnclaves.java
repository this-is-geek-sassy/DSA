package graphs;

// https://leetcode.com/problems/surrounded-regions/description/

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

// https://leetcode.com/problems/number-of-enclaves/


public class NoOfEnclaves {

    public static int numEnclaves(int[][] board) {
        int m = board.length, n = board[0].length;
        Deque<Integer> qx = new ArrayDeque<>();
        Deque<Integer> qy = new ArrayDeque<>();

        // Set<Map.Entry> set = new HashSet<>();
        // Set<Integer> sy = new HashSet<>();

        boolean[][] visited = new boolean[m][n];
        for (int i=0; i<n; i++) {
            if (board[0][i] == 1) {
                qx.offerLast(0);
                qy.offerLast(i);
                // sx.add(0);
                // sy.add(i);
                // set.add(Map.entry(0, i));
                visited[0][i] = true;
            }
            if (board[m-1][i] == 1) {
                qx.offerLast(m-1);
                qy.offerLast(i);
                // sx.add(m-1);
                // sy.add(i);
                // set.add(Map.entry(m-1, i));
                visited[m-1][i] = true;
            }
        }
        for (int i = 1; i<m-1; i++) {
            if (board[i][0] == 1) {
                qx.offerLast(i);
                qy.offerLast(0);
                // sx.add(i);
                // sy.add(0);
                // set.add(Map.entry(i, 0));
                visited[i][0] = true;
            }
            if (board[i][n-1] == 1) {
                qx.offerLast(i);
                qy.offerLast(n-1);
                // sx.add(i);
                // sy.add(n-1);
                // set.add(Map.entry(i, n-1));
                visited[i][n-1] = true;
            }
        }
        // enqueuing done
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // bfs loop
        while (!qx.isEmpty()) {
            int r = qx.pollFirst();
            int c = qy.pollFirst();

            int rr, cc;
            for (int i=0; i<4; i++) {
                rr = r + dr[i];
                cc = c + dc[i];

                if (rr < 0 || cc < 0 || rr >= m || cc >= n) continue;
                if (visited[rr][cc]) continue;
                if (board[rr][cc] != 1) continue;

                qx.offerLast(rr);
                qy.offerLast(cc);
                visited[rr][cc] = true;
                // sx.add(rr);
                // sy.add(cc);
                // set.add(Map.entry(rr, cc));
            }
        }
        int counter = 0;
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] == 1 && visited[i][j] == false) {
                    counter++;
                }
            }
        }
        return counter;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        char[][] board = GraphParser.parseBoard(input);
        
        
        // Print the parsed board
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        sc.close();
    }
}
