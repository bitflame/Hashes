import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/* Thanks to Rene Argento: https://github.com/reneargento for the following code */
public class Exercise34_HashCost {
    private interface HashTable<Key, Value> {
        long[] timeSpent = new long[2];

        Value get(Key key);

        void put(Key key, Value value);

        void delete(Key key);
    }

    private class SeparateChainingHashTableHashCost<Key, Value> implements HashTable<Key, Value> {
        class SequentialSearchSymbolTable<Key, Value> {
            private class Node {
                Key key;
                Value value;
                Node next;

                public Node(Key key, Value value, Node next) {
                    this.key = key;
                    this.value = value;
                    this.next = next;
                }
            }

            private Node first;
            private int size;

            public int size() {
                return size;
            }

            public boolean isEmpty() {
                return size == 0;
            }

            public boolean contains(Key key) {
                return get(key) != null;
            }

            public Value get(Key key) {
                for (Node node = first; node != null; node = node.next) {
                    long initTime = System.nanoTime();
                    boolean isEqualKeys = key.equals(node.key);
                    timeSpent[1] += System.nanoTime() - initTime;
                    if (isEqualKeys) {
                        return node.value;
                    }
                }
                return null;
            }

            public void put(Key key, Value value) {
                for (Node node = first; node != null; node = node.next) {
                    long initTime = System.nanoTime();
                    boolean isEqualKeys = key.equals(node.key);
                    timeSpent[1] += System.nanoTime() - initTime;

                    if (isEqualKeys) {
                        node.value = value;
                        return;
                    }
                }
                first = new Node(key, value, first);
                size++;
            }

            public void delete(Key key) {
                long initTime = System.nanoTime();
                boolean isEqualKeys = first.key.equals(key);
                timeSpent[1] += System.nanoTime() - initTime;
                if (isEqualKeys) {
                    first = first.next;
                    size--;
                    return;
                }

                for (Node node = first; node != null; node = node.next) {
                    if (node.next != null) {
                        initTime = System.nanoTime();
                        isEqualKeys = node.next.key.equals(key);
                        timeSpent[1] += System.nanoTime() - initTime;

                        if (isEqualKeys) {
                            node.next = node.next.next;
                            size--;
                            return;
                        }
                    }
                }
            }

            public Iterable<Key> keys() {
                Queue<Key> keys = new Queue<>();
                for (Node node = first; node != null; node = node.next) {
                    keys.enqueue(node.key);
                }
                return keys;
            }
        }

        private int averageListSize;

        private int size;
        private int keysSize;
        DoubleProbingHashTable[] symbolTable;

        private static final int DEFAULT_HASH_TABLE_SIZE = 997;
        private static final int DEFAULT_AVERAGE_LIST_SIZE = 5;

        //The largest prime <= 2^i for i = 1 to 31
        //Used to distribute keys uniformly in the hash table after resizes
        //PRIMES[n] = 2^k - Ak where k is the power of 2 and Ak is the value to subtract to reach the previous prime number
        private final int[] PRIMES = {
                1, 1, 3, 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
                32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
                8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
                536870909, 1073741789, 2147483647
        };

        //The lg of the hash table size
        //Used in combination with PRIMES[] to distribute keys uniformly in the hash function after resizes
        private int lgM;

        public SeparateChainingHashTableHashCost(int initialSize, int averageListSize) {
            this.size = initialSize;
            this.averageListSize = averageListSize;
            symbolTable = new SequentialSearchSymbolTable[size];
            for (int i = 0; i < size; i++) {
                symbolTable[i] = new SequentialSearchSymbolTable();
            }
            lgM = (int) (Math.log(size) / Math.log(2));
        }

        public int size() {
            return keysSize;
        }

        public boolean isEmpty() {
            return keysSize == 0;
        }

        private int hash(Key key) {
            int hash = key.hashCode() & 0x7fffffff;
            if (lgM < 26) {
                hash = hash % PRIMES[lgM + 5];
            }
            return hash % size;
        }

        private double getLoadFactor() {
            return ((double) keysSize) / (double) size;
        }

        public boolean contains(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to contains(0 cannot be null");
            }
            return get(key) != null;
        }

        public void resize(int newSize) {
            SeparateChainingHashTableHashCost<Key, Value> separateChainingHashTableTemp =
                    new SeparateChainingHashTableHashCost<>(newSize, averageListSize);

            for (Key key : keys()) {
                separateChainingHashTableTemp.put(key, get(key), false);
            }
            symbolTable = separateChainingHashTableTemp.symbolTable;
            size = separateChainingHashTableTemp.size;
            keysSize = separateChainingHashTableTemp.keysSize;
        }

        public Value get(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to get() cannot be null");
            }
            resetTimers();
            long initTime = System.nanoTime();
            int hash = hash(key);
            timeSpent[0] += System.nanoTime() - initTime;

            return (Value) symbolTable[hash].get(key);
        }

        public void put(Key key, Value value) {
            put(key, value, true);
        }

        public void put(Key key, Value value, boolean resetTimers) {
            if (value == null) {
                delete(key);
                return;
            }
            if (resetTimers) {
                resetTimers();
            }
            long initTime = System.nanoTime();
            int hashIndex = hash(key);
            timeSpent[0] += System.nanoTime() - initTime;

            int currentSize = symbolTable[hashIndex].size;
            symbolTable[hashIndex].put(key, value);

            if (currentSize < symbolTable[hashIndex].size) {
                keysSize++;
            }

            if (getLoadFactor() > averageListSize) {
                resize(size * 2);
                lgM++;
            }
        }

        public void delete(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to delete() cannot be null.");
            }
            if (isEmpty() || !contains(key)) {
                return;
            }
            resetTimers();

            long initTime = System.nanoTime();
            int hash = hash(key);
            timeSpent[0] += System.nanoTime() - initTime;

            symbolTable[hash].delete(key);
            keysSize--;
            if (size > 1 && getLoadFactor() <= averageListSize / (double) 4) {
                resize(size / 2);
                lgM--;
            }
        }

        private void resetTimers() {
            timeSpent[0] = 0;
            timeSpent[1] = 0;
        }

        public Iterable<Key> keys() {
            Queue<Key> keys = new Queue<>();
            for (SequentialSearchSymbolTable<Key, Value> sequentialSearchST : symbolTable) {
                for (Key key : sequentialSearchST.keys()) {
                    keys.enqueue(key);
                }
            }
            if (!keys.isEmpty() && keys.peek() instanceof Comparable) {
                Key[] keystoBeSorted = (Key[]) new Comparable[keys.size()];
                for (int i = 0; i < keystoBeSorted.length; i++) {
                    keystoBeSorted[i] = keys.dequeue();
                }

                Arrays.sort(keystoBeSorted);

                for (Key key : keystoBeSorted) {
                    keys.enqueue(key);
                }
            }
            return keys;
        }
    }

    private class LinearProbingHashTableHashCost<Key, Value> implements HashTable<Key, Value> {
        private int keysSize;
        private int size;
        private Key[] keys;
        private Value[] values;

        //The largest prime <= 2^i for i = 1 to 31
        //Used to distribute keys uniformly in the hash table after resizes
        //PRIMES[n] = 2^k - Ak where k is the power of 2 and Ak is the value to subtract to reach the previous prime number
        private final int[] PRIMES = {
                1, 1, 3, 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
                32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
                8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
                536870909, 1073741789, 2147483647
        };
        //The lg of the hash table size
        //Used in combination with PRIMES[] to distribute keys uniformly in the hash function after resizes
        private int lgM;

        LinearProbingHashTableHashCost(int size) {
            this.size = size;
            keys = (Key[]) new Object[size];
            values = (Value[]) new Object[size];

            lgM = (int) (Math.log(size) / Math.log(2));
        }

        public int size() {
            return keysSize;
        }

        public int hash(Key key) {
            int hash = key.hashCode() & 0x7fffffff;
            if (lgM < 26) {
                hash = hash % PRIMES[lgM + 5];
            }
            return hash % size;
        }

        private void resize(int newSize) {
            LinearProbingHashTableHashCost<Key, Value> tempHashTable = new LinearProbingHashTableHashCost<>(newSize);

            for (int i = 0; i < size; i++) {
                if (keys[i] != null) {
                    tempHashTable.put(keys[i], values[i]);
                }
            }
            keys = tempHashTable.keys;
            values = tempHashTable.values;
            size = tempHashTable.size;
        }

        public boolean contains(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to contains() cannot be null");
            }
            return get(key) != null;
        }

        public Value get(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to get() cannot be null");
            }
            resetTimers();

            long initTime = System.nanoTime();
            int hash = hash(key);
            timeSpent[0] += System.nanoTime() - initTime;

            for (int tableIndex = hash; keys[tableIndex] != null; tableIndex = (tableIndex + 1) % size) {
                initTime = System.nanoTime();
                boolean isEqualKeys = keys[tableIndex].equals(key);
                timeSpent[1] += System.nanoTime() - initTime;
                if (isEqualKeys) {
                    return values[tableIndex];
                }
            }
            return null;
        }

        public void put(Key key, Value value) {
            put(key, value, true);
        }

        public void put(Key key, Value value, boolean resetTimer) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
            if (value == null) {
                delete(key);
                return;
            }
            if (keysSize >= size / (double) 2) {
                resize(size * 2);
                lgM++;
            }
            if (resetTimer) {
                resetTimers();
            }
            long initTime = System.nanoTime();
            int tableIndex = hash(key);
            timeSpent[0] += System.nanoTime() - initTime;
            for (; keys[tableIndex] != null; tableIndex = (tableIndex + 1) % size) {
                initTime = System.nanoTime();
                boolean isEqualKeys = keys[tableIndex].equals(key);
                timeSpent[1] += System.nanoTime() - initTime;
                if (isEqualKeys) {
                    values[tableIndex] = value;
                    return;
                }
                keys[tableIndex] = key;
                values[tableIndex] = value;
                keysSize++;
            }
        }

        public void delete(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to delete() cannot be null");
            }
            if (!contains(key)) {
                return;
            }
            resetTimers();
            long initTime = System.nanoTime();
            int tableIndex = hash(key);
            timeSpent[0] += System.nanoTime() - initTime;

            initTime = System.nanoTime();
            boolean isNotEqualKeys = !keys[tableIndex].equals(key);
            timeSpent[1] += System.nanoTime() - initTime;

            while (isNotEqualKeys) {
                tableIndex = (tableIndex + 1) % size;

                initTime = System.nanoTime();
                isNotEqualKeys = !keys[tableIndex].equals(key);
                timeSpent[1] += System.nanoTime() - initTime;
            }

            keys[tableIndex] = null;
            values[tableIndex] = null;
            keysSize--;

            tableIndex = (tableIndex + 1) % size;
            while (keys[tableIndex] != null) {
                Key keyToRedo = keys[tableIndex];
                Value valueToRedo = values[tableIndex];
                keys[tableIndex] = null;
                values[tableIndex] = null;
                keysSize--;

                put(keyToRedo, valueToRedo, false);
                tableIndex = (tableIndex + 1) % size;
            }
            if (keysSize > 1 && keysSize <= size / (double) 8) {
                resize(size / 2);
                lgM--;
            }
        }
        public Iterable<Key> keys() {
            Queue<Key> keySet = new Queue<>();
            for (Object key: keys) {
                if (key!=null) {
                    keySet.enqueue((Key)key);
                }
            }
            if (!keySet.isEmpty() && keySet.peek() instanceof Comparable) {
                Key[] keysToBeSorted = (Key[]) new Comparable[keySet.size()];
                for (int i = 0; i < keysToBeSorted.length; i++) {
                    keysToBeSorted[i]=keySet.dequeue();
                }
                Arrays.sort(keysToBeSorted);

                for (Key key: keysToBeSorted) {
                    keySet.enqueue(key);
                }
            }
            return keySet;
        }
        private void resetTimers() {
            timeSpent[0]=0;
            timeSpent[1]=0;
        }
    }
    enum InputType {
        INTEGER, DOUBLE, STRING;
    }

    public static void main(String[] args) {
        Exercise34_HashCost hashCost = new Exercise34_HashCost();
        StdOut.println("Hash table with Separate Chaining\n");

        StdOut.println("Integer keys");
        HashTable<Integer, Integer> separateChainingTableIntegerHashCost =
                hashCost.new SeparateChainingHashTableHashCost<>();
        hashCost.computeHashToCompareTimeRatios(separateChainingHashTableIntegerHashCost, InputType.INTEGER);
        //todo -- need to type the rest and get rid of the errors
    }
}
