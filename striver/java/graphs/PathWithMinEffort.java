package graphs;

// https://leetcode.com/problems/path-with-minimum-effort/description/

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;


public class PathWithMinEffort {

    static class Pair {
        int row, col, effort;

        public Pair(int row, int col, int effort) {
            this.row = row;
            this.col = col;
            this.effort = effort;
        }
    }

    public static int minimumEffortPath(int[][] heights) {

        int n = heights.length;
        int m = heights[0].length;

        int[][] dist = new int[n][m];

        for (int[] row : dist)
            Arrays.fill(row, Integer.MAX_VALUE);

        PriorityQueue<Pair> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.effort, b.effort)
        );

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        dist[0][0] = 0;
        pq.offer(new Pair(0, 0, 0));

        while (!pq.isEmpty()) {

            Pair curr = pq.poll();

            int r = curr.row;
            int c = curr.col;
            int effort = curr.effort;

            // Ignore outdated entries
            if (effort > dist[r][c])
                continue;

            // Destination reached optimally
            if (r == n - 1 && c == m - 1)
                return effort;

            // Explore neighbours
            for (int i = 0; i < 4; i++) {

                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr < 0 || nc < 0 || nr >= n || nc >= m)
                    continue;

                int edgeWeight = Math.abs(
                    heights[r][c] - heights[nr][nc]
                );

                int newEffort = Math.max(effort, edgeWeight);

                if (newEffort < dist[nr][nc]) {
                    dist[nr][nc] = newEffort;
                    pq.offer(new Pair(nr, nc, newEffort));
                }
            }
        }

        return 0;
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
        int[][] heights = gridParser(gridInput);

        int ans = minimumEffortPath(heights);
        System.out.println(ans);
        sc.close();
    }
}
