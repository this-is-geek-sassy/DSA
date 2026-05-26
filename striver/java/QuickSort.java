import java.util.*;

// link: https://takeuforward.org/data-structure/quick-sort-algorithm'
// https://www.geeksforgeeks.org/problems/quick-sort/1

public class QuickSort {
    public static void swap (int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public static int partition (int[] nums, int low, int high) {
        int pivot = nums[high];

        int last = low;

        for (int i = low; i < high; i++) {
            if (nums[i] <= pivot) {
                swap(nums, i, last);
                last++;
            }
        }
        swap(nums, last, high);
        return last;
    }

    public static void quickSort(int[] nums, int low, int high) {

        if (low >= high) {
            return;
        }

        int mid = partition(nums, low, high);

        // for (int e : nums) {
        //     System.out.print(e + " ");
        // }
        // System.out.println();
        quickSort(nums, low, mid-1);
        quickSort(nums, mid+1, high);

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        int[] arr = new int[inp_arr.length];
        int i = 0;

        for (String s : inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        quickSort(arr, 0, arr.length-1);
        for (int e : arr) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
