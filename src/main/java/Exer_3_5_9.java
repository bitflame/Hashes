public class Exer_3_5_9 {
    /* Modify BST to keep duplicate keys - This was my attempt, but ti is not right in comparison to what
    Rene has */
    class BSTDupKeys<Key extends Comparable<Key>, Value extends Comparable<Value>> {
        Node root;
        int N;

        private class Node<Key, Value> {
            Key key;
            Value value;
            Node next, rt, lf;
            int N;

            public Node(Key key, Value val, int N) {
                this.key = key;
                this.value = val;
                this.N = N;
            }
        }

        public int size() {
            return size(root);
        }

        private int size(Node x) {
            return x.N;
        }

        public Key get(Value value) {
            return get(root, value);
        }

        private Key get(Node x, Value value) {
            if (x == null) return null;
            int cmp = value.compareTo((Value) x.value);
            if (cmp > 0) x.key = get(x.rt, value);
            else if (cmp < 0) x.key = get(x.lf, value);
            return (Key) x.key;
        }

        public void put(Key key, Value value) {
            root = put(root, key, value);
        }

        private Node put(Node x, Key key, Value value) {
            if (x == null) return new Node(key, value, 1);
            int cmp = value.compareTo((Value) x.value);
            if (cmp > 0) x = put(x.rt, key, value);
            else if (cmp < 0) x = put(x.lf, key, value);
            else x.key = key;
            x.N = size(x.lf) + size(x.rt) + 1;
            return x;
        }
    }

}
