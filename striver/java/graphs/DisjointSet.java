package graphs;

import java.util.Arrays;


public class DisjointSet {

    private int[] id, sz, rank;

    public DisjointSet (int n) {
        id = new int[n];
        sz = new int[n];
        rank = new int[n];

        Arrays.fill(sz, 1);
        Arrays.setAll(id, i -> i);
        // Arrays.parallelSetAll(id, i -> i);
    }
    private int findRoot (int p) {
        int root = p;
        while (root != id[root]) 
            root = id[root];
        // path compression
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }
        return root;
    }
    public boolean connected (int u, int v) {
        return findRoot(u) == findRoot(v);
    }
    public void unionByRank(int u, int v) {
        int root1 = findRoot(u);
        int root2 = findRoot(v);
        if (root1 == root2) return;
    
        if (rank[root1] < rank[root2]) {
            id[root1] = root2;
            sz[root2] += sz[root1];
        } else if (rank[root1] > rank[root2]) {
            id[root2] = root1;
            sz[root1] += sz[root2];
        } else {
            id[root2] = root1;
            sz[root1] += sz[root2];
            rank[root1]++;          // only a tie increases the height bound
        }
    }

    public void unionBySize (int u, int v) {

        int root1 = findRoot(u);
        int root2 = findRoot(v);
        if (root1 == root2) {
            return;
        } 
        if (sz[root1] < sz[root2]) {
            sz[root2] += sz[root1];
            id[root1] = root2;
        } else {
            sz[root1] += sz[root2];
            id[root2] = root1;
        }
    }
    
}
