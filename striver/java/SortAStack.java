import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class SortAStack {

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

    public static int findLargest(Stack<Integer> st, int length, int greatest) {

        if (length==1) {
            int lastElem = st.pop();
            
            if (lastElem > greatest) {
                st.push(greatest);
                greatest = lastElem;
            }
            else {
                st.push(lastElem);
            }
            return greatest;
        }

        int justPopped = st.pop(), biggestfound;
        
        if (justPopped > greatest) {
            
            biggestfound = findLargest(st, length-1, justPopped);
            st.push(greatest);
        } else {
            biggestfound = findLargest(st, length-1, greatest);
            st.push(justPopped);
        }
        
        return biggestfound;
    }

    public static void sortStack(Stack<Integer> st) {

        Stack<Integer> st_copy = new Stack<>();
        // st_copy.addAll(st);

        int largest;
        while (!st.isEmpty()) {

            largest = findLargest(st, st.size(), st.peek());
            st.pop();
            st_copy.push(largest);
        }
        // System.out.println("st_copy: " + st_copy);

        while (!st_copy.isEmpty()) {
            Integer e = st_copy.pop();
            st.push(e);
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String input = sc.nextLine();
            int[] nums = parseInputArray(input);
            Stack<Integer> st = buildStack(nums);
            // printStack(st);
            // System.out.println("stack: " + st);
            
            // // SortAStack sorter = new SortAStack();
            // // sorter.sortStack(st);

            // int largest = findLargest(st, st.size(), st.peek());

            // st.pop();
            // // st.push(largest);
            // System.out.println("largest = " + largest);
            // System.out.println("stack: " + st);
            
            // // 2nd iter
            // largest = findLargest(st, st.size(), st.peek());
            // st.pop();
            // System.out.println("largest = " + largest);
            // System.out.println("stack: " + st);
            

            sortStack(st);
            System.out.println("stack: " + st);
        }
    }
}
