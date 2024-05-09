package string;

// problem source: https://www.codechef.com/practice/course/strings/STRINGS/problems/DNASTORAGE

import java.util.IllegalFormatException;
import java.util.Scanner;

public class DNAStorage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        while (t-- > 0) {
            int n = scanner.nextInt();
            String s = scanner.next();

            StringBuilder sequence = new StringBuilder();

            // Your code goes here
            for (int i = 0; i < n; i++, i++) {
//                char first = s.charAt(i);
//                char second = s.charAt(i+1);
                String substring = s.substring(i, i+2);
//                System.out.println(substring);

                sequence.append(switch (substring) {
                    case "00" -> "A";
                    case "01" -> "T";
                    case "10" -> "C";
                    case "11" -> "G";
                    default -> throw new IllegalArgumentException("Please check your input");
                });
            }

            System.out.println(sequence);
        }
    }
}
