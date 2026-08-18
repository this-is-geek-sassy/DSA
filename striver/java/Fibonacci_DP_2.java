// link: https://takeuforward.org/data-structure/dynamic-programming-introduction

import java.util.Scanner;

public class Fibonacci_DP_2 {

    public static long fib (int n, long[] memory) {
        memory[0] = 0;
        memory[1] = 1;

        for (int i=2; i<=n; i++) {
            memory[i] = memory[i-1] + memory[i-2];
        }
        return memory[n];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] memory = new long[n+1];
        long ans = fib(n, memory);
        System.out.println(ans);
    }
}
