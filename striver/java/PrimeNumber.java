
import java.util.Scanner;



// link: https://www.geeksforgeeks.org/problems/prime-number2314/1

public class PrimeNumber {

    static boolean isPrime (int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        System.out.println(isPrime(n));
        s.close();
    }
}
