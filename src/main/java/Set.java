import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Set {
    class SeparateChainingHashSt<Key, Value> {
        private int N;  // number of key-value pairs
        private int M;  // Hash table size
        private SequentialSearchST<Key, Value>[] st;

        public SeparateChainingHashSt(int M) {
            this.M = M;
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
            for (int i = 0; i < M; i++) {
                st[i] = new SequentialSearchST<>();
            }
        }

        public SeparateChainingHashSt() {
            this(997);
        }


        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % M;
        }

        public Value get(Key key) {
            return (Value) st[hash(key)].get(key);
        }

        public void put(Key key, Value value) {
            st[hash(key)].put(key, value);
        }

        public Iterable<Key> keys() {
            Queue<Key> keys = new Queue<>();
            for (SequentialSearchST sequentialSearchST : st) {
                for (Object obj : sequentialSearchST.keys()) {
                    keys.enqueue((Key) obj);
                }
            }
            if (!keys.isEmpty() && keys.peek() instanceof Comparable) {
                Key[] keysToBeSorted = (Key[]) new Comparable[keys.size()];
                for (int i = 0; i < keysToBeSorted.length; i++) {
                    keysToBeSorted[i] = keys.dequeue();
                }
                Arrays.sort(keysToBeSorted);
                for (Key k : keysToBeSorted) {
                    keys.enqueue(k);
                }
            }
            return keys;
        }
    }

    class SequentialSearchST<Key, Value> {
        class Node<Key, Value> {
            Node(Key k, Value val) {
                this.key = k;
                this.value = val;
            }

            Key key;
            Value value;
            Node next;
        }

        Node first;

        public void put(Key key, Value value) {
            if (first == null) {
                first = new Node(key, value);
            }
            if (first != null) {
                first.next = first;
                first = new Node(key, value);
            }
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("The key to get() has to be valid");
            for (Node node = first; node != null; node = node.next) {
                if (key.equals(node.key)) return (Value) node.value;
            }
            return null;
        }

        public Iterable<Key> keys() {
            Queue<Key> keys = new Queue<>();
            for (Node node = first; node != null; node = node.next) {
                keys.enqueue((Key) node.key);
            }
            return keys;
        }
    }

    public static void main(String[] args) {
        Set s = new Set();
        Set.SeparateChainingHashSt<Integer, String> separateChainingHashStSET =
                s.new SeparateChainingHashSt<Integer, String>();
        separateChainingHashStSET.put(1,"test");
        StdOut.println("Expecting the word: \"test\" "+separateChainingHashStSET.get(1));
    }
}
