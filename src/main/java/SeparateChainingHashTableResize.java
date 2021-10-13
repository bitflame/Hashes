import edu.princeton.cs.algs4.StdOut;

public class SeparateChainingHashTableResize<Key, Value> extends SeparateChainingHashTable<Key, Value> {
    // problem 3.4.18
    private int averageSize;
    private final int[] PRIMES = {
            1, 1, 3, 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
            32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
            8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
            536870909, 1073741789, 2147483647
    };
    private int lgM;

    public SeparateChainingHashTableResize(int initialSize, int averageSize) {
        super(initialSize, averageSize);
        this.size = initialSize;
        this.averageSize = averageSize;
        symboleTable = new SequentialSearchSymboleTable[size];
        for (int i = 0; i < size; i++) {
            symboleTable[i] = new SequentialSearchSymboleTable();
        }
        lgM = (int) (Math.log(size) / Math.log(2));
    }

    protected int hash(Key key) {
        int hash = key.hashCode() & 0x7fffffff;
        if (lgM < 26) {
            hash = hash % PRIMES[lgM + 5];
        }
        return hash % size;
    }

    public void resize(int newSize) {
        SeparateChainingHashTable<Key, Value> separateChainingHashTableTemp = new SeparateChainingHashTable<>(newSize, averageSize);

        for (Key key : keys()) {
            separateChainingHashTableTemp.put(key, get(key));
        }

        symboleTable = separateChainingHashTableTemp.symboleTable;
        size = separateChainingHashTableTemp.size;
        keysSize = separateChainingHashTableTemp.keysSize;
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        // each hash table is composed of a number of symbole tables
        int hashIndex = hash(key);
        int currentSize = symboleTable[hashIndex].size;
        symboleTable[hashIndex].put(key, value);
        if (currentSize < symboleTable[hashIndex].size) {
            keysSize++;
        }
        if (getLoadFactor() >= averageSize) {
            StdOut.println("Resize - doubling hash table size.");
            resize(size * 2);
            lgM++;
        }
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete can not be null");
        if (isEmpty() || !contains(key)) {
            return;
        }
        symboleTable[hash(key)].delete(key);
        keysSize--;
        if (size > 1 && getLoadFactor() <= averageListSize / (double) 4) {
            StdOut.println("Resize - shrinking hash table size");
            resize(size / 2);
            lgM--;
        }
    }

    public static void main(String[] args) {
        SeparateChainingHashTableResize<Integer, Integer> sCTResize = new SeparateChainingHashTableResize<>(5,2);
        for (int i = 0; i < 20; i++) {
            sCTResize.put(i, i);
        }
        StdOut.println("Expected: Resize - doubling hash table size 2x");
        for (int i = 0; i < 10; i++) {
            sCTResize.delete(i);
        }
        StdOut.println("Expected: Resize - shrinking hash table size.");
        for (int i = 0; i < 15; i++) {
            sCTResize.delete(i);
        }
        StdOut.println("Expected: Resize - shrinking hash table size.");
    }
}
