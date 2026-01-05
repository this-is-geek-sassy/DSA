public class string_builder {
    
    public static void main(String[] args) {
        
        StringBuffer s = new StringBuffer("striver");

        StringBuffer j = s;
        // System.out.println(s);

        // for (int i = 0; i < s.length(); i++) {
        //     System.out.print(s.charAt(i) + " ");
        // }
        s.setCharAt(0, 'k');
        System.out.println(s);
        System.out.println(j);
    }
}
