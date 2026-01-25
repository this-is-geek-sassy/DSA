
import java.util.ArrayDeque;
import java.util.Scanner;



// link: https://www.geeksforgeeks.org/problems/all-divisors-of-a-number/1


public class AllDivisorsOfANumber {

    public static void print_divisors(int n) {

        ArrayDeque<Integer> a = new ArrayDeque<>();
        for (int i = 1; i <= Math.sqrt(n); i++) {

            if (n % i == 0){ 
                System.out.printf("%d ", i);
                a.push(n/i);
            }
        }
        for (Integer e : a) {
            if (e == Math.sqrt(n)) {
                continue;
            }
            System.out.printf("%d ", e);
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        print_divisors(n);
        s.close();
    }
}
