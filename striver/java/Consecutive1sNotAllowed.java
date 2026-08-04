import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class Consecutive1sNotAllowed {
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
    public static int countStrings(int n) {
        // code here
        // if (n==1)
        //     return 2;
        // if (n==2)
        //     return 3;
        
        // return countStrings(n-1) + countStrings(n-2);

        int[] mem = new int[n+1];

        if (n>=1)
            mem[1] = 2;
        if (n>=2)
            mem[2] = 3;

        for (int i=3; i<=n; i++) {
            mem[i] = mem[i-1] + mem[i-2];
        }
        return mem[n];
    }
    private static int helper (int[] mem, int n) {

        if (mem[n] != 0)
            return mem[n];
        mem[n] = helper(mem, n-1) + helper(mem, n-2);
        return mem[n];
    }
    public static int countStringsRec(int n) {
        // code here
        // if (n==1)
        //     return 2;
        // if (n==2)
        //     return 3;
        
        // return countStrings(n-1) + countStrings(n-2);

        int[] mem = new int[n + 1];
        mem[1] = 2;
        if (n >= 2)
            mem[2] = 3;

        return helper(mem, n);
    }
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String input = sc.nextLine();
            int[] nums = parseInputArray(input);
            Stack<Integer> st = buildStack(nums);
            
            // insertAtBottom(st, 100);
            
            System.out.println("stack: " + st);
        }
    }
}
