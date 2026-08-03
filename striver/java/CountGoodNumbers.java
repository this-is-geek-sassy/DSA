
import java.util.Scanner;

// ljnk: https://leetcode.com/problems/count-good-numbers/description/

public class CountGoodNumbers {

    private static final long[] POW10 = new long[8];
    static {
        POW10[0] = 1;
        for (int i = 1; i < POW10.length; i++) {
            POW10[i] = POW10[i - 1] * 10;
        }
    }

    private static long pow10(int n) {
        return POW10[n];
    }

    public static int countGoodNumbers(long n) {
        
        if (n==1)
            return 5;

        if ((n&1) != 0) {
            return (int)((countGoodNumbers(n-1) + 5) % (pow10(7) + 7));
        }
        else {
            
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        sc.close();
    }
}
