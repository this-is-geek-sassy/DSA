
import java.util.Scanner;

// link: https://takeuforward.org/data-structure/dynamic-programming-frog-jump-dp-3

public class FrogJumpDp3 {

    private static int helper (int[] heights, int runningIdx, int[] memory) {

        // base case 1
        if (runningIdx == heights.length-2) {
            return Math.abs(heights[runningIdx] - heights[runningIdx+1]);
        }
        // base case 2
        if (runningIdx == heights.length-1) {
            return 0;
        }
        if (memory[runningIdx] != 0)
            return memory[runningIdx];

        int energy1 = Math.abs(heights[runningIdx] - heights[runningIdx+1]) + helper(heights, runningIdx+1, memory);
        int energy2 = Math.abs(heights[runningIdx] - heights[runningIdx+2]) + helper(heights, runningIdx+2, memory);
        int minEnergy = Math.min(energy1, energy2);
        memory[runningIdx] = minEnergy;
        return minEnergy;
    }
    // Tabulation
    private static int helper2 (int[] heights, int[] energies) {

        // heights.length == energies.length, by design
        energies[energies.length-2] = Math.abs(heights[heights.length-2] - heights[heights.length-1]);

        for (int i = energies.length-3; i>=0; i--) {
            int energy1 = Math.abs(heights[i] - heights[i+2]) + energies[i+2];
            int energy2 = Math.abs(heights[i] - heights[i+1]) + energies[i+1];
            energies[i] = Math.min(energy1, energy2);
        }
        return energies[0];
    }
    public static int frogJump (int[] heights) {
        int[] memory = new int[heights.length];
        // return helper(heights, 0, memory);
        return helper2(heights, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        String[] ip_arr = input.trim().substring(1, input.length()-1).split(", ");
        int[] heights = new int[ip_arr.length];
        int i=0;
        for (String s: ip_arr) {
            heights[i++] = Integer.parseInt(s);
        }
        int ans = frogJump(heights);
        System.out.println(ans);
        sc.close();
    }
}
