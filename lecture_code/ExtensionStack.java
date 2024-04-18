import java.util.LinkedList;

public class ExtensionStack <item> extends LinkedList<item> {
    public  void  push(item x){
        add(x);
    }

    public static void main(String[] args) {
        ExtensionStack<Integer> t =new ExtensionStack<>();
        t.push(1);
        t.push(2);
    }
}
