import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap; // Importer TreeMap for å bruke den balanserte trestrukturen
import java.util.Map;    // Importer Map interface for god praksis

// WordReader: Read a text file word-by-word
// uendret fra oblig 6 binære søketrær og beholdes for kompletthet)
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
        scan.close();
    }
}


/**
 * WordBST: En klasse for å analysere tekst ved å lagre unike ord og deres frekvens.
 * Denne versjonen bruker Java's innebygde TreeMap, et balansert søketre (Red-Black tree),
 * for å garantere logaritmisk tidskompleksitet for innsetting, søk og fjerning.
 */
/**
 * WordBST: Lagrer unike ord og deres frekvens fra en tekst ved hjelp av TreeMap.
 */
public class WordBST {

    // TreeMap for å lagre ord (String) og frekvens (Integer).
    // Holder dataene sortert og gir effektiv tilgang.
    private Map<String, Integer> wordMap;

    /**
     * Konstruktør: Oppretter et tomt TreeMap.
     */
    public WordBST() {
        wordMap = new TreeMap<>();
    }

    /**
     * Returnerer antall unike ord.
     * @return Antall elementer i TreeMap'et.
     */
    public int size() {
        return wordMap.size();
    }

    /**
     * Legger til et ord eller øker frekvensen hvis det allerede finnes.
     * @param ord Ordet som skal behandles (forventes i små bokstaver).
     */
    public void insert(String ord) {
        // Hent frekvens (eller 0 hvis ny), øk med 1, og legg tilbake/oppdater.
        wordMap.put(ord, wordMap.getOrDefault(ord, 0) + 1);
    }


    /**
     * Søker etter et ord og skriver ut frekvensen hvis det finnes.
     * @param ord Ordet det søkes etter (forventes i små bokstaver).
     */
    public void search(String ord) {
        // Bruker containsKey for å sjekke om ordet finnes.
        if (wordMap.containsKey(ord)) {
            System.out.println(ord + ": " + wordMap.get(ord)); // Skriv ut ord og frekvens.
        }
        // Gjør ingenting hvis ordet ikke finnes.
    }

    /**
     * Skriver ut alle ord og frekvenser i alfabetisk rekkefølge.
     */
    public void print() {
        if (wordMap.isEmpty()) {
            System.out.println("Treet er tomt.");
            return;
        }
        // Iterer over TreeMap'ets nøkler (som er sortert).
        for (String key : wordMap.keySet()) {
            System.out.println(key + ": " + wordMap.get(key));
        }
    }

    /**
     * Hovedprogram for testing. Leser fil, bygger tre, og tilbyr meny.
     * @param argv Kommandolinjeargumenter (ikke i bruk).
     */
    public static void main (String argv[])
    {
        System.out.println("This is WordBST running and NOT WordReader");
        Scanner scan = new Scanner(System.in);
        System.out.print("Fil? ");
        String fileName = scan.next();

        WordReader wR = new WordReader(fileName);
        WordBST wBST = new WordBST(); // Bruker nå TreeMap internt.

        System.out.println("Leser filen " + fileName + "...");
        String ord = wR.nextWord();
        while (ord != null)
        {
            wBST.insert(ord); // Legg inn ordet
            ord = wR.nextWord(); // Les neste ord
        }
        System.out.println("Fil lesing ferdig.");
        System.out.println(wBST.size() + " unike ord lest fra filen " + fileName);

        // Meny-løkke
        int valg = 0;
        while(valg != 3)
        {
            System.out.print("\n1:Søk, 2:Skriv ut alle, 3:Avslutt ? ");
            while (!scan.hasNextInt()) { // Input validering
                System.out.println("Ugyldig input. Bruk tall 1, 2, eller 3.");
                System.out.print("\n1:Søk, 2:Skriv ut alle, 3:Avslutt ? ");
                scan.next();
            }
            valg = scan.nextInt();

            switch (valg) {
                case 1: // Søk
                    System.out.print("Søk etter? ");
                    ord = scan.next();
                    wBST.search(ord.toLowerCase());
                    break;
                case 2: // Skriv ut
                    System.out.println("\nAlle ord i alfabetisk rekkefølge:");
                    wBST.print();
                    break;
                case 3: // Avslutt
                    System.out.println("Programmet avsluttes.");
                    break;
                default: // Ugyldig valg
                    System.out.println("Ugyldig valg. Prøv igjen.");
                    break;
            }
        }
        scan.close(); // Lukk scanner
    }
}