package src.string;

// https://www.codechef.com/practice/course/strings/STRINGS/problems/DIFFCONSEC

import java.util.Scanner;

public class DIFFCONSEC {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int noOfOperations = 0;
            int N = sc.nextInt();
            String S = sc.next();

            for (int i = 0; i < N; i++) {
                if ((i+1 < N) && S.charAt(i) == '0' && S.charAt(i + 1) == '0')
                    noOfOperations++;
                else if ((i+1 < N) && S.charAt(i) == '1' && S.charAt(i + 1) == '1') {
                    noOfOperations++;
                }
            }
            System.out.println(noOfOperations);
        }
    }
}
