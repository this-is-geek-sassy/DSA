package graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;


public class RottenOranges {

    private static ArrayList<int[]> findRottens (int[][] grid) {

        ArrayList<int[]> rottens = new ArrayList<>();

        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    int[] coord = new int[2];
                    coord[0] = i;
                    coord[1] = j;
                    rottens.add(coord);
                }
            }
        }
        return rottens;
    }

    private static int bfs (Deque<Integer> rq, Deque<Integer> cq, int[][] grid, int[][] visited, int noOfRottensAtBegin) {

        int m = grid.length, n = grid[0].length, noFreshOranges = 0;

        for (int[] g: grid) {
            for (int c: g) {
                if (c == 1)
                    noFreshOranges++;
            }
        }
        GridTracer.vars(
                "noFreshOranges", noFreshOranges,
                "noOfRottensAtBegin", noOfRottensAtBegin,
                "nodeLeftInLayer", noOfRottensAtBegin,
                "nodesInNextLayer", 0,
                "moveCount", 0,
                "rqSize", rq.size(),
                "cqSize", cq.size());
        if (noFreshOranges == 0) {
            GridTracer.done(0);
            return 0;
        }

        int moveCount = 0;
        int nodeLeftInLayer = noOfRottensAtBegin;
        int nodesInNextLayer = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (noFreshOranges != 0 && !rq.isEmpty()) {
            int r = rq.pollFirst();
            int c = cq.pollFirst();

            GridTracer.vars(
                    "r", r,
                    "c", c,
                    "noFreshOranges", noFreshOranges,
                    "nodeLeftInLayer", nodeLeftInLayer,
                    "nodesInNextLayer", nodesInNextLayer,
                    "moveCount", moveCount,
                    "rqSize", rq.size(),
                    "cqSize", cq.size());
            GridTracer.pop(r, c);

            int rr, cc;
            for (int i=0; i<4; i++) {
                rr = r + dr[i];
                cc = c + dc[i];

                if (rr < 0 || cc < 0 || rr >= m || cc >= n) {
                    GridTracer.skip(rr, cc, "oob");
                    continue;
                }
                if (visited[rr][cc] == 1) {
                    GridTracer.skip(rr, cc, "visited");
                    continue;
                }
                if (grid[rr][cc] != 1) {
                    GridTracer.skip(rr, cc, "empty");
                    continue;
                }

                rq.offerLast(rr);
                cq.offerLast(cc);
                visited[rr][cc] = 1;
                grid[rr][cc] = 2;
                noFreshOranges--;
                nodesInNextLayer++;
                GridTracer.vars(
                        "noFreshOranges", noFreshOranges,
                        "nodesInNextLayer", nodesInNextLayer,
                        "rqSize", rq.size(),
                        "cqSize", cq.size());
                GridTracer.infect(rr, cc);
                if (noFreshOranges == 0) {
                    return moveCount + 1;
                }
            }

            nodeLeftInLayer--;
            GridTracer.vars("nodeLeftInLayer", nodeLeftInLayer);
            if (nodeLeftInLayer == 0) {
                nodeLeftInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                moveCount++;
                GridTracer.vars(
                        "nodeLeftInLayer", nodeLeftInLayer,
                        "nodesInNextLayer", nodesInNextLayer,
                        "moveCount", moveCount);
                GridTracer.tick(moveCount);
            }
        }
        int ans = noFreshOranges == 0 ? moveCount : -1;
        GridTracer.vars("noFreshOranges", noFreshOranges, "moveCount", moveCount);
        GridTracer.done(ans);
        return ans;
    }

    public static int orangesRotting(int[][] grid) {
        ArrayList<int[]> rottenCoords = findRottens(grid);

        int noOfRottensAtBegin = rottenCoords.size();

        int[][] visited = new int[grid.length][grid[0].length];

        Deque<Integer> rq = new ArrayDeque<>();
        Deque<Integer> cq = new ArrayDeque<>();

        for (int i=0; i<noOfRottensAtBegin; i++) {
            int[] coord = rottenCoords.get(i);
            rq.offerLast(coord[0]);
            cq.offerLast(coord[1]);
            visited[coord[0]][coord[1]] = 1;
        }

        GridTracer.vars(
                "noOfRottensAtBegin", noOfRottensAtBegin,
                "rqSize", rq.size(),
                "cqSize", cq.size(),
                "moveCount", 0,
                "nodeLeftInLayer", noOfRottensAtBegin,
                "nodesInNextLayer", 0);
        GridTracer.start(grid);

        int ans = bfs(rq, cq, grid, visited, noOfRottensAtBegin);
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        int[][] grid = GraphParser.parseGrid(input);
        int ans = orangesRotting(grid);
        System.out.println(ans);
        sc.close();
    }
}
