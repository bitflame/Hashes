public class Exer3_5_10 {
    /* Modify red black BSTs to hold duplicate keys. return any value associated with the given key for get(), and
     * remove all the nodes in the tree that have keys equal to the given key for delete()*/
    private class RedBalackBSTWithDupKeys<Key extends Comparable<Key>, Value> {
        private static final boolean RED = true;
        private static final boolean BLACK = false;
        private Node root;

        private class Node<Key, Value> {
            Key key;
            Value value;
            Node lf, rt;
            int N;
            boolean color;

            Node(Key key, Value val, int N, boolean color) {
                this.key = key;
                this.value = val;
                this.N = N;
                this.color = color;
            }
        }

        public Value get(Key key) {
            return get(root, key);
        }

        public Value get(Node x, Key key) {
            // if there is a duplicate of key in the rigt subtree, it should be the min()
        }

        public void put(Key key, Value value) {
            root = put(root, key, value);
            root.color = BLACK;
        }

        private Node put(Node h, Key key, Value value) {
            if (h == null) return new Node(key, value, 1, RED);
            int cmp = key.compareTo((Key) h.key);
            if (cmp <= 0) h.lf = put(h.lf, key, value);
            if (cmp > 0) h.rt = put(h.rt, key, value);
            if (h.lf != null && h.lf.lf != null & isRed(h.lf) && isRed(h.lf.lf)) h = rotateRight(h);
            if (h.lf != null & h.lf.rt != null & isRed(h.lf) && isRed(h.lf.rt)) h = rotateLeft(h);
            if (h.lf != null & h.rt != null & isRed(h.lf) & isRed(h.rt)) flipColors(h);
            h.N = size(h.lf) + size(h.rt) + 1;
            return h;
        }

        public int size() {
            return root.N;
        }

        private int size(Node x) {
            if (x == null) return 0;
            return x.N;
        }

        private boolean isRed(Node x) {
            if (x == null) return false;
            return x.color == RED;
        }
        // Go left if keys are equal

        // See if you can write a draw method to see how the tree looks once it is done
        Node rotateLeft(Node h) {
            Node x = h.rt;
            h.rt = x.lf;
            x.color = h.color;
            h.color = RED;
            x.N = h.N;
            h.N = 1 + size(h.lf) + size(h.rt);
            return x;
        }

        Node rotateRight(Node h) {
            Node x = h.lf;
            h.lf = x.rt;
            x.rt = h;
            x.color = h.color;
            h.color = RED;
            x.N = h.N;
            h.N = 1 + size(h.lf) + size(h.rt);
            return x;
        }

        void flipColors(Node h) {
            h.color = RED;
            h.lf.color = BLACK;
            h.rt.color = BLACK;
        }
    }

    public static void main(String[] args) {
        Exer3_5_10 e = new Exer3_5_10();
        RedBalackBSTWithDupKeys rBD = e.new RedBalackBSTWithDupKeys();
        rBD.put(0, 1);
        rBD.put(0, 2);
        rBD.put(0, 3);
        rBD.put(0, 4);
    }
}
