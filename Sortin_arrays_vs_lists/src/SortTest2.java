import java.util.*;

public class SortTest2 {
    public static void main(String argv[]) {
        System.out.println("    n       tA      tL    tL/tA");
        System.out.println("-------------------------------");

        for (int n = 1000000; n <= 10000000; n += 1000000) {
            int[] A = new int[n];
            Random r = new Random();

            // Fyller array med delvis sorterte tall
            for (int i = 0; i < n / 3; i++) {
                A[i] = i;
            }
            for (int i = n / 3; i < 2 * n / 3; i++) {
                A[i] = r.nextInt(2 * n);
            }
            for (int i = 2 * n / 3; i < n; i++) {
                A[i] = i - n / 3;
            }

            LinkedList<Integer> L = new LinkedList<>();

            // Fyller lenket liste med samme tall som i array
            for (int i = n - 1; i >= 0; i--) {
                L.addFirst(Integer.valueOf(A[i]));
            }

            long tA, tL, t;

            // Måler tid for sortering av array
            t = System.currentTimeMillis();
            Arrays.sort(A);
            tA = System.currentTimeMillis() - t;

            // Måler tid for sortering av lenket liste
            t = System.currentTimeMillis();
            Collections.sort(L);
            tL = System.currentTimeMillis() - t;

            System.out.printf("%9d %7d %7d %7.1f\n", n, tA, tL, (float) tL / tA);
        }
        System.out.println("-------------------------------");
    }
}