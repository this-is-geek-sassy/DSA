package graphs;

// [[1, 1, 0, 0, 0], [1, 1, 0, 0, 0], [0, 0, 0, 1, 1], [0, 0, 0, 1, 1]]
// [[1, 1, 0, 1, 1], [1, 0, 0, 0, 0], [0, 0, 0, 0, 1], [1, 1, 0, 1, 1]]

// https://takeuforward.org/practice/dsa/number-of-distinct-islands

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class NumberOfDistinctIslands {

    private static void gridDFS (int[][] grid, int i, int j, int baseI, int baseJ, ArrayList<String> island) {
        if (i<0 || j<0 || i>=grid.length || j>=grid[0].length) {
            return;
        }
        if (grid[i][j] == 0)
            return;

        // visited[i][j] = 1;
        grid[i][j] = 0;
        island.add((baseI-i) + "," + (baseJ-j));

        gridDFS(grid, i-1, j, baseI, baseJ, island);
        gridDFS(grid, i+1, j, baseI, baseJ, island);
        gridDFS(grid, i, j-1, baseI, baseJ, island);
        gridDFS(grid, i, j+1, baseI, baseJ, island);
    }

    public static int countDistinctIslands(int[][] grid) {
        
        // int count = 0;
        HashSet<ArrayList<String>> set = new HashSet<>();

        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    // check if component doesn't exis already
                    // if not:
                    ArrayList<String> island = new ArrayList<>();
                    gridDFS(grid, i, j, i, j, island);
                    set.add(island);
                }
            }
        }
        return set.size();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();

        String[] inpArr = input.substring(1, input.length()-1).split("], ");
        int[][] grid = new int[inpArr.length][];
        int i = 0;

        for (String row: inpArr) {
            if (row.endsWith("]"))
                row = row.substring(1, row.length()-1);
            else
                row = row.substring(1);
            String[] rowArr = row.split(", ");
            grid[i] = new int[rowArr.length];
            int j = 0;

            for (String s: rowArr) {
                grid[i][j++] = Integer.parseInt(s);
            }
            i++;
            // System.out.println(row);
        }

        // for (int[] a: grid) {
        //     for (int b: a) {
        //         System.out.print(b + " ");
        //     }
        //     System.out.println();
        // }
        int ans = countDistinctIslands(grid);
        System.out.println(ans);
        sc.close();
    }
}
