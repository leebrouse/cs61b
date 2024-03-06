package deque;

import java.util.Comparator;

public class MaxArrayDeque<Leebrosue> extends LinkedListDeque<Leebrosue>{
    private final Comparator<Leebrosue> comparator;
    public MaxArrayDeque(Comparator<Leebrosue> c){
        super();
        comparator=c;
    }

    public Leebrosue max(){
        if(isEmpty()){
            return null;
        }

        Leebrosue t=get(0);
        for (int c=0;c<size();c++){
             if ((comparator.compare(get(c),t))>0){
                 t=get(c);
             }
        }
        return t;
    }

    public Leebrosue max(Comparator<Leebrosue> c){
        if (isEmpty()){
            return null;
        }

        Leebrosue p=get(0);
        for (int t=0;t<size();t++){
            if ((c.compare(get(t),p))>0){
                p=get(t);
            }
        }
        return p;
    }

}
