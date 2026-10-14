package graphs;

// https://takeuforward.org/practice/dsa/print-shortest-path-?category=shortest-path-algorithms&source=strivers-a2z-dsa-sheet
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
// import java.util.List;
// import java.util.Scanner;

public class UndirectedPrintShortestPath {

    static class Pair {
        int distance;
        int node;

        public Pair (int node, int distance) {
            this.distance = distance;
            this.node = node;
        }
    }

    public List<Integer> shortestPath(int n, int m, int[][] edges) {
        // convert to AdjList
        ArrayList<ArrayList<Map.Entry<Integer, Integer>>> graph = new ArrayList<>();
        for (int i = 0; i < n+1; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge: edges) {
            Integer u = edge[0];
            Integer v = edge[1];
            Integer wt = edge[2];

            graph.get(u).add(new AbstractMap.SimpleEntry<>(v, wt));
            graph.get(v).add(new AbstractMap.SimpleEntry<>(u, wt));
        }
        // adjList done
        int S = 1;  // source is 1
        boolean[] visited = new boolean[n+1];
        int[] distancesFromSrc = new int[n+1];
        Arrays.fill(distancesFromSrc, 1_000_000_000);
        Arrays.fill(visited, false);

        int[] previous = new int[n+1];
        Arrays.fill(previous, -1);

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
            if (closestVertex == n) {
                break;
            }
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
                    previous[v] = closestVertex;
                }
            }
        }
        if (distancesFromSrc[n] == 1_000_000_000)
            return List.of(-1);

        List<Integer> path = new ArrayList<>();
        int prev = previous[n];
        // path.add(distancesFromSrc[n]);
        path.add(n);
        while (prev != -1) { 
            path.add(prev);
            prev = previous[prev];
        }
        path.addFirst(distancesFromSrc[n]);
        return path.reversed();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int m = sc.nextInt();
        String edgeListInput = sc.nextLine().trim();
        // ArrayList<ArrayList<Integer>> edgeList = new ArrayList<>();
        int[][] edgeList = new int[m][3];

        String[] edgeListArr = edgeListInput.substring(1, edgeListInput.length()-1).split("], ");

        int i = 0;
        for (String s: edgeListArr) {
            // System.out.println(s);
            if (s.endsWith("]")) {
                s = s.substring(1, s.length()-1);
            } else {
                s = s.substring(1);
            }
            
            String[] edge = s.split(", ");
            edgeList[i][0] = Integer.parseInt(edge[0]);
            edgeList[i][1] = Integer.parseInt(edge[1]);
            edgeList[i][2] = Integer.parseInt(edge[2]);
            i++;
        }
        sc.close();
    }
}
