package matrix_multiplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Write {
 
    // Use this method for fixed s but increasing dimensions of matrices
    protected static void writeLatexTabular_Fix_S(int fix_s, int[] ns, double[][] res, String filename) {

        File f = new File(filename); //put it in current directory
        
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("\\begin{tabular}{rrr}"+"\\");
            pw.println("m = " + fix_s);
            pw.println(" $n$ & Average & Standard deviation " + " \\\\");
            for (int i = 0; i < ns.length; ++i) {
                String[] fields = new String[] { Integer.toString(ns[i]), String.format(" %.6f", res[i][0]),
                        String.format(" %.6f", res[i][1]) };
                pw.println(String.join(" & ", fields) + " \\\\ ");
            }
            pw.println("\\end{tabular}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static void writeLatexTabular_Fix_Dimensions(int fix_dimensions, int[] ns, double[][] res, String filename, String changing_parameter) {

        File f = new File(filename); //put it in current directory
        
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("\\begin{tabular}{rrr}"+"\\");
            pw.println(" $" + changing_parameter + "$ & Average & Standard deviation " + " \\\\");
            for (int i = 0; i < ns.length; ++i) {
                String[] fields = new String[] { Integer.toString(ns[i]), String.format(" %.6f", res[i][0]),
                        String.format(" %.6f", res[i][1]) };
                pw.println(String.join(" & ", fields) + " \\\\ ");
            }
            pw.println("\\end{tabular}");
            pw.println("\\begin{tablenotes}");
            pw.println("\\item[1] N has been fixed to size " + (int) Math.pow(2, (int) Math.sqrt(fix_dimensions)));
            pw.println("\\end{tablenotes}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static void writeCsv_Fix_Dimensions(int[] ns, double[][] res, String filename, String changing_parameter) {
        File f = new File(filename);
        try (PrintWriter pw = new PrintWriter(f);) {
            String[] header = {changing_parameter, "Average", "Standard deviation"};
            pw.println(String.join(",", header));
            for (int i = 0; i < ns.length; ++i) {
                String[] fields = new String[] { Integer.toString(ns[i]), String.format(" %.17f", res[i][0]),
                        String.format(" %.17f", res[i][1]) };
                pw.println(String.join(",", fields));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static void writeCsv_Fix_m(int[] ns, double[][] res, String filename,int fix_s) {
        File f = new File(filename);
        try (PrintWriter pw = new PrintWriter(f);) {
            String[] header = {"Dimensions", "Average", "Standard deviation"};
            pw.println(String.join(",", header));
            for (int i = 0; i < ns.length; ++i) {
                String[] fields = new String[] { Integer.toString(ns[i]), String.format(" %.17f", res[i][0]),
                        String.format(" %.17f", res[i][1]) };
                pw.println(String.join(",", fields));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
