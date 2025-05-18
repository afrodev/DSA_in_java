import java.util.LinkedList;
import java.util.Queue;

class Trenode {
    int verdi;
    Trenode venstre, høyre;
    int sum;
    Trenode forelder;

    public Trenode(int verdi, Trenode venstre, Trenode høyre) {
        this.verdi = verdi;
        this.venstre = venstre;
        this.høyre = høyre;
        sum = 0;
        forelder = null;
    }
}

public class Oblig_5 {

    static void settSum(Trenode rot) {
        if (rot == null) {
            return;
        }

        settSum(rot.venstre);
        settSum(rot.høyre);

        int sum = 0;
        if (rot.venstre != null) {
            sum += rot.venstre.sum;
        }
        if (rot.høyre != null) {
            sum += rot.høyre.sum;
        }
        sum += rot.verdi;
        rot.sum = sum;
    }

    static void settForelder(Trenode rot) {
        if (rot == null) {
            return;
        }

        settForelder(rot.venstre);
        settForelder(rot.høyre);

        if (rot.venstre != null) {
            rot.venstre.forelder = rot;
        }
        if (rot.høyre != null) {
            rot.høyre.forelder = rot;
        }
    }

    static void skrivUt(Trenode rot) {
        if (rot == null) {
            return;
        }

        Queue<Trenode> queue = new LinkedList<>();
        queue.offer(rot);

        while (!queue.isEmpty()) {
            Trenode node = queue.poll();
            System.out.println("Verdi\tSum\tForelder");
            System.out.print(node.verdi + "\t" + node.sum + "\t");
            if (node.forelder != null) {
                System.out.println(node.forelder.verdi);
            } else {
                System.out.println("*");
            }

            if (node.venstre != null) {
                queue.offer(node.venstre);
            }
            if (node.høyre != null) {
                queue.offer(node.høyre);
            }
        }
    }

    public static void main(String args[]) {
        Trenode rot =
                new Trenode(8,
                        new Trenode(4,
                                new Trenode(2, null, null),
                                new Trenode(6, null, null)),
                        new Trenode(16,
                                new Trenode(14,
                                        new Trenode(12, null, null),
                                        new Trenode(15, null, null)),
                                new Trenode(18, null,
                                        new Trenode(20, null, null))));

        settSum(rot);
        settForelder(rot);
        skrivUt(rot);
    }
}