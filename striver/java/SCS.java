import java.util.Scanner;

public class SCS {
    private static String helper (String text1, String text2, int i, int j, String[][] memory) {

        if (i >= text1.length() || j >= text2.length())
            return "";
        if (memory[i][j] != null) {
            return memory[i][j];
        }
        if (text1.charAt(i) == text2.charAt(j)) {
            memory[i][j] = text1.charAt(i) + helper(text1, text2, i+1, j+1, memory);
        } else {
            // memory[i][j] =
            String s1 = helper(text1, text2, i+1, j, memory);
            String s2 = helper(text1, text2, i, j+1, memory);
            memory[i][j] = (s1.length() >= s2.length())? s1 : s2;
        }
        return memory[i][j];
    }

    public static String longestCommonSubsequence (String text1, String text2) {
        String[][] memory = new String[text1.length()][text2.length()];
        return helper(text1, text2, 0, 0, memory);
    }

    public static String shortestCommonSupersequence(String str1, String str2) {
        String lcs = longestCommonSubsequence(str1, str2);
        int lengthToBe = str1.length() + str2.length() - lcs.length();
        // System.out.println("lcs = " + lcs);
        // System.out.println("lengthToBe = " + lengthToBe);
        
        StringBuilder superString = new StringBuilder();
        int i = 0, j = 0, k = 0, l = 0;  // k tracks lcs string, i tracks str1, j tracks str2

        while (l < lengthToBe && i < str1.length() && j < str2.length()) {
            if (k < lcs.length() && (str1.charAt(i) == lcs.charAt(k) || str2.charAt(j) == lcs.charAt(k))) {

                if (str1.charAt(i) == lcs.charAt(k) && str2.charAt(j) == lcs.charAt(k)) {
                    superString.append(str1.charAt(i));
                    i++;
                    j++;
                    k++;
                }
                else if (str1.charAt(i) == lcs.charAt(k)) {
                    superString.append(str2.charAt(j));
                    j++;
                } else {
                    superString.append(str1.charAt(i));
                    i++;
                }
                // superString.append(lcs.charAt(k));
                // k++;
            } else {
                superString.append(str1.charAt(i));
                i++;
                superString.append(str2.charAt(j));
                j++;
            }
            ++l;
            // System.out.println("superString = " + superString.toString());
        }
        while (i < str1.length()) {
            superString.append(str1.charAt(i));
            i++;
        }
        while (j < str2.length()) {
            superString.append(str2.charAt(j));
            j++;
        }
        return superString.toString();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();

        String ans = shortestCommonSupersequence(str1, str2);
        System.out.println(ans);
        sc.close();
    }
}
