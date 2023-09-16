package matrix_multiplication;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Generator {
  
  private static final long seed = System.currentTimeMillis();
  private static Random rng = new Random(seed);
  private static final int mantis = (int) Math.pow(2.0,53.0);
  private static final int LOWER = 0;


  public static Matrix randomMatrix(int n){
    double[] data = randArray(n);
    return new Matrix(data,n,n);
  }

  public static Matrix matrixOfNumber(int n, double number){
    double[] data = arrayOfNumber(n,(int) number);
    return new Matrix(data,n,n);
  }

  public static Matrix[] randomMatrixSet(int n){

    double[] data = randArray(n);
    double[] negated = Arrays.stream(data).map((x) -> (-x)).toArray();
    Matrix[] matrices =  new Matrix[2];
    matrices[0] = new Matrix(data,n,n);
    matrices[1] = new Matrix(negated,n,n);
    return matrices;
  }

  public static double[] arrayOfNumber(int n, int number){
    return rng.ints().limit(n*n).map((x) -> (number)).asDoubleStream().toArray();
  }

  public static double[] randArray(int n){
    int UPPER = (int) Math.sqrt(mantis/n);
    return rng.ints(LOWER,UPPER).limit(n*n).asDoubleStream().toArray();
  }

}
