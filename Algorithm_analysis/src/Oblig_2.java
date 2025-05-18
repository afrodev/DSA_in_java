import java.io.*;
import java.util.Scanner;

class Oblig_2 {
    public static void lineær(long n) {
        int tmp = 1;
        for (long i = 0; i < n; i++)
            tmp *= 1;
    }

    public static void kvadratisk(long n) {
        int tmp = 1;
        for (long i = 0; i < n; i++)
            for (long j = 0; j < n; j++)
                tmp *= 1;
    }

    public static int logaritmisk(long n) {
        int tmp = 1, iterasjoner = 0;
        for (long i = n; i > 0; i /= 2, iterasjoner++)
            tmp *= 1;
        return iterasjoner;
    }

    public static void superlineær(long n) {
        int tmp = 1;
        for (long i = 0; i < n; i++) {
            for (long j = n; j > 0; j /= 2) {
                tmp *= 1;
            }
        }
    }

    public static void kubisk(long n) {
        int tmp = 1;
        for (long i = 0; i < n; i++) {
            for (long j = 0; j < n; j++) {
                for (long k = 0; k < n; k++) {
                    tmp *= 1;
                }
            }
        }
    }

    public static void eksponentiell(long n) {
        int tmp = 1;
        long grense = (long) Math.pow(2, n);
        for (long i = 0; i < grense; i++) {
            tmp *= 1;
        }
    }

    public static void kombinatorisk(long n) {
        int tmp = 1;
        long grense = fakultet(n);
        for (long i = 0; i < grense; i++) {
            tmp *= 1;
        }
    }

    public static long fakultet(long n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * fakultet(n - 1);
        }
    }

    public static void main(String[] args) {
        Scanner S = new Scanner(System.in);
        long n, T, T1, T2;
        int valg, iterasjoner = 0;

        System.out.print("1:O(n) 2:O(n²) 3:O(log_n) 4:O(n log_n) 5:O(n³) 6:O(2^n) 7:O(n!) ? ");
        valg = S.nextInt();
        System.out.print("n? ");
        n = S.nextLong();

        T1 = System.currentTimeMillis();
        switch (valg) {
            case 1:
                lineær(n);
                break;
            case 2:
                kvadratisk(n);
                break;
            case 3:
                iterasjoner = logaritmisk(n);
                break;
            case 4:
                superlineær(n);
                break;
            case 5:
                kubisk(n);
                break;
            case 6:
                eksponentiell(n);
                break;
            case 7:
                kombinatorisk(n);
                break;
        }
        T2 = System.currentTimeMillis();

        T = T2 - T1;
        System.out.print("T = " + T + " ms");
        if (valg == 3)
            System.out.print(" (" + iterasjoner + ")");
        System.out.println();
    }
}