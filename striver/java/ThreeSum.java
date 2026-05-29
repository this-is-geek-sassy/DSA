
// link: https://leetcode.com/problems/3sum/description/

import java.util.*;

public class ThreeSum {

    public static List<List<Integer>> threeSum2 (int[] nums) {

        Arrays.sort(nums);
        List <List<Integer>> ans = new ArrayList<>();
        
        for (int i=0; i < nums.length-2; i++) {

            if (i > 0 && nums[i-1] == nums[i])
                continue;

            int left = i+1;
            int right = nums.length-1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;

                    while (left < right && nums[left] == nums[left-1])
                        left++;

                    while (left < right && nums[right] == nums[right+1])
                        right--;

                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return ans;
    }

    public static List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);
        List <List<Integer>> ans = new ArrayList<>();
        Set<List<Integer>> set = new HashSet<>();

        int r_low = 0, r_high = nums.length-1;
        int l_low = 0, l_high = nums.length-1;

        for (int i=0; i < nums.length/2; i++) { 
            
            int count = nums[r_low] + nums[r_high] + nums[r_high-1];
            int count2 = nums[l_low] + nums[l_low+1] + nums[l_high];

            if (count == 0) {
                List<Integer> li = new ArrayList<>();
                li.add(nums[r_low]);
                li.add(nums[r_high]);
                li.add(nums[r_high-1]);

                li.sort(Comparator.naturalOrder());
                if (!set.contains(li)){
                    ans.add(li);
                    set.add(li);
                }

                r_low++;
                r_high--;
                // continue;
            } else if (count < 0) {
                r_low++;
                // continue;
            } else {
                r_high--;
                // continue;
            }

            if (count2 == 0) {
                List<Integer> li = new ArrayList<>();
                li.add(nums[l_low]);
                li.add(nums[l_low+1]);
                li.add(nums[l_high]);
                li.sort(Comparator.naturalOrder());
                if (!set.contains(li)) {
                    ans.add(li);
                    set.add(li);
                }

                l_low++;
                l_high--;
            } else if (count2 < 0) {
                l_low++;
            } else {
                l_high--;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);

        String[] input_arr = input.split(",");
        int[] nums = new int[input_arr.length];

        for (int i = 0; i < input_arr.length; i++) {
            
            nums[i] = Integer.parseInt(input_arr[i]);
        }

        List<List<Integer>> ans = threeSum2(nums);
        System.out.println(ans);
        sc.close();
    }
}
