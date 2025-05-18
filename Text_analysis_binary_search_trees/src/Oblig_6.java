import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
// DENNE FILEN BLE BARE BRUKT SOM ØVING PÅ Å LAGE EN NODE OG BINARY SEARCH TREE MANUELT!
class Node {
    String ord;
    int frekvens;
    Node venstre;
    Node hoyre;

    public Node(String ord) {
        this.ord = ord;
        this.frekvens = 1;
        this.venstre = null;
        this.hoyre = null;
    }
}

// ALTERNATIVT OG BEDRE: Å leggge Node klassen inn i BinarySearchTree klassen.
// Det er mer modulært ved import til andre filer.
// // Blir som når Java har egen import klasse
class BinarySearchTree {
    Node rot;

    public BinarySearchTree() {
        rot = null;
    }

    public void settInn(String ord) {
        rot = settInnRekursivt(rot, ord);
    }

    private Node settInnRekursivt(Node node, String ord) {
        if (node == null) {
            return new Node(ord);
        }

        int sammenligning = ord.compareTo(node.ord);

        if (sammenligning < 0) {
            node.venstre = settInnRekursivt(node.venstre, ord);
        } else if (sammenligning > 0) {
            node.hoyre = settInnRekursivt(node.hoyre, ord);
        } else {
            node.frekvens++;
        }
        return node;
    }

    public int finnFrekvens(String ord) {
        return finnFrekvensRekursivt(rot, ord);
    }

    private int finnFrekvensRekursivt(Node node, String ord) {
        if (node == null) {
            return 0;
        }

        int sammenligning = ord.compareTo(node.ord);

        if (sammenligning < 0) {
            return finnFrekvensRekursivt(node.venstre, ord);
        } else if (sammenligning > 0) {
            return finnFrekvensRekursivt(node.hoyre, ord);
        } else {
            return node.frekvens;
        }
    }

    public void skrivUtAlle() {
        skrivUtInOrder(rot);
    }

    private void skrivUtInOrder(Node node) {
        if (node != null) {
            skrivUtInOrder(node.venstre);
            System.out.println(node.ord + ": " + node.frekvens);
            skrivUtInOrder(node.hoyre);
        }
    }
}

public class Oblig_6 {
    public static void main(String[] args) {
        BinarySearchTree ordTre = new BinarySearchTree();
        String filnavn = "fruit.txt";

        try (BufferedReader leser = new BufferedReader(new FileReader(filnavn))) {
            String linje;
            while ((linje = leser.readLine()) != null) {
                String[] ordListe = linje.toLowerCase().split("\\s+");
                for (String ord : ordListe) {
                    if (!ord.isEmpty()) { // Sjekker for tomme strenger etter split
                        ordTre.settInn(ord);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Feil ved lesing av fil: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String sokOrd;

        System.out.println("Tekstanalyse fullført. Du kan nå søke etter ord (skriv 'slutt' for å avslutte).");

        do {
            System.out.print("Skriv inn ord du vil søke etter: ");
            sokOrd = scanner.nextLine().toLowerCase();
            if (!sokOrd.equals("slutt")) {
                int frekvens = ordTre.finnFrekvens(sokOrd);
                if (frekvens > 0) {
                    System.out.println("Ordet '" + sokOrd + "' forekommer " + frekvens + " ganger.");
                } else {
                    System.out.println("Ordet '" + sokOrd + "' finnes ikke i teksten.");
                }
            }
        } while (!sokOrd.equals("slutt"));

        System.out.println("\nAlle ord i alfabetisk rekkefølge med deres frekvenser:");
        ordTre.skrivUtAlle();

        scanner.close();
    }
}