public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * 
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * 
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * 
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * 
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // node.left must not be null
        RBTreeNode<T> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        boolean tmp = newRoot.isBlack;
        newRoot.isBlack = node.isBlack;
        node.isBlack = tmp;
        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * 
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // node.right must not be null
        RBTreeNode<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        boolean tmp = newRoot.isBlack;
        newRoot.isBlack = node.isBlack;
        node.isBlack = tmp;
        return newRoot;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes
     * (children or leaf
     * nodes) are automatically considered black.
     * 
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * 
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided
     * to help break
     * down the problem. For each case, consider the scenario needed to perform
     * those operations.
     * Make sure to also review the other methods in this class!
     * 
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // return the appropriate node to replace the given node
        //
        if (node == null)
            return new RBTreeNode<T>(false, item); // this happens only when the tree is empty
        int cmp = item.compareTo(node.item);
        if (cmp == 0)
            return node; // make no insertion

        if (cmp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        RBTreeNode<T> res = node;
        // handle different violations until res achieves the r-b properties
        while (true) {
            if (!isRed(res.left) && isRed(res.right)) { // the only red child must be on the left
                res = rotateLeft(res);
            } else if (isRed(res.left) && isRed(res.right)) { // two red children are not allowed
                flipColors(res);
            } else if (isRed(res.left) && isRed(res.left.left)) { // two consecutive left-leaning red nodes -> case 2
                res = rotateRight(res);
            } else if (isRed(res.left) && isRed(res.left.right)) { // left-right two red nodes -> case 3
                res.left = rotateLeft(res.left);
            } else
                break;
        }

        // the cases above are already exclusive mutually
        // IT CAN BE PROVED INDUCTIVELY that the algorithm is correct(?)
        // since the correctness of the operations upon children and grandchildren
        // implies the correctness at this level
        return res; // fix this return statement
    }

}
