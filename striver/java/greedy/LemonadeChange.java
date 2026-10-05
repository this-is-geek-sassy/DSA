package greedy;

// https://leetcode.com/problems/lemonade-change/

import java.util.Scanner;


public class LemonadeChange {

    public static boolean lemonadeChange(int[] bills) {
        
        int[] cashbox = new int[3];  // 3 possible bills, 5, 10, 20
        // cashbox[0] := #5 dollar bills
        // cashbox[1] := #10 dollar bills
        // cashbox[2] := #20 dollar bills

        for (int bill: bills) {
            if (bill == 5) {
                cashbox[0]++;
            }
            else if (bill == 10) {
                if (cashbox[0] >= 1) {
                    cashbox[0]--;
                    cashbox[1]++;
                } else {
                    return false;
                }
            }
            else {
                if (cashbox[1] >= 1 && cashbox[0] >= 1) {
                    // 15 change (10 + 5)
                    cashbox[0]--;
                    cashbox[1]--;
                    cashbox[2]++;
                } else if (cashbox[0] >= 3) {
                    // 15 = 5+5+5
                    cashbox[0] -= 3;
                    cashbox[2]++;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String billsInput = sc.nextLine().trim();
        String[] billsArr = billsInput.substring(1, billsInput.length()-1).split(",");
        int[] bills = new int[billsArr.length];
        int i = 0;

        for (String s: billsArr) {
            bills[i++] = Integer.parseInt(s);
        }
        boolean ans = lemonadeChange(bills);
        System.out.println(ans);
        sc.close();
    }
}
