
// link: https://leetcode.com/problems/palindrome-number/description/

import java.util.Scanner;

public class PalindromeNumber2 {

    public boolean isPalindrome(int x)
    {
        if (x < 0)
            return false;
        if (x < 10)
            return true;

        int noOfDigits = (int) Math.log10(x) + 1;

        int[] numb = new int[noOfDigits];
        int i = 0;
        while (x > 0) {

            int r = x % 10;
            numb[i] = r;  i++;
            x /= 10;
        }
        for (int j = 0; j < numb.length; j++) {
            System.out.println(numb[j]);
        }
        for (int j = 0; j < numb.length/2; j++) {
            if (numb[j] != numb[numb.length-1 - j]) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();

        PalindromeNumber2 number = new PalindromeNumber2();
        System.out.println(number.isPalindrome(x));

        s.close();
    }
}
