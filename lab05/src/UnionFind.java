public class UnionFind {
    private int[] tree;
    private int n;

    private boolean valid(int v) {
        return (0 <= v && v < n);
    }

    /*
     * Creates a UnionFind data structure holding N items. Initially, all
     * items are in disjoint sets.
     */
    public UnionFind(int N) {
        tree = new int[N];
        n = N;
        for (int i = 0; i < N; ++i)
            tree[i] = -1;
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -tree[find(v)];
    }

    /*
     * Returns the parent of V. If V is the root of a tree, returns the
     * negative size of the tree for which V is the root.
     */
    public int parent(int v) {
        return tree[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /*
     * Returns the root of the set V belongs to. Path-compression is employed
     * allowing for fast search-time. If invalid items are passed into this
     * function, throw an IllegalArgumentException.
     */
    public int find(int v) {
        if (!valid(v))
            throw new IllegalArgumentException();
        int res = v;
        while (tree[res] >= 0)
            res = tree[res];
        while (tree[v] >= 0) {
            int par = tree[v];
            tree[v] = res;
            v = par;
        }
        return res;
    }

    /*
     * Connects two items V1 and V2 together by connecting their respective
     * sets. V1 and V2 can be any element, and a union-by-size heuristic is
     * used. If the sizes of the sets are equal, tie break by connecting V1's
     * root to V2's root. Union-ing an item with itself or items that are
     * already connected should not change the structure.
     */

    public void union(int v1, int v2) {
        if (!valid(v1) || !valid(v2))
            throw new IllegalArgumentException();
        int r1 = find(v1), r2 = find(v2);
        if (r1 == r2)
            return; // should do nothing if v1, v2 are already connected
        if (-tree[r1] <= -tree[r2]) { // ensure that tree r1 is larger than tree r2
            int tmp = r1;
            r1 = r2;
            r2 = tmp;
        }
        tree[r1] += tree[r2]; // merge the size
        tree[r2] = r1; // append the root of the smaller tree to the root of the larger one
    }

}
