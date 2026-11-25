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
            Arrays.fill(sz, 1);
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

        public boolean isConnected (int a, int b, int i, int j) {
            // int node1 = n*a + b, node2 = n*i + j;
            return findRoot(a, b) == findRoot(i, j);
        }

        public void union (int a, int b, int i, int j) {
            // int node1 = n*a + b, node2 = n*i + j;
            int root1 = findRoot(a, b), root2 = findRoot(i, j);

            if (root1 == root2)
                return;
            else if (sz[root1] > sz[root2]) {
                id[root2] = root1;
                sz[root1] += sz[root2];
            }
            else {
                id[root1] = root2;
                sz[root2] += sz[root1];
            }
        }
    }

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        
        
    }
    public static void main(String[] args) {
        
    }
}
