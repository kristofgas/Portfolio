package matrix_multiplication;
public class Record{
    
    private final String name;
    private final int n;
    private final double mean, sdev, perOper; 

    public Record(String name, int n, double mean, double sdev, double perOper){
      this.name = name;
      this.n = n;
      this.mean = mean;
      this.sdev = sdev;
      this.perOper = perOper;
    }

    public String name(){
      return this.name;
    }
    public int n(){
      return this.n;
    }

    public double mean(){
      return this.mean;
    }

    public double sdev(){
      return this.mean;
    }

    public double perOper(){
      return this.perOper;
    }
}
