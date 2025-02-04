package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    // 首先，定义私有的节点类
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // 声明根节点和大小
    private Node root;
    private int size;

    // 构造函数
    public BSTMap() {
        root = null;
        size = 0;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return findNode(key, root)!=null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Node node = findNode(key, root);
        return node==null?null:node.value;
    }

    private Node findNode(K key, Node node){
        if(node==null || node.key.equals(key)){
            return node;
        }
        if(key.compareTo(node.key)<0){
            return findNode(key, node.left);
        }else{
            return findNode(key, node.right);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    private Node putHelper(K key, V value, Node node){
        if(node==null){
            size++;
            return new Node(key, value);
        }
        if(key.compareTo(node.key)<0){
            node.left = putHelper(key, value, node.left);
        }else if(key.compareTo(node.key)>0){
            node.right = putHelper(key, value, node.right);
        }else{
            node.value = value;
        }
        return node;
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}