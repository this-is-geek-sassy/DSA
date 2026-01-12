
// links: https://www.geeksforgeeks.org/problems/reverse-digit0316/1; https://takeuforward.org/maths/reverse-digits-of-a-number;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ReverseDigits {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String n = s.nextLine();

        // minus case handling:
        boolean isMinus = false;
        if (n.charAt(0) == '-') {
            isMinus = true;
            n = n.substring(1);
        }

        ArrayDeque<Character> a = new ArrayDeque<>();
        
        for (int i=0; i<n.length(); i++) {
            a.push(n.charAt(i));
        }
        // System.out.println(a);
        String r = "";
        for (int i=0; i<n.length(); i++) {
            r += a.pop();
        }
        if (isMinus)
            r = "-" + r;
        
        long k = Long.parseLong(r);
        System.out.println(k);
        s.close();
    }
}
