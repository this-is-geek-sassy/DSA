package src.string;
// link: https://leetcode.com/problems/reverse-string/description/
import java.util.Scanner;

public class ReverseString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        str = str.replaceAll("[\\[\\]\"]", "");  // Remove brackets and quotes

        String[] stringArray = str.split(",");
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = String.valueOf(stringArray[i].trim().charAt(0));
        }
        char[] s = String.join("", stringArray).toCharArray();


        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
//        for (int i = 0; i < (s.length / 2); i++) {
//            char left = s[i];
////            System.out.println("left : " + left );
//            char right = s[s.length - i - 1];
//            s[i] = right;
//            s[s.length - i - 1] = left;
//        }


//        System.out.println(stringArray.getClass());
        String finalOutput = "[";
        for (char string : s) {
            finalOutput += "\"" + string + "\"" + ", ";
        }
        finalOutput += "]";
        System.out.println(finalOutput);
    }
}
