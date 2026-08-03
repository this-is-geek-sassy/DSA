// https://leetcode.com/problems/string-to-integer-atoi/

import java.util.Scanner;

public class MyAtoi {

    private static final long[] POW10 = new long[19];
    static {
        POW10[0] = 1;
        for (int i = 1; i < POW10.length; i++) {
            POW10[i] = POW10[i - 1] * 10;
        }
    }

    private static long pow10(int n) {
        return POW10[n];
    }

    private static int atoiRecursive(String s, int sign) {

        if (s.isEmpty())
            return 0;

        int num = atoiRecursive(s.substring(1), sign);

        if (num == Integer.MIN_VALUE || num == Integer.MAX_VALUE)
            return num;

        long num2 = (long)(s.charAt(0) - '0') * pow10(s.length() - 1);
        long temp = num + num2;

        if (sign == -1 && temp >= (long) Integer.MAX_VALUE + 1) {
            return Integer.MIN_VALUE;
        }
        else if (temp > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        else {
            return (int)temp;
        }
    }

    public static int myAtoi(String s) {
        s = s.trim();
        if (s.isEmpty())
            return 0;
        int isNegative = 1;
        if (s.charAt(0) == '-') {
            isNegative = -1;
            s = s.substring(1);
        }
        else if (s.charAt(0) == '+')
            s = s.substring(1);
        int i;
        for (i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                break;
        }
        s = s.substring(0, i);
        // System.out.println(i);
        // System.out.println(s);
        // return 1<<3; // 2^3
        if (i == 0)
            return 0;

        // zero omission part
        for (i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0')
                break;
        }
        s = s.substring(i);
        // System.out.println("s = " + s);
        if (isNegative == -1 && s.length() > 10)
            return Integer.MIN_VALUE;
        else if (s.length() > 10) 
            return Integer.MAX_VALUE;
        int ans = atoiRecursive(s, isNegative);

        return (ans < 0) ? ans : ans*isNegative;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // System.out.println(Integer.MIN_VALUE);
        // System.out.println(Integer.MAX_VALUE);
        String input = sc.nextLine();
        System.out.println("Final ans = " + myAtoi(input));
        sc.close();
    }
}
