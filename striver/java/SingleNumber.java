

// link: https://leetcode.com/problems/single-number/description/

import java.util.Scanner;

public class SingleNumber {

    public static int singleNumber(int[] nums) {

        int xor = 0;
        for (int e : nums) {
            xor = e ^ xor;
        }
        return xor;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        input = input.trim().substring(1, input.length()-1);

        String[] arr = input.split(",");

        int[] nums = new int[arr.length];

        int i = 0;
        for (String elem : arr) {
            nums[i] = Integer.parseInt(elem);
            i++;
        }
        System.out.println(singleNumber(nums));
    }
}
