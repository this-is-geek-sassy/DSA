

// link: https://leetcode.com/problems/complete-prime-number/description/

import java.util.Arrays;
import java.util.Scanner;

public class CompletePrimeNumber {


    static boolean isPrime (int n) {
        if (n==0 || n==1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public static boolean completePrime (int n) {
        if (n==0 || n==1) {
            return false;
        }
        int d = (int) Math.log10(n) + 1, n_bkp = n;

        int[] digits = new int[d];
        for (int i = d-1; i >= 0 ; i--) {
            int t = n%10;
            digits[i] = t;
            n /= 10;
        }
        // System.out.println(Arrays.toString(digits));
        for (int i = 0; i < d; i++) {
            // reconstruct 0....i
            int temp = 0, c=1;
            for (int j=i; j>=0; j--) {
                temp += (digits[j] * c);
                c *= 10;
            }
            // System.out.println(temp);
            if (! isPrime(temp))
                return false;
        }
        for (int i = d-1; i >= 0; i--) {
            // reconstruct i....d-1
            int temp = 0, c=1;
            for (int j=d-1; j>=i; j--) {
                temp += (digits[j] * c);
                c *= 10;
            }
            // System.out.println(temp);
            if (! isPrime(temp))
                return false;

        }
        return true;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int num = s.nextInt();
        completePrime(num);
        s.close();
    }
}
