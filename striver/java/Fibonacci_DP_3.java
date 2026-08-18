// link: https://takeuforward.org/data-structure/dynamic-programming-introduction

import java.util.Scanner;

public class Fibonacci_DP_3 {

    private static long fib (int n, long[] memory) {

        memory[0] = 0;
        memory[1] = 1;

        if (n < 2)
            return memory[n];
        int i = 2;
        for (int j=2; j<n; j++) {
            memory[i] = memory[(i+1)%3] + memory[(i+2)%3];
            i = (i+1) % 3;
        }
        memory[i] = memory[(i+1)%3] + memory[(i+2)%3];
        // return memory[(i-1) % 3];
        return memory[i];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] memory = new long[3];
        long ans = fib(n, memory);
        System.out.println(ans);
        sc.close();
    }
}
