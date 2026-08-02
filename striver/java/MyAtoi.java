// https://leetcode.com/problems/string-to-integer-atoi/

import java.util.Scanner;

public class MyAtoi {

    private static int atoiRecursive(String s, int idx, int sign) {

        if (s.isEmpty())
            return 0;
        if (idx == 0)
            return (s.charAt(0) - '0');
        int num = atoiRecursive(s.substring(1), idx - 1, sign);

        long temp = ((s.charAt(0) - '0') * (long) Math.pow(10, idx)) + num;
        if (temp > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        else if (sign == -1 && temp > 2147483648L)
            return Integer.MIN_VALUE;
        else
            return (int) temp;
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
        System.out.println(i);
        System.out.println(s);
        // return 1<<3; // 2^3
        if (i == 0)
            return 0;

        for (i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0')
                break;
        }
        s = s.substring(i);
        System.out.println("s = " + s);
        int ans = atoiRecursive(s, s.length() - 1, isNegative);

        return (ans < 0) ? ans : ans*isNegative;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // System.out.println(Integer.MIN_VALUE);
        // System.out.println(Integer.MAX_VALUE);
        String input = sc.nextLine();
        System.out.println(myAtoi(input));
        sc.close();
    }
}
