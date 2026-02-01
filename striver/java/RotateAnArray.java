
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;


// link: https://leetcode.com/problems/rotate-array/


public class RotateAnArray {

    public static void rotate(int[] nums, int k) {

        k = k % nums.length;

        ArrayDeque<Integer> ad = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            ad.offerLast(nums[i]);
        }

        for (int i = 1; i <= k; i++) {
            
            Integer temp = ad.pollLast();
            ad.addFirst(temp);
        }

        int i=0;
        for (Integer elem : ad) {
            nums[i++] = (int) elem;
        }
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().trim(); 
        input = input.substring(1, input.length()-1);

        String[] parts = input.split(",");
        int[] arr = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        int k = s.nextInt();
        // System.out.println("Array: " + Arrays.toString(arr));
        // System.out.println("k = " + k);
        
        rotate(arr, k);
        System.out.println("Array: " + Arrays.toString(arr));
        // System.out.println("k = " + k);
        s.close();
    }
}
