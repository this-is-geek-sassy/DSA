package src.string;

// https://www.codechef.com/practice/course/strings/STRINGS/problems/TITLECASE

import java.util.Scanner;

public class TITLECASE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        sc.nextLine();
        while (T-- > 0) {
            String s = sc.nextLine();
//            System.out.println(s.length());
            String[] words = s.split("\\s+");

//            System.out.println(words.length);
            for (int i = 0; i < words.length; i++) {
//                System.out.println(words[i]);
                if (!words[i].equals(words[i].toUpperCase())) {
                    String firstLetter = words[i].substring(0, 1).toUpperCase();
                    String newString = firstLetter + words[i].substring(1).toLowerCase();
                    words[i] = newString;
                }
            }

            for (String word : words) {
                System.out.println(word);
            }


        }
    }
}
