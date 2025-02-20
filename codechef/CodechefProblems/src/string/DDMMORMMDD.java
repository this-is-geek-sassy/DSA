package src.string;

// https://www.codechef.com/practice/course/strings/STRINGSPRO/problems/DDMMORMMDD

import java.util.Scanner;

public class DDMMORMMDD {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            String date = sc.next();
            int firstPart = Integer.parseInt(date.substring(0, 2));
            int secondPart = Integer.parseInt(date.substring(3, 5));

            if (firstPart <= 12 && secondPart <= 12) {
                System.out.println("BOTH");
            } else if (firstPart <= 12) {
                System.out.println("MM/DD/YYYY");
            } else {
                System.out.println("DD/MM/YYYY");
            }
        }
        sc.close();
    }
}
