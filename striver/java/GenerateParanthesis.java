
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// link: https://leetcode.com/problems/generate-parentheses/description/

public class GenerateParanthesis {

    private static boolean check_valid_string (StringBuilder st) {

        int k = 0;
        for (int i=0; i < st.length(); i++) {
            if (st.charAt(i) == '(')
                k++;
            else
                k--;
            if (k < 0)
                return false;
        }
        return (k == 0);
    }

    public static List<String> generateParenthesis(int n) {
        
        List<String> ans = new ArrayList<>();

        int _2n = 2*n;
        long pow_2_2n = 1 << _2n;
        // System.out.println(pow_2_2n);

        StringBuilder sb = new StringBuilder();
        
        for (int i=0; i<pow_2_2n; i++) {
            for (int k=0; k<_2n; k++) {
                int t = i;
                if ((t & (1 << k)) != 0) {
                    sb.append('(');
                }
                else {
                    sb.append(')');
                }
            }
            // System.out.println(sb);
            if (check_valid_string(sb))
                ans.add(sb.toString());
            sb.setLength(0);
        }
        return ans;
    }

    private static Set<String> generateRecursively (int n, Set<String> set) {

        if (n==1) {
            set.add("()");
            return set;
        }
        set = generateRecursively(n-1, set);
        Set<String> next = new HashSet<>();

        for (String s : set) {
            String newEntry = "()" + s;
            if (!next.contains(newEntry)) {
                // set.remove(s);
                next.add(newEntry);
            }
            newEntry = s + "()";
            if (!next.contains(newEntry)) {
                // set.remove(s);
                next.add(newEntry);
            }

            newEntry = "(" + s + ")";
            if (!next.contains(newEntry)) {
                // set.remove(s);
                next.add(newEntry);
            }
            // set.remove(s);
        }
        return next;
    }
    public static List<String> generateParenthesisBetter(int n) {
        
        Set<String> set = new HashSet<>();
        set = generateRecursively(n, set);
        return new ArrayList<>(set);
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        List<String> ans = generateParenthesisBetter(n);
        // System.out.println(new StringBuilder().toString().getClass());
        System.out.println(ans);
        sc.close();
    }
}
