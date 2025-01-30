package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>,Iterable<T>{
    // 首先定义节点类
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        Node(T i) {
            item = i;
            next = null;
            prev = null;
        }
    }

    // 成员变量
    private Node sentinel;  // 哨兵节点
    private int size;      // 大小

    // 构造函数
    public LinkedListDeque() {
        sentinel = new Node(null);  // 哨兵节点不存储实际数据
        sentinel.next = sentinel;   // 初始时指向自己
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node newNode = new Node(item);
        // 你能完成剩下的部分吗？
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        newNode.prev = sentinel;
        size++;
    }

    public void addLast(T item) {
        Node newNode = new Node(item);
        sentinel.prev.next = newNode;
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev = newNode;
        size++;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentinel.next.item;    // 保存要返回的值

        sentinel.next = sentinel.next.next;     // 跳过第一个节点
        sentinel.next.prev = sentinel;          // 新的第一个节点向前指向哨兵

        size--;
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentinel.prev.item;

        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return removedItem;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    public T getRecursive(int index){

        if(index>=size){
            return null;
        }
        class getHelper{
            public T helper(Node curentNode,int currIndex){
                if(currIndex==index) {
                    return curentNode.item;
                }else{
                    return helper(curentNode.next,currIndex+1);
                }
            }
        }
        getHelper thisHelp = new getHelper();

        return thisHelp.helper(sentinel.next,0);
    }

    private class DequeIterator implements Iterator<T> {
        private Node current;

        DequeIterator() {
            current = sentinel.next;  // 从第一个实际元素开始
        }

        @Override
        public boolean hasNext() {
            return current != sentinel;  // 如果没有到达哨兵节点，就还有下一个元素
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T item = current.item;
            current = current.next;
            return item;
        }
    }

    // 3. 实现 Iterable 接口要求的 iterator() 方法
    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        if (size != ((Deque) o).size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(((Deque)o).get(i))) {
                return false;
            }
        }
        return true;
    }
}

