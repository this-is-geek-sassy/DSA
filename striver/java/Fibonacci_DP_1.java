
import java.util.Scanner;

// link: https://takeuforward.org/data-structure/dynamic-programming-introduction

public class Fibonacci_DP_1 {

    public static long fib (int n, long[] memory) {

        if (n==0) 
            return 0;
        if (n==1) 
            return 1;

        if (memory[n] != 0)
            return memory[n];

        memory[n] = fib(n-1, memory) + fib(n-2, memory);
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
