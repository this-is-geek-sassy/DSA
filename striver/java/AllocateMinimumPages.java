// link: https://www.geeksforgeeks.org/problems/allocate-minimum-number-of-pages0937/1

import java.util.Scanner;

public class AllocateMinimumPages {

    private static int getNoOfStudents (int arr[], int pages) {

        int students = 1, pagesForThisStudent = 0;

        for (int i = 0; i < arr.length; i++) {
            if (pagesForThisStudent + arr[i] <= pages) {
                pagesForThisStudent += arr[i];
            } else {
                students++;
                pagesForThisStudent = arr[i];
            }
        }
        return students;
    }

    public static int findPages(int[] arr, int k) {
        // code here
        if (arr.length < k)
            return -1;
        int high=-0, low =-1;

        for (int e : arr) {
            high += e;
            low = Math.max(low, e);
        }
        for (int pages=low; pages <= high; pages++) {
            if (getNoOfStudents(arr, pages) <= k) {
                return pages;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        int[] arr = new int[inp_arr.length];
        int i = 0;

        for (String e : inp_arr) {
            arr[i++] = Integer.parseInt(e);
        }
        int k = sc.nextInt();
        int ans = findPages(arr, k);
        System.out.println(ans);
        sc.close();
    }
}