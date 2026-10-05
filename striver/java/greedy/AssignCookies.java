package greedy;

// https://leetcode.com/problems/assign-cookies/description/

import java.util.Arrays;
import java.util.Scanner;


public class AssignCookies {
    public static int findContentChildren(int[] g, int[] s) {

        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0, j = 0, count = 0;

        for (; i<g.length && j<s.length; ) {

            if (s[j] >= g[i]) {
                count++;
                j++;
                i++;
            } else {
                j++;
            }
        }
        return count;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String g_inp = sc.nextLine().trim();
        String s_inp = sc.nextLine().trim();

        String[] g_inp_arr = g_inp.substring(1, g_inp.length()-1).split(",");
        String[] s_inp_arr = s_inp.substring(1, s_inp.length()-1).split(",");

        int[] g = new int[g_inp_arr.length];
        int[] s = new int[s_inp_arr.length];
        int i = 0;
        for (String _g: g_inp_arr) {
            g[i++] = Integer.parseInt(_g);
        }
        
        i = 0;
        for (String _s: s_inp_arr) {
            s[i++] = Integer.parseInt(_s);
        }
        int ans = findContentChildren(g, s);
        System.out.println(ans);
        sc.close();
    }
}
