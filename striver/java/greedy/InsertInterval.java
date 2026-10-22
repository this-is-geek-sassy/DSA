// https://leetcode.com/problems/insert-interval/

package greedy;

import java.util.ArrayList;
import java.util.Scanner;

public class InsertInterval {

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        
        int startOfNew = newInterval[0], i, finalLength = intervals.length;
        // int endOfNew = newInterval[1], lastUpdated = -1;

        for (i=0; i<intervals.length; i++) {
            if (i>0 && intervals[i][0] > startOfNew) {
                // break;
                intervals[i-1][1] = newInterval[1];
                if (intervals[i][1] < newInterval[1]) {
                    intervals[i][0] = -1;
                    finalLength--;
                }
                else if (intervals[i][0] < intervals[i-1][1]) {
                    intervals[i-1][1] = intervals[i][1];
                    intervals[i][0] = -1;
                    finalLength--;
                    // endOfNew = intervals[i][1];
                    // lastUpdated = i-1;
                }
                startOfNew = Integer.MAX_VALUE;
            }
            // else if (i)
            // if (i>0 && intervals[i][0] == endOfNew && lastUpdated >= 0) {
            //     intervals[lastUpdated][1] = intervals[i][1];
            //     intervals[i][0] = -1;
            //     finalLength--;
            // }
        }
        // check
        // for (int[] interval: intervals) {
        //     System.out.println(interval[0] + " " + interval[1]);
        // }
        // System.out.println("finalLength = " + finalLength);

        int[][] finalIntervals = new int[finalLength][2];
        i = 0;
        int fullNFinalLength = finalLength;

        for (int j = 0; j < intervals.length && i < finalLength; j++) {
            if (intervals[j][0] == -1)
            {
                // i++;
                continue;
            }
            finalIntervals[i][0] = intervals[j][0];
            finalIntervals[i][1] = intervals[j][1];
            if (j>0 && finalIntervals[i][0] == finalIntervals[i-1][1]) {
                finalIntervals[i-1][1] = finalIntervals[i][1];
                finalIntervals[i][0] = -1;
                fullNFinalLength--;
            }
            i++;
        }
        // return finalIntervals;
        intervals[i-1][1] = newInterval[1];
        intervals = new int[fullNFinalLength][2];
        i = 0;
        for (int j=0; j<finalIntervals.length && i<fullNFinalLength; j++) {
            if (finalIntervals[j][0] == -1) 
                continue;
            intervals[i][0] = finalIntervals[j][0];
            intervals[i][1] = finalIntervals[j][1];
            i++;
        }
        return intervals;
    }

    public static int[][] insert2(int[][] intervals, int[] newInterval) {

        ArrayList<int[]> finalList = new ArrayList<>();

        if (intervals.length == 0) {
            finalList.add(newInterval);
            return finalList.toArray(int[][]::new);
        }
        
        int i = 0;
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            finalList.add(intervals[i]);
            i++;
        }
        int beginningOfNewInt = (i < intervals.length && intervals[i][0] < newInterval[0]) ? intervals[i][0] : newInterval[0];

        while (i < intervals.length && intervals[i][1] < newInterval[1]) {
            i++;
        }
        // determine is there overlap
        int endOfNewInt = (i < intervals.length && intervals[i][0] <= newInterval[1] && intervals[i][1] > newInterval[1]) ? intervals[i][1] : newInterval[1];
        finalList.add(new int[]{beginningOfNewInt, endOfNewInt});

        while (i < intervals.length) {
            if (intervals[i][0] <= endOfNewInt)
            {
                i++;
                continue;
            }
            finalList.add(intervals[i]);
            i++;
        }
        return finalList.toArray(int[][]::new);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inpString = sc.nextLine().trim();
        String[] intervalArr = inpString.substring(1, inpString.length()-1).split("],");

        int[][] intervals = new int[intervalArr.length][2];
        int i = 0;

        for (String intStr: intervalArr) {
            if (intStr.endsWith("]")) {
                intStr = intStr.substring(1, intStr.length()-1);
            } else {
                intStr = intStr.substring(1);
            }
            String[] intArr = intStr.split(",");
            
            intervals[i][0] = Integer.parseInt(intArr[0]);
            intervals[i++][1] = Integer.parseInt(intArr[1]);
        }
        String newIntStr = sc.nextLine().trim();
        int[] newInterval = new int[2];
        String[] newIntArr = newIntStr.substring(1, newIntStr.length()-1).split(",");
        newInterval[0] = Integer.parseInt(newIntArr[0]);
        newInterval[1] = Integer.parseInt(newIntArr[1]);

        // // check
        // for (int[] interval: intervals) {
        //     System.out.println(interval[0] + " " + interval[1]);
        // }
        // System.out.println(newInterval[0] + " -- "  +newInterval[1]);
        int[][] finalIntervals = insert2(intervals, newInterval);
        for (int[] interval: finalIntervals) {
            System.out.println(interval[0] + " " + interval[1]);
        }
        sc.close();
    }
}