
// link: https://leetcode.com/problems/reverse-pairs/description/

import java.util.Scanner;

public class ReversePairs {

    // public static int counter = 0;

    public static int merge_sort (int[] nums, int start, int end) {
        if (start >= end)
            return 0;
        int mid = start + ((end - start) / 2), invCount = 0;

        invCount += merge_sort(nums, start, mid);
        // System.out.println(++counter + " : invCount = " + invCount);
        invCount += merge_sort(nums, mid+1, end);
        // System.out.println(++counter + " : invCount = " + invCount);
        // System.out.println("mid " + mid);
        invCount += merge(nums, start, mid, end);
        // System.out.println(++counter + " : invCount = " + invCount);
        return invCount;
    }

    public static int merge (int[] nums, int start, int mid, int end) {

        int[] newArr = new int[end - start + 1];
        int invCount = 0;
        int j = mid+1;

        for (int a=start; a <= mid; a++) {
            while (j <= end && (long)nums[a] > 2L*nums[j]) {
                j++;
            }
            invCount += j - (mid + 1);
        }
        int i = 0;
        j = start;
        int k = mid+1;

        while (j <= mid && k <= end) {
            if (nums[j] <= nums[k]) {
                newArr[i] = nums[j];
                j++;
            } else {
                // invCount += (mid + 1) -j;
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

    public static int merge_2 (int[] nums, int start, int mid, int end) {

        int[] newArr = new int[end - start + 1];
        int invCount = 0;
        int i = newArr.length-1, j = mid, k = end;

        while (j >= start && k > mid) {
            if (nums[j] < nums[k]) {
                newArr[i] = nums[k];
                k--;
            } else {
                if (nums[j] > 2*nums[k]) {
                    invCount += (mid + 1) - j;
                    System.out.println("j = " + j + " k = " + k);
                }
                newArr[i] = nums[j];
                j--;
            }
            // System.out.println("i = " + i);
            // System.out.println("newArr[i]: " + newArr[i]);
            i--;
        }
        if (j < start) {
            for (; i >=0; i--) {
                newArr[i] = nums[k];
                k--;
            }
        } else if (k <= mid) {
            for (; i >= 0; i--) {
                newArr[i] = nums[j];
                j--;
            }
        }
        i = 0; j = 0;
        for (i = start; i <= end; i++) {
            nums[i] = newArr[j];
            j++;
        }
        return invCount;
    }
    public static int reversePairs(int[] nums) {
        return merge_sort(nums, 0, nums.length-1);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] ip_arr = input.split(",");
        int[] arr = new int[ip_arr.length];

        for (int i=0; i<ip_arr.length; i++) {
            arr[i] = Integer.parseInt(ip_arr[i]);
        }
        
        int ans = reversePairs(arr);
        System.out.println(ans);
        System.out.println("nums: ");
        for (int e : arr) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
