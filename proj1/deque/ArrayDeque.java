package deque;
import java.util.Iterator;

public class ArrayDeque<T>  implements  Iterable<T>,Deque<T>{
    // 数组的初始大小是8
    private static final int INIT_SIZE = 8;
    // 对于长度>=16的array，其中的元素个数应该>=4，即负载率最低是0.25
    private static final double LOWEST_LOAD = 0.25;
    // 扩充率是1.25，只要数组的元素满了，就给数组扩充成原来的1.25倍
    private static final double EXPAND_RATE = 1.25;
    // 收缩率是0.5，如果数组负载<0.25，就
    private static final double SHRINK_RATE = 0.5;
   private T [] items;
   private  int front;
   private int last;
   private  int size;

    //构建一个空的ArrayDeque
   public ArrayDeque(){
        items=(T[]) new Object[INIT_SIZE];
        front=4;
        last=5;
        size=0;
   }

    private int minindex(int index){
       if (index==0){
           index= items.length-1;
           return index;
       }
       return index-1;
    }

    private int plusiindex(int index){
       if (index==items.length-1){
           index=0;
           return index;
       }
       return index+1;
    }

//    private void expand(){
//
//    }
//
//    private void shrink(){
//
//    }

    private void resize(double capacity) {
       T[] a = (T[])  new Object[(int) capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addFirst(T item){

        if (size == items.length) {
            resize(size * EXPAND_RATE);
        }

        items[front]=item;
        front=minindex(front);
        size++;
    }

    public void addLast(T item){

         if (size == items.length) {
             resize(size * EXPAND_RATE);
         }

        items[last]=item;
        last=plusiindex(last);
        size++;
    }

    //显示大小
    public int size(){
        return size;
    }

    public void printDeque() {
        int iter = Math.max(front, last);

        for (int i = 0; i < items.length; i++) {

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
       }

       T removeNumber=items[front];

       if(items[front]==null){
            return null;
       }

       front=plusiindex(front);
       items[front]=null;
       size--;
       return removeNumber;
    }

    public T removeLast(){

        if (isEmpty()){
            return null;
        }

       T removeNumber=items[last];

        if(items[last]==null){
            return null;
        }

        last=minindex(last);
        items[last]=null;
        size--;
        return removeNumber;
    }

    public T get(int index){

       if (isEmpty()){
           return null;
       }

        return items[(front + index+1) % items.length];
    }

    public Iterator<T> iterator(){
        return new Dequeiterator();
    }

    private class Dequeiterator implements  Iterator<T>{
        private int curpos;
        public Dequeiterator(){
            curpos=0;
        }

        public boolean hasNext(){
            return curpos<size;
        }

        public T next(){
            T item=items[curpos];
            curpos++;
            return item;
        }
    }

    public boolean equals(Object o){
        if (o==this){
            return true;
        }

        if (o==null){
            return false;
        }

        if (!(o instanceof Deque)){
            return false;
        }

        Deque<T> other=(Deque<T>) o;
        if (other.size()!=this.size){
            return false;
        }

        for (int i=0;i<size;i++){
            if (!this.get(i).equals(other.get(i))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
      //ArrayDeque p=new ArrayDeque();
        ArrayDeque<Integer> t=new ArrayDeque<>();
        t.addLast(1);
        t.addLast(2);
        t.addLast(3);
        t.addLast(4);
        t.addLast(5);
        t.addLast(6);
        t.addLast(7);
        t.addLast(8);
        //t.addFirst(10);
//        t.removeFirst();
//        t.removeLast();
//        t.removeLast();
//        t.removeFirst();
        System.out.println(t.get(0));
        t.printDeque();
    }
}


