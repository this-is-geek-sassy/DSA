
import java.util.Scanner;

// ljnk: https://leetcode.com/problems/count-good-numbers/description/

public class CountGoodNumbers {

    private static final long[] POW10 = new long[10];
    static {
        POW10[0] = 1;
        for (int i = 1; i < POW10.length; i++) {
            POW10[i] = POW10[i - 1] * 10;
        }
    }

    private static long pow10(int n) {
        return POW10[n];
    }

    private static int countRec (long n) {

        if (n==0)
            return 5;

        // n--;
        if ((n&1) != 0) {
            // odd indices
            return (int)(((long) countRec(n-1) * 4) % (pow10(9) + 7));
        }
        else {
            // even indices
            return (int)(((long) countRec(n-1) * 5) % (pow10(9) + 7));
        }
    }

    public static long myPow(long x, long n) {
        
        // System.out.println(n);
        if (n==0)
            return 1;
        if (n < 0)
            return myPow((long)1/x, (long)-n);

        long half = myPow(x, n / 2);
        if (n % 2 == 1)
            return half * half * x;
        else 
            return half * half;
    }

    public static int countGoodNumbers(long n) {
        // return countRec(n-1);

        long odd = n/2;
        long even = (n+1) / 2;

        long five = myPow(5, even);
        five = five % (pow10(9) + 7);

        long four = 0;
        if (odd != 0)
            four = myPow(4, odd) % (pow10(9) + 7);

        return (int)((five + four) % (pow10(9) + 7));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long n = sc.nextLong();
        int ans = countGoodNumbers(n);
        System.out.println(ans);
        sc.close();
    }
}
