
import java.util.Arrays;
import java.util.Scanner;

// https://www.geeksforgeeks.org/problems/longest-bitonic-subsequence0824/1

public class LongestBitonic {

    // private static boolean hasDecreased = false;

    private static int helper (int[] nums, int i, int lastIndex, boolean increasing, boolean hasIncreased, boolean hasDecreased, int[][][][][] memory) {

        if (i == nums.length) {
            return (hasDecreased && hasIncreased) ? 0 : Integer.MIN_VALUE;
        }
        int _increasing = increasing == true ? 1 : 0;
        int _hasDecreased = hasDecreased == true ? 1 : 0;
        int _hasIncreased = hasIncreased == true ? 1 : 0;

        if (memory[i][lastIndex+1][_increasing][_hasIncreased][_hasDecreased] != -1) {
            return memory[i][lastIndex+1][_increasing][_hasIncreased][_hasDecreased];
        }
        int skip = helper(nums, i+1, lastIndex, increasing, hasIncreased, hasDecreased, memory);
        int take = Integer.MIN_VALUE;

        if (lastIndex == -1) {
            take = 1 + helper(
                nums, i + 1, i,
                true,
                false,
                false,
                memory
            );
        }
        else if (nums[i] > nums[lastIndex] && increasing == true) {
            take = 1 + helper(nums, i+1, i, increasing, true, hasDecreased, memory);
        }
        else if (nums[i] < nums[lastIndex] && increasing == true) {
            hasDecreased = true;
            take = 1 + helper(nums, i+1, i, false, hasIncreased, hasDecreased, memory);
        }
        else if (nums[i] < nums[lastIndex]) {
            take = 1 + helper(nums, i+1, i, increasing, hasIncreased, hasDecreased, memory);
        }
        memory[i][lastIndex+1][_increasing][_hasIncreased][_hasDecreased] = Math.max(skip, take);
        return memory[i][lastIndex+1][_increasing][_hasIncreased][_hasDecreased];
    }

    public static int longestBitonicSequence(int n, int[] nums) {
        // code here
        int[][][][][] memory = new int[n][n+1][2][2][2];

        for (int[][][][] m4: memory) {
            for (int[][][] m3: m4) {
                for (int[][] m2: m3) {
                    for (int[] m: m2)
                        Arrays.fill(m, -1);
                }
            }
        }
        int ans = helper(nums, 0, -1, true, false, false, memory);
        return ans < 0 ? 0 : ans;
    }
    public static int longestBitonicSequenceBottomUp (int n, int[] nums) {

        int[] lis = new int[n];
        int[] lds = new int[n];

        // lis[i] = longest strictly increasing subsequence
        // ending at i
        Arrays.fill(lis, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {

                if (nums[i] > nums[j]) {
                    lis[i] = Math.max(lis[i], lis[j] + 1);
                }
            }
        }

        // lds[i] = longest strictly decreasing subsequence
        // starting at i
        Arrays.fill(lds, 1);

        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {

                if (nums[i] > nums[j]) {
                    lds[i] = Math.max(lds[i], lds[j] + 1);
                }
            }
        }

        int ans = 0;

        // Treat every index as the peak
        for (int i = 0; i < n; i++) {

            // Both increasing and decreasing parts must exist
            if (lis[i] > 1 && lds[i] > 1) {
                ans = Math.max(ans, lis[i] + lds[i] - 1);
            }
        }

        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inputArr = input.substring(1, input.length()-1).split(", ");
        int[] nums = new int[inputArr.length];
        int i = 0;

        for (String s: inputArr) {
            nums[i++] = Integer.parseInt(s);
        }
        int ans = longestBitonicSequence(nums.length, nums);
        System.out.println(ans);
        sc.close();
    }
}
