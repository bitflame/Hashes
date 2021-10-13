import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class LinearProbingHashTableAvgSearchMissCost<Key, Value> extends LinearProbingHashST<Key, Value> {
    public LinearProbingHashTableAvgSearchMissCost(int size) {
        super(size);
    }

    // Average cost of search miss 1/2 * (1 + (1/(1-a)^2))
    public double getAverageCostOfSearchMiss() {
        double loadFactor = getLoadFactor();
        return 0.5 * (1 + (1 / Math.pow(1 - loadFactor, 2)));
    }

    public static void main(String[] args) {
        LinearProbingHashTableAvgSearchMissCost<Integer, Integer> l =
                new LinearProbingHashTableAvgSearchMissCost<>(1000000);
        for (int i = 0; i < 500000; i++) {
            int randomkey = StdRandom.uniform(Integer.MAX_VALUE);
            l.put(randomkey, randomkey);
        }
        StdOut.printf("Average cost of search miss: %.2f", l.getAverageCostOfSearchMiss());
    }
}
