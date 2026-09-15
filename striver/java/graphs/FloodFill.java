package graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


public class FloodFill {

    public static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int m = image.length, n = image[0].length;
        int sourceColor = image[sr][sc];

        // int[][] visited = new int[m][n];

        Deque<Integer> rq = new ArrayDeque<>();
        Deque<Integer> cq = new ArrayDeque<>();

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        rq.offerLast(sr);
        cq.offerLast(sc);

        // visited[sr][sc] = 1;
        image[sr][sc] = color;

        while (!rq.isEmpty()) {
            int r = rq.pollFirst();
            int c = cq.pollFirst();

            // explore neighbours
            int rr, cc;
            for (int i=0; i<4; i++) {
                rr = r + dr[i];
                cc = c + dc[i];

                if (rr<0 || cc<0) continue;
                if (rr >= m || cc >= n) continue;

                // skip visited or not intereting cells
                // if (visited[rr][cc] == 1) continue;
                if (image[rr][cc] != sourceColor) continue;

                rq.offerLast(rr);
                cq.offerLast(cc);
                // visited[rr][cc] = 1;
                image[rr][cc] = color;
            }
        }
        return image;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        int[][] grid = GraphParser.parseGrid(input);

        
        sc.close();
    }
}
