// https://leetcode.com/problems/number-of-islands-ii/

package graphs;

import java.util.ArrayList;
import java.util.List;

public class NumberOfIslands2 {

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        
        int[][] grid = new int[m][n];
        int islands = 0;
        List<Integer> ans = new ArrayList<>();

        for (int i=0; i<positions.length; i++) {
            int rowNow = positions[i][0];
            int colNow = positions[i][1];

            grid[rowNow][colNow] = 1;
            islands = NumberOfIslands.numIslands(grid);
            ans.add(islands);
        }
        return ans;
    }
    public static void main(String[] args) {
        
    }
}
