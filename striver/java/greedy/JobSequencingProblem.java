package greedy;

// https://takeuforward.org/practice/dsa/job-sequencing-problem?category=scheduling-and-interval-problems&source=strivers-a2z-dsa-sheet

import java.util.Arrays;
import java.util.Scanner;

public class JobSequencingProblem {

    private static boolean linear_probe (int deadline, int profit, int[] schedule) {

        int i = deadline-1;
        while (i >= 0) {
            if (schedule[i] == 0) {
                schedule[i] = profit;
                return true;
            }
            i--;
        }
        return false;
    }

    public static int[] JobScheduling(int[][] jobs) {
        //your code goes here
        Arrays.sort(jobs, (a, b) -> Integer.compare(b[2], a[2]));

        int[] schedule = new int[jobs.length];
        int max_profit = 0, noJobsScheduled = 0;

        for (int i=0; i<jobs.length; i++) {
            int deadline = jobs[i][1];
            int profit = jobs[i][2];
            if (linear_probe (deadline, profit, schedule)) {
                max_profit += profit;
                noJobsScheduled++;
            }
        }
        for (int s: schedule) {
            System.out.print(s + " ");
        }
        System.out.println();
        return new int[]{noJobsScheduled, max_profit};
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inpArr = input.substring(1, input.length()-1).trim().split("] , ");

        int[][] jobs = new int[inpArr.length][3];

        int i = 0;
        for (String oneJob: inpArr) {

            if (oneJob.endsWith("]")) {
                oneJob = oneJob.substring(1, oneJob.length()-1);
            } else {
                oneJob = oneJob.substring(1);
            }
            String[] parts = oneJob.split(", ");
            jobs[i][0] = Integer.parseInt(parts[0]);
            jobs[i][1] = Integer.parseInt(parts[1]);
            jobs[i][2] = Integer.parseInt(parts[2]);
            i++;
            // System.out.println(oneJob);
        }

        // for (i = 0; i < jobs.length; i++) {
        //     System.out.print(jobs[i][0] + " " + jobs[i][1] +" " + jobs[i][2] + "\n");
        // }
        int[] ans = JobScheduling(jobs);
        System.out.println(ans[0] + " " + ans[1]);
        sc.close();
    }
}
