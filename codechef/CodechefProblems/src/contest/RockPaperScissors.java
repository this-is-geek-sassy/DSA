package src.contest;

import java.util.Scanner;

public class RockPaperScissors {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();
        while (t-- != 0) {
            int n, count = 1;
            String A, b;
            n = scanner.nextInt();
            A = scanner.next();
            b = "w";

            for (int i = 1; i < n; i++) {

                if (A.charAt(i) == 'R') {
                    if (A.charAt(i-1) == 'R' && b.charAt(i-1) == 'l') {
                        count++;
                        b += 'w';
                    }
                    else if (A.charAt(i-1) != 'R') {
                        count++;
                        b += 'w';
                    }
                    else
                        b += 'l';
                } else if (A.charAt(i) == 'P') {
                    if (A.charAt(i-1) == 'P' && b.charAt(i-1) == 'l') {
                        count++;
                        b += 'w';
                    }
                    else if (A.charAt(i-1) != 'P') {
                        count++;
                        b += 'w';
                    }
                    else
                        b += 'l';
                } else if (A.charAt(i) == 'S') {
                    if (A.charAt(i-1) == 'S' && b.charAt(i-1) == 'l') {
                        count++;
                        b += 'w';
                    }
                    else if (A.charAt(i-1) != 'S') {
                        count++;
                        b += 'w';
                    }
                    else
                        b += 'l';
                }
            }
            System.out.println(count);
        }
    }
}
