
// link: https://takeuforward.org/data-structure/merge-two-sorted-arrays-without-extra-space
// https://www.geeksforgeeks.org/problems/merge-two-sorted-arrays-1587115620/1

import java.util.Arrays;
import java.util.Scanner;

public class MergeArraysWoSpace {

    private int nextGap(int gap) {
        if (gap <= 1)
            return 0;
        return (gap / 2) + (gap % 2); // ceil(gap/2)
    }

    public void mergeArraysShellMethod(int a[], int b[]) {
        // https://www.geeksforgeeks.org/problems/merge-two-sorted-arrays-1587115620/1
        // code here
        int n = a.length;
        int m = b.length;

        int gap = nextGap(n + m);

        while (gap > 0) {

            int left = 0;
            int right = left + gap;

            while (right < n + m) {

                // Both pointers in first array
                if (left < n && right < n) {

                    if (a[left] > a[right]) {
                        int temp = a[left];
                        a[left] = a[right];
                        a[right] = temp;
                    }
                }

                // Left in a[], right in b[]
                else if (left < n && right >= n) {

                    if (a[left] > b[right - n]) {
                        int temp = a[left];
                        a[left] = b[right - n];
                        b[right - n] = temp;
                    }
                }

                // Both pointers in b[]
                else {

                    if (b[left - n] > b[right - n]) {
                        int temp = b[left - n];
                        b[left - n] = b[right - n];
                        b[right - n] = temp;
                    }
                }

                left++;
                right++;
            }

            gap = nextGap(gap);
        }
    }

    public void mergeArraysCheatingMthod(int arr1[], int arr2[]) {
        // code here

        // https://www.geeksforgeeks.org/problems/merge-two-sorted-arrays-1587115620/1
        int n = arr1.length, m = arr2.length;
        int i =0, j =0, k = n-1;
        while(i <= k && j<m){
            if(arr1[i] < arr2[j]){
                i++;
            }
            else{
                int temp = arr2[j];
                arr2[j] = arr1[k];
                arr1[k] = temp;
                j++;
                k--;
            }
        }
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        }

    public static void merge (int[] nums1, int[] nums2) {
        
        int n = nums2.length-1, m = nums1.length - n - 2;
        int i, j, k;
        for (i=m, j=n, k=nums1.length-1; i>=0 && j>=0; k--) {
            if (nums1[i] >= nums2[j]) {
                nums1[k] = nums1[i];
                // nums1[i] = Integer.MIN_VALUE;
                i--;
            } else {
                nums1[k] = nums2[j];
                // nums2[j] = Integer.MIN_VALUE;
                j--;
            }
        }
        if (i == -1 && j >= 0) {
            while (j >= 0) {
                nums1[k--] = nums2[j--];
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(", ");
        int[] nums1 = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums1[i] = Integer.parseInt(input_arr[i]);
        }
        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        input_arr = input.split(", ");
        int[] nums2 = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums2[i] = Integer.parseInt(input_arr[i]);
        }
        merge(nums1, nums2);
        for (int e : nums1) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
