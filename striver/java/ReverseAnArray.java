
import java.util.Arrays;
import java.util.Scanner;



// link: https://www.geeksforgeeks.org/problems/reverse-an-array/1

public class ReverseAnArray {

    public static void reverseArray(int arr[], int s, int e) {

        if (s >= e) {
            return;
        }
        int temp = arr[s];
        arr[s] = arr[e];
        arr[e] = temp;

        s++; e--;
        reverseArray(arr, s, e);
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().trim(); 
        input = input.substring(1, input.length()-1);

        String[] parts = input.split(", ");
        int[] arr = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }

        reverseArray(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
        s.close();
    }
}
