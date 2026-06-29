
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// link: https://www.geeksforgeeks.org/problems/row-with-max-1s0023/1

public class RowWithMax1s {
    public static int[][] readMatrixGFG (Scanner sc) {

        StringBuilder input = new StringBuilder();
        boolean matrixComplete = false;

        while (sc.hasNextLine() && !matrixComplete) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                input.append(line).append(' ');
                String currentInput = input.toString();
                int matrixStart = currentInput.indexOf("[[");

                if (matrixStart >= 0) {
                    int depth = 0;

                    for (int i = matrixStart; i < currentInput.length(); i++) {
                        char ch = currentInput.charAt(i);

                        if (ch == '[') {
                            depth++;
                        } else if (ch == ']') {
                            depth--;
                        }

                        if (depth == 0) {
                            matrixComplete = true;
                            break;
                        }
                    }
                }
            }
        }

        String currentInput = input.toString();
        int matrixStart = currentInput.indexOf("[[");

        if (matrixStart < 0) {
            throw new IllegalArgumentException("Could not find matrix input.");
        }

        currentInput = currentInput.substring(matrixStart);

        int matrixEnd = -1;
        int depth = 0;

        for (int i = 0; i < currentInput.length(); i++) {
            char ch = currentInput.charAt(i);

            if (ch == '[') {
                depth++;
            } else if (ch == ']') {
                depth--;
                if (depth == 0) {
                    matrixEnd = i + 1;
                    break;
                }
            }
        }

        if (matrixEnd < 0) {
            throw new IllegalArgumentException("Incomplete matrix input.");
        }

        currentInput = currentInput.substring(0, matrixEnd);

        List<int[]> rows = new ArrayList<>();
        Pattern rowPattern = Pattern.compile("\\[(?!\\[)([^\\[\\]]+)\\]");
        Matcher matcher = rowPattern.matcher(currentInput);

        while (matcher.find()) {
            String rowText = matcher.group(1).trim();
            if (rowText.isEmpty()) {
                continue;
            }

            String[] nums = rowText.split("[\\s,]+");
            int[] row = new int[nums.length];

            for (int i = 0; i < nums.length; i++) {
                row[i] = Integer.parseInt(nums[i]);
            }

            rows.add(row);
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Could not parse matrix input.");
        }

        return rows.toArray(new int[rows.size()][]);
    }
    

    public static int rowWithMax1s(int[][] matrix) {
        // code here
        int n = matrix.length, m = matrix[0].length;
        int maxOnes = 0, maxOnesRow = -1;
        for (int i=0; i < n; i++) {
            int onesForThisRow = 0;
            int low = 0, high = m-1, mid = 0;

            while (low <= high) {
                mid = low + (high - low)/2;

                if (matrix[i][mid] == 1) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            // try {
            //     System.out.println("low = " + low + " matrix["+i+"][low] = " + matrix[i][low]);
            // } catch (ArrayIndexOutOfBoundsException e) {
            //     System.out.println("low = " + low);
            //     e.printStackTrace();
            // }
            onesForThisRow = m - low;
            if (onesForThisRow > maxOnes) {
                maxOnes = onesForThisRow;
                maxOnesRow = i;
            }
        }
        return maxOnesRow;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // int n = sc.nextInt(), m = sc.nextInt();  // rows, columns
        int[][] matrix = readMatrixGFG(sc);

        int ans = rowWithMax1s(matrix);
        System.out.println("ans = " + ans);
        for (int[] row : matrix)
            System.out.println(Arrays.toString(row));
        sc.close();
    }
}
