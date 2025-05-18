import java.io.*;
import java.util.*;

// Topologisk sortering

public class TopSort
{
	int n;            // Antall noder i grafen
	boolean nabo[][]; // Nabomatrise
	String data[];    // Data i hver node

	// TopSort(): Konstruktør
	// Leser inn grafen fra fil, ingen feilsjekking
	public TopSort(String filNavn)
	{
		// Filformat:
		//   ant.noder
		//   node# data ant.naboer nabo# nabo# ...
		//   node# data ant.naboer nabo# nabo# ...
		//   ...
		try
		{
			// Åpner datafil for lesing
			Scanner in = new Scanner(new File(filNavn));
			// Leser antall noder
			n = in.nextInt();
			// Oppretter nabomatrisen
			nabo = new boolean[n][n];
			// Setter hele nabomatrisen, untatt diagonalen, til false
			// Diagonalen settes til true (en node er nabo med seg selv)
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					nabo[i][j] = (i == j); // Setter diagonalen til true, resten false initielt
				}
			}
			// Oppretter arrayen med data (string) for hver node
			data = new String[n];
			// For hver node: Les data og alle naboene noden har
			for (int i = 0; i < n; i++)
			{
				int nodeNr = in.nextInt();
				data[nodeNr] = in.next();
				int antNaboer = in.nextInt();
				for (int j = 0; j < antNaboer; j++)
				{
					int naboNr = in.nextInt();
					// Sjekker at nodeNr og naboNr er innenfor grensene før tilordning
					if (nodeNr >= 0 && nodeNr < n && naboNr >= 0 && naboNr < n) {
						nabo[nodeNr][naboNr] = true;
					} else {
						// Håndterer eventuelle feil i filformatet eller indekser utenfor rekkevidde
						System.err.println("Ugyldig node nummer funnet i filen: " + nodeNr + " eller " + naboNr);
						// Kan vurdere å kaste et unntak eller avslutte programmet her
					}

				}
			}
			in.close(); // Lukker Scanner
		}
		catch (FileNotFoundException e) {
			System.err.println("Error: Filen '" + filNavn + "' ble ikke funnet.");
			System.exit(1);
		}
		catch (InputMismatchException e) {
			System.err.println("Error: Ugyldig format i filen '" + filNavn + "'. Forventet heltall.");
			System.exit(1);
		}
		catch (NoSuchElementException e) {
			System.err.println("Error: Filen '" + filNavn + "' inneholder ikke nok data.");
			System.exit(1);
		}
		catch (Exception e)
		{
			// Fanger opp andre uventede feil
			System.err.println("En uventet feil oppstod under lesing av filen " + filNavn + ": " + e.getMessage());
			e.printStackTrace(); // Skriver ut stack trace for feilsøking
			System.exit(1);
		}
	}

	// findAndPrint(): Finner og skriver ut en topologisk sortering
	public void findAndPrint()
	{
		int[] inDegree = new int[n]; // Array for å lagre inngraden til hver node
		Queue<Integer> s = new LinkedList<>(); // Kø for noder med inngrad 0

		// 1. Beregn inngraden for alle noder
		for (int j = 0; j < n; j++) { // Kolonne (til-node)
			for (int i = 0; i < n; i++) { // Rad (fra-node)
				if (i != j && nabo[i][j]) { // Hvis det er en kant fra i til j (og ikke løkke)
					inDegree[j]++;
				}
			}
		}

		// 2. Legg alle noder med inngrad 0 i køen S
		for (int i = 0; i < n; i++) {
			if (inDegree[i] == 0) {
				s.add(i);
			}
		}

		int count = 0; // Teller for å oppdage sykler (selv om input forutsettes å være sykkel-fri)

		// 3. Så lenge køen S ikke er tom
		while (!s.isEmpty()) {
			// 3a. Ta ut en node 'a' fra køen S
			int a = s.poll();

			// 3b. Skriv ut noden 'a'
			System.out.print(data[a] + " ");
			count++;

			// 3c. For hver av noden 'a' sine naboer 'b'
			for (int b = 0; b < n; b++) {
				if (a != b && nabo[a][b]) { // Hvis 'b' er en nabo av 'a' (og ikke seg selv)
					// 3c.i. Reduser inngraden til naboen 'b' med 1
					inDegree[b]--;

					// 3c.ii. Hvis naboen 'b' nå har fått inngrad lik 0, legg den til i køen S
					if (inDegree[b] == 0) {
						s.add(b);
					}
				}
			}
		}

		// Ny linje etter utskrift
		System.out.println();

		// Valgfri sjekk for sykler (hvis count != n, finnes det en sykel)
		if (count != n) {
			System.err.println("Error: Grafen inneholder en sykel. Topologisk sortering er ikke mulig.");
		}
	}

	// main(); Testprogram
	public static void main(String args[])
	{
		System.out.println("This is TopSort running!!!!!!");
		Scanner scan = new Scanner(System.in);
		System.out.print("File? ");
		String filNavn = scan.next();
		scan.close(); // Lukker Scanner

		// Oppretter TopSort-objekt og kjører sorteringen
		TopSort ts = new TopSort(filNavn);
		ts.findAndPrint();
	}
}