import edu.princeton.cs.algs4.StdOut;

public class Exercise25 {
    public class LinearProbing<Key, Value> {
        int size;
        int keysSize;
        Key[] keys;
        Value[] values;
        int[] primes = {31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381, 32749, 65521, 131071, 262139, 524287,
                1048573, 2097143, 41943011, 8388593, 16777216, 33554393, 67108859, 134217689, 268435399, 536870909,
                1073741789, 2147483647};
        int lgM;

        public LinearProbing(int initialSize) {
            size = initialSize;
            keys = (Key[]) new Object[size];
            values = (Value[]) new Object[size];
        }

        public int size() {
            return keysSize;
        }

        public void put(Key key, Value value) {
            if (key == null) throw new IllegalArgumentException("The key argument to put() can not be null.");
            if (value == null) delete(key);
            if (keysSize > size / (double) 2) {
                resize(size * 2);
                lgM++;
            }
            int i = 0;
            for (i = hash(key); keys[i] != null; i = (i + 1) % size) {
                if (keys[i] == key) {
                    values[i] = value;
                    return;
                }
            }
            keys[i] = key;
            values[i] = value;
            keysSize++;
        }

        public void resize(int newSize) {
            Key[] newKeysArray = (Key[]) new Object[newSize];
            Value[] newValuesArray = (Value[]) new Object[newSize];
            for (int i = 0; i < keys.length; i++) {
                newKeysArray[i] = keys[i];
                newValuesArray[i] = values[i];
            }
            keys = newKeysArray;
            values = newValuesArray;
            size = newSize;
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("The key argument to get() cannot be null");
            for (int i = hash(key); keys[i] != null; i = (i + 1) % size) {
                if (keys[i].equals(key)) return values[i];
            }
            return null;
        }

        public int hash(Key key) {
            int h = key.hashCode() & 0x7fffffff;
            if (lgM < 26) {
                h = h % primes[lgM + 5];
            }
            h = (h) % size;
            return h;
        }

        public boolean contains(Key key) {
            return get(key) != null;
        }

        public void delete(Key key) {
            if (!contains(key)) return;
            if (key == null) throw new
                    IllegalArgumentException("key argumet to delete() can not be null.");
            int i = hash(key);
            while (!key.equals(keys[i])) {
                i = (i + 1) % size;
            }
            values[i] = null;
            keys[i] = null;
            i = (i + 1) % size;
            while (keys[i]!=null) {
                Key keyToRedo = keys[i];
                Value valueToRedo = values[i];
                keys[i] = null;
                values[i] = null;
                keysSize--;
                put(keyToRedo, valueToRedo);
                i = (i + 1) % size;
            }
            keysSize--;
            if (keysSize>1 && keysSize< (double) size/8){
                resize(size/2);
                lgM--;
            }
        }
    }

    public static void main(String[] args) {
        Exercise25 e = new Exercise25();
        LinearProbing<Integer, Integer> l = e.new LinearProbing<>(10);

        for (int i = 0; i < 20; i++) {
            l.put(i, i / 2);
        }

        for (int i = 0; i < 10; i++) {
            l.delete(i);
        }
        StdOut.println("Just put in 20 items, removed 10, and have :"+l.size());
    }
}
