
// link: https://takeuforward.org/arrays/recursive-insertion-sort-algorithm

import java.util.Scanner;

public class RecursiveInsertionSort {
    
    public static void insertionSort(int[] nums, int n, int i) {

        if (i == n)
            return;
        int j = i-1, key = nums[i];
        
        while (j>=0 && nums[j] > key) {
            nums[j+1] = nums[j];
            j--;
        }
        nums[++j] = key;
        insertionSort(nums, n, i+1);
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String ip = s.nextLine();
        ip = ip.trim().substring(1, ip.length()-1);
        String[] str_arr = ip.split(",");

        int[] numbers = new int[str_arr.length];
        int i=0;

        for (String str : str_arr) {
            int a = (int)Integer.parseInt(str);
            numbers[i] = a;
            i++;
        }
        insertionSort(numbers, numbers.length, 1);
        s.close();

        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
