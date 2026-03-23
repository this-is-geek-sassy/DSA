
import java.util.ArrayDeque;
import java.util.Scanner;


// link: https://leetcode.com/problems/move-zeroes/description/

public class MoveZeros {
    
    public static void moveZeroes(int[] nums) {

        ArrayDeque<Integer> ad = new ArrayDeque<>();
        // int zeros = 0;
        for (int i=0; i < nums.length; i++) {
            
            if (nums[i] != 0 ){
                ad.offerLast(nums[i]);
            } 
            // else {
            //     zeros++;
            // }
        }
        int j = 0;
        while(!ad.isEmpty()) {
            nums[j] = ad.pollFirst();
            j++;
        }
        if (j != nums.length) {
            for (; j < nums.length; j++) {
                nums[j] = 0;
                // zeros--;
            }
        }
        // System.out.println("Array: ");
        // for (Integer elem : nums) {
        //     System.out.print(elem + " ");
        // }
        // System.out.println();
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
        moveZeroes(nums);
    }
}
