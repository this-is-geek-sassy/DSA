
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/inversion-of-array-1587115620/1

public class CountInversionsMerge {

    public static int merge_sort (int[] nums, int start, int end) {
        if (start >= end)
            return 0;
        int mid = start + ((end - start) / 2), invCount = 0;

        invCount += merge_sort(nums, start, mid);
        invCount += merge_sort(nums, mid+1, end);
        // System.out.println("mid " + mid);
        invCount += merge(nums, start, mid, end);
        return invCount;
    }

    public static int merge (int[] nums, int start, int mid, int end) {

        int[] newArr = new int[end - start + 1];
        int invCount = 0;
        int i = 0, j = start, k = mid+1;

        while (j <= mid && k <= end) {
            if (nums[j] <= nums[k]) {
                newArr[i] = nums[j];
                j++;
            } else {
                invCount += (mid + 1) -j;
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
        return invCount;
    }

    public static int inversionCount(int arr[]) {
        // Code Here
        
        // int _jmp = 1;
        // for (int j = 1; 2*j < arr.length; j++) {

        //     for (int i=0; i< arr.length; i += (2*j)) {
        //         int k = i + _jmp;
                
        //     }
        // }
        return merge_sort(arr, 0, arr.length-1);
    }
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] ip_arr = input.split(", ");
        int[] arr = new int[ip_arr.length];

        for (int i=0; i<ip_arr.length; i++) {
            arr[i] = Integer.parseInt(ip_arr[i]);
        }
        
        int ans = inversionCount(arr);
        System.out.println(ans);
        sc.close();
    }
}
