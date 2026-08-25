import java.util.*;

public class HouseRobbery2 {

    private static int helper (int[] nums, int runningIdx, int[] memory, boolean isFirstSelected) {

        if (runningIdx == nums.length-1 && isFirstSelected == false) {
            return nums[runningIdx];
        }
        if (runningIdx == nums.length-1 ) {
            // isFirstSelected == true
            return Integer.MAX_VALUE;
        }
        if (runningIdx == nums.length-2 && isFirstSelected == true) {
            return nums[runningIdx];
        }
        if (runningIdx == nums.length-2) {
            return Math.max(nums[runningIdx], nums[runningIdx+1]);
        }
        if (memory[runningIdx] != -1) {
            return memory[runningIdx];
        }
        int optionA=-1, optionB=-1;
        if (runningIdx == 0) {
            optionA = nums[runningIdx] + helper(nums, runningIdx+2, memory, true);
            
            if (runningIdx+3 <= nums.length-1)
                optionB = nums[runningIdx+1] + helper(nums, runningIdx+3, memory, false);
            else 
                optionB = nums[runningIdx+1];
        } else {
            optionA = nums[runningIdx] + helper(nums, runningIdx+2, memory, isFirstSelected);
            
            if (runningIdx+3 <= nums.length-1)
                optionB = nums[runningIdx+1] + helper(nums, runningIdx+3, memory, isFirstSelected);
            else 
                optionB = nums[runningIdx+1];
        }
        memory[runningIdx] = Math.max(optionA, optionB);
        return memory[runningIdx];
    }

    public static int rob(int[] nums) {
        int[] memory = new int[nums.length];
        Arrays.fill(memory, -1);
        return helper(nums, 0, memory, false);
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
