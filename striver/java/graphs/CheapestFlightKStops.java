package graphs;

// https://leetcode.com/problems/cheapest-flights-within-k-stops/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;


public class CheapestFlightKStops {

    static class Edge {
        int to;
        int weight;

        public Edge (int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    static class Pair {
        int node;
        int dist, flightsUsed;

        public Pair(int node, int dist, int flightsUsed) {
            this.node = node;
            this.dist = dist;
            this.flightsUsed = flightsUsed;
        }
    }

    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        
        // build adj list first
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for (int i=0; i<n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge: flights) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];

            graph.get(from).add(new Edge(to, weight));
        }
        // adj list done
        // Maximum allowed flights = k + 1
        int maxFlights = k + 1;

        // dist[node][flightsUsed]
        int[][] dist = new int[n][maxFlights + 1];

        for (int[] row : dist)
            Arrays.fill(row, Integer.MAX_VALUE);

        PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));

        q.offer(new Pair(src, 0, 0));
        dist[src][0] = 0;

        while (!q.isEmpty()) {
            Pair shortestPair = q.poll();
            int closestVertex = shortestPair.node;
            int shortestDist = shortestPair.dist;
            int flightsUsed = shortestPair.flightsUsed;

            if (shortestDist > dist[closestVertex][flightsUsed])
                continue;

            // visited[closestVertex] = true;
            if (closestVertex == dst)
                return shortestDist;

            // Cannot take more flights
            if (flightsUsed == maxFlights)
                continue;

            ArrayList<Edge> neighbours = graph.get(closestVertex);

            for (Edge neighbour: neighbours) {
                int v = neighbour.to;
                int wt = neighbour.weight;
                int newFlights = flightsUsed + 1;

                int newDist = shortestDist + wt;

                if (newDist < dist[v][newFlights]) {
                    dist[v][newFlights] = newDist;
                    q.add(new Pair(v, newDist, newFlights));
                }
                // else if (newDist < distancesFromSrc[v] && newHopCount <= k)
            }
        }
        return -1;
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
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        String edgeListString = sc.nextLine().trim();
        int src = sc.nextInt();
        int dest = sc.nextInt();
        int k = sc.nextInt();
        int[][] edgeList = parseEdgeListString(edgeListString);

        int ans = findCheapestPrice(n, edgeList, src, dest, k);
        System.out.println(ans);
        sc.close();
    }
}
