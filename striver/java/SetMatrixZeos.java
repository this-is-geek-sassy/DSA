
// link: https://leetcode.com/problems/set-matrix-zeroes/description/

import java.io.Console;
import java.util.*;

public class SetMatrixZeos {
    public static void setZeroes(int[][] matrix) {

        ArrayList<Integer> al = new ArrayList<>();

        int i, k;
        for (i = 0; i < matrix.length; i++) {
            for (k = 0; k < matrix[i].length; k++) {
                if (matrix[i][k] == 0) {
                    // break;
                    al.add(i);
                    al.add(k);
                }
            }
            // if (k < matrix[i].length && matrix[i][k] == 0) {
            //     al.add(i);
            //     al.add(k);
            //     // break;
            // }
        }
        HashSet<Integer> hst = new HashSet<>();
        for (int h=0; h < al.size(); h+=2) {
            if (hst.contains(al.get(h))) {
                al.set(h, Integer.MIN_VALUE);
            }
            else {
                hst.add(al.get(h));
            }
        }
        hst.clear();
        for (int h=1; h < al.size(); h+=2) {
            if (hst.contains(al.get(h))) {
                al.set(h, Integer.MIN_VALUE);
            }
            else {
                hst.add(al.get(h));
            }
        }
        System.out.print("positions: ");
        for (Integer e : al) {
            System.out.print(e + " ");
        }
        System.out.println();
        for (int h=0; h < al.size(); h++) {
            if (al.get(h) == Integer.MIN_VALUE) {
                continue;
            }
            if (h%2 == 0) {
                int H = al.get(h);
                for (int y=0; y < matrix[H].length; y++) {
                    matrix[H][y] = 0;
                }
            } else {
                int H = al.get(h);
                for (int y=0; y < matrix.length; y++) {
                    matrix[y][H] = 0;
                }
            }
        }
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
        // for (int i = 0; i < matrix.length; i++) {
        //     for (int k = 0; k < matrix[i].length; k++) {

        //         System.out.print(matrix[i][k] + " ");
        //     }
        //     System.out.println();
        // }
        
        setZeroes(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {

                System.out.print(matrix[i][k] + " ");
            }
            System.out.println();
        }
        
        sc.close();
    }
}
