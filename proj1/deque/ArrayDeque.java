package deque;

import java.util.Arrays;

public class ArrayDeque<T> implements Deque<T> {
    // 属性定义
    private T[] items;         // 存储元素的数组
    private int size;          // 当前元素个数
    private int nextFirst;     // 下一个首元素位置
    private int nextLast;      // 下一个尾元素位置

    // 构造函数
    public ArrayDeque() {
        // Java不允许直接创建泛型数组，需要这样转换
        items = (T[]) new Object[8];
        size = 0;
        // 思考：nextFirst 和 nextLast 初始值应该设为多少？
        nextFirst = 3;
        nextLast = 4;
    }

    // 基础方法
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        int startPos = (newSize - size) / 2;
        int firstPos = nextFirst + 1;

        // 复制元素
        for (int i = 0; i < size; i++) {
            newArray[startPos + i] = items[(firstPos + i) % items.length];
        }

        // 更新成员变量
        items = newArray;
        nextFirst = startPos - 1;
        nextLast = startPos + size;
    }

    // addFirst的框架
    public void addFirst(T item) {
        // 检查是否需要扩容
        if (size == items.length) {
            resize(size * 2);  // 扩容为原来的2倍
        }

        // 添加元素
        items[nextFirst] = item;

        // 更新nextFirst（使用取余来处理循环情况）
        nextFirst = (nextFirst - 1 + items.length) % items.length;

        // 更新size
        size++;
    }

    // addLast的框架
    public void addLast(T item) {
        // 类似addFirst的逻辑
        if(size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T ReturnItem = items[nextFirst+1];
        items[nextFirst+1] = null;
        nextFirst = (nextFirst + 1) % items.length;
        size--;
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return ReturnItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T ReturnItem = items[nextLast];
        items[nextLast] = null;
        nextLast = (nextLast - 1 + items.length) % items.length;
        size--;
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return ReturnItem;
    }

    public T get(int index) {
        if(index > size || index < 0) return null;
        // 直接计算实际位置
        int actualIndex = (nextFirst + 1 + index) % items.length;
        return items[actualIndex];
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

    public void printDeque() {
        for(int i = nextFirst + 1; i < nextLast; i++) {
            System.out.print(items[i] + " ");
        }
    }
}

