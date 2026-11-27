// https://leetcode.com/problems/making-a-large-island/

package graphs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MakingALargeIsland {

    public static class SpecialDS {
        int m, n;
        int[] id, sz;

        public SpecialDS (int m, int n) {
            this.m = m;
            this.n = n;
            id = new int[m*n];
            sz = new int[m*n];
            Arrays.setAll(id, i->i);
            // Arrays.fill(sz, val);
        }

        public int findRoot (int i, int j) {
            int p = n*i + j, root = p;
            while (root != id[root]) {
                root = id[root];
            }
            while (p != root) {
                int next = id[p];
                id[p] = root;
                p = next;
            }
            return root;
        }
        public boolean isConnected (int a, int b, int i, int j) {
            // int node1 = n*a + b;
            // int node2 = n*i + j;
            return findRoot(a, b) == findRoot(i, j);
        }
        public boolean union (int a, int b, int i, int j) {
            int root1 = findRoot(a, b);
            int root2 = findRoot(i, j);

            if (root1 == root2)
                return false;
            else if (sz[root1] >= sz[root2]) {
                id[root2] = root1;
                sz[root1] += sz[root2];
            }
            else {
                id[root1] = root2;
                sz[root2] += sz[root1];
            }
            return true;
        }
        public int size (int i, int j) {
            return sz[findRoot(i, j)];
        }
    }

    public static int largestIsland(int[][] grid) {
        int n = grid.length;
        SpecialDS dsu = new SpecialDS(n, n);
        int islands = 0;

        int[] r = {-1, 0};
        int[] c = {0, -1};

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 1) {
                    dsu.sz[n*i + j] = 1;
                    islands++;

                    for (int k=0; k<2; k++) {
                        int rr = i + r[k];
                        int cc = j + c[k];

                        if (rr<0 || cc<0 || rr>=n || cc>=n) continue;
                        
                        if (grid[rr][cc] == 1) {
                            // dsu.sz[rr*n + cc] = 1;
                            dsu.union(i, j, rr, cc);
                            islands--;
                        }
                    }
                }
            }
        }
        // System.out.println("#islands = " + islands);

        int maxSize = 0;
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                // System.out.println(dsu.id[n*i + j]);
                if (grid[i][j] == 1)
                    continue;

                int[] _r = {-1, 1, 0, 0};
                int[] _c = {0, 0, -1, 1};

                int netSize = 1;
                Set<Integer> seenRoots = new HashSet<>();

                for (int k=0; k<4; k++) {
                    int rr = i + _r[k];
                    int cc = j + _c[k];
                    if (rr<0 || cc<0 || rr>=n || cc>=n) continue;

                    int root  = dsu.findRoot(rr, cc);
                    if (seenRoots.contains(root)) continue;
                    seenRoots.add(root);

                    int neighbourSize = dsu.size(rr, cc);
                    // System.out.println("neighbourSize = " + neighbourSize);
                    netSize += neighbourSize;
                }
                maxSize = Math.max(maxSize, netSize);
                // System.out.println("maxSize = " + maxSize);
            }
        }
        if (maxSize == 0)
            return dsu.size(0, 0);
        return maxSize;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] rows = input.substring(1, input.length()-1).split("],");

        int[][] grid = new int[rows.length][rows.length];
        int i=0, j=0;

        for (String row : rows) {
            if (row.endsWith("]"))
                row = row.substring(1, row.length()-1);
            else
                row = row.substring(1);
            String[] elements = row.split(",");
            for (String element: elements) {
                grid[i][j++] = Integer.parseInt(element);
            }
            i++;
            j=0;
        }
        int ans = largestIsland(grid);
        System.out.println(ans);
        sc.close();
    }
}
