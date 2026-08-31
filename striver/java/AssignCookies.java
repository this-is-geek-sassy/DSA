// https://leetcode.com/problems/assign-cookies/description/

import java.util.*;

public class AssignCookies {

    private static int helper (int[] g, int[] s, int childIdx, int cookieIdx, Integer[][] memory) {

        if (childIdx >= g.length || cookieIdx >= s.length) {
            return 0;
        }
        if (memory[childIdx][cookieIdx] != null) {
            return memory[childIdx][cookieIdx];
        }
        if (s[cookieIdx] >= g[childIdx]) {
            memory[childIdx][cookieIdx] = Math.max(1 + helper(g, s, childIdx + 1, cookieIdx + 1, memory), helper(g, s, childIdx, cookieIdx+1, memory));
        }
        else
            memory[childIdx][cookieIdx] = helper(g, s, childIdx, cookieIdx+1, memory);
        return memory[childIdx][cookieIdx];
    }

    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        Integer[][] memory = new Integer[g.length][s.length];
        return helper(g, s, 0, 0, memory);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");
        // int k = sc.nextInt();

        int i = 0;
        int[] g = new int[inp_arr.length];
        for (String s : inp_arr) {
            g[i++] = Integer.parseInt(s);
        }

        input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        inp_arr = input.split(",");
        i = 0;
        int[] s = new int[inp_arr.length];
        for (String x: inp_arr) {
            s[i++] = Integer.parseInt(x);
        }
        int ans = findContentChildren(g, s);
        System.out.println(ans);
        sc.close();
    }
}
