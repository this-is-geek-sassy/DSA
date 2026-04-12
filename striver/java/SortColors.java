
// link: https://leetcode.com/problems/sort-colors/
// dutch national flag problem: https://en.wikipedia.org/wiki/Dutch_national_flag_problem

import java.util.Scanner;

public class SortColors {

    public static void sortColors(int[] nums) {
        int[] mem = new int[3];
        for (int i=0; i<nums.length; i++) {
            mem[nums[i]]++;
        }
        mem[1] += mem[0];
        mem[2] += mem[1];
        for (int e: mem) {
            System.out.print(e + " ");
        }
        System.out.println();
        int j = 0;
        for (int i = 0; i < 3; i++) {
            for (; j < mem[i]; j++)
            {
                nums[j] = i;
            }
        }
    }
    

    public static void sortColors2(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low, mid);
                ++low;
                ++mid;
            } else if (nums[mid] == 1) {
                ++mid;
            } else {
                swap(nums, mid, high);
                --high;
            }
        }
    }
    private static void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
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
        sortColors2(numbers);
        s.close();

        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
