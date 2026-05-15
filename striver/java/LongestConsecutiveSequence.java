
// link: https://leetcode.com/problems/longest-consecutive-sequence/description/

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LongestConsecutiveSequence {

    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0)
            return 0;

        TreeMap<Integer, Integer> store = new TreeMap<>();

        for (int num : nums) {
            store.put(num, store.getOrDefault(num, 0)+1);
        }
        System.out.println(store);

        Integer old_key = store.firstKey();
        int indicator = 0;
        int len_of_seq = 0, max_len_of_seq = 0;

        for (Map.Entry<Integer, Integer> e : store.entrySet()) {
            Integer new_key = e.getKey();
            if (indicator == 0) {
                indicator = 1;
                continue;
            }
            if (new_key - old_key == 1) {
                // System.out.println("HOLLA!");
                len_of_seq++;
            } else {
                len_of_seq++;
                if (len_of_seq > max_len_of_seq)
                    max_len_of_seq = len_of_seq;
                len_of_seq = 0;
            }
            // System.out.println("max_len_of_seq: " + max_len_of_seq);
            old_key = new_key;
        }
        len_of_seq++;
        if (len_of_seq > max_len_of_seq)
            max_len_of_seq = len_of_seq;
        return max_len_of_seq;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");
        int[] nums = new int[inp_arr.length];
        int i = 0;
        for (String e : inp_arr) {
            nums[i++] = Integer.parseInt(e);
        }
        int ans = longestConsecutive(nums);
        System.out.println(ans);
        sc.close();
    }
}