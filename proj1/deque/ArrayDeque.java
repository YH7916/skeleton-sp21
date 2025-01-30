package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[capacity / 4 + i] = get(i);
        }
        items = newArray;
        nextFirst = capacity / 4 - 1;
        nextLast = nextFirst + size + 1;
    }

    public void addFirst(T item) {
        if (size == items.length - 2) {
            resize(items.length * 2);
        }

        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length - 2) {
            resize(items.length * 2);
        }

        items[nextLast] = item;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
    }

    private int arrayInd(int ind) {
        if (nextFirst + 1 + ind >= items.length) {
            return nextFirst + 1 + ind - items.length;
        } else {
            return nextFirst + 1 + ind;
        }
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[arrayInd(index)];
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if ((size < items.length / 4) && (size > 8)) {
            resize(items.length / 2);
        }
        T item = get(0);
        int ind = arrayInd(0);
        items[ind] = null;
        size -= 1;
        nextFirst = ind;
        return item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if ((size < items.length / 4) && (size > 8)) {
            resize(items.length / 2);
        }
        T item = get(size - 1);
        int ind = arrayInd(size - 1);
        items[ind] = null;
        size -= 1;
        nextLast = ind;
        return item;
    }

    public void printDeque() {
        for (T i : this) {
            System.out.print(i + " ");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        private ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T item = get(wizPos);
            wizPos += 1;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (other.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!(other.get(i).equals(this.get(i)))) {
                return false;
            }
        }
        return true;
    }
}