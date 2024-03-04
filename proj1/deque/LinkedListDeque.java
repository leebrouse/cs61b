package deque;

import java.util.Iterator;

public class LinkedListDeque<Leebrouse> implements  Iterable<Leebrouse>,Deque<Leebrouse>{
    public class IntNode{
        public Leebrouse item;
        public IntNode next;
        public IntNode pre;

        public IntNode(Leebrouse i,IntNode n,IntNode p){
            item=i;
            next=n;
            pre=p;
        }
    }

    int size;
    public IntNode sentinel;

    //创建一个空的链表双端队列。
    public LinkedListDeque(){
        sentinel=new IntNode(null,null,null);
        sentinel.next=sentinel;
        sentinel.pre=sentinel;
        size=0;
    }

    //构建第一个节点
    public LinkedListDeque(Leebrouse item){
        sentinel=new IntNode(null,null,null);
        sentinel.next=new IntNode(item,sentinel,sentinel);
        sentinel.pre=sentinel.next;
        size=1;
    }
    //前插
    public void addFirst(Leebrouse item){
       sentinel.next=new IntNode(item,sentinel.next,sentinel);
       sentinel.next.next.pre=sentinel.next;
       size++;
    }

    //后插
    public void addLast(Leebrouse item){
        sentinel.pre.next=new IntNode(item,sentinel,sentinel.pre);
        sentinel.pre= sentinel.pre.next;
        size++;
    }

    //判断是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    //显示大小
    public int size(){
        return size;
    }

    //遍历链表
    public void printDeque(){
        IntNode t=sentinel.next;

        while(t!=sentinel){
            if (t.next==sentinel){
                System.out.println(t.item);
            }else {
                System.out.print(t.item+"->");
            }
            t=t.next;
        }

    }

    //删前
    public Leebrouse removeFirst(){
        if (size==0){
            return null;
        }

        //tips:画图
        IntNode removedNode = sentinel.next;
        sentinel.next= sentinel.next.next;
        sentinel.next.pre= sentinel.next.pre.pre;
        size--;
        return removedNode.item;
    }

    //删后
    public Leebrouse removeLast(){
        if (size==0){
            return null;
        }

        //tips:画图(Using graph)
        IntNode removedNode = sentinel.pre;
        sentinel.pre=sentinel.pre.pre;
        sentinel.pre.next=sentinel;
        size--;
        return removedNode.item;
    }

    public Leebrouse get(int index){
        //Temporary pointer for iteration
        IntNode p=sentinel.next;

        //Judge the list is empty or not
        if (size==0){
            return null;
        }

        //iterate the list to find the intended item
        for (int c=0;c<size;c++){
            if (c==index){
                return p.item;
            }
            p=p.next;
        }
        return null;//No!,return 0 as null
    }

    private  Leebrouse getRecursive(int index,IntNode p){
        if(p==null || p==sentinel){
            return null;
        }

        if (index==0){
            return p.item;
        }

        return getRecursive(--index,p.next);
    }
    public Leebrouse getRecursive(int index){
        return getRecursive(index,sentinel.next);
    }

    public Iterator<Leebrouse> iterator(){
        return new Dequeiterator();
    }

    private class Dequeiterator implements  Iterator<Leebrouse>{
        private int curpos;
        public Dequeiterator(){
            curpos=0;
        }

        public boolean hasNext(){
            return curpos<size;
        }

        public Leebrouse next(){
            return get(curpos++);
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

        Deque<Leebrouse> other=(Deque<Leebrouse>) o;
        if (other.size()!=this.size){
            return false;
        }

        for (int i=0;i<size;i++){
            if (!this.get(i).equals(other.equals(i))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //LinkedListDeque r=new LinkedListDeque();
        LinkedListDeque<Integer> t=new LinkedListDeque<>(8);
        t.addFirst(7);
        t.addLast(9);
        //System.out.println(t.isEmpty());
        //System.out.println(t.size());
        //t.removeFirst();
        //t.removeLast();
        //System.out.println(t.get(1));
        //System.out.println(t.getRecursive(2));
        //t.printDeque();

    }
}
