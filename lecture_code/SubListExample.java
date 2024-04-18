import java.util.*;

public class SubListExample {
    public static void main(String[] args) {
        // Create a list
        List<String> l = new ArrayList<>();
        l.add("apple");
        l.add("banana");
        l.add("orange");
        l.add("grape");
        l.add("kiwi");

        // subList
       List<String> SL=l.subList(1,4);

        // Mutate the sublist
        SL.set(1, "jug");

        // Print the original list and sublist
        System.out.println("Original List: " + l);
        System.out.println("Sublist: " + SL);
    }
}
