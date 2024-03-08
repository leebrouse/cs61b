package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    // 数组的初始大小是8
    private static final int INIT_SIZE = 8;
    // 对于长度>=16的array，其中的元素个数应该>=4，即负载率最低是0.25
    private static final double LOWEST_LOAD = 0.25;
    // 扩充率是1.25，只要数组的元素满了，就给数组扩充成原来的1.25倍
    private static final double EXPAND_RATE = 1.25;
    // 收缩率是0.5，如果数组负载<0.25，就
    private static final double SHRINK_RATE = 0.5;

    // 数组存储元素
    private T[] items;
    // 前指针，指向队头前一个位置
    private int front;
    // 后指针，指向队尾
    private int tail;
    // 队列中元素数量
    private int size;

    /**
     * 构造函数，初始化数组大小和指针位置
     */
    public ArrayDeque() {
        items = (T[]) new Object[INIT_SIZE];
        front = 4;
        tail = 5;
        size = 0;
    }

    /**
     * 获取前指针的前一个位置
     */
    private int leftPointer(int index) {
        if (index == 0) {
            return index = items.length - 1;
        }
        return index - 1;
    }

    /**
     * 获取后指针的后一个位置
     */
    private int rightPointer(int index) {
        if (index == items.length - 1) {
            return index = 0;
        }
        return index + 1;
    }

    /**
     * 计算当前负载率
     */
    private double loadRate() {
        return (double) size / items.length;
    }

    /**
     * 调整数组大小
     */
    private void resize(double capacity) {
        T[] newArray = (T[]) new Object[(int) (capacity)];
        for (int i = 0; i < size; i++) {
            if (this.get(i) == null) {
                continue;
            }
            newArray[i] = this.get(i);
        }
        items = newArray;
        front = 0;
        front = leftPointer(front);
        tail = size;
    }

    /**
     * 在队头添加元素
     */
    public void addFirst(T item) {
        if (front == tail) {
            resize(items.length * EXPAND_RATE);
        }
        items[front] = item;
        front = leftPointer(front);
        size++;
    }

    /**
     * 在队尾添加元素
     */
    public void addLast(T item) {
        if (front == tail) {
            resize(items.length * EXPAND_RATE);
        }
        items[tail] = item;
        tail = rightPointer(tail);
        size++;
    }

    /**
     * 获取队列大小
     */
    public int size() {
        return size;
    }

    /**
     * 打印队列元素
     */
    public void printDeque() {
        int iter = front;
        for (int i = 0; i < items.length; ++i) {
            if (items[iter] == null) {
                iter = (iter + 1) % items.length;
                continue;
            }
            System.out.print(items[iter] + " ");
            iter = (iter + 1) % items.length;
        }
        System.out.println();
    }

    /**
     * 从队头删除元素
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else if (items[front] == null) {
            front = rightPointer(front);
        }
        T removeNum = items[front];
        items[front] = null;
        size--;
        if (loadRate() < LOWEST_LOAD && items.length >= 16) {
            resize(items.length * SHRINK_RATE);
        }
        return removeNum;
    }

    /**
     * 从队尾删除元素
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else if (items[tail] == null) {
            tail = leftPointer(tail);
        }
        T removeNum = items[tail];
        items[tail] = null;
        size--;
        if (loadRate() < LOWEST_LOAD && items.length >= 16) {
            resize(items.length * SHRINK_RATE);
        }
        return removeNum;
    }

    /**
     * 获取指定位置的元素
     */
    public T get(int index) {
        if (size == 0 || index >= size || index < 0) {
            return null;
        }
        return items[(front + index + 1) % items.length];
    }

    /**
     * 实现迭代器
     */
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    /**
     * 内部类实现迭代器
     */
    private class DequeIterator implements Iterator<T> {
        private int curPos; // 表示即将访问的索引位置，初始即将访问索引0

        public DequeIterator() {
            curPos = 0;
        }

        public boolean hasNext() {
            return curPos < size;
        }

        public T next() {
            return get(curPos++);
        }
    }

    /**
     * 判断双端队列是否相等
     */
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
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
}
