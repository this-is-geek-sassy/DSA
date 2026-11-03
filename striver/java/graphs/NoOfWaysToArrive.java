// https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/

package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class NoOfWaysToArrive {

    static class Edge {
        int to;
        long wt;

        public Edge(int to, long wt) {
            this.to = to;
            this.wt = wt;
        }
    }

    static class Pair {
        int vertex;
        long dist;

        public Pair(int vertex, long dist) {
            this.vertex = vertex;
            this.dist = dist;
        }
    }

    private static int[][] parseEdgeListString (String input) {

        String[] inputList = input.substring(1, input.length()-1).split("],");
        int[][] edgeList = new int[inputList.length][3];
        int i = 0;

        for (String edgeString: inputList) {

            if (edgeString.endsWith("]"))
                edgeString = edgeString.substring(1, edgeString.length()-1);
            else
                edgeString = edgeString.substring(1);
            String[] splitedEdgeString = edgeString.split(",");
            
            edgeList[i][0] = Integer.parseInt(splitedEdgeString[0]);
            edgeList[i][1] = Integer.parseInt(splitedEdgeString[1]);
            edgeList[i][2] = Integer.parseInt(splitedEdgeString[2]);
            i++;
        }
        return edgeList;
    }

    public static int countPaths(int n, int[][] roads) {
        // prepare adj list
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();

        for (int i=0; i<n; i++)
            graph.add(new ArrayList<>());

        for (int[] road: roads) {
            int from = road[0];
            int to = road[1];
            int time = road[2];
            graph.get(from).add(new Edge(to, time));
            graph.get(to).add(new Edge(from, time));
        }
        // adj list done
        boolean[] visited = new boolean[n];
        long[] distancesFromSrc = new long[n];
        long[] ways = new long[n];
        ways[0] = 1;
        Arrays.fill(visited, false);
        Arrays.fill(distancesFromSrc, Long.MAX_VALUE);

        final long MOD = 1_000_000_007;

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Long.compare(a.dist, b.dist));
        pq.offer(new Pair(0, 0));
        distancesFromSrc[0] = 0;

        while (!pq.isEmpty()) {
            Pair shortestPair = pq.poll();
            int closestVertex = shortestPair.vertex;
            long shortestDist = shortestPair.dist;

            if (visited[closestVertex])
                continue;
            visited[closestVertex] = true;

            // if end vertex then stop and report
            if (closestVertex == n-1)
                return (int)ways[n-1];
                // return noOfShortestPathFoundTillDest;

            ArrayList<Edge> neighbours = graph.get(closestVertex);
            for (Edge neighbour: neighbours) {
                int v = neighbour.to;
                long distance = neighbour.wt;
                if (visited[v])
                    continue;

                long newDist = distance + distancesFromSrc[closestVertex];
                if (newDist < distancesFromSrc[v]) {
                    distancesFromSrc[v] = newDist;
                    pq.add(new Pair(v, newDist));
                    // if (v == n-1)
                    //     noOfShortestPathFoundTillDest = 1;
                    ways[v] = ways[closestVertex];

                } else if (/* v == n-1 && */ newDist == distancesFromSrc[v]) {
                    // noOfShortestPathFoundTillDest++;
                    ways[v] = (ways[v] + ways[closestVertex]) % MOD;
                }
            }
        }
        // return noOfShortestPathFoundTillDest;
        return (int)ways[n-1];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        String roadsInput = sc.nextLine().trim();

        int[][] roads = parseEdgeListString(roadsInput);
        int ans = countPaths(n, roads);
        System.out.println(ans);
        sc.close();
    }
}
