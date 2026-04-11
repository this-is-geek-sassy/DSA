
import java.util.Scanner;

public class MergeSort {

    public static void merge(int[] nums, int start, int mid, int end) {

        int[] newArr = new int[end - start + 1];
        int i = 0, j = start, k = mid+1;

        while (j <= mid && k <= end) {
            if (nums[j] < nums[k]) {
                newArr[i] = nums[j];
                j++;
            } else {
                newArr[i] = nums[k];
                k++;
            }
            // System.out.println("i = " + i);
            // System.out.println("newArr[i]: " + newArr[i]);
            i++;
        }
        if (j > mid) {
            for (; i < newArr.length; i++) {
                newArr[i] = nums[k];
                k++;
            }
        } else if (k > end) {
            for (; i < newArr.length; i++) {
                newArr[i] = nums[j];
                j++;
            }
        }
        
        i = 0; j = 0;
        for (i = start; i <= end; i++) {
            nums[i] = newArr[j];
            j++;
        }
    }

    public static int[] merge(int[] left, int[] right) {

        int[] finalArr = new int[left.length + right.length];
        int i=0, j = 0, k = 0;
        while (j < left.length && k < right.length) {
            if (left[j] <= right[k]) {
                finalArr[i] = left[j];
                // if (j+1 < left.length)
                    j++;
            }
            else {
                finalArr[i] = right[k];
                // if (k+1 < right.length)
                    k++;
            }
            // if (j >= left.length || k >= right.length)
            //     break;
            System.out.println("i = " + i);
            System.out.println("finalArr[i]: " + finalArr[i]);
            i++;
        }
        if (j >= left.length) {
            // j--;
            for (; i < finalArr.length; i++) {
                finalArr[i] = right[k];
                k++;
            }
        } else if (k >= right.length) {
            for (; i < finalArr.length; i++) {
                finalArr[i] = left[j];
                j++;
            }
        }
    return finalArr;
    }
    public static void merge_sort(int[] nums, int start, int end) {
        if (start >= end)
            return;
        int mid = start + ((end - start) / 2);
        merge_sort(nums, start, mid);
        merge_sort(nums, mid+1, end);
        // System.out.println("mid " + mid);
        merge(nums, start, mid, end);
    }
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        String[] arr = input.trim().substring(1, input.length()-1).split(", ");

        int[] nums = new int[arr.length];
        int i = 0;
        for (String e : arr) {
            nums[i] = Integer.parseInt(e);
            i++;
        }
        s.close();

        // int[] left = {1, 4};
        // int[] right = {3, 7, 9};

        // int[] nums = {1, 4, 3, 7, 9};

        // merge(nums, 0, 1, 4);
        merge_sort(nums, 0, nums.length-1);
        
        for (int e : nums) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

}
