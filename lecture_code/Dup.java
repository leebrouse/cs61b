import java.util.List;
import java.util.ArrayList;

public class Dup {
    public  static  boolean dup(int[]A){
        for (int i = 0; i <A.length-1 ; i++) {
            if(A[i]==A[i+1]){
                return  true;
            }
        }
        return  false;
    }

    public static  int [] makearray(int N){
        int[] A=new int[N];
        for (int i = 0; i <N ; i++) {
            A[i]=i;
        }
        return A;
    }

    public static void main(String[] args) {
        int N=Integer.parseInt(args[0]);
        int[] A=makearray(N);
        dup(A);
    }
}
