
// link: https://www.geeksforgeeks.org/problems/cyclically-rotate-an-array-by-one2614/1

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class LeftRotateArrayByOne {

    public static void rotate(int[] arr) {

        Deque<Integer> dq = new ArrayDeque<>();

        for (int i = 0; i < arr.length; i++) {
            dq.offerLast(arr[i]);
        }
        Integer temp = dq.pollLast();
        dq.offerFirst(temp);

        int i=0;
        for (Integer elem : dq) {
            arr[i++] = (int) elem;
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        input = input.trim().substring(1, input.length()-1);
        
        String[] splitted = input.split(", ");
        int[] arr = new int[splitted.length];
        int i = 0;
        for (String str : splitted) {
            arr[i] = Integer.parseInt(str);
            i++;
        }

    }
}
