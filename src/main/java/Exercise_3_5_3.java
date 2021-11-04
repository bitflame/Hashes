public class Exercise_3_5_3 {
    class BinarySeachSET<Key extends Comparable<Key>, Value> {
        private Node root;

        private class Node {
            private Key key;
            private Node next;
            private Node left, right; // links to subtrees
            private int N; // # nodes in the subtree

            public Node(Key key, int N) {

            }
        }

        public int size() {
            return size(root);
        }

        private int size(Node x) {
            if (x == null) return 0;
            else return x.N;
        }

        public void put(Key key) {
            if (key == null) throw new IllegalArgumentException("The key value to put() can not be null");

            root = put(root, key);
        }

        private Node put(Node x, Key key) {
            if (x == null) return new Node(key, 1);
            int i = x.key.compareTo(key);
            if (i > 0) x.right = put(x.right, key);
            else if (i < 0) x.left = put(x.left, key);
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }
    }
}
