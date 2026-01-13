// Link: https://leetcode.com/problems/reverse-integer/description/
import java.util.Scanner;

public class ReverseDigits2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int n = s.nextInt(), m = 0;
        // System.out.println(n);
        boolean isMinus = (n < 0);

        if (isMinus) {
            n = Math.abs(n);
        }

        while (n > 0) {
            int r = n % 10;
            System.out.println("r = " + r);
            long temp = (long)m*10 + r;
            if (temp > 2147483647L || temp < -2147483648L)
            {
                System.out.println(0);
                return;
            }
            m = (int) temp;
            System.out.println("m = " + m);
            n = n / 10;
        }
        if (isMinus)
            m = -m;
        System.out.println(m);

        s.close();
    }
}
