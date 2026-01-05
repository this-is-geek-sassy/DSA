public class strings {
    public static void main(String[] args) {
        
        String s = "striver";

        System.out.println(s.length());

        // for (String l : s) {
        //     System.out.println(l);
        // } // does not work because string is not iterable in java

        // for (int i = 0; i < s.length(); i++) {
        //     System.out.print(s.charAt(i) + " ");
        // }

        String j = s;

        s += " jio";

        System.out.println(s);
        System.out.println(j);
    }
}