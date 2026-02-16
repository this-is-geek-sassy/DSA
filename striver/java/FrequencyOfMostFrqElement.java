
import java.util.Arrays;
import java.util.Scanner;

// link: https://leetcode.com/problems/frequency-of-the-most-frequent-element/description/

public class FrequencyOfMostFrqElement {

    public static int maxFrequency(int[] nums, int k) {

        int res=0, l=0, r=0;
        long total=0;
        Arrays.sort(nums);

        while (r < nums.length) {
            total += nums[r];

            while (((long)nums[r]*(r - l + 1)) > total+k) {
                total -= nums[l];
                l++;
            }
            if ((r - l + 1) > res)
                res = (r - l + 1);
            r++;
        }
        return res;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().trim();
        int k = s.nextInt();
        input = input.substring(1, input.length()-1);

        String[] parts = input.split(",");
        int[] arr = new int[parts.length];

        int i = 0;
        for (String e : parts) {
            arr[i] = Integer.parseInt(e);
            i++;
        }
        System.out.println(maxFrequency(arr, k));
    }
}
