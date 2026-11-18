package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class NetworkDelayTime {
    
    static class Pair {
        public int node, dist;
        public Pair (int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }
    static class Edge {
        public int to, wt;
        public Edge (int to, int wt) {
            this.to = to;
            this.wt = wt;
        }
    }

    public static int networkDelayTime(int[][] times, int n, int k) {
        k--;
        // adj list creation
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge: times) {
            graph.get(edge[0]-1).add(new Edge(edge[1]-1, edge[2]));
        }
        // adj list done

        int[] distfromSrc = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);
        Arrays.fill(distfromSrc, 1_000_000_000);

        PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));

        distfromSrc[k] = 0;
        q.add(new Pair(k, 0));

        while (!q.isEmpty()) {
            Pair shortestPair = q.poll();
            int closestVertex = shortestPair.node;
            int shortestDist = shortestPair.dist;

            if (visited[closestVertex])
                continue;
            visited[closestVertex] = true;
            ArrayList<Edge> neighbours = graph.get(closestVertex);

            for (Edge neighbour: neighbours) {
                int v = neighbour.to;
                int wt = neighbour.wt;
                if (visited[v])
                    continue;
                int newDist = distfromSrc[closestVertex] + wt;
                if (newDist < distfromSrc[v]) {
                    distfromSrc[v] = newDist;
                    q.add(new Pair(v, newDist));
                }
            }
        }
        int min = Integer.MIN_VALUE;
        for (int i=0; i<n; i++) {
            // System.out.print(distfromSrc[i] + " ");
            if (distfromSrc[i] > min)
                min = distfromSrc[i];
        }
        // System.out.println();
        return (min == 1_000_000_000)? -1 : min;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        int n = sc.nextInt();
        // sc.nextLine();
        
        int k = sc.nextInt();

        String[] edgeInput = input.substring(1, input.length()-1).split("],");

        int[][] edges = new int[edgeInput.length][3];
        int i = 0;

        for (String edge: edgeInput) {
            if (edge.endsWith("]"))
                edge = edge.substring(1, edge.length()-1);
            else
                edge = edge.substring(1);
            String[] components = edge.split(",");
            edges[i][0] = Integer.parseInt(components[0]);
            edges[i][1] = Integer.parseInt(components[1]);
            edges[i++][2] = Integer.parseInt(components[2]);
        }
        int ans = networkDelayTime(edges, n, k);
        System.out.println(ans);
        sc.close();
    }
}
