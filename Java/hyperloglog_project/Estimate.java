import java.io.IOException;
import java.util.BitSet;

public class Estimate {

    private int n;
    private int m;
    private double[] results;
    private int runs;
    private HyperLogLog estimator;
    private final double sigma;
    private int first_std_dev;
    private int second_std_dev;


    public Estimate(int n, int m, int runs) {
        this.sigma = 1.04/Math.sqrt((double)m);
        this.first_std_dev = 0;
        this.second_std_dev = 0;
        this.n = n;
        this.m = m;
        this.runs = runs;
        results = new double[runs];
        estimator = new HyperLogLog(m);
    }

    public void generateResults() throws NumberFormatException, IOException {

        for (int i=0; i < runs; i++) { 
            BitSet input = HashQualityEvaluation.bitInput(n, i); //generate random input of size n and i simply be seed value
            estimator.setRegistersInput(input);
            double res = estimator.estimate();
            results[i] = res;
            if ((double) n*(1-1*sigma) <= res && res <= (double) n*(1+1*sigma)) first_std_dev++;
            if ((double) n*(1-2*sigma) <= res && res <= (double) n*(1+2*sigma)) second_std_dev++;
        }
    }

    public int getN() {
        return this.n;
    }
    public int getM() {
        return this.m;
    }
    public int getRuns() {
        return this.runs;
    }
    public double[] getResults() {
        return this.results;
    }
    public int getSigma1() {
        return this.first_std_dev;
    }
    public int getSigma2() {
        return this.second_std_dev;
    }
    


}
    
    // public static void main(String[] args) throws NumberFormatException, IOException {

    //     int[] input = HashQualityEvaluation.generateInputUniqueRandom(10000, 42);

    //     HyperLogLog loglog = new HyperLogLog(1024);

    //     loglog.setRegistersInput(input);

    //     System.out.println(loglog.estimate());

    // }

