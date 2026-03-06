
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/second-largest3735/1

public class SecondLargestElement {

    public static int getSecondLargest(int[] arr) {

        int largest = -1, secondLargest = -1;
        
        for (int i=0; i<arr.length; i += 1) {
            // if (arr[i] == arr[i+1]) {  // the case of equality
            //     if (arr[i] > largest) 
            //         largest = arr[i];
            //     else if (arr[i] > secondLargest)
            //         secondLargest = arr[i];
            // }
            if (arr[i] > largest) {
                secondLargest = largest;
                largest = arr[i];
            }
            else if (arr[i] > secondLargest && arr[i] != largest)
                secondLargest = arr[i];
            // System.out.println(secondLargest);
        }
        return secondLargest;
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
        System.out.println(getSecondLargest(arr));
    }
}
