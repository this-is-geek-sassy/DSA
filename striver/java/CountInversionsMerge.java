
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/inversion-of-array-1587115620/1

public class CountInversionsMerge {

    public static int inversionCount(int arr[]) {
        // Code Here
        
        int _jmp = 1;
        for (int j = 1; 2*j < arr.length; j++) {

            for (int i=0; i< arr.length; i += (2*j)) {
                int k = i + _jmp;
                
            }
        }
    }
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] ip_arr = input.split(", ");
        int[] arr = new int[ip_arr.length];

        for (int i=0; i<=ip_arr.length; i++) {
            arr[i] = Integer.parseInt(ip_arr[i]);
        }

        int ans = inversionCount(arr);
        sc.close();
    }
}
