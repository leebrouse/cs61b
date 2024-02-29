package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */


public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void testThreeAddThreeRemove() {
      AList<Integer> correct=new AList<>();
      BuggyAList<Integer> Bug=new BuggyAList<>();

      correct.addLast(10);
      correct.addLast(15);
      correct.addLast(20);

      Bug.addLast(10);
      Bug.addLast(15);
      Bug.addLast(20);

      //test the size of the two class
      assertEquals(correct.size(),Bug.size());

      //test remove function
      assertEquals(correct.removeLast(),Bug.removeLast());
      assertEquals(correct.removeLast(),Bug.removeLast());
      assertEquals(correct.removeLast(),Bug.removeLast());
  }
//  @Test
//    public void randomizedTest(){
//      AListNoResizing<Integer> L = new AListNoResizing<>();
//
//      int N = 500;
//      for (int i = 0; i < N; i += 1) {
//          int operationNumber = StdRandom.uniform(0, 4);
//          if (operationNumber == 0) {
//              // addLast
//              int randVal = StdRandom.uniform(0, 100);
//              L.addLast(randVal);
//              System.out.println("addLast(" + randVal + ")");
//          } else if (operationNumber == 1) {
//              // size
//              int size = L.size();
//              System.out.println("size: " + size);
//          } else if (operationNumber == 2) {
//              //getLast
//              if(L.size()==0){
//                  continue;
//              }
//
//              int last = L.getLast();
//              System.out.println("last: " + last);
//          } else if (operationNumber == 3) {
//              //removeLast
//              if(L.size()==0){
//                  continue;
//              }
//              L.removeLast();
//              System.out.println("remove: " + L.getLast());
//          }
//      }
//  }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                //size
                assertEquals(correct.size(),broken.size());
            }else if (operationNumber==2){
                //getLast
               if (correct.size()==0||broken.size()==0){
                   continue;
               }

               assertEquals(correct.getLast(),broken.getLast());
            }else {
                //removelast
                if (correct.size()==0||broken.size()==0){
                    continue;
                }

                assertEquals(correct.removeLast(),broken.removeLast());
            }
        }
    }

}
