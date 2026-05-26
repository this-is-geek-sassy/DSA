
// link: https://leetcode.com/problems/pascals-triangle/description/

import java.util.*;

public class PascalsTriangle {

    public static List<List<Integer>> generate (int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        triangle.add(firstRow);
        if (numRows == 1) {
            return triangle;
        }
        List<Integer> secondRow = new ArrayList<>();
        secondRow.add(1);
        secondRow.add(1);
        triangle.add(secondRow);

        if (numRows == 2) {
            return triangle;
        }

        for (int i=3; i <= numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            for (int j=2; j <= i-1; j++) {

                int entry = triangle.get(triangle.size()-1).get(j-2) + triangle.get(triangle.size()-1).get(j-1);
                row.add(entry);
            }
            row.add(1);
            triangle.add(row);
        }
        return triangle;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        
        int numRows = Integer.parseInt(input);
        List<List<Integer>> triangle = generate(numRows);
        for (List<Integer> e : triangle) {
            
            for (Integer num : e) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}