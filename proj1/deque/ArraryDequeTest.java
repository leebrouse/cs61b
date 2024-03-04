package deque;

import org.junit.Test;
import static org.junit.Assert.*;
public class ArraryDequeTest {
    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad=new ArrayDeque<>();
        assertTrue("A newly initialized LLDeque should be empty", ad.isEmpty());
        ad.addFirst("front");

        assertEquals(1,ad.size());
        assertFalse("ad should now contain 1 item",ad.isEmpty());

        ad.addLast("middle");
        assertEquals(2,ad.size());

        ad.addLast("back");
        assertEquals(3,ad.size());


        System.out.println("Printing out deque: ");
        ad.printDeque();
    }
    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        ArrayDeque<Integer> ad=new ArrayDeque<>();

        assertTrue("ad should be empty upon initialization",ad.isEmpty());

        ad.addLast(10);
        assertFalse("ad should contain 1 item", ad.isEmpty());

        ad.removeFirst();
        assertTrue("ad should be empty after removal", ad.isEmpty());

    }

    @Test
    public void removeEmptyTest() {
        ArrayDeque<Integer> ad=new ArrayDeque<>();
        ad.addFirst(10);

        ad.removeFirst();
        ad.removeLast();
        ad.removeFirst();
        ad.removeLast();

        int size=ad.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg,0,size);
    }

    @Test
    public void multipleParamTest() {
        ArrayDeque<String>  ad1 = new ArrayDeque<>();
        ArrayDeque<Double>  ad2 = new   ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new   ArrayDeque<Boolean>();

        ad1.addFirst("String");
        ad2.addFirst(3.1415);
        ad3.addFirst(true);

        String a= ad1.removeFirst();
        Double b=ad2.removeLast();
        Boolean c=ad3.removeFirst();
    }

    @Test
    public void emptyNullReturnTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> ad = new ArrayDeque<>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 1000000; i++) {
            ad.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad.removeLast(), 0.0);
        }


    }


}
