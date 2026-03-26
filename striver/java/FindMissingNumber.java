
// link: https://www.geeksforgeeks.org/problems/missing-number-in-array1416/1

import java.util.Scanner;

public class FindMissingNumber {

    public static int xor_1_to_n(int n) {
        if (n % 4 == 0) return n;
        if (n % 4 == 1) return 1;
        if (n % 4 == 2) return n + 1;
        return 0;
    }
    public static int missingNum(int arr[]) {
        int exp_xor = xor_1_to_n(arr.length+1);

        int act_xor = 0;
        for (int e : arr) {
            act_xor = act_xor ^ e;
        }
        return exp_xor ^ act_xor;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        input = input.trim().substring(1, input.length()-1);

        String[] arr = input.split(", ");

        int[] nums = new int[arr.length];

        int i = 0;
        for (String elem : arr) {
            nums[i] = Integer.parseInt(elem);
            i++;
        }

        System.out.println(missingNum(nums));
    }
}
