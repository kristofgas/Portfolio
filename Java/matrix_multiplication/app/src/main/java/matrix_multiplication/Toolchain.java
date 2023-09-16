package matrix_multiplication;

import java.util.function.Consumer;

public class Toolchain {

    // /**
    // * Use this method when benchmarking for increasing dimensions but fixed s
    // *
    // * @param max_dimensions maximum dimensions
    // * @param N
    // * @param s
    // * @param filename
    // * @param inputType
    // */
    protected static void writeLatexTabular_Fix_m(int max_dimensions, int N, TriConsumer<Matrix, Matrix, Integer> f,
            int fix_s, String filename) {
        Benchmark b = new Benchmark(max_dimensions, N);
        Write.writeLatexTabular_Fix_S(fix_s, b.getNs(), b.benchmark_fix_n(fix_s, f), filename);
    }

    protected static void writeCsv_Fix_m(int fix_dimensions, TriConsumer<Matrix, Matrix, Integer> f, int N, int max_m,
            String filename, int fix_s) {
        Benchmark b = new Benchmark(fix_dimensions, N);
        Write.writeCsv_Fix_m(b.getNs(), b.benchmark_fix_n(fix_s, f), filename, fix_s);
    }

    protected static void writeLatexTabular_Fix_Dimensions(int fix_dimensions, int N,
            TriConsumer<Matrix, Matrix, Integer> f, int max_param, String filename, String changing_parameter) {
        Benchmark b = new Benchmark(fix_dimensions, N, max_param);
        Write.writeLatexTabular_Fix_Dimensions(fix_dimensions, b.getNs(), b.benchmark_increasing_n(f),
                filename, changing_parameter);

    }

    protected static void writeCsv_Fix_Dimensions(int fix_dimensions, TriConsumer<Matrix, Matrix, Integer> f, int N,
            int max_m,
            String filename, String changing_parameter) {
        Benchmark b = new Benchmark(fix_dimensions, N, max_m);
        Write.writeCsv_Fix_Dimensions(b.getNs(), b.benchmark_increasing_n(f), filename, changing_parameter);

    }

    protected static void writeLatexTabular_Fix_n_and_Dimensions(int fix_dimensions, int N,
            TriConsumer<Matrix, Matrix, Integer> f, int max_param, String filename, String changing_parameter,
            int fix_s) {
        Benchmark b = new Benchmark(fix_dimensions, N, max_param);
        Write.writeLatexTabular_Fix_Dimensions(fix_dimensions, b.getNs(), b.benchmark_fix_n(fix_s, f),
                filename, changing_parameter);

    }

    protected static void writeCsv_Fix_n_and_Dimensions(int fix_dimensions, TriConsumer<Matrix, Matrix, Integer> f,
            int N,
            int max_m,
            String filename, String changing_parameter, int fix_s) {
        Benchmark b = new Benchmark(fix_dimensions, N, max_m);
        Write.writeCsv_Fix_Dimensions(b.getNs(), b.benchmark_fix_n(fix_s, f), filename, changing_parameter);

    }

    protected static void writeLatexTabular_Fix_m(int max_dimensions, int N, Consumer<Matrix> f,
            int fix_s, String filename) {
        Benchmark b = new Benchmark(max_dimensions, N);
        Write.writeLatexTabular_Fix_S(fix_s, b.getNs(), b.benchmark_fix_n(fix_s, f), filename);
    }

    protected static void writeCsv_Fix_m(int fix_dimensions, Consumer<Matrix> f, int N, int max_m,
            String filename, int fix_s) {
        Benchmark b = new Benchmark(fix_dimensions, N);
        Write.writeCsv_Fix_m(b.getNs(), b.benchmark_fix_n(fix_s, f), filename, fix_s);
    }

}
