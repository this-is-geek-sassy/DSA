
//link: https://www.geeksforgeeks.org/problems/selection-sort/1

import java.util.Scanner;

public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        int s = 0;
        for (int i = 0; i < n; i++) {
            s = i;
            for (int j = i; j < n; j++) {
                // find the smallest number
                if (arr[j] < arr[s]) {
                    s = j;
                }
            }
            // swap arr[i] and arr[s]
            if (s != i) {
                int t = arr[i];
                arr[i] = arr[s];
                arr[s] = t;
            }

            // System.out.println("\n"+ i + "-th iterations result: ");
            // for (Integer number : arr) {
            //     System.out.print(number + " ");
            // }
            // System.out.println("\ns = " + s);
        }
        
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
        selectionSort(numbers);
        s.close();
    }
}