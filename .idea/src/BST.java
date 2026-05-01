import java.util.Iterator;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {

    public static class Entry<K, V> {
        public K key;
        public V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey()   { return key; }
        public V getValue() { return value; }
    }

    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    // ── put ──────────────────────────────────────────────────────────────────
    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node node, K key, V val) {
        if (node == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(node.key);
        if      (cmp < 0) node.left  = put(node.left,  key, val);
        else if (cmp > 0) node.right = put(node.right, key, val);
        else              node.val   = val;   // update existing
        return node;
    }

    // ── get ──────────────────────────────────────────────────────────────────
    public V get(K key) {
        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if      (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else              return node.val;
        }
        return null;
    }


    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if      (cmp < 0) node.left  = delete(node.left,  key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            size--;
            if (node.right == null) return node.left;
            if (node.left  == null) return node.right;

            Node successor = min(node.right);
            node.key = successor.key;
            node.val = successor.val;
            node.right = deleteMin(node.right);
            size++;
        }
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) { size--; return node.right; }
        node.left = deleteMin(node.left);
        return node;
    }

    // ── size ─────────────────────────────────────────────────────────────────
    public int size() { return size; }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<Entry<K, V>> {
        private final Stack<Node> stack = new Stack<>();

        public InOrderIterator() {
            pushLeft(root);
        }

        private void pushLeft(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() { return !stack.isEmpty(); }

        @Override
        public Entry<K, V> next() {
            Node node = stack.pop();
            pushLeft(node.right);
            return new Entry<>(node.key, node.val);
        }
    }
}