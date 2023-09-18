
class Record {


  private final int n;
  private final int m;
  private final double sigma;
  private double[] results;


  public Record(int n, int m, double[] data){
    this.n = n;
    this.m = m;
    this.sigma = 1.04/Math.sqrt((double)m);
    this.results = data;
    
  }

  private int getSigmaN(int m){
    int count = 0;
    for (double r : results){
      if ((double) n*(1-m*sigma) <= r && r <= (double) n*(1+m*sigma)) count++;
      // if (((double) n - sigma*m) <= r && r <= ((double) n + sigma*m)) count++;
    }
    return count;
  }
  
  public int getSigma1() {
    return getSigmaN(1);
  }

  public int getSigma2() {
    return getSigmaN(2);
  }

  public int getN() {
    return this.n;
  }

  public int getM() {
    return this.m;
  }
  }
