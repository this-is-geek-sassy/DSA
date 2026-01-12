
// links: https://www.geeksforgeeks.org/problems/reverse-digit0316/1; https://takeuforward.org/maths/reverse-digits-of-a-number;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ReverseDigits {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String n = s.nextLine();
        ArrayDeque<Character> a = new ArrayDeque<>();
        
        for (int i=0; i<n.length(); i++) {
            a.push(n.charAt(i));
        }
        // System.out.println(a);
        String r = "";
        for (int i=0; i<n.length(); i++) {
            r += a.pop();
        }
        System.out.println(r);
        s.close();
    }
}
