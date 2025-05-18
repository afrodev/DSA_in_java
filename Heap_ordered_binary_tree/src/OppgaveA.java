// OppgaveA.java - Løsning for Oppgave A
import java.io.*; // Standard import

public class OppgaveA {

    /**
     * Teller antall forekomster av 'verdi' i et heap-ordnet tre.
     * Utnytter heap-ordningen for effektivitet.
     *
     * @param rot   Roten til treet/deltreet.
     * @param verdi Verdien som telles.
     * @return Antall forekomster av 'verdi'.
     */
    public static int tell(Node rot, int verdi) {
        // Basistilfelle: Tomt tre.
        if (rot == null) {
            return 0;
        }

        // Optimalisering: Verdien kan ikke finnes i dette deltreet.
        if (rot.verdi > verdi) {
            return 0;
        }

        // Tell i subtrærne.
        int antall = tell(rot.venstre, verdi) + tell(rot.høyre, verdi);

        // Sjekk roten.
        if (rot.verdi == verdi) {
            antall++;
        }

        return antall;
    }

    // main(): Testprogram
    public static void main(String argv[]) {
        // Lager treet spesifisert i testfilen.
        Node rot = new Node(1,
                new Node(2,
                        new Node(17,
                                new Node(25, null, null),
                                new Node(19, null, null)),
                        null),
                new Node(17, null,
                        new Node(36, null, null)));

        // Tester tell()-metoden.
        System.out.println("tell(2)  = " + tell(rot, 2));
        System.out.println("tell(17) = " + tell(rot, 17));
        System.out.println("tell(25) = " + tell(rot, 25));
        System.out.println("tell(50) = " + tell(rot, 50));
    }
}