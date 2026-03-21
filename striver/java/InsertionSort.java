
// link: https://www.geeksforgeeks.org/problems/insertion-sort/1

import java.util.Scanner;

public class InsertionSort {
    
    public static void insertionSort(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i-1;
            while (j >= 0 && arr[j] > key) {
                arr[j+1] = arr[j];
                j--;
            }
            if (j == -1) {
                arr[0] = key;
            } else {
                arr[j+1] = key;
            }

            // System.out.println("\n"+ i + "-th iterations result: ");
            // for (Integer number : arr) {
            //     System.out.print(number + " ");
            // }
        }
        // System.out.println("\nFinal result: ");
        // for (Integer number : arr) {
        //     System.out.print(number + " ");
        // }
    }
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);

        String ip = s.nextLine();
        ip = ip.trim().substring(1, ip.length()-1);
        String[] str_arr = ip.split(", ");

        int[] numbers = new int[str_arr.length];
        int i=0;

        for (String str : str_arr) {
            int a = (int)Integer.parseInt(str);
            numbers[i] = a;
            i++;
        }
        insertionSort(numbers);
        s.close();
    }
}
