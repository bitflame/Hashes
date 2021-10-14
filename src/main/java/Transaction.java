import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.StdOut;

public class Transaction {
    private final String who;
    private final Date when;
    private final double amount;
    private int hash;

    Transaction(String who, Date when, double amount) {
        this.who = who;
        this.when = when;
        this.amount = amount;
        hash = -1;
    }

    public int hashCode() {
        int hash;
        if (this.hash != -1) {
            hash = this.hash;
            StdOut.println("Cache hit");
        } else {
            hash = 17;
            hash = 31 * hash + who.hashCode();
            hash = 31 * hash + when.hashCode();
            hash = 31 * hash + ((Double) amount).hashCode();
            System.out.println("Cache miss");
            this.hash = hash;
        }
        return hash;
    }
}
