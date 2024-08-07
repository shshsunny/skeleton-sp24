package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * A hash table-backed Map implementation.
 *
 * Assumes null keys will never be inserted, and does not resize down upon
 * remove().
 * 
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private static final int resizeFactor = 2;
    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double maxLoadFactor;
    private int size;
    private int iCapacity;
    private int capacity;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor      maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = new Collection[initialCapacity];
        maxLoadFactor = loadFactor;
        size = 0;
        capacity = iCapacity = initialCapacity;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * Note that that this is referring to the hash table bucket itself,
     * not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    private void resize() { // resize up by resizeFactor
        int newCapacity = capacity * resizeFactor;
        Collection<Node>[] newBuckets = new Collection[newCapacity];
        for (int i = 0; i < capacity; ++i) {
            if (buckets[i] != null) {
                for (Node node : buckets[i]) {
                    move(node, newBuckets, newCapacity);
                }
            }
        }
        capacity = newCapacity;
        buckets = newBuckets;

    }

    private void move(Node node, Collection<Node>[] theBuckets, int theCapacity) {
        int idx = Math.floorMod(node.key.hashCode(), theCapacity);
        if (theBuckets[idx] == null)
            theBuckets[idx] = createBucket();
        theBuckets[idx].add(node);
    }

    @Override
    public void put(K key, V value) {
        int idx = Math.floorMod(key.hashCode(), capacity);
        if (buckets[idx] == null) {
            buckets[idx] = createBucket();
            buckets[idx].add(new Node(key, value));
            size++;
        } else {
            for (Node node : buckets[idx]) {
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }
            }
            buckets[idx].add(new Node(key, value));
            size++;
        }
        if ((double) size / (double) capacity > maxLoadFactor)
            resize();
    }

    @Override
    public V get(K key) {
        int idx = Math.floorMod(key.hashCode(), capacity);
        if (buckets[idx] == null)
            return null;
        for (Node node : buckets[idx]) {
            if (node.key.equals(key))
                return node.value;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int idx = Math.floorMod(key.hashCode(), capacity);
        if (buckets[idx] == null)
            return false;
        for (Node node : buckets[idx]) {
            if (node.key.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        if (capacity > iCapacity) {
            buckets = new Collection[iCapacity];
            capacity = iCapacity;
        }

        else {
            for (int i = 0; i < capacity; ++i)
                buckets[i] = null;
        }
    }

    // optionals

    @Override
    public Set<K> keySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keySet'");
    }

    @Override
    public V remove(K key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    private void remove(K key, V value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Iterator<K> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

}
