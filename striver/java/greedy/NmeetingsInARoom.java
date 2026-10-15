package greedy;

import java.util.Arrays;
import java.util.Scanner;

public class NmeetingsInARoom {

    public static int maxMeetings(int[] start, int[] end) {
        //your code goes here
        int[][] jobs = new int[start.length][4];

        for (int i = 0; i < start.length; i++) {
            jobs[i][0] = i;
            jobs[i][1] = start[i];
            jobs[i][2] = end[i];
            jobs[i][3] = end[i] - start[i];
        }
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[2], b[2]));

        int canSchedule = 0;
        int lastMeetingFinishTime = -1;
        for (int i = 0; i < start.length; i++) {
            if (jobs[i][1] > lastMeetingFinishTime) {
                canSchedule++;
                lastMeetingFinishTime = jobs[i][2];
            }
        }
        return canSchedule;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inpArr = input.substring(1, input.length()-1).split(", ");
        int[] start = new int[inpArr.length];

        int i = 0;
        for (String s: inpArr) {
            start[i++] = Integer.parseInt(s);
        }
        input = sc.nextLine().trim();
        inpArr = input.substring(1, input.length()-1).split(", ");
        int[] end = new int[inpArr.length];
        i = 0;

        for (String s: inpArr) {
            end[i++] = Integer.parseInt(s);
        }
        int ans = maxMeetings(start, end);
        System.out.println(ans);
        sc.close();
    }
}
