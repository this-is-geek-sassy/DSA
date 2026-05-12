
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// link: https://www.geeksforgeeks.org/problems/leaders-in-an-array-1587115620/1

public class ArrayLeaders {

    public static ArrayList<Integer> leaders(int arr[]) {
        // code here
        int greater = arr[arr.length-1];
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(arr[arr.length-1]);

        for (int i = arr.length-2; i >= 0; i--) {
            if (arr[i] >= greater) {
                greater = arr[i];
                ans.add(arr[i]);
            }
        }
        Collections.reverse(ans);
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        int[] arr = new int[inp_arr.length];
        int i = 0;

        for (String s : inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        ArrayList<Integer> ans = leaders(arr);
        for (Integer e : ans) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
