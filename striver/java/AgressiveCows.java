
import java.util.Arrays;
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/aggressive-cows/1

public class AgressiveCows {
    private static boolean isPossibleToPlace (int[] stalls, int k, int dist) {

        int count = 1;
        int lastPos = stalls[0];

        for (int i = 0; i < stalls.length; i++) {
            if (stalls[i] - lastPos >= dist) {
                count++;
                lastPos = stalls[i];
            }
            if (count == k)
                return true;
        }
        return false;
    }
    public static int aggressiveCows(int[] arr, int k) {
        // code here
        Arrays.sort(arr);
        int high = arr[arr.length-1] - arr[0];
        int low = 0, mid = low, ans = -1;

        while (low <= high) {
            mid = low + (high-low)/2;
            if (isPossibleToPlace(arr, k, mid)) {
                low = mid + 1;
                ans = mid;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(", ");
        int[] arr = new int[inp_arr.length];
        int n = arr.length;
        int i = 0;

        for (String s : inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        int k = sc.nextInt();
        // System.out.println(input);
        int ans = aggressiveCows(arr, k);
        System.out.println(ans);
        sc.close();
    }
}
