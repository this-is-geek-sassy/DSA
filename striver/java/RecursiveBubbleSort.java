
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/bubble-sort/1

public class RecursiveBubbleSort {

    public static void bubbleSort(int[] arr, int last_idx) {
        // code here
        if (last_idx == 0) {
            return;
        }
        boolean changed = false;
        for (int i = 0; i <= last_idx-1; i++) {
            if (arr[i] > arr[i+1]) {
                changed = true;
                int temp = arr[i+1];
                arr[i+1] = arr[i];
                arr[i] = temp;
            }
        }
        if (changed)
            bubbleSort(arr, last_idx - 1);
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String input = s.nextLine().trim();
        input = input.substring(1, input.length() - 1);

        String[] numbers = input.split(", ");
        int[] arr = new int[numbers.length];

        int i = 0;
        for (String str : numbers) {
            arr[i] = Integer.parseInt(str);
            i++;
        }
        bubbleSort(arr, arr.length-1);

        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
        s.close();
    }
}
