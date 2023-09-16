package matrix_multiplication;
import java.time.Instant;
import java.time.Duration;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Benchmark {

    Matrix[] args; // stores input matrices of different sizes of n
    int[] ns; // the powers of two increasing
    int max_n;
    int N;

   /**
    * This constructor initializes a list of matrices with increasing dimensions
    * @param max_dimension the maximum power of two dimension of input matrix (MUST BE POWER OF 2)
    * @param N the amount of times to iterate over the results
    */
    public Benchmark(int max_dimension, int N) {
        
        // InputEnum input = InputEnum.valueOf(typeOfInput);
        // Matrix A = new Matrix(new double[] { 1.0, 2.0, 3.0, 4.0 }, 2, 2); // this is for testing
        // Matrix B = new Matrix(new double[] { 5.0, 6.0, 7.0, 8.0 }, 2, 2);

        this.max_n = (int) Math.sqrt(max_dimension); // input Matrix array should be of size sqrt(max_dimension)
        this.N = N;

        ns = new int[max_n];
        args = new Matrix[max_n]; 
        for (int i = 0; i < max_n; ++i) {
            ns[i] = (int) Math.pow(2, i+1);
            // args[0] = A;
            args[i] = Generator.randomMatrix(ns[i]); // generate input matrix with i as power of two
        }
    }

   /**
    * This constructor initializes a list of matrices with FIXED dimensions and compares running time of different values of s
    * @param fix_dimensions the fixed size for the input matrices
    * @param N the amount of times to iterate over the results
    * @param max_s the maximum tile size (must be a power of 2) / or max_m
    */
    public Benchmark(int fix_dimensions, int N, int max_s) {
        
        // int length = (int) Math.sqrt(max_s); // length defines the max amount of s values to test for (as powers of 2)
        int length = (int)(Math.log(max_s) / Math.log(2)); //base 2 log
        this.max_n = fix_dimensions; 
        this.N = N;

        ns = new int[length]; // stores the different S values as powers of 2 (increasing)
        args = new Matrix[length]; // for each s value, have a matrix of fixed length
        args[0] = Generator.randomMatrix(fix_dimensions); 
        for (int i = 0; i < length; ++i) {
            ns[i] = (int) Math.pow(2, i+1);
            // args[0] = A;
           // generate input matrix with fixed dimensions
        }
    }

    public static double measure(Runnable f) {
        Instant start = Instant.now();
        f.run();
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos() / 1e9;
    }

    /**
     * Use this method when you want to compare the impact of different sizes of s on a fixed matrix size
     * @param s here, tile size s is increasing
     * @return returns the measurements
     */
    public double[][] benchmark_increasing_n( TriConsumer<Matrix,Matrix, Integer> f) {
        int x = args.length;
        double[][] M = new double[x][N];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < N; ++j) {
                Matrix A = args[0]; // Matrix A and B will have the elements for the measurement
                Matrix B = args[0];
                int m = ns[i];
                M[i][j] = measure(()-> f.accept(A,B,m));
            }
        }
        double[][] R = new double[x][2];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < N; ++j) {
                R[i][0] += M[i][j];
            }
            R[i][0] /= N;
            for (int j = 0; j < N; ++j) {
                double y = M[i][j] - R[i][0];
                R[i][1] += y * y;
            }
            R[i][1] = Math.sqrt(R[i][1] / (N - 1));
        }
        return R;
    }

    public static int[] generateInput(int n) {
        return IntStream.range(1, n + 1).toArray();
    }



    // /**
    //  * Use this method when you want to compare the running time for different matrix dimensions with fixed s
    //  * @param s here, tile size s is fixed
    //  * @return returns the measurements
    //  */
     public double[][] benchmark_fix_n(int s,TriConsumer<Matrix, Matrix, Integer> f) {
        int x = args.length;
        double[][] M = new double[x][N];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < N; ++j) {
                Matrix A = args[i]; // Matrix A and B will have the elements for the measurement
                Matrix B = args[i];
                 M[i][j] = measure(() -> f.accept(A,B,s));
             }
         }
        double[][] R = new double[x][2];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < N; ++j) {
                R[i][0] += M[i][j];
            }
            R[i][0] /= N;
            for (int j = 0; j < N; ++j) {
                double y = M[i][j] - R[i][0];
                R[i][1] += y * y;
            }
            R[i][1] = Math.sqrt(R[i][1] / (N - 1));
        }
        return R;
    }

    public double[][] benchmark_fix_n(int s,Consumer<Matrix> f) {
        int x = args.length;
        double[][] M = new double[x][N];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < N; ++j) {
                Matrix A = args[i]; // Matrix A and B will have the elements for the measurement
                 M[i][j] = measure(() -> f.accept(A));
             }
         }
        double[][] R = new double[x][2];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < N; ++j) {
                R[i][0] += M[i][j];
            }
            R[i][0] /= N;
            for (int j = 0; j < N; ++j) {
                double y = M[i][j] - R[i][0];
                R[i][1] += y * y;
            }
            R[i][1] = Math.sqrt(R[i][1] / (N - 1));
        }
        return R;
    }

    

  
    public Matrix[] getArgs() {
        return args;
    }

    public int getmax_n() {
        return max_n;
    }

    public int getN() {
        return N;
    }

    public int[] getNs() {
        return ns;
    }


}
