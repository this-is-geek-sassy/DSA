package graphs;

// https://leetcode.com/problems/course-schedule/description/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

public class CourseSechdule {

    private static final int UNVISITED = 0;
    private static final int ACTIVE = 1;
    private static final int SAFE = 2;

    static class Frame {
        int node;
        int nextNeighbour;

        public Frame(int node) {
            this.node = node;
            this.nextNeighbour = 0;
        }
    }

    private static boolean dfs (int v, ArrayList<ArrayList<Integer>> graph) {

        int[] state = new int[v];

        for (int start = 0; start < v; start++) {
            if (state[start] == SAFE) continue;

            if (hasCycle(start, graph, state)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasCycle (int start, ArrayList<ArrayList<Integer>> graph, int[] state) {

        Deque<Frame> stack = new ArrayDeque<>();
        state[start] = ACTIVE;

        stack.push(new Frame(start));

        while (!stack.isEmpty()) {

            Frame current = stack.peek();

            int node = current.node;

            if (current.nextNeighbour == graph.get(node).size()) {
                state[node] = SAFE;
                stack.pop();
                continue;
            }

            int neighbour = graph.get(node).get(current.nextNeighbour);
            current.nextNeighbour++;

            if (state[neighbour] == UNVISITED) {
                state[neighbour] = ACTIVE;
                stack.push(new Frame(neighbour));
            }
            else if (state[neighbour] == ACTIVE) {
                // back edge found
                return true;
            }
        }
        return false;
    }

    public static boolean bfs (int v, ArrayList<ArrayList<Integer>> graph) {

        // Failed attampt
        int[] visited = new int[v];
        int[] prev = new int[v];
        Arrays.fill(prev, -1);
        Deque<Integer> q = new ArrayDeque<>();
        q.offerLast(0);
        visited[0] = 1;

        // System.out.println("visiting node = 0");

        while (!q.isEmpty()) {
            int node = q.pollFirst();

            // System.out.println("visiting node = " + node);

            ArrayList<Integer> neighbours = graph.get(node);
            for (Integer neighbour: neighbours) {
                if (visited[neighbour] == 0) {
                    q.offerLast(neighbour);
                    visited[neighbour] = 1;
                    prev[neighbour] = node;
                }
                else if (visited[neighbour] == 1 && prev[neighbour] != node) {
                    // System.out.println("Cycle found!!!");
                    // System.out.println("...at nodes " + node +" & " + neighbour);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        for (int i=0; i<numCourses; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i=0; i<prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];

            // directed edge from v ---> u
            adjList.get(v).add(u);
        }
        // System.out.println(adjList);
        return dfs(numCourses, adjList);
        // return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numCourses = sc.nextInt();
        sc.nextLine();
        String input = sc.nextLine();
        int[][] prerequisites = GraphParser.parseEdgeMatrix(input);

        // System.out.println("numCourses = " + numCourses);
        // System.out.println("prerequisites = " + Arrays.deepToString(prerequisites));

        boolean ans = canFinish(numCourses, prerequisites);
        System.out.println(ans);
        sc.close();
    }
}
