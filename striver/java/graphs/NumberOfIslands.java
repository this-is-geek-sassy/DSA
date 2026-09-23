package graphs;

// https://leetcode.com/problems/number-of-islands/

import java.util.ArrayList;
import java.util.Scanner;


public class NumberOfIslands {

    private static void gridDFS (char[][] grid, int i, int j, int[][] visited) {
        if (i<0 || j<0 || i>=grid.length || j>=grid[0].length) {
            return;
        }
        if (grid[i][j] == '0' || visited[i][j] == 1)
            return;

        visited[i][j] = 1;
        gridDFS(grid, i-1, j, visited);
        gridDFS(grid, i+1, j, visited);
        gridDFS(grid, i, j-1, visited);
        gridDFS(grid, i, j+1, visited);
    }

    public static int numIslands(char[][] grid) {
        
        int count = 0;
        int[][] visited = new int[grid.length][grid[0].length];

        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j] == '1' && visited[i][j] == 0) {
                    count++;
                    gridDFS(grid, i, j, visited);
                }
            }
        }
        return count;
    } 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        ArrayList<ArrayList<Character>> grid = new ArrayList<>();
        while (sc.hasNextLine()) {
            String row = sc.nextLine().trim();
            // row = row.trim();
            // End of grid
            if (row.equals("]")) {
                break;
            }
            // Remove '['
            row = row.substring(1);

            // Remove either '],' or ']'
            if (row.endsWith(",")) {
                row = row.substring(0, row.length() - 2);
            } else {
                row = row.substring(0, row.length() - 1);
            }
            String[] entryList = row.split(",");
            ArrayList<Character> rowList = new ArrayList<>();
            
            for (String entry: entryList) {
                rowList.add(entry.substring(1, entry.length()-1).charAt(0));
            }
            grid.add(rowList);
        }
        // Convert ArrayList<ArrayList<Character>> -> char[][]
        int m = grid.size();
        int n = grid.get(0).size();

        char[][] gridArr = new char[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                gridArr[i][j] = grid.get(i).get(j);
            }
        }
        int ans = numIslands(gridArr);
        System.out.println(ans);
    }
}
