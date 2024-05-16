package src.contest;

import java.util.Scanner;

public class Origin {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();
        while (t-- != 0) {
            long n, sum=0, grandSum = 0;
            n = scanner.nextLong();

            for (int i = 1; i <= n; i++) {
                long k = i;
                while (true) {
                    while (k != 0) {
                        long r = k % 10;
                        sum += r;
                        k /= 10;
                    }
//                System.out.print(sum + " ");
                    boolean isSingleDigit = (sum > 0 && sum < 10);
                    if (isSingleDigit)
                        break;
                    k = sum;
                    sum = 0;
                }
//                System.out.println(sum);
                grandSum += sum;
                sum = 0;
            }
            System.out.println(grandSum);
        }
    }
//    public static boolean isSingleDigit(int n) {
//
//        return n % 10 != 0 && isSingleDigit(n/10);
//    }
}
