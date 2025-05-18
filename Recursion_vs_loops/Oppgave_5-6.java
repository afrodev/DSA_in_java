import java.util.Scanner;

public class Oppgave_5_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("n? ");
        int n = scanner.nextInt();

        for (int m = 0; m <= n; m++) {
            long resultat = C_iterativ(n, m);
            System.out.println("C(" + n + ", " + m + ") = " + resultat);
        }
    }

    public static long C_iterativ(int n, int m) {
        long[][] pascal = new long[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            pascal[i][0] = 1;
            if (i <= m) {
                pascal[i][i] = 1;
            }
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, m); j++) {
                pascal[i][j] = pascal[i - 1][j - 1] + pascal[i - 1][j];
            }
        }

        return pascal[n][m];
    }
}