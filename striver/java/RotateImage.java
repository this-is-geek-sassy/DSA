
// link: https://leetcode.com/problems/rotate-image/description/

import java.util.*;

public class RotateImage {

    public static void rotate(int[][] matrix) {
        // int[] temp = new int[matrix[0].length];
        int temp_;
        // transpose
        for (int i=0; i < matrix.length; i++) {
            for (int j=i; j < matrix[0].length; j++) {
                temp_ = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp_;
            }
        }
        // // pretty print
        // System.out.println("Transposed matrix:");
        // for (int i=0; i < matrix.length; i++) {
        //     for (int j=0; j < matrix[0].length; j++) {
        //         System.out.print(matrix[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        int len = matrix[0].length;
        for (int i=0; i < matrix.length; i++) {
            for (int j=0; j < len/2; j++) {
                int temp = matrix[i][len-1-j];
                matrix[i][len-1-j] = matrix[i][j];
                matrix[i][j] = temp;
            }
            // System.out.println();
        }
    //     // pretty print
    //     System.out.println("Transformed matrix:");
    //     for (int i=0; i < matrix.length; i++) {
    //         for (int j=0; j < matrix[0].length; j++) {
    //             System.out.print(matrix[i][j] + " ");
    //         }
    //         System.out.println();
    //     }
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
        rotate(matrix);
        long end = System.nanoTime();
        System.out.println("Time expended in setZeros: " + (end - start) +" NS.");
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
        // sc.close();
    }
}