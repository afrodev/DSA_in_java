import java.io.*; // Nødvendig for File, FileNotFoundException i main (via WordReader)
import java.util.Scanner; // Nødvendig for Scanner i main

// WordBST: Binært søketre med ord og ordfrekvenser
// NB! Må ligge i samme mappe som Wordreader


// WordReader: Read a text file word-by-word
// Author: Jan Høiberg, 2024
// Note: All non-alphabetic characters are ignored

class WordReader
{
    private BufferedReader reader; // Line-by-line file reader
    private String S[];            // All words on last read line
    private int numWords;          // Number of words on last line
    private int currentWord;       // Next word to be delievered
    private boolean finished;      // True if all words on file read

    // WordReader(): Constructor, opens file for reading
    public WordReader(String fileName)
    {
        currentWord = numWords = 0;
        finished = false;
        try {reader = new BufferedReader(new FileReader(fileName));}
        catch (IOException e) {e.printStackTrace();}
    }

    // nextWord(): Returns next word on file, null if all words read
    public String nextWord()
    {
        // Finished reading all words on file?
        if (finished)
            return null;

        // If there are no more words left on the last line read from
        // file, then read a new line and split it into separate words
        if (currentWord == numWords)
        {
            String line = "";
            // Read new line from file, skip both blank/empty lines
            // and lines with no alphabetical characters
            while (line.length() == 0)
            {
                // Try reading a line of text
                try {line = reader.readLine();}
                catch (IOException e) {e.printStackTrace();}
                // No more words on file?
                if (line == null)
                {
                    finished = true;
                    return null;
                }
                // Replace non-alphabetic characters with single space
                line = line.replaceAll("[^\\p{IsAlphabetic}]+", " ");
                // Trim off leading and trailing whitespace
                line = line.trim();
            }
            // Convert line to lowercase
            line = line.toLowerCase();
            // Split line into array of words
            S = line.split(" ");
            // Set number of words on line and the index of the next
            // word to be returned from the method
            numWords = S.length;
            currentWord = 0;
        }
        // Return next word from last read line
        String word = S[currentWord];
        currentWord++;
        return word;
    }

    // main(): Test program, prints all words on a given file
    public static void main (String argv[])
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("File? ");
        String fileName = scan.nextLine();

        WordReader wR = new WordReader(fileName);
        String word = wR.nextWord();
        while (word != null)
        {
            System.out.println(word);
            word = wR.nextWord();
        }
    }
}

public class WordBST {
    // WordNode: Indre klasse for en node (hvert enkelt ord) i søketreet (teksten)
    class WordNode {
        String ord;       // Ordet i noden
        int frekvens;     // Antall forekomster av ordet
        WordNode venstre; // Peker til venstre barn
        WordNode hoyre;   // Peker til høyre barn

        // Konstruktør for WordNode
        WordNode(String ord) {
            this.ord = ord;
            this.frekvens = 1; // Første gang ordet leses
            this.venstre = null; // Noder som skal til venstre. Starter som ingen
            this.hoyre = null; // Noder som skal til høyre. Starter som ingen
        }

        // print(): Skriver ut dataene for et ord
        void print() {
            System.out.println(ord + ": " + frekvens);
        }
    }

    private WordNode rot; // Roten i hele søketreet
    private int n;        // Antall unike ord (noder) i hele treet

    // WordBST(): Konstruktør som lager et tomt søketre
    public WordBST() {
        rot = null;
        n = 0;
    }

    // size(): Antall unike ord som er lagret i treet
    public int size() {
        return n;
    }

    // insert(): Setter inn ny forekomst av et ord
    public void insert(String ord) {
        if (rot == null) {
            rot = new WordNode(ord);
            n++;
            return;
        }

        WordNode p = rot;
        WordNode q = null; // Forelder til p

        while (p != null) {
            q = p;
            int cmp = ord.compareTo(p.ord);
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp > 0) {
                p = p.hoyre;
            } else {
                // Ordet finnes fra før, øk frekvensen
                p.frekvens++;
                return; // Ikke sett inn ny node, bare økt frekvens
            }
        }

        // Ordet fantes ikke, sett inn ny node som barn av q
        WordNode nyNode = new WordNode(ord);
        n++; // Øk antall unike ord
        if (ord.compareTo(q.ord) < 0) {
            q.venstre = nyNode;
        } else {
            q.hoyre = nyNode;
        }
    }


    // search(): Søk etter et ord. Skriv ut ordet og ordfrekvensen
    // hvis det finnes i søketreet. (Iterativ)
    public void search(String ord) {
        WordNode p = rot;
        while (p != null) {
            int cmp = ord.compareTo(p.ord);
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp > 0) {
                p = p.hoyre;
            } else {
                // Ordet funnet
                p.print();
                return; // Avslutt søket
            }
        }
        // Hvis løkken fullføres uten retur, ble ordet ikke funnet.
        // Skal ikke gjøre noen utskrift i dette tilfellet.
    }

    // print(): Alfabetisk utskrift av hele søketreet. Kaller en
    // rekursiv metode som gjør selve utskriften.
    public void print() {
        print(rot); // Kaller den private rekursive metoden
    }

    // print(): Rekursiv utskrift av hele søketreet med rot i "node" (In-order traversal)
    private void print(WordNode node) {
        if (node == null) {
            return; // Base case: tomt subtre
        }
        print(node.venstre);   // 1. Skriv ut venstre subtre
        node.print();          // 2. Skriv ut nodens data
        print(node.hoyre);    // 3. Skriv ut høyre subtre
    }

    // main(): Testprogram (uendret fra oppgaveteksten)
    public static void main (String argv[])
    {
        // Leser filnavn fra bruker
        Scanner scan = new Scanner(System.in);
        System.out.print("File? ");
        String fileName = scan.next(); // Bruker next() for filnavn uten mellomrom

        // Oppretter ordleser og tomt søketre
        // Antar at WordReader-klassen finnes og fungerer (kompileres separat)
        WordReader wR = new WordReader(fileName);
        WordBST wBST = new WordBST();

        // Leser alle ordene på filen og legger inn i treet
        String ord = wR.nextWord();
        while (ord != null)
        {
            // WordReader konverterer allerede til lowercase, så .toLowerCase() her er strengt tatt ikke nødvendig
            // hvis man bruker den gitte WordReader, men lar den stå for robusthet.
            wBST.insert(ord/*.toLowerCase()*/);
            ord = wR.nextWord();
        }
        // Skriver ut antall ulike ord som fantes i filen
        System.out.println(wBST.size() + " unique words " +
                "read from file " + fileName);

        // Menyvalg for å teste programmet
        int valg = 0;
        while(valg != 3)
        {
            System.out.print("\n1:Search, 2:Print, 3:Quit ? ");
            // Sjekk om neste input er et tall før lesing
            while (!scan.hasNextInt()) {
                System.out.println("Vennligst skriv inn et tall (1, 2, eller 3).");
                System.out.print("\n1:Search, 2:Print, 3:Quit ? ");
                scan.next(); // Konsumer ugyldig input
            }
            valg = scan.nextInt();

            if (valg == 1)
            {
                System.out.print("Search for? ");
                ord = scan.next();
                // Søker etter ord i små bokstaver siden WordReader gjør om alt til små bokstaver
                wBST.search(ord.toLowerCase());
            }
            else if (valg == 2)
                wBST.print();
            else if (valg != 3) {
                System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
        scan.close(); // Lukker scanneren
        System.out.println("Program avsluttet."); // Melding ved avslutning
    }
}