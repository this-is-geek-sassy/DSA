
import java.util.HashMap;
import java.util.Scanner;


// link: https://leetcode.com/problems/roman-to-integer/description/

public class RomanToInteger {
    private static int value(char ch) {
        switch (ch) {
            case 'I' -> {
                return 1;
            }
            case 'V' -> {
                return 5;
            }
            case 'X' -> {
                return 10;
            }
            case 'L' -> {
                return 50;
            }
            case 'C' -> {
                return 100;
            }
            case 'D' -> {
                return 500;
            }
            case 'M' -> {
                return 1000;
            }
        }
        return 0;
    }
    public static int romanToInt2 (String s) {
        int number = 0;
        for (int i=s.length()-1; i >=0; i--) {

            if (i+1 <= s.length()-1 && value(s.charAt(i)) < value(s.charAt(i+1))) {
                number -= value(s.charAt(i));
            } else {
                number += value(s.charAt(i));
            }
        }
        return number;
    }
    public static int romanToInt(String s) {
        
        HashMap<Character, Integer> trans = new HashMap<>();
        trans.put('I', 1);
        trans.put('V', 5);
        trans.put('X', 10);
        trans.put('L', 50);
        trans.put('C', 100);
        trans.put('D', 500);
        trans.put('M', 1000);

        // PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        // pq.addAll(trans.entrySet());

        int number = 0;
        for (int i=s.length()-1; i >=0; i--) {

            if (i+1 <= s.length()-1 && trans.get(s.charAt(i)) < trans.get(s.charAt(i+1))) {
                number -= trans.get(s.charAt(i));
            } else {
                number += trans.get(s.charAt(i));
            }
        }
        return number;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        int ans = romanToInt(input);
        System.out.println(ans);
        sc.close();
    }
}
