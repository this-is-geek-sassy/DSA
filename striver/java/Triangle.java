
import java.util.*;


// link: https://leetcode.com/problems/triangle/description/

public class Triangle {

    private static int helper (List<List<Integer>> triangle, int rowNow, int colNow, int[][] memory) {

        if (rowNow == triangle.size()-1) {
            return triangle.get(rowNow).get(colNow);
        }

        if (memory[rowNow][colNow] != Integer.MAX_VALUE) {
            return memory[rowNow][colNow];
        }
        // if (memory.size()-1 < rowNow) {
        //     memory.add(new ArrayList<>());
        // }
        int optionA = triangle.get(rowNow).get(colNow) + helper(triangle, rowNow+1, colNow, memory);
        int optionB = triangle.get(rowNow).get(colNow) + helper(triangle, rowNow+1, colNow+1, memory);
        
        // memory.get(rowNow).add(Math.min(optionA, optionB));
        memory[rowNow][colNow] = Math.min(optionA, optionB);
        // return memory.get(rowNow).get(colNow);
        return memory[rowNow][colNow];
    }

    public static int minimumTotal(List<List<Integer>> triangle) {

        int[][] memory = new int[triangle.size()][triangle.get(triangle.size()-1).size()];

        for (int[] memRow: memory)
            Arrays.fill(memRow, Integer.MAX_VALUE);

        return helper(triangle, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split("\\],\\[");

        List<List<Integer>> triangle = new ArrayList<>();

        for (String s: inpArr) {
            // s = s.substring(1, s.length()-1);
            s = s.replace("[", "").replace("]", "");
            String[] sArr = s.split(",");
            List<Integer> row = new ArrayList<>();
            
            for (String _s : sArr) {
                // System.out.println("Parsing: [" + _s + "]");
                row.add(Integer.parseInt(_s));
            }
            triangle.add(row);
        }
        int ans = minimumTotal(triangle);
        System.out.println(ans);
        sc.close();
    }
}