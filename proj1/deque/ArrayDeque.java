package deque;
import java.util.Iterator;
public class ArrayDeque<T>  implements  Iterable<T>,Deque<T>{
   private T [] items;
   private  int size;

    //构建一个空的ArrayDeque
   public ArrayDeque(){
        items=(T[]) new Object[8];
        size=0;
   }

   //构建一个带有element的ArrayDeque
    public ArrayDeque(T item){
        items=(T[]) new Object[8];
       items[0]=item;
       size=1;
    }

    private void resize(int capacity) {
       T[] a = (T[])  new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addFirst(T item){

        if (size == items.length) {
            resize(size * 2);
        }

       for (int i=size-1;i>=0;i--){
           items[i+1]=items[i];
       }

       items[0]=item;
       size++;
    }

    public void addLast(T item){

         if (size == items.length) {
             resize(size * 2);
         }

       items[size]=item;
       size++;
    }

//    public boolean isEmpty(){
//        return size == 0;
//    }

    //显示大小
    public int size(){
        return size;
    }

    public void printDeque() {
       T [] p = items;
        for ( int c=0;c<size;c++){
            if (c == size-1) {
                System.out.println(p[c]);
            } else {
                System.out.print(p[c] + "->");
            }
        }
    }

    public T removeFirst(){

       if (size==0){
           return null;
       }

       T removeNumber=items[0];

       for (int i=1;i<size;i++){
           items[i-1]=items[i];
       }
       items[size-1]=null;
       size--;
       return removeNumber;
    }

    public T removeLast(){

        if (size==0){
            return null;
        }

       T removeNumber=items[size-1];

        items[size-1]=null;
        size--;
        return removeNumber;
    }

    public T get(int index){
        return items[index];
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
        ArrayDeque<Integer> t=new ArrayDeque<>(3);
        t.addFirst(2);
        t.addFirst(1);
        t.addLast(4);
        t.addLast(5);
        t.addLast(6);
        t.addLast(7);
        t.addLast(8);
        t.addLast(9);
        t.removeFirst();
        t.removeLast();
        //System.out.println(t.get(2));
        t.printDeque();
    }
}


