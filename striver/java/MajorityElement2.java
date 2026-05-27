
// link: https://leetcode.com/problems/majority-element-ii/description/

import java.util.*;


public class MajorityElement2 {

    public static List<Integer> majorityElement(int[] nums) {
        
        int elem1 = 0, elem2 = 0;
        int count1 = 0, count2 = 0;

        for (int x : nums) {

            if (x == elem1) {
                count1++;
            }
            else if (x == elem2) {
                count2++;
            }
            else if (count1 == 0) 
            {
                elem1 = x;
                count1++;
            } else if (count2 == 0) {
                elem2 = x;
                count2++;
            }
            else {
                count1--;
                count2--;
            }
        }
        // Second pass: verify
        count1 = 0;
        count2 = 0;

        for (int x : nums) {
            if (x == elem1) {
                count1++;
            }
            else if (x == elem2) {
                count2++;
            }
        }
        List<Integer> li = new ArrayList<>();
        int threshold = nums.length / 3;

        if (count1 > threshold) {
            li.add(elem1);
        }
        if (count2 > threshold) {
            li.add(elem2);
        }
        return li;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        input = input.trim().substring(1, input.length()-1);
        String[] inp_arr = input.split(",");
        int[] arr = new int[inp_arr.length];
        int i = 0;

        for (String s : inp_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        List<Integer> elements = majorityElement(arr);
        
        for (int e: elements) {
            System.out.print(e + " ");
        }
        sc.close();
    }
}
