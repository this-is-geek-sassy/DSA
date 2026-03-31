import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//link: 560. subarray sum equals K: https://leetcode.com/problems/subarray-sum-equals-k/description/
// link: Longest Subarray with Sum K: https://www.geeksforgeeks.org/problems/longest-sub-array-with-sum-k0809/1
public class SubArraySumEqK {

    public static int longestSubarray (int[] nums, int k) {

        Map<Integer, Integer> mp = new HashMap<>();
        int res = 0;
        int prefSum = 0;

        for (int i = 0; i < nums.length; i++) {
            prefSum += nums[i];

            if (prefSum == k)
                res = i+1;
            else if (mp.containsKey(prefSum - k))
                res = Math.max(res, i - mp.get(prefSum - k));
            
            if (!mp.containsKey(prefSum))
                mp.put(prefSum, i);
        }
        return res;
    }

    
    public static int subarraySum_3 (int[] nums, int k) {

        Map<Integer, Integer> mp = new HashMap<>();
        mp.put(0, 1);  // Initialize: empty prefix has sum 0
        int existance = 0;
        int prefSum = 0;

        for (int i = 0; i < nums.length; i++) {
            prefSum += nums[i];
            
            if (mp.containsKey(prefSum - k))
                existance += mp.get(prefSum - k);  // Add frequency, not just increment
            
            mp.put(prefSum, mp.getOrDefault(prefSum, 0) + 1);  // Track frequency
        }
        return existance;
    }
    
    
    public static int subarraySum_2 (int[] nums, int k) {

        int existance=0, i=0, j=0, sum = nums[0];   // i & j are slding window boundary
        int itr = 0;

        while (j < nums.length && i < nums.length) {
            if (sum < k) {
                if (j == nums.length - 1)
                    break;
                sum += nums[++j];
                // j++;
                System.out.println("At itr: " + itr + " || sum: " + sum);
            } else if (sum > k) {
                sum -= nums[i++];
                // i++;
                System.out.println("At itr: " + itr + " || sum: " + sum);
            } else if (sum == k) {
                existance++;
                sum -= nums[i++];
                // i++;
                System.out.println("At itr: " + itr + " || sum: " + sum + " || existance: " + existance);
            }
            System.out.println("i = " + i + " j = " + j);
            itr++;
        }
        // if (sum == k)
        //     existance++;
        return existance;
    }
    
    public static int subarraySum(int[] nums, int k) {
        
        int l_sw_size=0, c_sw_size = 0, i = 0, j = 0, sum = 0;   // i & j are slding window boundary
        int itr = 0;

        while (j < nums.length) {
            
            if (sum < k) {
                sum += nums[j];
                // c_sw_size++;
                j++;
                System.out.println("At itr: " + itr + " || sum: " + sum + " || c_sw_size: " + c_sw_size);
            } else if (sum > k) {
                sum -= nums[i];
                // c_sw_size--;
                i++;
            } else if (sum == k) {
                if (c_sw_size > l_sw_size) {
                    l_sw_size = c_sw_size;
                    c_sw_size = 0;
                    // i++;
                    // sum -= nums[i];
                }
                sum -= nums[i];
                i++;
            }
            c_sw_size = j-i+1;
            itr++;
        }
        return l_sw_size;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        int k = s.nextInt();
        input = input.trim().substring(1, input.length()-1);

        String[] arr = input.split(",");

        int[] nums = new int[arr.length];

        int i = 0;
        for (String elem : arr) {
            nums[i] = Integer.parseInt(elem);
            i++;
        }
        System.out.println(subarraySum_3(nums, k));
        s.close();
    }
}