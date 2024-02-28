package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static double times(int N){
        Stopwatch t=new Stopwatch();
        AList<Integer> p=new AList<>();
        for (int i=0;i<N;i++){
            p.addLast(i);
        }
        double timeInSeconds = t.elapsedTime();
        return timeInSeconds;
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
            System.out.println("Timing table for addLast");
            AList<Integer> Ns=new AList<>();
            Ns.addLast(1000);
            Ns.addLast(2000);
            Ns.addLast(4000);
            Ns.addLast(8000);
            Ns.addLast(16000);
            Ns.addLast(32000);
            Ns.addLast(64000);
            Ns.addLast(128000);

            AList<Double> times=new AList<>();
            //computer times
            times.addLast(times(1000));
            times.addLast(times(2000));
            times.addLast(times(4000));
            times.addLast(times(8000));
            times.addLast(times(16000));
            times.addLast(times(32000));
            times.addLast(times(64000));
            times.addLast(times(128000));


            AList<Integer> opCounts =new AList<>();
            opCounts.addLast(1000);
            opCounts.addLast(2000);
            opCounts.addLast(4000);
            opCounts.addLast(8000);
            opCounts.addLast(16000);
            opCounts.addLast(32000);
            opCounts.addLast(64000);
            opCounts.addLast(128000);

            printTimingTable(Ns,times,opCounts);



    }
}
