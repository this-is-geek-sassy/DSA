package graphs;

// https://leetcode.com/problems/shortest-path-in-binary-matrix/description/

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


public class ShortestClearPath {

    public static int shortestPathBinaryMatrix(int[][] grid) {

        if (grid[0][0] == 1)
            return -1;
        
        int n = grid.length;
        Deque<Integer> rq = new ArrayDeque<>();
        Deque<Integer> cq = new ArrayDeque<>();

        int moveCount = 1, nodesLeftInLayer = 1, nodesInNextLayer = 0;
        boolean reachedEnd = false;
        boolean[][] visited = new boolean[n][n];

        // for (boolean[] v: visited) {
        //     Arrays.fill(v, false);
        // }
        // upto 8 potential neighbours
        int[] dr = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dc = {0, 0, -1, 1, -1, 1, -1, 1};

        rq.offer(0);
        cq.offer(0);

        visited[0][0] = true;

        while (!rq.isEmpty()) {
            int r = rq.poll();
            int c = cq.poll();

            if (r == n-1 && c == n-1) {
                reachedEnd = true;
                break;
            }
            // explore neighbours
            for (int i=0; i<8; i++) {
                int rr = r + dr[i];
                int cc = c + dc[i];

                if (rr < 0 || cc < 0) continue;
                if (rr >= n || cc >= n) continue;

                if (visited[rr][cc]) continue;
                if (grid[rr][cc] == 1) continue;

                rq.offer(rr);
                cq.offer(cc);
                visited[rr][cc] = true;
                nodesInNextLayer++;
            }
            nodesLeftInLayer--;
            if (nodesLeftInLayer == 0) {
                nodesLeftInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                moveCount++;
            }
        }
        if (reachedEnd == true)
            return moveCount;
        return -1;
    }

    private static int[][] gridParser (String gridInput) {

        String[] gridArr = gridInput.substring(1, gridInput.length()-1).split("],");
        int[][] grid = new int[gridArr.length][gridArr.length];

        int i = 0;
        for (String oneRow: gridArr) {
            if (oneRow.endsWith("]"))
                oneRow = oneRow.substring(1, oneRow.length()-1);
            else
                oneRow = oneRow.substring(1);
            String[] rowVecStr = oneRow.split(",");
            int j = 0;
            
            for (String e: rowVecStr) {
                grid[i][j++] = Integer.parseInt(e);
            }
            i++;
            // System.out.println(oneRow);
        }
        return grid;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String gridInput = sc.nextLine().trim();
        int[][] grid = gridParser(gridInput);

        // for (int[] g: grid) {
        //     for (int r: g) {
        //         System.out.print(r + " ");
        //     }
        //     System.out.println();
        // }
        int ans = shortestPathBinaryMatrix(grid);
        System.out.println(ans);
        sc.close();
    }
}
