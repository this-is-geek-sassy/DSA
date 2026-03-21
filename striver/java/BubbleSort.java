
// link: https://www.geeksforgeeks.org/problems/bubble-sort/1

import java.util.Scanner;

public class BubbleSort {

    public static void bubbleSort(int[] arr) {

        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-i-1; j++) {
                // compare arr[j] & arr[j+1], if their sorting order is not aligned, swap
                if (arr[j] > arr[j+1]) {
                    int t = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = t;
                }
            }
            System.out.println("\n"+ i + "-th iterations result: ");
            for (Integer number : arr) {
                System.out.print(number + " ");
            }
            // System.out.println("\ns = " + s);
            // break;
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
        bubbleSort(numbers);
        s.close();
    }
}
