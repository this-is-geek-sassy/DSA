
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/frequency-of-array-elements-1587115620/1

public class FrequenciesInLimitedArray {
    
    public static List<Integer> frequencyCount(int[] arr) {

        List<Integer> l = new ArrayList<>(Collections.nCopies(arr.length + 1, 0));

        for (Integer elem : arr) {
            l.set(elem, l.get(elem) + 1);
        }
        return l.subList(1, l.size());
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().trim();
        input = input.substring(1, input.length()-1);

        String[] parts = input.split(",");
        int[] arr = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        frequencyCount(arr);
    }
}
