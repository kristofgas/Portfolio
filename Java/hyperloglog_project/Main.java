import java.io.IOException;

public class Main {

    // Function to calculate the
    // log base 2 of an integer
    private static int log2(int N) {
        // calculate log2 N indirectly
        // using log() method
        int result = (int) (Math.log(N) / Math.log(2));

        return result;
    }

    public static void main(String[] args) throws NumberFormatException, IOException {

        /**
         * Create table: Make estimations for different input sizes n and m values ->
         * Make estimate object for each -> Wrap all estimate objects in Record object
         * -> Put in array of Estimate objects
         */

        int[] N = { 
                1000000,1000000,1000000 ,1000000 };
        int[] M = { 64,256, 1024, 4096};

        Estimate[] estimates = new Estimate[N.length];

        for (int i = 0; i < N.length; i++) {
            Estimate estimation = new Estimate(N[i], M[i], 100); // 100 runs
            estimation.generateResults();
            estimates[i] = estimation;
        }

        Write.writeLatexTabular(estimates, "first_results.tex");

        // Estimate estimation = new Estimate(10000, 1024, 100);

        // estimation.generateResults();

        // double[] results = estimation.getResults();

        // System.out.println(Arrays.toString(results));

        System.out.println(log2(1025));
        System.out.println(log2(1023));

        /**
         * Diff 1 -> add to right shift 21 -> 22
         */

    }
}
