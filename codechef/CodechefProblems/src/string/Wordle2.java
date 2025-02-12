package src.string;

import java.util.Scanner;

public class Wordle2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            String s = sc.next();
            String t = sc.next();
            String m = "";
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == t.charAt(i)) {
                    m += "G";
                } else {
                    m += "B";
                }
            }
            System.out.println(m);
        }
    }
}
