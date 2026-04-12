
// link: https://leetcode.com/problems/majority-element/description/

import java.util.Scanner;

public class MajorityElement {

    public static int majorityElement(int[] nums) {
        
        int n = nums.length;
        int count = 0, element = 0;

        for (int i = 0; i < n; i++) {
            
            if (nums[i] != element && count == 0) {
                element = nums[i];
                count++;
            } else if (nums[i] != element) {
                count--;
            } else {
                count++;
            }
        }
        return element;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String ip = s.nextLine();
        ip = ip.trim().substring(1, ip.length()-1);
        String[] str_arr = ip.split(",");

        int[] numbers = new int[str_arr.length];
        int i=0;

        for (String str : str_arr) {
            int a = (int)Integer.parseInt(str);
            numbers[i] = a;
            i++;
        }
        
        s.close();

        // for (int n : numbers) {
        //     System.out.print(n + " ");
        // }
        System.out.println(majorityElement(numbers));
    }
}
