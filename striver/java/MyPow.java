public class MyPow {
    public double myPow(double x, long n) {
        
        // System.out.println(n);
        if (n==0)
            return 1;
        if (n < 0)
            return myPow((double)1/x, (long)-n);

        double half = myPow(x, n / 2);
        if (n % 2 == 1)
            return half * half * x;
        else 
            return half * half;
    }

    public static void main(String[] args) {
        
    }
}
