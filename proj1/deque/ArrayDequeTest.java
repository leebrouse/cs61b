package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;
//add,remove,resize,equal,bigArrayDeque_Test

public class ArrayDequeTest {
    @Test
    public void  addIsEmptySizeTest(){
        ArrayDeque<Integer> ad=new ArrayDeque<>();
        assertTrue("A newly initialized AD should be empty",ad.isEmpty());

        ad.addFirst(1);

        assertEquals(1, ad.size());
        assertFalse("ad should now contain 1 item", ad.isEmpty());

        ad.addLast(2);
        assertEquals(2, ad.size());

        ad.addLast(3);
        assertEquals(3, ad.size());

        System.out.println("Printing out deque: ");
        ad.printDeque();
    }


    @Test
    /* 引入random进行随机化测试 */
    public void firstTest() {
        Deque<Integer> deque1 = new LinkedListDeque<>();
        Deque<Integer> deque2 = new ArrayDeque<Integer>();
        int n = 5000;
        for (int i = 0; i < n; ++i) {
            int operationNumber = StdRandom.uniform(0, 5);
            switch (operationNumber) {
                case 0:
                    deque1.addFirst(i);
                    deque2.addFirst(i);
                    break;
                case 1:
                    deque1.addLast(i);
                    deque2.addLast(i);
                case 2:
                    assertEquals(deque1.size(), deque2.size());
                case 3:
                    assertEquals(deque1.removeLast(), deque2.removeLast());
                case 4:
                    if (deque2.size() > 0) {
                        int getIdx = StdRandom.uniform(0, deque2.size());
                        assertEquals(deque1.get(getIdx), deque2.get(getIdx));
                    }
            }
        }
    }

    @Test
    /* 随机测试实现的不同操作 */
    public void randomMethodTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        assertTrue(lld1.isEmpty());
        assertNull(lld1.get(0));
        assertNull(lld1.get(4000));
        assertNull(lld1.removeLast());
        lld1.addFirst(5);
        lld1.addLast(10);
        lld1.addFirst(15);
        assertEquals((long) lld1.removeLast(),10);
        //lld1.printDeque();
        assertEquals((long) lld1.removeLast(),5);
        assertEquals((long) lld1.removeLast(),15);
        assertNull(lld1.removeLast());
        assertNull(lld1.removeFirst());
        lld1.addFirst(11555000);
        lld1.addLast(259);
        Deque<Integer> lld2 = new LinkedListDeque<>();
        lld2.addFirst(11555000);
        lld2.addLast(259);
        for (Integer elem : lld1) {
            System.out.print(elem + " ");
        }
        System.out.println();
        assertEquals(lld1, lld2);
    }

    @Test
    public void testEquals() {
        Deque<Integer> deque1 = new LinkedListDeque<>();
        Deque<Integer> deque2 = new ArrayDeque<>();
        assertEquals(deque1, deque2);
    }

}
