package src.string;

// https://www.codechef.com/practice/course/strings/STRINGS/problems/ADDONE

import java.util.Scanner;

public class addOne {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            String N = sc.next();
//            System.out.println("N is " + N);
            String NPlusOne = "";
            int carry = 1;
            int i;
            for (i = N.length()-1; i >= -1 && carry == 1; i--) {

                if (i >=0 && N.charAt(i) == '9') {
                    int digit = (int) (N.charAt(i) - '0');
                    //                    System.out.println(digit);
                    NPlusOne = "0" + NPlusOne;
                } else if (i >= 0) {
                    int digit = (int) (N.charAt(i) - '0');
                    NPlusOne = (digit + 1) + NPlusOne;
                    carry = 0;
//                    break;
                } else {
                    NPlusOne = "1" + NPlusOne;
                }
            }
            if (i >= 0)
                NPlusOne = N.substring(0, i+1) + NPlusOne;
            System.out.println(NPlusOne);
        }
    }
}
