import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Exer_3_5_4 {
    class HashSTin {
        
            private int keysSize;
            private int size = 16;
            private int[] keys;
            private int[] vals;
            private int numberOfCompares;
            int[] primes = {31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381, 32749, 65521, 131071, 262139, 524287,
                    1048573, 2097143, 41943011, 8388593, 16777216, 33554393, 67108859, 134217689, 268435399, 536870909,
                    1073741789, 2147483647};
            int lgM;

            public HashSTin(int size) {
                this.size = size;
                keys = new int[size];
                vals =  new int[size];
                lgM = (int) (Math.log(size) / Math.log(2));
            }

            private int size() {
                return keysSize;
            }

            private boolean isEmpty() {
                return keysSize == 0;
            }

            private int hash(Integer key) {
                int hash = (key.hashCode() & 0x7fffffff);
                if (lgM < 26) hash = hash % primes[lgM + 5];
                return hash % size;
            }

            protected double getLoadFactor() {
                return keysSize / (double) size;
            }

            private void resize(int newSize) {
                HashSTin t;
                t = new HashSTin(newSize);
                for (int i = 0; i < size; i++) {
                    if (keys[i] != 0) t.put(keys[i], vals[i]);
                }
                keys = t.keys;
                vals = t.vals;
                size = t.size;
                numberOfCompares = t.numberOfCompares;
            }

            private int getAverageCostOfSearchHit() {
                return numberOfCompares / keysSize;
            }

            public void put(int key, int val) {
                if (key == 0) throw new IllegalArgumentException("int cannot be null");
                if (val == 0) {
                    delete(key);
                    return;
                }
                if (keysSize >= size / (double) 2) {
                    resize(2 * size);
                    lgM++;
                }
                int numberOfComparesBeforeFindingint = 1;
                int i;
                for (i = hash(key); keys[i] != 0; i = (i + 1) % size) {
                    numberOfComparesBeforeFindingint++;
                    if (keys[i]==(key)) {
                        vals[i] = val;
                        return;
                    }
                }
                numberOfCompares += numberOfComparesBeforeFindingint;
                keys[i] = key;
                vals[i] = val;
                keysSize++;
            }


            public int get(int key) {
                if (key == 0) {
                    throw new IllegalArgumentException("Argument to get() cannot be null");
                }
                StdOut.println("Calling get() ");
                for (int i = hash(key); keys[i] != 0; i = (i + 1) % size)
                    if (keys[i]==(key)) {
                        return vals[i];
                    }
                return 0;
            }

            public boolean contains(int key) {
                return get(key) != 0;
            }

            public void delete(int key) {
                if (!contains(key)) return;
                int i = hash(key);
                while (key!=(keys[i]))
                    i = (i + 1) % size;
                keys[i] = 0;
                vals[i] = 0;
                i = (i + 1) % size;
                while (keys[i] != 0) {
                    int keyToRedo = keys[i];
                    int valueToRedo = vals[i];
                    keys[i] = 0;
                    vals[i] = 0;
                    keysSize--;
                    put(keyToRedo, valueToRedo);
                    i = (i + 1) % size;
                }
                keysSize--;
                if (keysSize > 1 || keysSize <= size / (double) 8) resize(size / 2);
            }

            private void printHashTable() {
                for (int i = 0; i < keys.length; i++) {
                    if (keys[i] != 0) StdOut.println("Here is key: " + keys[i] + " Here is it's hash: " + i);
                }
            }

            private Iterable<Integer> keys() {
                Queue<Integer> keyset = new Queue<>();
                for (Object key : keys) {
                    if (key != null) keyset.enqueue((int) key);
                }
                if (!keyset.isEmpty() && keyset.peek() instanceof Comparable) {
                    int[] keysToBeSorted = new int [keyset.size()];
                    for (int i = 0; i < keysToBeSorted.length; i++) {
                        keysToBeSorted[i] = keyset.dequeue();
                    }
                    Arrays.sort(keysToBeSorted);
                    for (int key : keysToBeSorted) {
                        keyset.enqueue(key);
                    }
                }
                return keyset;
            }
        
    }

    class HashSTdouble {
        
    }
}
