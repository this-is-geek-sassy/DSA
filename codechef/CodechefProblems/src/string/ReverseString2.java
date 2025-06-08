package src.string;

// link: https://leetcode.com/problems/reverse-string-ii/description/

import java.util.Scanner;

public class ReverseString2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder s = new StringBuilder(sc.nextLine());
        int k = sc.nextInt();

        for (int i = 0; i < s.length(); i += 2*k) {
            if ((i+k) < s.length()) {
                StringBuilder chunk = new StringBuilder(s.substring(i, i + k));
                s.replace(i, i + k, chunk.reverse().toString());
            } else {
                StringBuilder chunk = new StringBuilder(s.substring(i, s.length()));
                s.replace(i, s.length(), chunk.reverse().toString());
            }
            //            System.out.println(chunk.reverse());
        }
        System.out.println(s);
    }
}
