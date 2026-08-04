// link: https://takeuforward.org/data-structure/reverse-a-stack-using-recursion
// https://www.geeksforgeeks.org/problems/reverse-a-stack/1

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class ReverseAStack {

    private static int[] parseInputArray(String input) {

        input = input.trim();

        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1);
        }

        if (input.isEmpty()) {
            return new int[0];
        }

        StringTokenizer tokenizer = new StringTokenizer(input, ", []");
        int[] nums = new int[tokenizer.countTokens()];

        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            nums[i] = Integer.parseInt(tokenizer.nextToken());
        }
        return nums;
    }

    private static Stack<Integer> buildStack(int[] nums) {
        Stack<Integer> stack = new Stack<>();

        for (int num : nums) {
            stack.push(num);
        }
        return stack;
    }

    private static void printStack(Stack<Integer> st) {
        for (int i = st.size() - 1; i >= 0; i--) {
            System.out.print(st.get(i));
            if (i > 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
    private static void insertAtBottom (Stack<Integer> st, int number) {

        if (st.isEmpty()) {
            st.push(number);
            return;
        }
        int temp = st.pop();
        insertAtBottom(st, number);
        st.push(temp);
    }

    public static void reverseStack(Stack<Integer> st) {
        // code here
        if (st.isEmpty())
            return;
        int top = st.pop();
        reverseStack(st);
        insertAtBottom(st, top);
    }
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String input = sc.nextLine();
            int[] nums = parseInputArray(input);
            Stack<Integer> st = buildStack(nums);
            
            // insertAtBottom(st, 100);
            reverseStack(st);
            System.out.println("stack: " + st);
        }
    }
}
