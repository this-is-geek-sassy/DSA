
// link: https://leetcode.com/problems/koko-eating-bananas/description/

import java.util.Scanner;

public class KokoEatingBananas {
    public static int minEatingSpeed(int[] piles, int h) {
        int low = 1, high = 0, cand_mid=0;

        for (int x : piles)
            high = Math.max(high, x);

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // System.out.println("mid: " + mid);
            long total_time = 0;
            for (int i=0; i < piles.length; i++) {
                total_time += (piles[i] + mid -1) / mid;
                if (total_time > h)
                    break;
            }
            // if (total_time == h)
            //     return mid;
            if (total_time <= h) {
                cand_mid = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return cand_mid;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] ip_arr = input.split(",");
        int[] arr = new int[ip_arr.length];

        for (int i=0; i<ip_arr.length; i++) {
            arr[i] = Integer.parseInt(ip_arr[i]);
        }
        int h = sc.nextInt();
        int ans = minEatingSpeed(arr, h);
        System.out.println(ans);
        // System.out.println("nums: ");
        // for (int e : arr) {
        //     System.out.print(e + " ");
        // }
        sc.close();
    }
}
