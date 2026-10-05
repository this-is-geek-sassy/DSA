package greedy;

// https://www.geeksforgeeks.org/problems/fractional-knapsack-1587115620/1

import java.util.Arrays;
import java.util.Scanner;


public class FractionalKnapsack {

    static class Item {
        int val;
        int wt;
        double ratio;

        Item (int val, int wt) {
            this.val = val;
            this.wt = wt;
            this.ratio = (double)val/wt;
        }
    }

    public static double fractionalKnapsack(int[] val, int[] wt, int capacity) {
        // code here
        Item[] items = new Item[wt.length];

        for (int i=0; i<wt.length; i++) {
            items[i] = new Item(val[i], wt[i]);
        }

        Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));

        double ans = 0;

        for (Item item: items) {
            if (item.wt <= capacity) {
                capacity -= item.wt;
                ans += item.val;
            } else {
                // double fraction = (double)capacity/item.wt;
                ans += (item.ratio * capacity);
                break;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String valInp = sc.nextLine().trim();
        String wtInp = sc.nextLine().trim();

        String[] valInpArr = valInp.substring(1, valInp.length()-1).split(", ");
        String[] wtInpArr = wtInp.substring(1, wtInp.length()-1).split(", ");

        int capacity = sc.nextInt(), i = 0;
        int[] val = new int[valInpArr.length];
        int[] wt = new int[wtInpArr.length];

        for (String s: valInpArr) {
            val[i++] = Integer.parseInt(s);
        }
        i = 0;
        for (String s: wtInpArr) {
            wt[i++] = Integer.parseInt(s);
        }
        double ans = fractionalKnapsack(val, wt, capacity);
        System.out.println(ans);
        sc.close();
    }
}
