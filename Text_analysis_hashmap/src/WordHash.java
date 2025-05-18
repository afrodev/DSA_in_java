import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap; // Bruker Java's hashtabell
import java.util.Map;    // Bruker Map-interfacet
import java.util.ArrayList; // For sortering før utskrift
import java.util.Collections; // For sortering

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

// WordHash: Lagrer ord og frekvens fra tekst vha. HashMap.
public class WordHash {

    // HashMap for ord (String) -> frekvens (Integer). Rask, usortert.
    // Bruker Map-interfacet for fleksibilitet (se forklaring nedenfor).
    private Map<String, Integer> wordMap;

    // Konstruktør: Oppretter et tomt HashMap.
    public WordHash() {
        wordMap = new HashMap<>();
    }

    // Returnerer antall unike ord.
    public int size() {
        return wordMap.size();
    }

    // Legger til ord / øker frekvens.
    public void insert(String ord) {
        wordMap.put(ord, wordMap.getOrDefault(ord, 0) + 1);
    }

    // Søker og skriver ut ord + frekvens hvis funnet.
    public void search(String ord) {
        if (wordMap.containsKey(ord)) {
            System.out.println(ord + ": " + wordMap.get(ord));
        }
    }

    // Skriver ut alle ord alfabetisk (krever sortering).
    public void print() {
        if (wordMap.isEmpty()) {
            System.out.println("Hashtabellen er tom.");
            return;
        }
        // Hent nøkler, sorter dem, og skriv ut fra map'et
        ArrayList<String> sortedKeys = new ArrayList<>(wordMap.keySet());
        Collections.sort(sortedKeys);

        System.out.println("\nAlle ord i alfabetisk rekkefølge:");
        for (String key : sortedKeys) {
            System.out.println(key + ": " + wordMap.get(key));
        }
    }

    // Hovedprogram for test.
    public static void main (String argv[])
    {
        System.out.println("This is WordHash running and NOT WordReader");
        Scanner scan = new Scanner(System.in);
        System.out.print("Fil? ");
        String fileName = scan.next();

        WordReader wR = new WordReader(fileName);
        WordHash wHash = new WordHash(); // Bruker WordHash

        System.out.println("Leser filen " + fileName + "...");
        String ord = wR.nextWord();
        while (ord != null)
        {
            wHash.insert(ord); // Bruker WordHash metoder
            ord = wR.nextWord();
        }
        System.out.println("Fil lesing ferdig.");
        System.out.println(wHash.size() + " unike ord lest fra filen " + fileName);

        // Meny-løkke
        int valg = 0;
        while(valg != 3)
        {
            System.out.print("\n1:Søk, 2:Skriv ut alle, 3:Avslutt ? ");
            while (!scan.hasNextInt()) {
                System.out.println("Ugyldig input. Bruk tall 1, 2, eller 3.");
                System.out.print("\n1:Søk, 2:Skriv ut alle, 3:Avslutt ? ");
                scan.next();
            }
            valg = scan.nextInt();

            switch (valg) {
                case 1:
                    System.out.print("Søk etter? ");
                    ord = scan.next();
                    wHash.search(ord.toLowerCase()); // Bruker WordHash metoder
                    break;
                case 2:
                    wHash.print(); // Bruker WordHash metoder
                    break;
                case 3:
                    System.out.println("Programmet avsluttes.");
                    break;
                default:
                    System.out.println("Ugyldig valg. Prøv igjen.");
                    break;
            }
        }
        scan.close();
    }
}
