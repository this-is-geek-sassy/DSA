
// link: https://leetcode.com/problems/merge-intervals/description/

import java.util.*;

public class MergeIntervals {

    public static int[][] merge(int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> ans = new ArrayList<>();

        for (int[] interval : intervals) {

            if (ans.isEmpty() || ans.get(ans.size()-1)[1] < interval[0]) {
                ans.add(interval);
            } else {
                int[] new_interval = new int[2];
                new_interval[0] = ans.get(ans.size()-1)[0];
                new_interval[1] = Math.max(ans.get(ans.size()-1)[1], interval[1]);

                ans.set(ans.size()-1, new_interval);
            }
        }

        return ans.toArray(new int[ans.size()][]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-2);
        String[] inputArray = input.split("],");
        int[][] intervals = new int[inputArray.length][2];
        int i = 0;

        for (String e : inputArray) {
            e = e.substring(1);
            // System.out.println(e + " ");
            String[] interval = e.split(",");
            intervals[i][0] = Integer.parseInt(interval[0]);
            intervals[i++][1] = Integer.parseInt(interval[1]);
        }
        int[][] ans = merge(intervals);
        for (int[] e : ans) {
            System.out.print(e[0] + " ");
            System.out.print(e[1] + " ");
            System.out.println();
        }
        sc.close();
    }
}