package graphs;
import java.util.ArrayList;

// https://leetcode.com/problems/course-schedule-ii/

public class CourseSchedule2 {

    private static int dfs (int i, int start, int V, int[] visited, int[] ordering, ArrayList<ArrayList<Integer>> adjList) {

        visited[start] = 1;

        ArrayList<Integer> neighbours = adjList.get(start);

        for (Integer neighbour: neighbours) {
            if (visited[neighbour] == 1) {
                return Integer.MIN_VALUE;
            }
            else if (visited[neighbour] == 0) {
                i = dfs(i, neighbour, V, visited, ordering, adjList);
                if (i == Integer.MIN_VALUE)
                    return i;
            }
        }
        visited[start] = 2;
        ordering[i] = start;
        return i-1;
    }
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        for (int i=0; i<numCourses; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i=0; i<prerequisites.length; i++) {
            adjList.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        // adj list creation done
        int[] visited = new int[numCourses];
        // Arrays.fill(visited, false);

        int[] ordering = new int[numCourses];
        int i = numCourses-1;

        for (int at = 0; at < numCourses; at++) {
            if (visited[at] == 0) {
                i = dfs (i, at, numCourses, visited, ordering, adjList);
                if (i == Integer.MIN_VALUE) {
                    return new int[0];
                }
            }
            // ordering[i] = at;
            // i--;
        }
        return ordering;
    }
    public static void main(String[] args) {
        
    }
}