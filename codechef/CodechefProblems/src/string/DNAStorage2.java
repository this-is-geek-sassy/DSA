package src.string;

import java.util.Scanner;

public class DNAStorage2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        while (t-- > 0) {
            int n = scanner.nextInt();
            String s = scanner.next();

            // Your code goes here
            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < n; i+=2) {
                String part = s.substring(i, i+2);
                if (part.equals("00"))
                    newString.append("A");
                else if (part.equals("10")) {
                    newString.append("C");
                } else if (part.equals("01")) {
                    newString.append("T");
                }
                else
                    newString.append("G");
            }
            System.out.println(newString);
        }
    }
}
