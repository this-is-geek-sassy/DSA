package string;

// problem source: https://www.codechef.com/practice/course/strings/STRINGS/problems/WORDLE?tab=statement

import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        int n;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        while (n-- != 0) {
            String s, t, m;
            s = scanner.next();
            t = scanner.next();
            m ="";

//            System.out.println(s);
//            System.out.println(t);

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == t.charAt(i))
                    m += "G";
                else
                    m += "B";
            }
            System.out.println(m);
        }
    }
}
