package tester;

import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class TestArrayDequeEC {
    private String ErrorShow(List<String> strings){
        StringBuilder result=new StringBuilder();
        for (String items:strings){
            result.append(items);
            result.append("\n");
        }
        return  result.toString();
    }

    @Test
    public void ErrorTest(){
        StudentArrayDeque<Integer> student= new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution=new ArrayDequeSolution<>();
        List<String> strings=new ArrayList<>();

        for(int i=0;i<100;i++){
            int MethodSelector = StdRandom.uniform(0,4);
            switch (MethodSelector){
                case 0:
                        strings.add("addFirst("+i+")");
                        solution.addFirst(i);
                        student.addFirst(i);
                        assertEquals(ErrorShow(strings),solution.get(0),student.get(0));
                        break;

                case 1:
                        strings.add("addLast("+i+")");
                        solution.addLast(i);
                        student.addLast(i);
                        assertEquals(ErrorShow(strings),solution.get(solution.size()-1),student.get(student.size()-1));
                        break;

                case 2:
                    if (solution.size()>0){
                        strings.add("removeFirst()") ;
                    }

                    assertEquals(solution.size() > 0 ? solution.removeFirst() : null
                            ,student.size() > 0 ? student.removeFirst() : null);
                    break;

                case 3:
                    if (solution.size()>0){
                        strings.add("removeLast()") ;
                    }

                    assertEquals(solution.size() > 0 ? solution.removeLast() : null
                            ,student.size() > 0 ? student.removeLast() : null);
                    break;

            }
        }
    }

}

