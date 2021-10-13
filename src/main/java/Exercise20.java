import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import org.spockframework.runtime.SpecUtil;

import java.util.Arrays;

public class Exercise20 {
    // Class to enable hash collision test
    private class TestKey {
        int key;

        TestKey(int key) {
            this.key = key;
        }

        @Override
        public int hashCode() {
            return key % 4;
        }
    }

    private class LinearProbingHashST<Key, Value> {
        private int keysSize;
        private int size = 16;
        private Key[] keys;
        private Value[] vals;
        private int numberOfCompares;
        int[] primes = {31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381, 32749, 65521, 131071, 262139, 524287,
                1048573, 2097143, 41943011, 8388593, 16777216, 33554393, 67108859, 134217689, 268435399, 536870909,
                1073741789, 2147483647};
        int lgM;

        public LinearProbingHashST(int size) {
            this.size = size;
            keys = (Key[]) new Object[size];
            vals = (Value[]) new Object[size];
            lgM = (int) (Math.log(size) / Math.log(2));
        }

        private int size() {
            return keysSize;
        }

        private boolean isEmpty() {
            return keysSize == 0;
        }

        private int hash(Key key) {
            int hash = (key.hashCode() & 0x7fffffff);
            if (lgM < 26) hash = hash % primes[lgM + 5];
            return hash % size;
        }

        protected double getLoadFactor() {
            return keysSize / (double) size;
        }

        private void resize(int newSize) {
            LinearProbingHashST<Key, Value> t;
            t = new LinearProbingHashST<Key, Value>(newSize);
            for (int i = 0; i < size; i++) {
                if (keys[i] != null) t.put(keys[i], vals[i]);
            }
            keys = t.keys;
            vals = t.vals;
            size = t.size;
            numberOfCompares = t.numberOfCompares;
        }

        private int getAverageCostOfSearchHit() {
            return numberOfCompares / keysSize;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            if (val == null) {
                delete(key);
                return;
            }
            if (keysSize >= size / (double) 2) {
                resize(2 * size);
                lgM++;
            }
            int numberOfComparesBeforeFindingKey = 1;
            int i;
            for (i = hash(key); keys[i] != null; i = (i + 1) % size) {
                if (keys[i].equals(key)) {
                    numberOfComparesBeforeFindingKey++;
                    vals[i] = val;
                    return;
                }
            }
            numberOfCompares += numberOfComparesBeforeFindingKey;
            keys[i] = key;
            vals[i] = val;
            keysSize++;
        }


        public Value get(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to get() cannot be null");
            }
            StdOut.println("Calling get() ");
            for (int i = hash(key); keys[i] != null; i = (i + 1) % size)
                if (keys[i].equals(key)) {
                    return vals[i];
                }
            return null;
        }

        public boolean contains(Key key) {
            return get(key) != null;
        }

        public void delete(Key key) {
            if (!contains(key)) return;
            int i = hash(key);
            while (!key.equals(keys[i]))
                i = (i + 1) % size;
            keys[i] = null;
            vals[i] = null;
            i = (i + 1) % size;
            while (keys[i] != null) {
                Key keyToRedo = keys[i];
                Value valueToRedo = vals[i];
                keys[i] = null;
                vals[i] = null;
                keysSize--;
                put(keyToRedo, valueToRedo);
                i = (i + 1) % size;
            }
            keysSize--;
            if (keysSize > 1 || keysSize <= size / (double) 8) resize(size / 2);
        }

        private void printHashTable() {
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] != null) StdOut.println("Here is key: " + keys[i] + " Here is it's hash: " + i);
            }
        }

        private Iterable<Key> keys() {
            Queue<Key> keyset = new Queue<>();
            for (Object key : keys) {
                if (key != null) keyset.enqueue((Key) key);
            }
            if (!keyset.isEmpty() && keyset.peek() instanceof Comparable) {
                Key[] keysToBeSorted = (Key[]) new Comparable[keyset.size()];
                for (int i = 0; i < keysToBeSorted.length; i++) {
                    keysToBeSorted[i] = keyset.dequeue();
                }
                Arrays.sort(keysToBeSorted);
                for (Key key : keysToBeSorted) {
                    keyset.enqueue(key);
                }
            }
            return keyset;
        }
    }

    public static void main(String[] args) {
        Exercise20 e = new Exercise20();
        LinearProbingHashST<TestKey, Integer> l = e.new LinearProbingHashST<>(20);
        l.put(e.new TestKey(5), 5);
        StdOut.println(l.getAverageCostOfSearchHit() + " Expected: 1");
    }
}

