package deque;

import java.util.Iterator;

public interface Deque<Leebrouse> {
    void addFirst(Leebrouse item);
    void addLast(Leebrouse item);
    default boolean isEmpty() {
        return size() == 0;
    }
    int size();
    void printDeque();
    Leebrouse removeFirst();
    Leebrouse removeLast();
    Leebrouse get(int index);
    boolean equals(Object o);
    Iterator<Leebrouse> iterator();
}
