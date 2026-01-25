

// link: https://www.geeksforgeeks.org/problems/gcd-of-two-numbers3459/1

import java.util.Scanner;

class GcdOrHcf {

    public static int gcd(int a, int b) {

        if (a==1 || b==1)
            return 1;
        if (a == b)
            return a;

        int r = a % b;
        if (r == 0)
            return b;
        return gcd(b, r);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt(), b = s.nextInt();

        int g = gcd(a, b);
        System.out.println(g);
        s.close();
    }
}