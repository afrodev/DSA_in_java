import java.util.Queue;
import java.util.LinkedList;
import java.io.*; // Standard import

public class OppgaveBC {

	/**
	 * Oppgave B: Reparerer heap-ordningen nedover fra 'rot'.
	 * Forutsetter at subtrærne til 'rot' er heap-ordnet. Iterativ.
	 *
	 * @param rot Noden hvor reparasjonen starter.
	 */
	public static void reparer(Node rot) {
		if (rot == null) return; // Håndterer tomt (del)tre.
		Node p = rot; // Nåværende node for sjekk/bytte.

		while (p != null) {
			Node minsteBarn = null;

			// Finn minste barn.
			if (p.venstre != null && p.høyre != null) {
				// Begge barn finnes.
				minsteBarn = (p.venstre.verdi < p.høyre.verdi) ? p.venstre : p.høyre;
			} else if (p.venstre != null) {
				// Kun venstre barn.
				minsteBarn = p.venstre;
			} else if (p.høyre != null) {
				// Kun høyre barn.
				minsteBarn = p.høyre;
			} else {
				// Bladnode, ferdig herfra.
				break;
			}

			// Sjekk heap-betingelse mot minste barn.
			if (p.verdi > minsteBarn.verdi) {
				// Bytt verdier.
				int temp = p.verdi;
				p.verdi = minsteBarn.verdi;
				minsteBarn.verdi = temp;

				// Fortsett nedover fra byttet node.
				p = minsteBarn;
			} else {
				// Heap-ordning OK for 'p'.
				break;
			}
		}
	}

	/**
	 * Oppgave C: Omdanner et vilkårlig tre til et heap-ordnet tre.
	 * Rekursiv metode som bruker 'reparer'.
	 *
	 * @param rot Roten til treet som skal ordnes.
	 */
	public static void lagHeapOrdning(Node rot) {
		// Basistilfelle: Tomt tre.
		if (rot == null) {
			return;
		}

		// Ordne subtrærne først (postorden).
		lagHeapOrdning(rot.venstre);
		lagHeapOrdning(rot.høyre);

		// Reparer roten etter at subtrærne er ordnet.
		reparer(rot);
	}

	// Nivå for nivå utskrift, for testing.
	public static void print(Node rot) {
		if (rot == null)
			return;
		Queue<Node> q = new LinkedList<Node>();
		int n_nivå = 1, n_print = 0, n_neste = 0;
		q.add(rot);
		while (!q.isEmpty()) {
			Node denne = q.remove();
			if (denne.venstre != null) {
				q.add(denne.venstre);
				n_neste++;
			}
			if (denne.høyre != null) {
				q.add(denne.høyre);
				n_neste++;
			}
			System.out.print(denne.verdi + " ");
			// Håndterer linjeskift mellom nivåer.
			if (++n_print == n_nivå) {
				System.out.println();
				n_print = 0;
				n_nivå = n_neste;
				n_neste = 0;
			}
		}
		if (n_print != 0) // Siste linje.
			System.out.println();
		System.out.println(); // Ekstra blank linje.
	}

	// Testprogram.
	public static void main(String argv[]) {
		// Lager treet fra figur 3.
		Node rot = new Node(36,
				new Node(19,
						new Node(17,
								new Node(25, null, null),
								new Node(2, null, null)),
						new Node(7, null, null)),
				new Node(25,
						new Node(1, null, null),
						new Node(3, null, null)));
		print(rot); // Skriv uordnet tre.

		// Ordne treet.
		lagHeapOrdning(rot);
		print(rot); // Skriv heap-ordnet tre.
	}
}