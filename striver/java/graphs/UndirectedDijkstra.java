package graphs;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class UndirectedDijkstra {

    static class Pair {
        int distance;
        int node;

        public Pair (int node, int distance) {
            this.distance = distance;
            this.node = node;
        }
    }

    public  static int[] dijkstra(int V, ArrayList<ArrayList<Integer>> edges, int S)
    {
        // convert to AdjList
        ArrayList<ArrayList<Map.Entry<Integer, Integer>>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }
        for (ArrayList<Integer> edge: edges) {
            Integer u = edge.get(0);
            Integer v = edge.get(1);
            Integer wt = edge.get(2);

            graph.get(u).add(new AbstractMap.SimpleEntry<>(v, wt));
            graph.get(v).add(new AbstractMap.SimpleEntry<>(u, wt));
        }
        // adjList done
        boolean[] visited = new boolean[V];
        int[] distancesFromSrc = new int[V];
        Arrays.fill(distancesFromSrc, 1_000_000_000);
        Arrays.fill(visited, false);

        PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));
        q.add(new Pair(S, 0));
        
        distancesFromSrc[S] = 0;

        while (!q.isEmpty()) {
            Pair shortestPair = q.poll();
            int closestVertex = shortestPair.node;
            int shortestDist = shortestPair.distance;

            if (visited[closestVertex]) {
                continue;
            }
            visited[closestVertex] = true;
            ArrayList<Map.Entry<Integer, Integer>> neighbours = graph.get(closestVertex);

            for (Map.Entry<Integer, Integer> neighbour: neighbours) {
                int v = neighbour.getKey();
                int wt = neighbour.getValue();
                if (visited[v])
                    continue;
                int newDist = distancesFromSrc[closestVertex] + wt;
                if (newDist < distancesFromSrc[v]) {
                    distancesFromSrc[v] = newDist;
                    q.add(new Pair(v, newDist));
                }
            }
        }
        return distancesFromSrc;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        sc.nextLine();
        String edgeListInput = sc.nextLine().trim();
        int S = sc.nextInt();

        ArrayList<ArrayList<Integer>> edgeList = new ArrayList<>();

        String[] edgeListArr = edgeListInput.substring(1, edgeListInput.length()-1).split("], ");
        
        for (String s: edgeListArr) {
            // System.out.println(s);
            if (s.endsWith("]")) {
                s = s.substring(1, s.length()-1);
            } else {
                s = s.substring(1);
            }
            ArrayList<Integer> temp = new ArrayList<>();
            String[] edge = s.split(", ");
            temp.add(Integer.valueOf(edge[0]));
            temp.add(Integer.valueOf(edge[1]));
            temp.add(Integer.valueOf(edge[2]));
            edgeList.add(temp);
        }
        int[] result = dijkstra(V, edgeList, S);

        for (int d: result) {
            System.out.print(d + " ");
        }
        System.out.println();
        sc.close();
    }
}
