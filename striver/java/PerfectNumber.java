
import java.util.ArrayDeque;
import java.util.Scanner;


// link: https://leetcode.com/problems/perfect-number/description/

public class PerfectNumber {

    public static boolean checkPerfectNumber (int num) {

        ArrayDeque<Integer> a = new ArrayDeque<>();
        for (int i = 1; i <= Math.sqrt(num); i++) {

            if (num % i == 0) {
                a.addFirst(i);
                a.addLast(num/i);
            }
        }

        int counter = 0;
        for (Integer e : a) {
            if (e == Math.sqrt(num) || e == num) {
                continue;
            }
            counter += e;
        }
        return counter == num;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        System.out.println(checkPerfectNumber(n));
        s.close();
    }
}
