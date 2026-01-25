
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/armstrong-numbers2727/1

public class ArmstrongNumber {

    public static boolean isArmstrongNumber (int num) {
        int d = (int) Math.log10((int)num) + 1;
        int numcpy = num;
        int an = 0;
        while (num != 0) {
            int r = num % 10;
            an += Math.pow(r, d);
            num /= 10;
        }
        // System.out.println(an);
        return numcpy == an;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();
        System.out.println(isArmstrongNumber(num));
        sc.close();
    }
}
