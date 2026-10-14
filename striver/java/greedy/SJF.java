package greedy;

// https://takeuforward.org/practice/dsa/shortest-job-first?category=scheduling-and-interval-problems&source=strivers-a2z-dsa-sheet

import java.util.Arrays;
import java.util.Scanner;

public class SJF {

    public static long solve(int[] bt) {
        //your code goes here
        Arrays.sort(bt);
        long timeGoneTillNow = 0;
        long[] wt = new long[bt.length];

        for (int i=0; i<bt.length; i++) {
            wt[i] = timeGoneTillNow;
            timeGoneTillNow += bt[i];
        }
        long totalWtTime = 0;
        for (long w: wt) {
            // System.out.print(w + " ");
            totalWtTime += w;
        }
        // System.out.println();
        return totalWtTime / bt.length;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inpArr = input.substring(1, input.length()-1).split(", ");
        int[] bt = new int[inpArr.length];
        int i = 0;

        for (String s : inpArr) {
            bt[i++] = Integer.parseInt(s);
        }
        long ans = solve(bt);
        System.out.println(ans);
        sc.close();
    }
}
