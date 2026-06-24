
import java.util.Scanner;


// link: https://www.geeksforgeeks.org/problems/square-root/1

public class SquareRootOfN {
    public static int floorSqrt(int n) {
        // code here
        long low = 1, high = n, cand = 0;

        while (low <= high) {
            long mid = (low + high)/2;
            long sq = (mid * mid);
            if (sq == n) {
                return (int)mid;
            }
            else if (sq < n) {
                cand = mid;
                low = mid+1;
            } else {
                high = mid - 1;
            }
        }
        return (int)cand;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int ans = floorSqrt(n);
        System.out.println(ans);
        sc.close();
    }
}
