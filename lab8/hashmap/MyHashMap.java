package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V>, Iterable<K> {

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
    private int size = 0;  // 当前存储的键值对数量
    private double loadFactor;  // 负载因子
    private static final int DEFAULT_INITIAL_SIZE = 16;  // 默认初始大小
    private static final double DEFAULT_LOAD_FACTOR = 0.75;  // 默认负载因子

    /** Constructors */
    // 构造器1: 使用默认值
    public MyHashMap() {
        this(DEFAULT_INITIAL_SIZE, DEFAULT_LOAD_FACTOR);
    }

    // 构造器2: 指定初始大小,使用默认负载因子
    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }



    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    // 构造器3: 指定初始大小和负载因子
    public MyHashMap(int initialSize, double maxLoad) {
        this.loadFactor = maxLoad;
        buckets = createTable(initialSize);
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
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
        return new LinkedList<>();
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
        @SuppressWarnings("unchecked")
        Collection<Node>[] temp = (Collection<Node>[]) new Collection[tableSize];
        return temp;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    public void put(K key, V value) // 插入键值对
    {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        // 计算哈希值并确定桶的索引
        int bucketIndex = (key.hashCode() & 0x7fffffff) % buckets.length;

        // 查找key是否已存在
        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                // 如果key存在，更新value
                node.value = value;
                return;
            }
        }

        // key不存在，添加新节点
        buckets[bucketIndex].add(createNode(key, value));
        size += 1;

        // 检查是否需要扩容
        if ((double) size / buckets.length > loadFactor) {
            resize(buckets.length * 2);
        }
    }
    private void resize(int newSize) {
        Collection<Node>[] oldBuckets = buckets;
        buckets = createTable(newSize);

        // 初始化新桶
        for (int i = 0; i < newSize; i++) {
            buckets[i] = createBucket();
        }

        // 重新散列所有元素
        size = 0;
        for (Collection<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }
    public V get(K key)              // 获取值
    {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        int bucketIndex = (key.hashCode() & 0x7fffffff) % buckets.length;
        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }
    public boolean containsKey(K key) // 检查键是否存在
    {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        int bucketIndex = (key.hashCode() & 0x7fffffff) % buckets.length;
        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
    public int size(){
        return size;
    }
    public void clear()              // 清空哈希表
    {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i].clear();
        }
        size = 0;
    }
    public Set<K> keySet()    // 返回所有键的集合
    {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }
    public Iterator<K> iterator()  // 返回键的迭代器
    {
        return keySet().iterator();
    }
    @Override
    public V remove(K key) {
        // 1. 计算桶的索引
        int bucketIndex = (key.hashCode() & 0x7fffffff) % buckets.length;

        // 2. 在对应的桶中查找节点
        Iterator<Node> iterator = buckets[bucketIndex].iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.key.equals(key)) {
                // 找到节点，删除它
                iterator.remove();  // 使用迭代器的remove方法安全删除
                size -= 1;
                return node.value;  // 返回被删除的值
            }
        }

        // 没找到key，返回null
        return null;
    }

    @Override
    public V remove(K key, V value) {
        // 1. 计算桶的索引
        int bucketIndex = (key.hashCode() & 0x7fffffff) % buckets.length;

        // 2. 在对应的桶中查找节点
        Iterator<Node> iterator = buckets[bucketIndex].iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            // 检查key和value是否都匹配
            if (node.key.equals(key) && node.value.equals(value)) {
                iterator.remove();
                size -= 1;
                return node.value;
            }
        }

        // 没找到匹配的键值对，返回null
        return null;
    }
}
