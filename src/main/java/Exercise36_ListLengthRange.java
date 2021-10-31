import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/* Thanks to Rene Argento for the code below from:
 * https://github.com/reneargento/algorithms-sedgewick-wayne/blob/51825f5cc65efccbad692ca94b00c15e06100601/src/chapter3/section4/Exercise36_ListLengthRange.java*/
public class Exercise36_ListLengthRange {
    private void doExperiment() {
        int[] keySizes = {1000, 10000, 10000, 1000000};
        for (int keySizeIndex = 0; keySizeIndex < keySizes.length; keySizeIndex++) {
            int numberOfKeys = keySizes[keySizeIndex];
            StdOut.println("N = " + numberOfKeys);

            SeparateChainingHashTableFixedSize<Integer, Integer> separateChainingHashTableFixedSize = new
                    SeparateChainingHashTableFixedSize<>(numberOfKeys / 100);
            for (int i = 0; i < numberOfKeys; i++) {
                int randomIntegerKey = StdRandom.uniform(Integer.MAX_VALUE);
                separateChainingHashTableFixedSize.put(randomIntegerKey, randomIntegerKey);
            }
            int shortestListLength = Integer.MAX_VALUE;
            int longestListLenght = Integer.MIN_VALUE;

            for (SeparateChainingHashTableFixedSize.SequentialSearchSymbolTable list :
                    separateChainingHashTableFixedSize.symbolTable) {
                if (list != null) {
                    if (list.size() < shortestListLength) {
                        shortestListLength = list.size();
                    }
                    if (list.size() > longestListLenght) {
                        longestListLenght = list.size();
                    }
                }
            }
            StdOut.println("Length of the shortest list: " + shortestListLength);
            StdOut.println("Length of the longest list: " + longestListLenght);
        }
    }

    public static void main(String[] args) {
        Exercise36_ListLengthRange listLengthRange = new Exercise36_ListLengthRange();
        listLengthRange.doExperiment();
    }
}
