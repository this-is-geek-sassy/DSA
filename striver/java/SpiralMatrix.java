import java.util.*;
// import javafx.util.Pair;

public class SpiralMatrix {
    record Pair<T, U>(T first, U second) {

    }
    public static List<Integer> spiralOrder2 (int[][] matrix) {
        List<Integer> li = new ArrayList<>();
        int n = matrix.length;
        int m = matrix[0].length;
        int top = 0, bottom = n-1, right = m-1, left = 0;

        while (top <= bottom && left <= right) {

            // left -> right
            for (int j=left; j <=right; j++) {
                li.add(matrix[top][j]);
            }
            top++;
            if (top > bottom || left > right)
                break;

            // top->bottom
            for (int j=top; j <= bottom; j++) {
                li.add(matrix[j][right]);
            }
            right--;
            if (top > bottom || left > right)
                break;
            
            // right -> left
            for (int j=right; j >= left; j--) {
                li.add(matrix[bottom][j]);
            }
            bottom--;
            if (top > bottom || left > right)
                break;
            
            // bottom -> top
            for (int j=bottom; j >= top; j--) {
                li.add(matrix[j][left]);
            }
            left++;
            // break;
        }

        return li;
    }
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> li = new ArrayList<>();

        int n = matrix.length, m = matrix[0].length;

        if (m == 1 && n > 1) {
            // column matrix
            for (int i=0; i<n; i++) {
                li.add(matrix[i][0]);
            }
            return li;
        }
        // Pair<Integer, Integer> p = new Pair<>(1, "abc");
        Set<Pair<Integer, Integer>> set = new HashSet<>();

        int i = 0, j= 0, a = 0, b = m-1;
        while (a < b) { 
            for (; j < m; j++) {
                if (set.contains(new Pair<>(i,j))) {
                    break;
                }
                // System.out.print(matrix[i][j] + " ");
                li.add(matrix[i][j]);
                set.add(new Pair<>(i, j));
            }
            j--;
            i++;
            // if (set.contains(new Pair<>(i,j))) {
            //     break;
            // }
            for (; i < n; i++) {
                if (set.contains(new Pair<>(i,j))) {
                    break;
                }
                // System.out.print(matrix[i][j] + " ");
                li.add(matrix[i][j]);
                set.add(new Pair<>(i, j));
            }
            i--;
            j--;
            // if (set.contains(new Pair<>(i,j))) {
            //     break;
            // }
            for (; j >= 0; j--) {
                if (set.contains(new Pair<>(i,j))) {
                    break;
                }
                // System.out.print(matrix[i][j] + " ");
                li.add(matrix[i][j]);
                set.add(new Pair<>(i, j));
            }
            j++;
            i--;
            // if (set.contains(new Pair<>(i,j))) {
            //     break;
            // }
            for (; i > 0; i--) {
                if (set.contains(new Pair<>(i,j))) {
                    break;
                }
                // System.out.print(matrix[i][j] + " ");
                li.add(matrix[i][j]);
                set.add(new Pair<>(i, j));
            }
            ++j;i++;
            a++;b--;
            // break;
        }
          // FIX: process remaining unvisited region
        while  (i >= 0 && i < n &&
                j >= 0 && j < m &&
                !set.contains(new Pair<>(i, j))) {

            li.add(matrix[i][j]);
            set.add(new Pair<>(i, j));

            // Try moving down
            if (i + 1 < n &&
                !set.contains(new Pair<>(i + 1, j))) {
                i++;
            }
            // Try moving right
            else if (j + 1 < m && !set.contains(new Pair<>(i, j + 1))) {
                j++;
            }
            else {
                break;
            }
        }
        // System.out.println("i = " + i + " j = " + j);

        return li;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String _2d_input = sc.nextLine();
        _2d_input = _2d_input.trim().substring(1, _2d_input.length()-1);
        // System.out.println(_2d_input);
        String[] _1d_parts = _2d_input.split("]+,");
        int len = _1d_parts[_1d_parts.length-1].length()-1;
        _1d_parts[_1d_parts.length-1] = _1d_parts[_1d_parts.length-1].substring(0, len);

        // for (String part : _1d_parts) {
        //     System.out.println(part + " ");
        // }
        int[][] matrix = new int[_1d_parts.length][];
        int j = 0;
        for (String _1d_part : _1d_parts) {
            _1d_part = _1d_part.trim().substring(1, _1d_part.length());
            // System.out.println(_1d_part);
            String[] numbers = _1d_part.split(",");
            int[] matrix_row = new int[numbers.length];
            int i = 0;
            for (String number : numbers) {
                matrix_row[i++] = Integer.parseInt(number);
            }
            matrix[j] = new int[matrix_row.length];
            matrix[j++] = matrix_row;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {

                System.out.print(matrix[i][k] + " ");
            }
            System.out.println();
        }
        long start = System.nanoTime();
        List<Integer> li = spiralOrder2(matrix);
        long end = System.nanoTime();
        System.out.println("\nTime expended in setZeros: " + (end - start) +" NS.");
        // for (int i = 0; i < matrix.length; i++) {
        //     for (int k = 0; k < matrix[i].length; k++) {

        //         System.out.print(matrix[i][k] + " ");
        //     }
        //     System.out.println();
        // }
        // start = System.nanoTime();
        // setZeroes2(matrix);
        // end = System.nanoTime();
        // System.out.println("Time expended in setZeros2: " + (end - start) + " NS");

        for (int e : li) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
