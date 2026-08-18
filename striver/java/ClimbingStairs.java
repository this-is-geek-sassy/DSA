
import java.util.Scanner;

// link: https://leetcode.com/problems/climbing-stairs/description/

public class ClimbingStairs {

    private static int helper (int n, int[] memory) {

        memory[1] = 1;
        memory[2] = 2;

        if (n < 3) {
            return memory[n];
        }
        if (memory[n] != 0) {
            return memory[n];
        }
        memory[n] = helper(n-1, memory) + helper(n-2, memory);
        return memory[n];
    }

    public static int climbStairs (int n) {
        if (n==1)
            return 1;
        int[] memory = new int[n+1];
        
        return helper(n, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int ans = climbStairs(n);
        System.out.println(ans);
        sc.close();
    }
}
