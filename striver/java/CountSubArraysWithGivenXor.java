
// link: https://www.geeksforgeeks.org/problems/count-subarray-with-given-xor/1

import java.util.HashMap;
import java.util.Scanner;

public class CountSubArraysWithGivenXor {
    public static long subarrayXor (int arr[], int k) {
        // code here
        HashMap<Integer, Integer> hp = new HashMap<>();
        int prefXor = 0, count = 0;
        hp.put(prefXor, hp.getOrDefault(prefXor, 0)+1);

        for (int i = 0; i < arr.length; i++) {
            prefXor = prefXor ^ arr[i];
            int target = prefXor ^ k;

            if (hp.containsKey(target))
                count += hp.get(target);
            hp.put(prefXor, hp.getOrDefault(prefXor, 0)+1);
        }
        return count;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(", ");
        int[] nums = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums[i] = Integer.parseInt(input_arr[i]);
        }
        int target = sc.nextInt();

        long ans = subarrayXor(nums, target);

        System.out.println(ans);
        sc.close();
    }
}
