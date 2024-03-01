// Break and Continue
public class exercise4 {
    public static void windowPosSum(int[] a, int n) {
        /** your code here */
        for (int i = 0; i < a.length; i++) {
            // Judge the number whether is negitive or not! if yes then skip it,no then go on!
            if (a[i] < 0) {
                continue;
            }

            for (int j = i; j < i + n; j++) {
                // Judege the number whether is to the end or not! if yes then quit the loop, no then execute the sum!
                if (j == a.length - 1) {
                    break;
                }

                a[i] += a[j + 1];

            }
        }
    }

    public static void main(String[] args) {
          int[] a = {1, 2, -3, 4, 5, 4};
          int n=3;
          
        // eg2:
        //   int[] a = {1, -1, -1, 10, 5, -1};
        //   int n = 2;

          windowPosSum(a, n);
      
          // Should print 4, 8, -3, 13, 9, 4
          System.out.println(java.util.Arrays.toString(a));
        }
}
