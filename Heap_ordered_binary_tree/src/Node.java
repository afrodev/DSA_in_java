class Node
{
    int verdi;    // Heltallsverdi
    Node venstre; // Venstre barn er en peker i Java stil
    Node høyre;   // Høyre barn er en peker i Java stil.

    // Konstruktør
    public Node(int data, Node v, Node h)
    {
        verdi = data;
        venstre = v;
        høyre = h;
    }
}