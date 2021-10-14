import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Exercise26_LazyDeleteLinearProbing {
    private class LinearProbingHashTableLazyDelete<Key, Value> extends LinearProbingHashST<Key, Value> {
        private int tombstoneItemCount;

        public LinearProbingHashTableLazyDelete(int size) {
            super(size);
        }

        private void resize(int newSize) {
            StdOut.println("Deleting tombstone items");
            tombstoneItemCount = 0;
            LinearProbingHashTableLazyDelete<Key, Value> tempHashTable = new
                    LinearProbingHashTableLazyDelete<>(newSize);
            for (int i = 0; i < size; i++) {
                if (vals[i] != null) {
                    tempHashTable.put(keys[i], vals[i]);
                }
            }
            keys = tempHashTable.keys;
            vals = tempHashTable.vals;
            size = tempHashTable.size;
        }

        public void put(Key key, Value value) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
            if (value == null) {
                delete(key);
                return;
            }
            if (keysSize + tombstoneItemCount >= size / (double) 2) {
                resize(size * 2);
                lgM++;
            }
            int tableIndex;
            for (tableIndex = hash(key); keys[tableIndex] != null; tableIndex = (tableIndex + 1) % size) {
                if (keys[tableIndex].equals(key)) {
                    if (vals[tableIndex] == null) {
                        tombstoneItemCount--;
                        keysSize++;
                    }
                    vals[tableIndex] = value;
                    return;
                }
            }
            keys[tableIndex] = key;
            vals[tableIndex] = value;
            keysSize++;
        }

        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException("Argument to delete() can not be null");
            if (!contains(key)) {
                return;
            }
            int tableIndex = hash(key);
            while (!keys[tableIndex].equals(key)) {
                tableIndex = (tableIndex + 1) % size;
            }
            keys[tableIndex] = null;
            vals[tableIndex] = null;
            keysSize--;
            tableIndex = (tableIndex + 1) % size;

            while (keys[tableIndex] != null) {
                Key keyToRedo = keys[tableIndex];
                Value valueToRedo = vals[tableIndex];
                keys[tableIndex] = null;
                vals[tableIndex] = null;
                keysSize--;

                put(keyToRedo, valueToRedo);
                tableIndex = (tableIndex + 1) % size;
            }
            if (keysSize > 1 && keysSize <= size / (double) 8) {
                resize(size / 2);
                lgM--;
            }
        }

        public void lazyDelete(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to delete() cannot be null");
            }
            if (!contains(key)) {
                return;
            }
            int tableIndex = hash(key);
            while (!keys[tableIndex].equals(key)) {
                tableIndex = (tableIndex + 1) % size;
            }
            vals[tableIndex] = null;
            tombstoneItemCount++;
            keysSize--;
            if (keysSize <= (size / (double) 8)) {
                resize(size / 2);
                lgM--;
            }
        }
    }

    public static void main(String[] args) {
        Exercise26_LazyDeleteLinearProbing l = new Exercise26_LazyDeleteLinearProbing();
        LinearProbingHashTableLazyDelete<Integer, Integer> lP = l.new LinearProbingHashTableLazyDelete<>(16);
        for (int i = 0; i < 4; i++) {
            lP.put(i, i);
        }
        StdOut.println("Size: " + lP.size() + " Expected: 4");
        StdOut.println("Lazy delete 2");
        lP.lazyDelete(2);
        StdOut.println("Size: " + lP.size() + " Expected: 3");
        StdOut.println("Lazy delete 0");
        lP.lazyDelete(0);
        StdOut.println("Expected Deleting tombstone items");
        StdOut.println("Size: " + lP.size() + " Expected: 2");
        for (int i = 0; i < 8; i++) {
            lP.put(i, i);
        }
        StdOut.println("\nLazy delete 3");
        lP.lazyDelete(3);

        StdOut.println("\nLazy delete 4");
        lP.lazyDelete(4);

        StdOut.println("\nLazy delete 5");
        lP.lazyDelete(5);

        StdOut.println("Put 3");
        lP.put(3, 3);

        StdOut.println("Lazy delete 6");
        lP.lazyDelete(6);

        StdOut.println("Lazy delete 7");
        lP.lazyDelete(7);

        StdOut.println("Expected: Deleting tombstone items");
        StdOut.println("Size: " + lP.size() + " Expected: 2");
    }
}
