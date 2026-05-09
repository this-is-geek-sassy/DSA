
import java.util.Scanner;


// link: https://leetcode.com/problems/rearrange-array-elements-by-sign/description/

public class RearrangeArrayElemBySign {

    private static int findnextPos (int[] nums, int now) {

        for (int i = now+1; i < nums.length; i++) {

            if (nums[i] >= 0)
                return i;
        }
        return -1;
    }
    private static int findnextNeg (int[] nums, int now) {

        for (int i = now+1; i < nums.length; i++) {

            if (nums[i] < 0)
                return i;
        }
        return -1;
    }

    public static int[] rearrangeArray(int[] nums) {

        int[] newNums = new int[nums.length];

        int pos = findnextPos(nums, -1), neg = findnextNeg(nums, -1), last = 0;

        for (int i = 0; i < nums.length; i++) {
            // if (nums[i] >= 0) {
            //     pos = i;
            // } else {
            //     neg = i;
            // }
            if (i%2 == 0) {
                newNums[i] = nums[pos];
                pos = findnextPos(nums, pos);
            } else {
                newNums[i] = nums[neg];
                neg = findnextNeg(nums, neg);
            }

        }

        return newNums;
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        
        String[] ip_arr = input.split(",");
        int[] nums = new int[ip_arr.length];

        int i = 0;
        for (String elem : ip_arr) {
            nums[i++] = Integer.parseInt(elem);
        }

        nums = rearrangeArray(nums);
        for (int e : nums) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
