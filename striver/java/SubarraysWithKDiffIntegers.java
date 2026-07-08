
import java.util.*;


// link: https://leetcode.com/problems/subarrays-with-k-different-integers/description/

public class SubarraysWithKDiffIntegers {
    public static int subarraysWithAtmostKDist (int[] nums, int k) {
        int result = 0, left = 0;
        Map<Integer, Integer> map = new HashMap<>();

        for (int right = 0; right < nums.length; right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0)+1);

            if (map.size() > k) {
                int leftInt = nums[left];
                map.put(leftInt, map.get(leftInt)-1);
                if (map.get(leftInt) == 0)
                    map.remove(leftInt);
                left++;
            }
            result += (right - left + 1);
        }
        return result;
    }

    public static int subarraysWithKDistinct(int[] nums, int k) {
        return subarraysWithAtmostKDist(nums, k) - subarraysWithAtmostKDist(nums, k-1);

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
        int k = sc.nextInt();
        int ans = subarraysWithKDistinct(nums, k);
        System.out.println(ans);
        sc.close();
    }
}
