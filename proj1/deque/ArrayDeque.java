package deque;

public class ArrayDeque<Leebrouse> {
   int [] items;
    int size;

    //构建一个空的ArrayDeque
   public ArrayDeque(){
        items=new int[8];
        size=0;
   }

   //构建一个带有element的ArrayDeque
    public ArrayDeque(int item){
       items = new int[8];
       items[0]=item;
       size=1;
    }

    public void addFirst(int item){
       for (int i=size-1;i>=0;i--){
           items[i+1]=items[i];
       }
       items[0]=item;
       size++;
    }

    public void addLast(int item){
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
        int[] p = items;
        for ( int c=0;c<size;c++){
            if (c == size-1) {
                System.out.println(p[c]);
            } else {
                System.out.print(p[c] + "->");
            }
        }
    }

    public int removeFirst(){
       int removeNumber=items[0];
       if (size==0){
           return 0;
       }

       for (int i=0;i<size;i++){
           items[i]=items[i+1];
       }
       size--;
       return removeNumber;
    }

    public int removeLast(){
        int removeNumber=items[size-1];

        if (size==0){
            return 0;
        }

        items[size-1]=0;
        size--;
        return removeNumber;
    }

    public int get(int index){
        return items[index];
    }

    public static void main(String[] args) {
      //ArrayDeque p=new ArrayDeque();
        ArrayDeque t=new ArrayDeque(3);
        t.addFirst(2);
        t.addFirst(1);
        t.addLast(4);
        //t.removeFirst();
        //t.removeLast();
        //System.out.println(t.get(2));
        t.printDeque();
    }
}


