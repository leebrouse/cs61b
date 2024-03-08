package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    // 定义节点内部类
    public class IntNode {
        public T item;
        public IntNode next;
        public IntNode pre;

        // 节点构造函数
        public IntNode(T i, IntNode n, IntNode p) {
            item = i;
            next = n;
            pre = p;
        }
    }

    int size;
    public IntNode sentinel;

    // 创建一个空的链表双端队列。
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }

    // 构造只有一个元素的双端队列
    public LinkedListDeque(T item) {
        sentinel = new IntNode(null, null, null);
        sentinel.next = new IntNode(item, sentinel, sentinel);
        sentinel.pre = sentinel.next;
        size = 1;
    }

    // 在双端队列的头部添加元素
    public void addFirst(T item) {
        sentinel.next = new IntNode(item, sentinel.next, sentinel);
        sentinel.next.next.pre = sentinel.next;
        size++;
    }

    // 在双端队列的尾部添加元素
    public void addLast(T item) {
        sentinel.pre.next = new IntNode(item, sentinel, sentinel.pre);
        sentinel.pre = sentinel.pre.next;
        size++;
    }

    // 获取双端队列的大小
    public int size() {
        return size;
    }

    // 遍历打印双端队列
    public void printDeque() {
        IntNode t = sentinel.next;
        while (t != sentinel) {
            if (t.next == sentinel) {
                System.out.println(t.item);
            } else {
                System.out.print(t.item + "->");
            }
            t = t.next;
        }
    }

    // 从双端队列的头部删除元素
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        IntNode removedNode = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size--;
        return removedNode.item;
    }

    // 从双端队列的尾部删除元素
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        IntNode removedNode = sentinel.pre;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        size--;
        return removedNode.item;
    }

    // 获取指定位置的元素
    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        IntNode p = sentinel.next;
        for (int c = 0; c < size; c++) {
            if (c == index) {
                return p.item;
            }
            p = p.next;
        }
        return null;
    }

    // 递归方式获取指定位置的元素
    private T getRecursive(int index, IntNode p) {
        if (p == null || p == sentinel) {
            return null;
        }
        if (index == 0) {
            return p.item;
        }
        return getRecursive(--index, p.next);
    }

    // 递归方式获取指定位置的元素
    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }

    // 实现迭代器
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    // 内部类实现迭代器
    private class DequeIterator implements Iterator<T> {
        private int curpos;

        public DequeIterator() {
            curpos = 0;
        }

        public boolean hasNext() {
            return curpos < size;
        }

        public T next() {
            return get(curpos++);
        }
    }

    // 判断双端队列是否相等
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>) o;
        if (other.size() != this.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
}

