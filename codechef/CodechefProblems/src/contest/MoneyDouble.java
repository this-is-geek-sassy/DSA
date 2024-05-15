package src.contest;

import java.util.Scanner;

public class MoneyDouble {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();
        while (t-- != 0) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();

            for (int i = 1; i <= y; i++) {
                x = Integer.max(x + 1000, x * 2);
            }
            System.out.println(x);
        }
    }
}
