import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        K key;
        V val;
        Node lchild, rchild;

        public Node() {

        }

        public Node(K k, V v) {
            key = k;
            val = v;
        }
    }

    private class BSTMapIterator implements Iterator<K> { // provides deep-first iteration (inorder traversal)
        private Stack<Node> stack;
        private Node cur;
        private K curKey;
        private V curVal;

        public BSTMapIterator() {
            stack = new Stack<>();
            cur = root; // the next node to be explored
            curKey = null; // the last visited key & val
            curVal = null;
        }

        @Override
        public boolean hasNext() {
            return !(cur == null && stack.isEmpty());
        }

        @Override
        public K next() {
            if (!hasNext())
                throw new NoSuchElementException("BSTMapIterator elements exhausted");
            while (true) {
                if (cur != null) { // explore its left child first
                    stack.push(cur);
                    cur = cur.lchild;
                } else { //
                    cur = stack.pop();
                    K res = cur.key;
                    curKey = res;
                    curVal = cur.val;
                    cur = cur.rchild;
                    return res;
                }
            }
        }

        public K curKey() {
            return curKey;
        }

        public V curVal() {
            return curVal;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    public void putRecursive(Node node, K key, V value) { // ensure that node is not null
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            if (node.lchild == null) {
                node.lchild = new Node(key, value);
                size++;
            } else
                putRecursive(node.lchild, key, value);
        } else if (cmp > 0) {
            if (node.rchild == null) {
                node.rchild = new Node(key, value);
                size++;
            } else
                putRecursive(node.rchild, key, value);
        } else
            node.val = value;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
        } else {
            putRecursive(root, key, value);
        }
    }

    private Node getRecursive(Node node, K key) { // return the Node corresponding to the key. return null if not found.
        if (node == null)
            return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0)
            return node;
        if (cmp < 0)
            return getRecursive(node.lchild, key);
        return getRecursive(node.rchild, key);
    }

    @Override
    public V get(K key) {
        Node node = getRecursive(root, key);
        if (node == null)
            return null;
        return node.val;
    }

    @Override
    public boolean containsKey(K key) {
        return getRecursive(root, key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (K key : this)
            set.add(key);
        return set;
    }

    /*
     * private void removeExactly(Node parent, Node node) { // helper method for
     * removing the target node after taking down
     * // the value
     * // parent==null means node is root
     * // node must not be null
     * if(parent == null){
     * if(node.lchild == null && node.rchild == null) root = null;
     * else if(node.lchild != null && node.rchild != null){
     * 
     * }
     * } else if(parent.lchild == node){
     * 
     * } else {
     * 
     * }
     * }
     * 
     * private V removeRecursive(Node parent, Node node, K key) { // ensure that
     * node is not null
     * // provide the parent so as to remove the node itself
     * int cmp = key.compareTo(node.key);
     * V res = null;
     * if (cmp == 0) {
     * res = node.val;
     * removeExactly(parent, node);
     * size--;
     * } else if (cmp < 0 && node.lchild != null) {
     * res = removeRecursive(node, node.lchild, key);
     * } else if (cmp > 0 && node.rchild != null)
     * res = removeRecursive(node, node.rchild, key);
     * return res;
     * }
     * 
     * @Override
     * public V remove(K key) {
     * 
     * // * mechanism: do different things regarding the number of child(ren) of the
     * // * removed node
     * // * 0: do nothing
     * // * 1: let the children replace the removed node
     * // * 2: replace the removed node with an appropriate node from one of the
     * // * sub-trees
     * 
     * if (root == null)
     * return null;
     * return removeRecursive(null, root, key);
     * }
     */
    private Node removeGreatestInLeft(Node parent, Node node) {
        // initially node is parent's lchild
        while (node.rchild != null) {
            parent = node;
            node = node.rchild;
        }
        if (parent.rchild == node) {
            parent.rchild = node.lchild;
            node.lchild = null;
        }

        return node;
    }

    public Node removeRecursive(Node node, K key) { // delete the key only
        // return the node used to replace the current node
        if (node == null)
            return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            size--;
            if (node.lchild == null && node.rchild == null)
                return null;
            else if (node.lchild != null && node.rchild != null) {
                Node replacer = removeGreatestInLeft(node, node.lchild);
                if (replacer == node.lchild) {
                    replacer.rchild = node.rchild;
                } else {
                    replacer.rchild = node.rchild;
                    replacer.lchild = node.lchild;
                }

                return replacer;

            } else if (node.lchild != null && node.rchild == null) {
                Node replacer = node.lchild;
                node.lchild = null;
                return replacer;
            } else {
                Node replacer = node.rchild;
                node.rchild = null;
                return replacer;
            }
        } else if (cmp < 0) {
            node.lchild = removeRecursive(node.lchild, key);
            return node;
        } else {
            node.rchild = removeRecursive(node.rchild, key);
            return node;
        }
    }

    @Override
    public V remove(K key) {
        V res = get(key);
        if (res == null)
            return null;
        root = removeRecursive(root, key);
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        BSTMapIterator it = (BSTMapIterator) iterator();
        while (it.hasNext()) {
            it.next();
            sb.append(it.curKey() + ": " + it.curVal() + ", ");
        }
        sb.append("}");
        return sb.toString();
    }

    public void printInOrder() {
        System.out.println(this);
    }

}