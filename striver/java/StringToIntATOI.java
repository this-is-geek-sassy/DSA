import java.util.Scanner;

public class StringToIntATOI {
    
    public static int myAtoi(String s) {

        if (s.trim().equals(""))
            return 0;
        StringBuilder sb = new StringBuilder(s.trim());
        boolean isNegative = (sb.charAt(0) == '-');

        if (isNegative || sb.charAt(0) == '+')
            sb.deleteCharAt(0);

        StringBuilder res = new StringBuilder("0");
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) >= '0' && sb.charAt(i) <= '9')
            {
                res.append(sb.charAt(i));
            } else {
                break;
            }
        }
        int ans;
        try {
            ans = Integer.parseInt(res.toString());
        } catch (java.lang.NumberFormatException e) {
            if (isNegative)
                ans = Integer.MIN_VALUE;
            else
                ans = Integer.MAX_VALUE;
        }
        return isNegative ? (ans * -1) : ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int ans = myAtoi(s);
        System.out.println(ans);
        sc.close();
    }
}
