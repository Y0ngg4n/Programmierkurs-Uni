public class Calculations {
    public static void iquot(int n, int m){
        int u = 0;
        assert ((n >= 0) && (m > 0) && (u == 0)); // V
        assert ((u * m <= n)); // I
        while ((u+1) * m <= n){
            assert ((u * m <= n) && ((u + 1) * m <= m))
        }

    }
}
