
import java.util.Scanner;

// link: https://leetcode.com/problems/rotate-string/description/

public class RotateString {

    public static boolean rotateString(String s, String goal) {
        //trivial base case
        if (s.length()!= goal.length())
            return false;
        if (s.equals(goal))
            return true;

        // int start = ThreadLocalRandom.current().nextInt(0, s.length());
        // System.out.println("start = " + start);
        // for (int i = (start); i != start; i = (i+1)%s.length()) {
        //     System.out.print(s.charAt(i) + " ");
        // }
        int[] potential_starts = new int[s.length()+1];
        int x=0;
        int start = s.indexOf(goal.charAt(0));
        if (start == -1)
            return false;
        while (start != -1) {
            // System.out.println(start);
            potential_starts[x++] = start;
            start = s.indexOf(goal.charAt(0), start + 1);
        }
        while (x!= potential_starts.length) {
            potential_starts[x++] = -1;
        }

        // for (int ind : potential_starts) {
        //     System.out.print(ind + " ");
        // }
        // System.out.println();

        int marker = 0;
        
        int potential_marker = 0;
        start = potential_starts[potential_marker];
        while (start != -1)
        {
            for (int i = 0; i < goal.length(); i++) {
                if (s.charAt(start) != goal.charAt(i)) {
                    marker = 1;
                    break;
                }
                start = (start +1) % s.length();
            }
            if (marker == 0) 
                return true;
            else {
                potential_marker++;
                start = potential_starts[potential_marker];
                marker = 0;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String goal = sc.nextLine();
        boolean ans = rotateString(s, goal);
        System.out.println(ans);
        sc.close();
    }
}
