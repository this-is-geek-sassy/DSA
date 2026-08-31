
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

// https://leetcode.com/problems/partition-array-into-two-arrays-to-minimize-sum-difference/description/

public class PartiotionToMinSum {

    static class State {
        // int index;
        int length;
        int runningSum;

        public State(int index, int length, int runningSum) {
            // this.index = index;
            this.length = length;
            this.runningSum = runningSum;
        }

        @Override
        public boolean equals (Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof State)) return false;

            State other = (State) obj;

            return 
                    // index == other.index && 
                    length == other.length
                    && runningSum == other.runningSum;
        }

        @Override
        public int hashCode () {
            return Objects.hash(length, runningSum);
        }
    }

    private static int helper (int[] nums, int runningIdx, int length, int runningSum, int total, HashMap<State, Integer> memory) {

        if (length == nums.length/2) {
            return Math.abs(2*runningSum - total);
        }
        if (runningIdx == nums.length)
            return Integer.MAX_VALUE;

        State state = new State(runningIdx, length, runningSum);
        if (memory.containsKey(state)) {
            return memory.get(state);
        }

        int optionA = helper(nums, runningIdx+1, length+1, runningSum + nums[runningIdx], total, memory);
        int optionB = helper(nums, runningIdx+1, length, runningSum, total, memory);

        memory.put(state, Math.min(optionA, optionB));
        return memory.get(state);
    }
    
    public static int minimumDifference(int[] nums) {
        // have to minimize the quantity: |2 * sumA - total|
        int total = 0;
        for (int n: nums) {
            total += n;
        }
        HashMap<State, Integer> memory = new HashMap<>();
        return helper(nums, 0, 0, 0, total, memory);
    }
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        String[] inpArr = input.trim().substring(1, input.length()-1).split(",");
        int i = 0;
        int[] nums = new int[inpArr.length];

        for (String s: inpArr) {
            nums[i++] = Integer.parseInt(s);
        }
        int ans = minimumDifference(nums);
        System.out.println(ans);
        sc.close();
    }
}