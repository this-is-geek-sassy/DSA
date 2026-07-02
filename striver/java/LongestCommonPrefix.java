
import java.util.Scanner;


// link: https://leetcode.com/problems/longest-common-prefix/description/

public class LongestCommonPrefix {
    public static String longestCommonPrefix(String[] strs) {
        // do not touch this method body
        if (strs[0].isEmpty())
            return "";
        String common = strs[0];
        int j=0;
        for (int i=1; i<strs.length; i++) {
            j = Math.min(strs[i].length()-1, common.length()-1);
            while (common.substring(0, j+1).compareTo(strs[i].substring(0, j+1)) != 0)
                j--;
            common = common.substring(0, j+1);

        }
        return common;
    }

    private static String[] parseStringArray(String input) {
        String trimmed = input.trim();

        if (trimmed.length() < 2 || !trimmed.startsWith("[") || !trimmed.endsWith("]")) {
            return new String[0];
        }

        trimmed = trimmed.substring(1, trimmed.length() - 1).trim();

        if (trimmed.isEmpty()) {
            return new String[0];
        }

        String[] parts = trimmed.split(",");
        String[] result = new String[parts.length];

        for (int i = 0; i < parts.length; i++) {
            String value = parts[i].trim();
            if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
                value = value.substring(1, value.length() - 1);
            }
            result[i] = value;
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        String[] strs = parseStringArray(input);
        String ans = longestCommonPrefix(strs);

        System.out.println(ans);
        sc.close();
    }
}
