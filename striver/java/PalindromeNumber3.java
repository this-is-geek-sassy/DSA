import java.util.Scanner;

public class PalindromeNumber3 {
    public boolean isPalindrome(int x) {

        if (x < 0) 
            return false;
        if (x < 10) 
            return true;
        int rev = 0, y = x;
        while (x > 0) {

            int r = x % 10;
            rev = rev*10 + r;
            x /= 10;
        }
        // System.out.println(rev);
        if (y == rev)
            return true;
        return false;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();

        PalindromeNumber3 number = new PalindromeNumber3();
        System.out.println(number.isPalindrome(x));

        s.close();
    }
}
