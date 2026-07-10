import java.util.Scanner;

public class CapacityOfShip {
    public static int shipWithinDays(int[] weights, int days) {
        
        // find high & low
        int high = 0, low = Integer.MIN_VALUE;
        for (int e: weights) {
            high += e;
            low = (e > low) ? e : low;
        }
        int mid = 0;
        while (low <= high) {
            mid = low + (high - low)/2;

            int day_reqd = 1;
            int load = 0;

            for (int w : weights) {
                if (load + w <= mid) {
                    load += w;
                } else {
                    day_reqd++;
                    load = w;
                }
            }
            if (day_reqd > days) {
                low = mid+1;
            } else {
                high = mid-1;
            }
        }

        return low;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] parts = input.split(",");
        int[] weights = new int[parts.length];
        int i =0;

        for (String e : parts) {
            weights[i++] = Integer.parseInt(e);
        }
        int days = sc.nextInt();
        int ans = shipWithinDays(weights, days);
        System.out.println(ans);
        sc.close();
    }
}
