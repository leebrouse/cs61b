package deque;

import java.util.Iterator;

/**
 * @apiNote 实现基于ARRAY的双端队列
 * 使用双指针，就像队列一样来维护数组
 * */
public class ArrayDeque<T> implements Iterable<T>,Deque<T> {
    // 数组的初始大小是8
    private static final int INIT_SIZE = 8;
    // 对于长度>=16的array，其中的元素个数应该>=4，即负载率最低是0.25
    private static final double LOWEST_LOAD = 0.25;
    // 扩充率是1.25，只要数组的元素满了，就给数组扩充成原来的1.25倍
    private static final double EXPAND_RATE = 1.25;
    // 收缩率是0.5，如果数组负载<0.25，就
    private static final double SHRINK_RATE = 0.5;
    private T[] items;
    private int front;
    private int tail;
    private int size;

    public ArrayDeque(){
        items=(T[])new Object[INIT_SIZE];
        front=4;
        tail=5;
        size=0;
    }

    private int LeftPointer(int index){
        if (index==0){
            return index=items.length-1;
        }

        return index-1;
    }

    private int RightPointer(int index){
        if (index==items.length-1){
            return index=0;
        }

        return index+1;
    }

    private double LoadRate(){
        return (double) size / items.length;
    }
    private void Resize(double capacity){
        T[] newArray = (T[]) new Object[(int) (capacity)];
        for (int i=0;i< size;i++){
            if (this.get(i)==null){
                continue;
            }

            newArray[i]=this.get(i);
        }

        items=newArray;
        front=0;
        front=LeftPointer(front);
        tail=size;
    }

    public void addFirst(T item){
        // 还有resize(),暂留;
        if (front==tail){
            Resize(items.length*EXPAND_RATE);
        }

        items[front]=item;
        front=LeftPointer(front);
        size++;
    }

    public void addLast(T item){
        if (front==tail){
            Resize(items.length*EXPAND_RATE);
        }

        items[tail]=item;
        tail=RightPointer(tail);
        size++;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        int iter = front;
        for (int i = 0; i < items.length; ++i) {

            if (items[iter]==null){
                iter = (iter + 1) % items.length;
                continue;
            }

            System.out.print(items[iter] + " ");
            iter = (iter + 1) % items.length;
        }
        System.out.println();

    }

    public T removeFirst(){
        if (isEmpty()){
           return null;
        }else if (items[front]==null){
            front=RightPointer(front);
        }



        T removeNum=items[front];

        items[front]=null;
        size--;

        if (LoadRate()<LOWEST_LOAD&&items.length>=16){
            Resize(items.length*SHRINK_RATE);
        }

        return removeNum;
    }
    public T removeLast(){
        if (isEmpty()){
            return null;
        }else if (items[tail]==null){
            tail=LeftPointer(tail);
        }

        T removeNum=items[tail];

        items[tail]=null;
        size--;

        if (LoadRate()<LOWEST_LOAD&&items.length>=16){
            Resize(items.length*SHRINK_RATE);
        }

        return removeNum;
    }

    public T get(int index) {
        if (size == 0) {
            return null;
        }
        if (index >= size || index < 0) {
            return null;
        }
        return items[(front + index+1) % items.length];
    }

    public Iterator<T> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
        private int curPos; // 表示即将访问的索引位置，初始即将访问索引0
        DequeIterator() {
            curPos = 0;
        }

        public boolean hasNext() {
            return curPos < size;
        }

        public T next() {
            return get(curPos++);
        }
    }

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


    public static void main(String[] args) {
        ArrayDeque<Integer> t=new ArrayDeque<>();
        t.addFirst(1);
        t.addFirst(2);
        t.addFirst(3);
        t.addFirst(4);
        t.addFirst(5);
        t.addLast(6);
        t.addFirst(7);
        t.addFirst(0);
        t.addFirst(10);
        t.addLast(11);
        t.removeLast();
//        t.removeFirst();
//        t.removeLast();
//        t.removeFirst();
//        t.addLast(8);
        System.out.println(t.get(0));
        t.printDeque();
    }
}