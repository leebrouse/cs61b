package hashmap;


import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Leebrouse
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

    /* Instance Variables */

    private Collection<Node>[] buckets;
    private HashSet<K> keySet = new HashSet<>();
    private int initialSize = 16;
    private double loadFactor = 0.75;

    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets=createTable(initialSize);

    }

    public MyHashMap(int initialSize) {
        buckets=createTable(initialSize);

    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets=createTable(initialSize);
        this.loadFactor=maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
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
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private int hash (Object key) {
        return (key == null) ? 0
                : (0x7fffffff & key.hashCode ()) % buckets.length;
    }

    public int resize(int size){
        return 0;
    }

    @Override
    public void clear() {
        //buckets=null;
        Arrays.fill(buckets, null);
        keySet.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)){
            return null;
        }

        int index=hash(key);
        for (Node node:buckets[index]){
            if (node.key.equals(key)){
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return keySet.size();
    }

    @Override
    public void put(K key, V value) {
        double rate= (double) this.size() / buckets.length;
        if (rate>loadFactor){
            resize(buckets.length*2);
        }

        int index=hash(key);
        if (!containsKey(key)){
            Node node=new Node(key,value);
            if (buckets[index]==null){
                buckets[index]=createBucket();
            }
            buckets[index].add(node);
            keySet.add(key);
        }else {
            for (Node node1:buckets[index]){
                if (node1.key.equals(key)){
                    node1.value=value;
                }
            }
        }


    }

    @Override
    public Set<K> keySet() {
        return this.keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet.iterator();
    }

}
