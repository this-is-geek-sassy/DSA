package graphs;

// https://leetcode.com/problems/path-with-minimum-effort/description/

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;


public class PathWithMinEffort {

    static class Pair {
        int distance;
        int node;

        public Pair (int node, int distance) {
            this.distance = distance;
            this.node = node;
        }
    }

    public static int minimumEffortPath(int[][] grid) {
        
        if (grid.length == 1 && grid[0].length == 1)
            return 0;
            
        int n = grid.length, m = grid[0].length;
        PriorityQueue<Pair> rq = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));
        PriorityQueue<Pair> cq = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));

        boolean reachedEnd = false;
        boolean[][] visited = new boolean[n][m];
        int[][] minDistEncounteredTillNow = new int[n][m];

        for (int[] d: minDistEncounteredTillNow) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        minDistEncounteredTillNow[0][0] = 0;

        int[] prev = new int[n*m];
        // node with row i and col j will be stored in (i*m + j)th cell
        Arrays.fill(prev, Integer.MIN_VALUE);

        // upto 4 potential neighbours
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        rq.offer(new Pair(0, 0));
        cq.offer(new Pair(0, 0));

        // visited[0][0] = true;

        while (!rq.isEmpty()) {
            int r = rq.poll().node;
            int c = cq.poll().node;

            if (r == n-1 && c == m-1) {
                reachedEnd = true;
                break;
            }
            if (visited[r][c]) continue;
            visited[r][c] = true;
            // explore neighbours
            for (int i=0; i<4; i++) {
                int rr = r + dr[i];
                int cc = c + dc[i];

                if (rr<0 || cc<0) continue;
                if (rr>=n || cc>=m) continue;

                if (visited[rr][cc]) continue;
                if (Math.abs(grid[r][c] - grid[rr][cc]) < minDistEncounteredTillNow[rr][cc]) {
                    minDistEncounteredTillNow[rr][cc] = Math.abs(grid[r][c] - grid[rr][cc]);
                    prev[rr*m + cc] = r*m + c;
                    rq.add(new Pair(rr, Math.abs(grid[r][c] - grid[rr][cc])));
                    cq.add(new Pair(cc, Math.abs(grid[r][c] - grid[rr][cc])));
                }
            }
        }
        // path reconstruction and max weight check
        int maxDistInThePath = Integer.MIN_VALUE;
        int _prev = prev[prev.length-1], _curr = prev.length-1;
        while (_prev != Integer.MIN_VALUE) {
            int prev_i = _prev/m;
            int prev_j = _prev%m;

            int curr_i = _curr/m;
            int curr_j = _curr%m;

            int curr_wt = Math.abs(grid[prev_i][prev_j] - grid[curr_i][curr_j]);
            if (curr_wt > maxDistInThePath)
                maxDistInThePath = curr_wt;
            _curr = _prev;
            _prev = prev[_prev];
        }
        return maxDistInThePath;
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
