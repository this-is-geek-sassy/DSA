package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

// https://leetcode.com/problems/alien-dictionary/description/

public class AlienDictionary {

    private static boolean edgeDoesntExist (Map<Character, Set<Character>> graph, char i, char j) {
        // checks if edges does exist b/w i -> j or not, if not then returns true, ow false

        Set<Character> neighbours = graph.get(i);
        if (neighbours.contains(j))
            return false;
        return true;
    }
    private static void printGraph(Map<Character, Set<Character>> graph) {
        for (Map.Entry<Character, Set<Character>> e: graph.entrySet()) {
            Character node = e.getKey();
            Set<Character> neighbours = graph.get(node);
            System.out.print(node + " -> ");

            for (char n: neighbours) {
                System.out.print(" " + n + " ");
            }
            System.out.println();
        }
    }
    public static String alienOrder(String[] words) {
        
        // ArrayList<ArrayList<Character>> graph = new ArrayList<>();
        Map<Character, Set<Character>> graph = new HashMap<>();
        Set<Character> seenChars = new HashSet<>();

        for (int i=0; i<words.length; i++) {
            String s = words[i];
            char[] word = s.toCharArray();
            for (char c: word) {
                if (!seenChars.contains(c)) {
                    graph.put(c, new HashSet<>());
                    seenChars.add(c);
                }
            }
        }
        
        for (int i=0; i<words.length-1; i++) {
            String first = words[i];
            String second = words[i+1];

            if (first.startsWith(second) && first.length() > second.length()) {
                printGraph(graph);
                return "";
            }
            int len = Math.min(first.length(), second.length());
            for (int j=0; j<len; j++) {
                if (first.charAt(j) != second.charAt(j)) {
                    if (edgeDoesntExist(graph, second.charAt(j), first.charAt(j)) == false) {
                        printGraph(graph);
                        return "";  // inconsistency
                    }
                    else if (edgeDoesntExist(graph, first.charAt(j), second.charAt(j))) {
                        // how to add the vertex as a neighbour?  (question for gpt: is the following implementation correct)
                        Set<Character> neighbours = graph.getOrDefault(first.charAt(j), new HashSet<>());
                        neighbours.add(second.charAt(j));
                        graph.put(first.charAt(j), neighbours);
                        
                    }
                    break;
                }
            }
            
        }
        // graph ready by now
        int[] visited = new int[26];
        char[] ordering = new char[graph.size()];
        int k = graph.size()-1;

        for (Map.Entry<Character, Set<Character>> e: graph.entrySet()) {
            Character start = e.getKey();
            if (visited[start - 'a'] == 0)
            {
                k = dfs(k, start, visited, ordering, graph);
                if (k == Integer.MIN_VALUE)
                    return "";
            }
        }
        // System.out.println(graph.size());
        // System.out.println(seenChars);
        // for (char c: ordering) {
        //     System.out.print(c + " ");
        // }
        // System.out.println();
        
        return new String(ordering);
    }
    private static int dfs (int k, char start, int[] visited, char[] ordering, Map<Character, Set<Character>> graph) {

        visited[start - 'a'] = 1;

        Set<Character> neighbours = graph.get(start);

        for (Character neighbour: neighbours) {
            if (visited[neighbour - 'a'] == 0) {
                k = dfs(k, neighbour, visited, ordering, graph);
                if (k == Integer.MIN_VALUE)
                    return k;
            }
            if (visited[neighbour - 'a'] == 1) {
                // back edge detected => cycle
                return Integer.MIN_VALUE;
            }
        }
        visited[start - 'a'] = 2;
        ordering[k] = start;
        return k-1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inputArr = input.substring(1, input.length()-1).split(",");

        int i=0;
        for (String s: inputArr) {
            s = s.substring(1, s.length()-1);
            inputArr[i++] = s;
        }
        String ans = alienOrder(inputArr);
        System.out.println(ans);
        sc.close();
    }
}