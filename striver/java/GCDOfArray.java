
import java.util.Arrays;
import java.util.Scanner;


// link: https://leetcode.com/problems/find-greatest-common-divisor-of-array/description/

public class GCDOfArray {

    public static int gcd(int a, int b) {

        if (a==1 || b==1)
            return 1;
        if (a == b)
            return a;

        int r = a % b;
        if (r == 0)
            return b;
        return gcd(b, r);
    }

    public static int findGCDOfArray(int[] a) {

        int s, l, s_i, l_i;
        // for (int i = 1; i < a.length; i++) {
        //     if (i+1 >= a.length) {
        //         s_i = a[i];   l_i = a[i];
        //     }
        //     else {
        //         if (a[i] < a[i+1]) {
        //             s_i = a[i];   l_i = a[i+1];
        //         }
            
        //         else {
        //             s_i = a[i+1];    l_i = a[i];
        //         }
        //     }
        //     if (s_i < s)
        //         s = s_i;
        //     if (l_i > l)
        //         l = l_i;
        // }

        Arrays.sort(a);
        s = a[0]; 
        l = a[a.length-1];
        return gcd(l, s);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim(); 
        input = input.substring(1, input.length()-1);

        String[] parts = input.split(",");
        int[] arr = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        // Test
        // System.out.println(Arrays.toString(arr));

        int g = findGCDOfArray(arr);
        System.out.println(g);
        sc.close();
    }
}
