
// link: https://www.geeksforgeeks.org/problems/find-nth-root-of-m5843/1

import java.util.Scanner;

public class NthRootOfM {
    public static int nthRoot(int n, int m) {
        // code here
        long low = 1, high = m, cand = 0;

        while (low <= high) {
            long mid = (low + high)/2;
            long sq = (long) Math.pow(mid, n);
            // for (int i=1; i<n; i++) {
            //     sq *= mid;
            // }
            if (sq == m) {
                return (int)mid;
            }
            else if (sq < m) {
                cand = mid;
                low = mid+1;
            } else {
                high = mid - 1;
            }
        }
        if ((int)Math.pow(cand, n) != m)
            return -1;
        return (int)cand;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int ans = nthRoot(n, m);
        System.out.println(ans);
        sc.close();
    }
    
}
