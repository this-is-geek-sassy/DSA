
// link: https://leetcode.com/problems/longest-consecutive-sequence/description/

import java.util.*;

public class LongestConsecutiveSequence {

    // HashSet (optimal) solution
    public static int longestConsecutive3(int[] nums) {
        // Get the length of the array
        int n = nums.length;

        // If the array is empty, no sequence exists
        if (n == 0) return 0;

        // Variable to store the longest sequence length found
        int longest = 1; 

        // HashSet to store unique elements for O(1) lookup
        Set<Integer> st = new HashSet<>();

        // Add all elements to the set to remove duplicates
        for (int i = 0; i < n; i++) {
            st.add(nums[i]);
        }

        /* Loop through each element in the set to find 
           the starting point of consecutive sequences */
        for (int it : st) {
            // If there is no number before 'it', it’s the start of a sequence
            if (!st.contains(it - 1)) {
                // Start the count for this sequence
                int cnt = 1; 
                // Store the current number
                int x = it; 

                // Keep checking for the next consecutive number
                while (st.contains(x + 1)) {
                    // Move to the next number in sequence
                    x = x + 1; 
                    // Increment the length of current sequence
                    cnt = cnt + 1; 
                }

                // Update the longest sequence length if needed
                longest = Math.max(longest, cnt);
            }
        }

        // Return the length of the longest sequence
        return longest;
    }

    // Arrays.sort() solution
    public static int longestConsecutive2(int[] nums) { 
        if(nums.length==0){ 
            return 0; 
        }
        Arrays.sort(nums); 
        int max=1; int count=1; 
        for(int i=1;i<nums.length;i++){ 
            if(nums[i]==nums[i-1]+1){ 
                count++; 
            }else if(nums[i]!=nums[i-1]){ 
                count=1; 
            } 
            if(count>max){ 
                max=count;
            }
        } 
        return max;
    }

    // TreeMap solution
    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0)
            return 0;

        TreeMap<Integer, Integer> store = new TreeMap<>();

        for (int num : nums) {
            store.put(num, store.getOrDefault(num, 0)+1);
        }
        // System.out.println(store);

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
        // generate random testcase with random length
        Random random = new Random();
        int length = 1 + random.nextInt(50_000);
        int[] nums = new int[length];
        for (int i = 0; i < length; i++) {
            nums[i] = random.nextInt(200_000) - 100_000;
        }

        // timer start
        long start = System.nanoTime();
        int ans = longestConsecutive(nums);
        // timer stop
        long end = System.nanoTime();
        System.out.println(ans);
        // timer print
        System.out.println("longestConsecutive time (micros): " + 1.0*(end - start)/1000);

        // timer start
        start = System.nanoTime();
        ans = longestConsecutive2(nums);
        // timer stop
        end = System.nanoTime();
        System.out.println(ans);
        // timer print
        System.out.println("longestConsecutive2 time (micros): " + 1.0*(end - start)/1000);


        // timer start
        start = System.nanoTime();
        ans = longestConsecutive3(nums);
        // timer stop
        end = System.nanoTime();
        System.out.println(ans);
        // timer print
        System.out.println("longestconsecutive3 time (micros): " + 1.0*(end - start)/1000);

    }
}