import java.util.*;

public class HouseRobbery2 {

    private static int helper (int[] nums, int runningIdx, int[] memory) {

        // System.out.println("runningIdx = " + runningIdx);
        // base case
        // if (runningIdx > nums.length-1)
        //     return Integer.MAX_VALUE;
        
        if (runningIdx == nums.length-1) {
            return nums[runningIdx];
        }

        if (runningIdx == nums.length-2) {
            return Math.max(nums[runningIdx], nums[runningIdx+1]);
        }
        if (memory[runningIdx] != -1) {
            return memory[runningIdx];
        }
        int optionA;
        if (runningIdx+2 <= nums.length-1)
            optionA = nums[runningIdx] + helper(nums, runningIdx+2, memory);
        else
            optionA = nums[runningIdx];

        int optionB;
        if (runningIdx+3 <= nums.length-1)
            optionB = nums[runningIdx+1] + helper(nums, runningIdx+3, memory);
        else 
            optionB = nums[runningIdx+1];

        memory[runningIdx] = Math.max(optionA, optionB);
        return memory[runningIdx];
    }

    public static int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] memory = new int[nums.length];
        Arrays.fill(memory, -1);
        int[] nums1 = new int[nums.length-1];

        for (int i=0; i < nums.length-1; i++) {
            nums1[i] = nums[i];
        }
        int optionA = helper(nums1, 0, memory);

        Arrays.fill(memory, -1);
        // int[] nums2 = new int[nums.length-1];

        for (int i=1; i < nums.length; i++) {
            nums1[i-1] = nums[i];
        }
        int optionB = helper(nums1, 0, memory);
        return Math.max(optionA, optionB);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(",");
        int[] nums = new int[inpArr.length];
        int i = 0;

        for (String s : inpArr) {
            nums[i++] = Integer.parseInt(s);
        }
        int ans = rob(nums);
        System.out.println(ans);
        sc.close();
    }
}
