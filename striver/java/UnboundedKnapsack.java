
import java.util.*;


// https://www.geeksforgeeks.org/problems/knapsack-with-duplicate-items4201/1

public class UnboundedKnapsack {

    private static int helper (int[] val, int[] wt, int remainingCapacity, int i, int[][] memory) {
        
        if (i == val.length) {
            return 0;
        }
        if (remainingCapacity <= 0) {
            return 0;
        }
        if (memory[remainingCapacity][i] != -1) {
            return memory[remainingCapacity][i];
        }
        if (remainingCapacity >= wt[i])
            memory[remainingCapacity][i] = Math.max(val[i] + helper(val, wt, remainingCapacity - wt[i], i, memory),    // take & stay
                        helper(val, wt, remainingCapacity, i+1, memory)         // don't take and move
                    );
        else
            memory[remainingCapacity][i] = helper(val, wt, remainingCapacity, i+1, memory);

        return memory[remainingCapacity][i];
    }

    public static int knapSack(int val[], int wt[], int capacity) {
        // code here
        // Arrays.sort(wt, Collections.reverseOrder());
        int[][] memory = new int[capacity+1][val.length];
        for (int[] m: memory) {
            Arrays.fill(m, -1);
        }
        return helper(val, wt, capacity, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        // int k = sc.nextInt();

        int i = 0;
        int[] val = new int[inp_arr.length];
        for (String s : inp_arr) {
            val[i++] = Integer.parseInt(s);
        }
        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        inp_arr = input.split(", ");
        i = 0;
        int[] wt = new int[inp_arr.length];
        for (String s : inp_arr) {
            wt[i++] = Integer.parseInt(s);
        }
        int capacity = sc.nextInt();

        int ans = knapSack(val, wt, capacity);
        System.out.println(ans);
        sc.close();
    }
}
