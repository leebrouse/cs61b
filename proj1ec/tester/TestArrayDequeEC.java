package tester;

import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void ErrorTest(){
        StudentArrayDeque<Integer> student= new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution=new ArrayDequeSolution<>();

        for(int i=0;i<100;i++){
            int MethodSelector = StdRandom.uniform(4);
            switch (MethodSelector){
                case 0:
                        solution.addFirst(i);
                        student.addFirst(i);
                        Integer expect=solution.get(0);
                        Integer actual=student.get(0);
                        assertEquals("student is "+actual+",correct is "+expect,expect,actual);
                        break;

                case 1:
                        solution.addLast(i);
                        student.addLast(i);
                        Integer expect1=solution.get(solution.size()-1);
                        Integer actual1=student.get(student.size()-1);
                        assertEquals("student is "+actual1+",correct is "+expect1,expect1,actual1);
                        break;

                case 2:
                    Integer expect2=solution.size() > 0 ? solution.removeFirst() : null;
                    Integer actual2=student.size() > 0 ? solution.removeFirst() : null;
                    assertEquals("student is "+actual2+",correct is "+expect2,expect2,actual2);
                    break;

                case 3:
                    Integer expect3=solution.size() > 0 ? solution.removeLast() : null;
                    Integer actual3=student.size() > 0 ? solution.removeLast() : null;
                    assertEquals("student is "+actual3+",correct is "+expect3,expect3,actual3);
                    break;

            }
        }
    }

}

