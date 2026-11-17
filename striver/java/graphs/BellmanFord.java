package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BellmanFord {

    private static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {

        int[] dists = new int[V];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[S] = 0;

        for (int i=0; i<V-1; i++) {
            for (ArrayList<Integer> edge: edges) {
                if (dists[edge.get(0)] + edge.get(2) < dists[edge.get(1)]) {
                    dists[edge.get(1)] = dists[edge.get(0)] + edge.get(2);
                }
            }
        }
        for(ArrayList<Integer> edge: edges) {
            if (dists[edge.get(0)] + edge.get(2) < dists[edge.get(1)]) {
                return new int[] {-1};
            }
        }
        for (int i=0; i<dists.length; i++) {
            if (dists[i] == Integer.MAX_VALUE) {
                dists[i] = 1_000_000_000;
            }
        }
        return dists;
    }
    private static int[] bellman_fordDouble(int V, ArrayList<ArrayList<Integer>> edges, int S) {

        double[] dists = new double[V];
        Arrays.fill(dists, Double.POSITIVE_INFINITY);
        dists[S] = 0;

        for (int i=0; i<V-1; i++) {
            for (ArrayList<Integer> edge: edges) {
                if (dists[edge.get(0)] + edge.get(2) < dists[edge.get(1)]) {
                    dists[edge.get(1)] = dists[edge.get(0)] + edge.get(2);
                }
            }
        }
        for(ArrayList<Integer> edge: edges) {
            if (dists[edge.get(0)] + edge.get(2) < dists[edge.get(1)]) {
                return new int[] {-1};
            }
        }
        int[] ans = new int[dists.length];
        for (int i=0; i<dists.length; i++) {
            if (dists[i] == Double.POSITIVE_INFINITY) {
                ans[i] = 1_000_000_000;
            }else {
                ans[i] = (int) dists[i];
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        sc.nextLine();
        String edgesInput = sc.nextLine().trim();
        int S = sc.nextInt();

        String[] indivEdges = edgesInput.substring(1, edgesInput.length()-1).split("], ");
        ArrayList<ArrayList<Integer>> edgeList = new ArrayList<>();

        for (String indivEdge: indivEdges) {
            if (indivEdge.endsWith("]"))
                indivEdge = indivEdge.substring(1, indivEdge.length()-1);
            else
                indivEdge = indivEdge.substring(1);
            String[] componenets = indivEdge.split(", ");
            ArrayList<Integer> edge = new ArrayList<>();
            edge.add(Integer.valueOf(componenets[0]));
            edge.add(Integer.valueOf(componenets[1]));
            edge.add(Integer.valueOf(componenets[2]));

            edgeList.add(edge);            
        }
        int[] dists = bellman_fordDouble(V, edgeList, S);

        for (int d: dists) {
            System.out.print(d+ " ");
        }
        sc.close();
    }
}
