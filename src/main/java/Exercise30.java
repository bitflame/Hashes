import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Exercise30 {
    class SeparateChainingHashTableChiSquare<Key, Value> extends SeparateChainingHashTable<Key, Value> {
        SeparateChainingHashTableChiSquare(int initialSize, int averageListSize) {
            super(initialSize, averageListSize);
        }
        double computeChiSquareStatistic() {
            double sizeOverKeyCount = size / (double) keysSize;
            double keyCountOverSize = keysSize / (double) size;

            int [] keyCountByHashValue = new int[symboleTable.length];
            for (int bucket = 0; bucket < symboleTable.length; bucket++) {
                keyCountByHashValue[bucket] = symboleTable[bucket].size;
            }

            double fSum = 0;
            for (int i = 0; i < keyCountByHashValue.length; i++) {
                fSum += Math.pow(keyCountByHashValue[i] - keyCountOverSize, 2);
            }
            return sizeOverKeyCount * fSum;
        }
    }

    public static void main(String[] args) {
        Exercise30 e = new Exercise30();
        SeparateChainingHashTableChiSquare<Integer, Integer> separateChainingHashTableChiSquare =
                e.new SeparateChainingHashTableChiSquare<>(100,20);
        for (int key = 0; key < 100000; key++) {
            int randIntegerKey = StdRandom.uniform(Integer.MAX_VALUE);
            separateChainingHashTableChiSquare.put(randIntegerKey, randIntegerKey);
        }
        double lowerBound = separateChainingHashTableChiSquare.size - Math.sqrt(separateChainingHashTableChiSquare.size);
        double upperBound = separateChainingHashTableChiSquare.size + Math.sqrt(separateChainingHashTableChiSquare.size);
        double constant = separateChainingHashTableChiSquare.keysSize / (double) separateChainingHashTableChiSquare.size;
        double probability = 1 - (1/ constant);

        double chiSquareStatisticValue = separateChainingHashTableChiSquare.computeChiSquareStatistic();

        StdOut.printf("M - sqrt(M) = "+ String.format("%.2f", lowerBound));
        StdOut.printf("M + sqrt(M) = "+ String.format("%.2f", upperBound));
        StdOut.println("Chi square statistics = "+ String.format("%.2f", chiSquareStatisticValue));
        StdOut.println("Probability = " + String.format("%.2f%%", probability  * 100));

        StdOut.print("Produces random values: ");
        if (lowerBound <= chiSquareStatisticValue && chiSquareStatisticValue <= upperBound) {
            StdOut.println("True");
        } else {
            StdOut.print("False");
        }
    }
}
