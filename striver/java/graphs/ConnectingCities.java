// https://leetcode.com/problems/connecting-cities-with-minimum-cost/

package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ConnectingCities {

    public static class Edge {
        public int to, wt;

        public Edge (int to, int wt) {
            this.to = to;
            this.wt = wt;
        }
    }
    public static class Pair {
        public int node, dist;

        public Pair (int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    public static int minimumCost(int n, int[][] connections) {
        
        Arrays.sort(connections, (a,b) -> Integer.compare(a[2], b[2]));
        DisjointSet u = new DisjointSet(n+1);

        int sum = 0;

        for (int[] edge: connections) {
            if (u.connected(edge[0], edge[1])) continue;

            u.unionBySize(edge[0], edge[1]);
            sum += edge[2];

            if (u.size(1) == n)
                break;
        }
        if (u.size(1) != n)
            return -1;
        return sum;
    }

    public static int minimumCostPrims(int n, int[][] connections) {
        n++;
        // adj list conversion
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>(n);

        for (int i = 0; i < n; i++) 
            graph.add(new ArrayList<>());

        for (int[] connection: connections) {
            graph.get(connection[0]).add(new Edge(connection[1], connection[2]));
            graph.get(connection[1]).add(new Edge(connection[0], connection[2]));
        }
        // adj list done
        boolean[] visited = new boolean[n];
        int[] distFromSrc = new int[n];
        Arrays.fill(distFromSrc, Integer.MAX_VALUE);
        distFromSrc[connections[0][0]] = 0;

        PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));
        q.offer(new Pair(connections[0][0], 0));

        while (!q.isEmpty()) {
            Pair closestPair = q.poll();
            int closestNode = closestPair.node;
            int closestCost = closestPair.dist;

            if (visited[closestNode])
                continue;
            visited[closestNode] = true;

            ArrayList<Edge> neighbours = graph.get(closestNode);
            
            for (Edge neighbour: neighbours) {

                if (visited[neighbour.to])
                    continue;

                if (neighbour.wt < distFromSrc[neighbour.to]) {
                    
                    distFromSrc[neighbour.to] = neighbour.wt;
                    q.add(new Pair(neighbour.to, distFromSrc[neighbour.to]));
                }
            }
        }
        int sum = 0;
        for (int i=1; i<distFromSrc.length; i++) {
            if (distFromSrc[i] == Integer.MAX_VALUE)
                return -1;
            sum += distFromSrc[i];
        }
        return sum;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        String input = sc.nextLine().trim();
        int[][] connections = new int[n][3];

        String[] edgeStrings = input.substring(1, input.length()-1).split("],");

        int i = 0;
        for (String edgeString: edgeStrings) {
            if (edgeString.endsWith("]"))
                edgeString = edgeString.substring(1, edgeString.length()-1);
            else
                edgeString = edgeString.substring(1);
            String[] edgeComponents = edgeString.split(",");
            connections[i][0] = Integer.parseInt(edgeComponents[0]);
            connections[i][1] = Integer.parseInt(edgeComponents[1]);
            connections[i][2] = Integer.parseInt(edgeComponents[2]);
            i++;
        }
        int ans = minimumCostPrims(n, connections);
        System.out.println(ans);
        sc.close();
    }
}
