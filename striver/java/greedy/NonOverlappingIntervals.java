package greedy;

// https://leetcode.com/problems/non-overlapping-intervals/description/

import java.util.Arrays;
import java.util.Scanner;


public class NonOverlappingIntervals {

    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int canSchedule = 0, lastFinished = Integer.MIN_VALUE;
        for (int i=0; i<intervals.length; i++) {
            if (intervals[i][0] >= lastFinished) {
                canSchedule++;
                lastFinished = intervals[i][1];
            }
        }
        return intervals.length - canSchedule;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inpArr = input.substring(1, input.length()-1).split("],");

        int[][] intervals = new int[inpArr.length][2];
        int i = 0;

        for (String s: inpArr) {
            if (s.endsWith("]")) {
                s = s.substring(1, s.length()-1);
            } else {
                s = s.substring(1);
            }
            String[] intervalStr = s.split(",");
            intervals[i][0] = Integer.parseInt(intervalStr[0]);
            intervals[i][1] = Integer.parseInt(intervalStr[1]);
            i++;
        }
        // for (int[] interval: intervals) {
        //     System.out.println(interval[0] + " " + interval[1]);
        // }
        int ans = eraseOverlapIntervals(intervals);
        System.out.println(ans);
        sc.close();
    }
}
