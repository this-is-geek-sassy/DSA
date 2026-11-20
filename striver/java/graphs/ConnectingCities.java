// https://leetcode.com/problems/connecting-cities-with-minimum-cost/

package graphs;

import java.util.Arrays;
import java.util.Scanner;

public class ConnectingCities {

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
        int ans = minimumCost(n, connections);
        System.out.println(ans);
        sc.close();
    }
}
