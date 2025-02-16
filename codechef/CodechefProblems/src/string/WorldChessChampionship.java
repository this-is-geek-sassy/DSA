package src.string;

import java.util.Scanner;

// https://www.codechef.com/practice/course/strings/STRINGS/problems/WCC
public class WorldChessChampionship {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int prevWinner = -1;
        while (T-- > 0) {
            int thisWinner = 0;
            int carlsenPoints = 0, chefPoints = 0;
            long carlsenPrizeMoney = 0, chefPrizeMoney = 0;

            long X = sc.nextInt();
            String matchResult = sc.next();

            for (int i = 0; i < matchResult.length(); i++) {
                char ch = matchResult.charAt(i);
                if (ch == 'C') {
                    carlsenPoints += 2;
                } else if (ch == 'N') {
                    chefPoints += 2;
                } else {
                    chefPoints += 1;
                    carlsenPoints += 1;
                }
            }
            if (carlsenPoints > chefPoints) {
                thisWinner = 0;
                carlsenPrizeMoney = 60 * X;
                chefPrizeMoney =40 * X;

            } else if (carlsenPoints < chefPoints) {
                thisWinner = 1;
                carlsenPrizeMoney = 40 * X;
                chefPrizeMoney = 60 * X;

            } else {
//                thisWinner = prevWinner;
//                if (prevWinner == 0) {
//                    carlsenPrizeMoney = 55 * X;
//                    chefPrizeMoney = 45 * X;
//                } else if (prevWinner == 1) {
//                    carlsenPrizeMoney = 45 * X;
//                    chefPrizeMoney = 55 * X;
//                }
                thisWinner = 0;
                carlsenPrizeMoney = 55 * X;
                chefPrizeMoney = 45 * X;
            }
            prevWinner = thisWinner;
            System.out.println(carlsenPrizeMoney);
        }
    }
}
