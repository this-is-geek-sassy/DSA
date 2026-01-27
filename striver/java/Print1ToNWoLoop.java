
import java.util.Scanner;



// link: https://www.geeksforgeeks.org/problems/print-1-to-n-without-using-loops-1587115620/1&selectedLang=python3

public class Print1ToNWoLoop {

    // backtracking solution:
    public static void printNos (int n) {

        if (n == 0)
            return;
        printNos(n-1);
        System.out.print(n +" ");
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

    }
}
