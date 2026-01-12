
// links: https://www.geeksforgeeks.org/problems/count-digits-1606889545/1; https://takeuforward.org/data-structure/count-digits-in-a-number

import java.util.Scanner;

class CountDigits {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        // System.out.println(n);

        int noOfDigits = (int)Math.log10(n) + 1;
        System.out.println(noOfDigits);
        scanner.close();
    }
}