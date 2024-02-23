package deque;

public class ArrayDeque<Leebrouse> {
   private Leebrouse [] items;
   private  int size;

    //构建一个空的ArrayDeque
   public ArrayDeque(){
        items=(Leebrouse[]) new Object[8];
        size=0;
   }

   //构建一个带有element的ArrayDeque
    public ArrayDeque(Leebrouse item){
        items=(Leebrouse[]) new Object[8];
       items[0]=item;
       size=1;
    }

    private void resize(int capacity) {
        Leebrouse[] a = (Leebrouse[])  new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addFirst(Leebrouse item){
       for (int i=size-1;i>=0;i--){
           items[i+1]=items[i];
       }
       items[0]=item;
       size++;
    }

    public void addLast(Leebrouse item){

            if (size == items.length) {
                resize(size * 2);
            }

       items[size]=item;
       size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    //显示大小
    public int size(){
        return size;
    }

    public void printDeque() {
        Leebrouse [] p = items;
        for ( int c=0;c<size;c++){
            if (c == size-1) {
                System.out.println(p[c]);
            } else {
                System.out.print(p[c] + "->");
            }
        }
    }

    public Leebrouse removeFirst(){

       if (size==0){
           return null;
       }

       Leebrouse removeNumber=items[0];

       for (int i=0;i<size;i++){
           items[i]=items[i+1];
       }
       size--;
       return removeNumber;
    }

    public Leebrouse removeLast(){

        if (size==0){
            return null;
        }

        Leebrouse removeNumber=items[size-1];

        items[size-1]=null;
        size--;
        return removeNumber;
    }

    public Leebrouse get(int index){
        return items[index];
    }

    public static void main(String[] args) {
      //ArrayDeque p=new ArrayDeque();
        ArrayDeque<Integer> t=new ArrayDeque<>(3);
        t.addFirst(2);
        t.addFirst(1);
        t.addLast(4);
        //t.removeFirst();
        //t.removeLast();
        //System.out.println(t.get(2));
        t.printDeque();
    }
}


