import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Write {

    public static void writeLatexTabular(Estimate[] estimates, String filename) {
        
        File f = new File("tests\\" + filename); //put it in the tests folder
        
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("\\begin{tabular}{rrr}"+"\\");
            pw.println(" $n$ & $m$ & 1st standard dev & 2nd standard dev " + " \\\\");
            for (int i = 0; i < estimates.length; ++i) {
                String[] fields = new String[] { Integer.toString(estimates[i].getN()), Integer.toString(estimates[i].getM()),
                        Integer.toString(estimates[i].getSigma1()), Integer.toString(estimates[i].getSigma2()) };
                pw.println(String.join(" & ", fields) + " \\\\ ");
            }
            pw.println("\\end{tabular}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
