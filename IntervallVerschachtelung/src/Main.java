class Main {
    public static void main(String[] args) {
        /* Test iquot with a prime number*/
        int num = 103;
        for(int div=num; div > 0; div--) {
            assert(iquot(num,div) == Math.floorDiv(num, div));
        }
//        /* Test iquot with a non-prime number*/
//        num = 420;
//        for(int div=num; div > 0; div--) {
//            assert(iquot(num,div) == Math.floorDiv(num, div));
//        }
//        System.out.println("Der Integer-Quotient ohne Fortschaltung oder Intervallhalbierung scheint zu funktionieren.");
//
//        System.out.println();
//
//        int max = 100;
//        /* Test isqrt_halbierung with a all numbers up to max*/
//        for(int n = 1; n <= max; n++) {
//            assert(isqrt_halbierung(n) == Math.floor(Math.sqrt(n)));
//        }
//        System.out.println("Die Integer-Wurzel mit Intervallhalbierung scheint zu funktionieren.");
//
//        System.out.println();
//
//        int nmax = 23;
//        /* test ilog_fortschaltung for some values */
//        for(int n = 1; n <= nmax; n++) {
//            for(int a = 2; a <= n+2; a++) {
//                assert(ilog_fortschaltung(a,n) == Math.floor(Math.log(n) / Math.log(a)));
//            }
//        }
//        System.out.println("Der Integer-Logarithmus mit Fortschaltungstechnik scheint zu funktionieren.");
//
//        System.out.println();
//
//        double eps = 0.001;
//        /* test cosine for some values */
//        for(double x = 0; x < 10; x += 0.1) {
//            assert(Math.abs(cosinus(x,eps) - Math.cos(x)) <= eps);
//        }
//        System.out.println("Die Berechnung des Cosinus scheint zu funktionieren.");
//
//        System.out.println();
//
//        max = 10;
//        /* test newton for calculating the square root up until max*/
//        for(double x = 1; x < max; x += 0.1) {
//            assert(Math.abs(newton_sqrt(x,eps) - Math.sqrt(x)) <= eps);
//        }
//        System.out.println("Die Berechnung der Quadratwurzel mit Hilfe des Newton-Verfahrens scheint zu funktionieren.");
//
//        System.out.println();
//
//        /* test newton for calculating the logarithm up until max */
//        for(double x = 1; x < max; x += 0.1) {
//            assert(Math.abs(newton_log2(x,eps) - (Math.log(x) / Math.log(2))) < eps);
//        }
//        System.out.println("Die Berechnung des 2er-Logarithmus mit Hilfe des Newton-Verfahrens scheint zu funktionieren.");
    }

}