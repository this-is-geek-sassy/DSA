// link: https://www.geeksforgeeks.org/problems/allocate-minimum-number-of-pages0937/1

import java.util.Scanner;

public class AllocateMinimumPages {

    private static int getNoOfStudents (int arr[], long pages) {
        // returns the minimum number of students it requires to stay within the max-page per student limit
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
        long high=-0, low =-1, mid;

        for (int e : arr) {
            high += e;
            low = Math.max(low, e);
        }
        while (low <= high) {
            mid = low + (high-low)/2;
            if (getNoOfStudents(arr, mid) <= k) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return (int)low;
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