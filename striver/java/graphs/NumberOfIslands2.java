// https://leetcode.com/problems/number-of-islands-ii/

package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberOfIslands2 {

    static class SpecialDS {
        public int[] id, sz;
        public int m, n;

        public SpecialDS (int m, int n) {
            this.m = m;
            this.n = n;
            id = new int[m*n];
            sz = new int[m*n];

            Arrays.setAll(id, i->i);
            Arrays.fill(sz, 0);
        }

        public int findRoot (int i, int j) {
            int node = n*i + j;
            int root = node;
            
            while (root != id[root])
                root = id[root];

            while (node != root) {
                int next = id[node];
                id[node] = root;
                node = next;
            }
            return root;
        }

        // public int noComponents () {
        //     int count = 0;
        //     for (int e : sz) {
        //         count += e;
        //     }
        //     return count;
        // }

        public boolean isConnected (int a, int b, int i, int j) {
            // int node1 = n*a + b, node2 = n*i + j;
            return findRoot(a, b) == findRoot(i, j);
        }

        public boolean union (int a, int b, int i, int j) {
            // int node1 = n*a + b, node2 = n*i + j;
            int root1 = findRoot(a, b), root2 = findRoot(i, j);

            if (root1 == root2)
                return false;
            else if (sz[root1] > sz[root2]) {
                id[root2] = root1;
                sz[root1] += sz[root2];
            }
            else {
                id[root1] = root2;
                sz[root2] += sz[root1];
            }
            return true;
        }
    }

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        
        int[][] grid = new int[m][n];
        int islands = 0;
        List<Integer> ans = new ArrayList<>();
        SpecialDS dsu = new SpecialDS(m, n);

        int[] r = {-1, 1, 0, 0};
        int[] c = {0, 0, -1, 1};

        for (int i=0; i<positions.length; i++) {
            int rowNow = positions[i][0];
            int colNow = positions[i][1];

            if (dsu.sz[n*rowNow + colNow] > 0) {
                ans.add(islands);
                continue;
            }
            // grid[rowNow][colNow] = 1;
            dsu.sz[n*rowNow + colNow] = 1;
            islands++;

            for (int j=0; j<4; j++) {
                int rr = rowNow + r[j];
                int cc = colNow + c[j];
                if (rr<0 || cc<0 || rr>=m || cc>=n) continue;

                if (dsu.sz[n*rr + cc] > 0) {
                    if (dsu.union(rowNow, colNow, rr, cc))
                        islands--;
                }
            }
            ans.add(islands);
        }
        return ans;
    }
    public static void main(String[] args) {
        
    }
}
