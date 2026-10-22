// https://leetcode.com/problems/divide-intervals-into-minimum-number-of-groups/

package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DivideIntervals {

    public static int minGroups(int[][] intervals) {
        // this soln gets TLE
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // for (int[] interval: intervals) {
        //     System.out.println(interval[0] + " " + interval[1]);
        // }
        ArrayList<ArrayList<int[]>> containers = new ArrayList<>();
        for (int[] interval: intervals) {
            if (containers.isEmpty()) {
                containers.add(new ArrayList<>());
                containers.get(0).add(interval);
                continue;
            }
            int size = containers.size(), i = 0;
            while (i < size) {
                int lastBoundaryofith = containers.get(i).getLast()[1];
                if (lastBoundaryofith < interval[0]) {
                    containers.get(i).add(interval);
                    break;
                }
                i++;
            }
            if (i == size) {
                containers.add(new ArrayList<>());
                containers.getLast().add(interval);
            }
        }
        for (ArrayList<int[]> container: containers) {
            for (int[] interval: container) {
                System.out.print("[" + interval[0] +"," + interval[1] + "]");
            }
            System.out.println();
        }
        return containers.size();
    }
    
    public static int minGroups2 (int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int[] interval: intervals) {
            if (pq.isEmpty()) {
                pq.add(interval[1]);
                continue;
            }
            if (interval[0] <= pq.peek()) {
                pq.add(interval[1]);  // new group creation
            }
            else {
                pq.poll();
                pq.add(interval[1]);
            }
        }
        return pq.size();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inputIntervals = input.substring(1, input.length()-1).split("],");

        int[][] intervals = new int[inputIntervals.length][2];
        int i = 0;

        for (String intervalStr: inputIntervals) {
            if (intervalStr.endsWith("]")) {
                intervalStr = intervalStr.substring(1, intervalStr.length()-1);
            } else {
                intervalStr = intervalStr.substring(1);
            }
            String[] oneInt = intervalStr.split(",");
            intervals[i][0] = Integer.parseInt(oneInt[0]);
            intervals[i][1] = Integer.parseInt(oneInt[1]);
            i++;
        }
        int ans = minGroups2(intervals);
        System.out.println(ans);
        sc.close();
    }
}
