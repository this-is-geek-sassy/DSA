
// link: https://www.geeksforgeeks.org/problems/check-if-an-array-is-sorted0701/1

import java.util.Scanner;

public class IsArraySorted {

    public static boolean isSorted(int[] arr) {

        for (int i = 1; i < arr.length-1; i++) {
            if (arr[i] > arr[i+1]) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        input = input.trim().substring(1, input.length()-1);
        
        String[] splitted = input.split(", ");
        int[] arr = new int[splitted.length];
        int i = 0;
        for (String str : splitted) {
            arr[i] = Integer.parseInt(str);
            i++;
        }
        System.out.println(isSorted(arr));
    }
}
