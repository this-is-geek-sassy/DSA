// https://takeuforward.org/practice/dsa/minimum-multiplications-to-reach-end?category=shortest-path-algorithms&source=strivers-a2z-dsa-sheet

package graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MinMultiplicationsToReachEnd {

    public static int minimumMultiplications(int[] arr, int start, int end) {

        if (start == end)
            return 0;
        
        Deque<Integer> q = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        q.offer(start);
        visited.add(start);
        int nodesInCurrentLayer = 1, nodesInNextLayer = 0, stepCount = 0;

        while (!q.isEmpty()) {
            int node = q.poll();
            nodesInCurrentLayer--;

            for (int elem: arr) {
                int next = node * elem;
                next = (next < 100000)? next : (next % 100000);
                if (visited.contains(next))
                    continue;

                if (next == end && start != end) // terminate and report the step count
                    return stepCount+1;
                
                q.offer(next);
                visited.add(next);
                nodesInNextLayer++;
            }
            if (nodesInCurrentLayer == 0) {
                nodesInCurrentLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                stepCount++;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String arrStr = sc.nextLine().trim();

        String[] arrList = arrStr.substring(1, arrStr.length()-1).split(", ");
        int[] arr = new int[arrList.length];
        int i = 0;

        for (String s: arrList) {
            arr[i++] = Integer.parseInt(s);
        }
        int start = sc.nextInt(), end = sc.nextInt();
        
        int ans = minimumMultiplications(arr, start, end);
        System.out.println(ans);
        sc.close();
    }
}
