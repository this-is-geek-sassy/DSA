import java.util.Scanner;


// link: https://leetcode.com/problems/kth-missing-positive-number/

public class KthMissingPositiveNumber {

    public static int findKthPositiveLinear(int[] arr, int k) {
        int i, n = arr.length;
        for (i=0; i<arr.length; i++) {
            int missngBeforeThis = arr[i]-i-1;
            if (missngBeforeThis >= k) {
                if (i == 0) {
                    return k;
                }
                int prevMissing = arr[i-1] - (i-1) - 1;
                return arr[i-1] + (k - prevMissing);
            }
        }
        int lastMissing = arr[n-1] - n;
        return arr[n-1] + (k - lastMissing);
    }

    public static int findKthPositiveBinary (int[] arr, int k) {
        int low = 0, high = arr.length-1, mid;

        while (low <= high) {
            mid = low + (high - low)/2;
            int missingBeforeThis = arr[mid]-mid-1;
            if (missingBeforeThis >= k) {
                high = mid -1;
            } else {
                low = mid + 1;
            }
        }
        if (low == 0) {
            return k;
        }
        
        int prevMissing = arr[low-1]- (low-1) - 1;
        return arr[low-1] + (k - prevMissing);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");

        int[] arr = new int[inp_arr.length];
        int i = 0;
        for (String s: inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        int k = sc.nextInt();
        int ans = findKthPositiveLinear(arr, k);
        sc.close();
    }
}