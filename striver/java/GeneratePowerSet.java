// link: https://takeuforward.org/data-structure/power-set-print-all-the-possible-subsequences-of-the-string

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GeneratePowerSet {

    private static void helper (String mainString, String runningString, Set<String> powSetList, int n) {

        if (n == mainString.length()) {
            powSetList.add(runningString);
            return;
        }
        helper(mainString, runningString, powSetList, n+1);
        helper(mainString, runningString + mainString.substring(n, n+1), powSetList, n+1);
    }

    // Function to return all subsequences of string s
    public static List<String> getSubsequences(String s) {
        Set<String> ans = new HashSet<>();

        helper(s, "", ans, 0);
        return new ArrayList<>(ans);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        List<String> ans = getSubsequences(input);
        System.out.println(ans);
        sc.close();
    }
}
