
import java.util.*;

// link: https://takeuforward.org/data-structure/subset-sum-sum-of-all-subsets
// https://www.geeksforgeeks.org/problems/subset-sums2234/1

public class SumOfAllSubsets {

    private static void helper(int[] arr, int n, List<Integer> runningArr, List<Integer> mainSet) {

        if (n == arr.length) {
            int sum = 0;
            for (int i: runningArr)
                sum += i;
            mainSet.add(sum);
            return;
        }
        // not take
        helper(arr, n+1, runningArr, mainSet);
        // take
        if (n < arr.length) {
            runningArr.add(arr[n]);
            helper(arr, n+1, runningArr, mainSet);
            runningArr.remove(runningArr.size()-1);
        }
    }

    public static ArrayList<Integer> subsetSums(int[] arr) {
        // code here
        Arrays.sort(arr);
        ArrayList<Integer> ans = new ArrayList<>();
        helper(arr, 0, new ArrayList<>(), ans);
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] inp_arr = input.trim().substring(1, input.length()-1).split(", ");
        int i = 0;
        int[] arr = new int[inp_arr.length];
        for (String s: inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        List<Integer> ans = subsetSums(arr);
        System.out.println(ans);
        sc.close();
    }
}
