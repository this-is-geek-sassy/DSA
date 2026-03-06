
// link: https://www.geeksforgeeks.org/problems/remove-duplicate-elements-from-sorted-array/1

import java.util.ArrayList;
import java.util.Scanner;

public class RemoveDuplicateFromSortedArray {

    public static ArrayList<Integer> removeDuplicates(int[] arr) {
        ArrayList<Integer> finaList = new ArrayList<>();

        int elem = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == elem) {
                arr[i] = -1;
            } else {
                elem = arr[i];
                finaList.add(elem);
            }
            // System.out.println(arr[i]);
        }
        return finaList;
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
        System.out.println(removeDuplicates(arr));
    }
}
