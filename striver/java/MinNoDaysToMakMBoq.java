
// link: https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/description/

import java.util.Scanner;

public class MinNoDaysToMakMBoq {

    public static boolean canIgetMBoq (int[] bloomDay, int m, int k, int mid) {
        // do not modify this method body
        int count = 0, i, j=0;
        for (i=0; i<=bloomDay.length-k; i++) {
            // DebugLogger.log(
            //     DebugLogger.Arg.of("i", i),
            //     DebugLogger.Arg.of("j", j),
            //     DebugLogger.Arg.of("count", count),
            //     DebugLogger.Arg.of("mid", mid)
            // );
            
            for (j=i; j<i+k; j++) {
                if (bloomDay[j] > mid) {
                    break;
                }
            }
            if (j == i+k) {
                count++;
                i += k-1;
            }
            // DebugLogger.log(
            //     DebugLogger.Arg.of("i", i),
            //     DebugLogger.Arg.of("j", j),
            //     DebugLogger.Arg.of("count", count),
            //     DebugLogger.Arg.of("mid", mid)
            // );
        }
        // DebugLogger.log(
        //         DebugLogger.Arg.of("i", i),
        //         DebugLogger.Arg.of("j", j),
        //         DebugLogger.Arg.of("count", count),
        //         DebugLogger.Arg.of("mid", mid)
        //     );
        return count >= m;
    }

    public static int minDays(int[] bloomDay, int m, int k) {
        // do not modify this method body
        if (bloomDay.length < m || bloomDay.length < k || (long)bloomDay.length < (long)(m*k))
            return -1;
        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE, cand_mid = low;

        for (int day : bloomDay) {
            low = Math.min(low, day);
            high = Math.max(high, day);
        }
        int mid = -1;
        while (low <= high) {
            mid = low + (high - low)/2;
            // DebugLogger.log(
            //     DebugLogger.Arg.of("low", low),
            //     DebugLogger.Arg.of("high", high),
            //     DebugLogger.Arg.of("mid", mid),
            //     DebugLogger.Arg.of("cand_mid", cand_mid)
            // );
            if (canIgetMBoq(bloomDay, m, k, mid)) {
                cand_mid = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        // DebugLogger.log(
        //     DebugLogger.Arg.of("low", low),
        //         DebugLogger.Arg.of("high", high),
        //         DebugLogger.Arg.of("mid", mid),
        //         DebugLogger.Arg.of("cand_mid", cand_mid)
        // );
        return cand_mid;
    }
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String input = sc.nextLine();
            input = input.trim().substring(1, input.length()-1);
            String[] ip_arr = input.split(",");
            int[] arr = new int[ip_arr.length];

            for (int i=0; i<ip_arr.length; i++) {
                arr[i] = Integer.parseInt(ip_arr[i]);
            }
            int m = sc.nextInt();
            int k = sc.nextInt();
            int ans = minDays(arr, m, k);
            System.out.println(ans);
            // System.out.println(canIgetMBoq(arr, m, k, 9));
            // System.out.println("nums: ");
            // for (int e : arr) {
            //     System.out.print(e + " ");
            // }
        }
    }
}
