package graphs;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;


public class SwimInRisingWater {

    public static class Pair {
        public int x, y, wt;

        public Pair (int x, int y, int wt) {
            this.x = x;
            this.y = y;
            this.wt = wt;
        }
    }

    public static int swimInWater(int[][] grid) {
        int n = grid.length;

        // boolean reachedEnd = false;
        // boolean[][] visited = new boolean[n][n];
        int[][] distFromSrc = new int[n][n];

        for (int[] d: distFromSrc) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        distFromSrc[0][0] = grid[0][0];

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> Integer.compare(a.wt, b.wt));

        q.offer(new Pair(0, 0, grid[0][0]));
        // visited[0][0] = true;

        while (!q.isEmpty()) {
            Pair closestPair = q.poll();
            int closestX = closestPair.x;
            int closestY = closestPair.y;
            int closestWt = closestPair.wt;

            if (closestX == n-1 && closestY == n-1) {
                return distFromSrc[n-1][n-1];
            }
            if (distFromSrc[closestX][closestY] < closestWt)
                continue;
            // visited[closestX][closestY] = true;

            // explore neighbours
            for (int i=0; i<4; i++) {
                int rr = closestX + dr[i];
                int cc = closestY + dc[i];

                if (rr < 0 || cc < 0 || rr >= n || cc >= n)
                    continue;
                // if (visited[rr][cc])
                //     continue;

                if (Math.max(distFromSrc[closestX][closestY], grid[rr][cc]) < distFromSrc[rr][cc]) {
                    distFromSrc[rr][cc] = Math.max(distFromSrc[closestX][closestY], grid[rr][cc]);
                    q.offer(new Pair(rr, cc, distFromSrc[rr][cc]));
                }
            }
        }
        return distFromSrc[n-1][n-1];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] rowStrings = input.substring(1, input.length()-1).split("],");
        int[][] grid = new int[rowStrings.length][rowStrings.length];
        int i=0, j=0;

        for (String rowString: rowStrings) {
            if (rowString.endsWith("]"))
                rowString = rowString.substring(1, rowString.length()-1);
            else 
                rowString = rowString.substring(1);
            String[] indivStrings = rowString.split(",");
            
            for (String elem: indivStrings) {
                grid[i][j++] = Integer.parseInt(elem);
            }
            j = 0;
            i++;
        }
        // check
        // for (int[] row: grid) {
        //     for (int r: row) {
        //         System.out.print(r + " ");
        //     }
        //     System.out.println();
        // }
        int ans = swimInWater(grid);
        System.out.println(ans);
        sc.close();
    }
}
