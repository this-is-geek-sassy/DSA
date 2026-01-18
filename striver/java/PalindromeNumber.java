
// link: https://leetcode.com/problems/palindrome-number/description/

import java.util.Scanner;

public class PalindromeNumber {

    public boolean isPalindrome(int x) {

        if (x < 0)
            return false;

        int noOfDigits = (int)Math.log10(x);
        int initNoOfDigits = (noOfDigits+1) / 2, count = 1;
        // System.out.println(noOfDigits);
        int y = x, i = 1;
        while (count <= initNoOfDigits) { 
            // divide y by 10^noOfDigits
            int leftDigit = (int) (y / Math.pow(10, noOfDigits));
            y = y - (leftDigit * (int)Math.pow(10, noOfDigits));
            int rightDigit = x % (int) (Math.pow(10, 1));
            x = x / (int) (Math.pow(10, 1));

            // logs:
            System.out.println("i: " + i);
            System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println("leftDigit: " + leftDigit);
            System.out.println("rightDigit: " + rightDigit);

            //checking:
            if (leftDigit != rightDigit)
                return false;

            // updation:
            i++;
            noOfDigits = (int)Math.log10(y);
            count++;
        }
        return true;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();

        PalindromeNumber number = new PalindromeNumber();
        System.out.println(number.isPalindrome(x));

        s.close();
    }
}
