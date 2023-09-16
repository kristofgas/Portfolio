package matrix_multiplication;

import java.io.File ;
import java.io.FileNotFoundException ;
import java.io.PrintWriter ;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

class CacheSize {

  private static int seed = 52;
  private static Random rng = new Random(seed);
  private static List<Record> results = new ArrayList<>(); 


/// *Timer* class is taken from "Microbenchmarks in Java and C#" by Peter Sestoft
private static class Timer {
  private long start, spent = 0;
  public Timer() { play(); }
  public double check() { return (System.nanoTime()-start+spent)/1e9; }
  public void pause() { spent += System.nanoTime()-start; }
  public void play() { start = System.nanoTime(); }
}


public static double[] getDoubles(int n){
    return rng.doubles().limit(n).toArray();
}

public static double lookUp(double[] doubles,int range){
    int index = rng.nextInt(range);
    return doubles[index];
}

public static void toCSV(String filename){
    File f = new File(filename);
    try (PrintWriter pw = new PrintWriter(f);){
      for (Record r : results){
       pw.println(String.format("%-25s,%15.1f,%10.2f,%10d,%10.2f",r.name(),r.mean(),r.sdev(), r.n(),r.perOper()));
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


/// *Mark7* is taken from "Microbenchmarks in Java and C#" by Peter Sestoft
public static double Mark7(String msg, BiFunction<double[],Integer,Double> f) {
    int n = 10, count = 2, totalCount = 0;
    double dummy =0.0;
    double runningTime = 0.0, st = 0.0, sst = 0.0;
    do {
      count *= 2;
      double[] doubles = getDoubles(count);   
      int lookUps = 100_000_000;
      st = sst = 0.0;
      for (int j=0; j<n; j++) {
        Timer t = new Timer();
        for (int i=0; i<lookUps; i++)
          dummy += f.apply(doubles,count);//perform i look ups
        runningTime = t.check();
        double time = runningTime * 1e9;
        st += time;
        sst += time * time;
        totalCount += count;
      }
      double mean = st/n, sdev = Math.sqrt((sst - mean*mean*n)/(n-1)), perLookUp =mean/lookUps;
      results.add(new Record("CacheSize",count,mean,sdev,perLookUp));
      System.out.printf("%-25s %15.1f ns %10.2f %10d %10.2f ns/oper%n", msg, mean, sdev, count, perLookUp);
    } while (runningTime < 5*60 && count < 4_194_304);
    return dummy;
}


public static void main (String[] args){

 System.out.println(Mark7("Cachesize", CacheSize::lookUp));
 toCSV("CacheSize.csv");
 



}


}
