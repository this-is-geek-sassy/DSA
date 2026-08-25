
import java.util.*;

// link: https://takeuforward.org/data-structure/dynamic-programming-frog-jump-with-k-distances-dp-4, https://www.geeksforgeeks.org/problems/minimal-cost/1 (maybe?)
//  https://www.geeksforgeeks.org/problems/minimal-cost/1


public class FrogJumpKDist {

    private static int helper (int[] heights, int k, int stepNow, int[] memory) {

        if (stepNow == heights.length-1) {
            return 0;
        }
        if (memory[stepNow] != -1)
            return memory[stepNow];
        
        int[] energies = new int[k+1];
        Arrays.fill(energies, Integer.MAX_VALUE);

        int minEn = Integer.MAX_VALUE;
        for (int i = 1; i <= k; i++) {
            if (stepNow + i < heights.length) {
                energies[i] = helper(heights, k, stepNow+i, memory);
                energies[i] += Math.abs(heights[stepNow] - heights[stepNow+i]);
            }
            if (energies[i] < minEn) {
                minEn = energies[i];
            }
        }
        memory[stepNow] = minEn;
        return minEn;
    }
    public static int solver (int[] heights, int k) {

        int[] memory = new int[heights.length];
        Arrays.fill(memory, -1);
        return helper(heights, k, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        int k = sc.nextInt();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(", ");
        int[] heights = new int[inpArr.length];
        int i = 0;

        for (String s: inpArr) {
            heights[i++] = Integer.parseInt(s);
        }
        int ans = solver(heights, k);
        System.out.println(ans);
        sc.close();
    }
}
