package matrix_multiplication;

public class Main {

        public static void main(String[] args) throws Exception { //
                // The Main method will generate all tables, reproducing all results for the
                // experiments.
                int N = 20;
                int fixM = 64;
                int maxValue = 128; // makes max dimension 2048 inside the calculations in benchmark
                int maxDim = 2048; // makes max dimension 2048 inside the calculations in benchmark

                int max_m = (int) Math.pow(2, (int) Math.sqrt(maxValue)) / 2; // the half of eg. max dimension (2048)=//
                                                                              // 1024

                // results for increasing m from 2 to 1024, and dimensions fixed to 2048
                Toolchain.writeLatexTabular_Fix_Dimensions(maxDim, N,
                                Matrix::recursiveMultiplicationCopying,
                                max_m,
                                "recursive_copying_increasing_m_fix_dimensions(" + max_m + ").tex",
                                "m");
                Toolchain.writeCsv_Fix_Dimensions(maxDim,
                                Matrix::recursiveMultiplicationCopying, N, max_m,
                                ("recursive_copying_increasing_m_fix_dimensions(" + max_m + ")"), "m");

                Toolchain.writeCsv_Fix_Dimensions(maxDim, Matrix::strassen, N, max_m,
                                ("strassen_increasing_m_fix_dimensions(" + max_m + ")"), "m");

                Toolchain.writeLatexTabular_Fix_Dimensions(maxDim, N, Matrix::strassen,
                                max_m,
                                "strassen_increasing_m_fix_dimensions(" + max_m + ").tex",
                                "m");
                Toolchain.writeCsv_Fix_Dimensions(maxDim,
                                Matrix::strassenParallel, N, max_m,
                                ("strassenParallel_increasing_m_fix_dimensions(" + max_m + ")"), "m");

                Toolchain.writeLatexTabular_Fix_Dimensions(maxDim, N,
                                Matrix::strassenParallel, max_m,
                                "strasseParalleln_increasing_m_fix_dimensions(" + max_m + ").tex",
                                "m");
                Toolchain.writeCsv_Fix_Dimensions(maxDim,
                                Matrix::recursiveMultiplicationWriteThrough, N, max_m,
                                ("recursive_increasing_m_fix_dimensions(" + max_m + ")"), "m");

                Toolchain.writeLatexTabular_Fix_Dimensions(maxDim, N,
                                Matrix::recursiveMultiplicationWriteThrough, max_m,
                                "recursive_increasing_m_fix_dimensions(" + max_m + ").tex",
                                "m");

                Toolchain.writeCsv_Fix_Dimensions(maxDim,
                                Matrix::tiledMultiplication, N, max_m,
                                ("tiled_increasing_s_fix_dimensions(" + max_m + ")"), "s");

                Toolchain.writeLatexTabular_Fix_Dimensions(maxDim, N,
                                Matrix::tiledMultiplication, max_m,
                                "tiled_increasing_s_fix_dimensions(" + max_m + ").tex",
                                "m");

                Toolchain.writeLatexTabular_Fix_Dimensions(maxDim, N, Matrix::elementaryMultiplication,
                                max_m,
                                "elementary_fix_dimensions(" + max_m + ").tex",
                                "m");
                Toolchain.writeCsv_Fix_Dimensions(maxDim,
                                Matrix::elementaryMultiplicationTransposed, N, max_m,
                                ("elementary_transposed_fix_dimensions(" + max_m + ")"), "m");





                                
                // results for m fixed to 64, and dimensions going from 2 to 2048

                Toolchain.writeLatexTabular_Fix_m(maxValue, N,
                                Matrix::elementaryMultiplication, fixM,
                                "elementary_increasing_dimensions(" + max_m + ").tex");
                Toolchain.writeCsv_Fix_m(maxValue, Matrix::elementaryMultiplication, N,
                                max_m,
                                ("elementary_increasing_dimensions(" + max_m + ")"), fixM);

                Toolchain.writeLatexTabular_Fix_m(maxValue, N,
                                Matrix::tiledMultiplication, 2,
                                "tiled_Fix_s_increasing_dimensions(" + max_m + ").tex");

                Toolchain.writeLatexTabular_Fix_m(maxValue, N,
                                Matrix::elementaryMultiplicationTransposed, fixM,
                                "elementary_transposed_increasing_dimensions(" + max_m + ").tex");
                Toolchain.writeCsv_Fix_m(maxValue,
                                Matrix::elementaryMultiplicationTransposed, N, max_m,
                                ("elementary_transposed_increasing_dimensions(" + max_m + ")"), fixM);
                Toolchain.writeLatexTabular_Fix_m(maxValue, N,
                                Matrix::recursiveMultiplicationCopying, fixM,
                                "recursive_coying_Fix_m_increasing_dimensions(" + max_m + ").tex");
                Toolchain.writeCsv_Fix_m(maxValue, Matrix::recursiveMultiplicationCopying, N,
                                max_m,
                                ("recursive_coying_Fix_m_increasing_dimensions(" + max_m + ")"), fixM);
                Toolchain.writeLatexTabular_Fix_m(maxValue, N, Matrix::strassen, fixM,
                                "strassen_Fix_m_increasing_dimensions(" + max_m + ").tex");
                Toolchain.writeCsv_Fix_m(maxValue, Matrix::strassen, N, max_m,
                                ("strassen_Fix_m_increasing_dimensions(" + max_m + ")"), fixM);

                Toolchain.writeLatexTabular_Fix_m(maxValue, N,
                                Matrix::strassenParallel, fixM,
                                "strassenParallel_Fix_m_increasing_dimensions(" + max_m + ").tex");
                Toolchain.writeCsv_Fix_m(maxValue, Matrix::strassenParallel,
                                N, max_m,
                                ("strassenParallel_Fix_m_increasing_dimensions(" + max_m + ")"), fixM);
                Toolchain.writeLatexTabular_Fix_m(maxValue, N,
                                Matrix::recursiveMultiplicationWriteThrough, fixM,
                                "recursive_Fix_m_increasing_dimensions(" + max_m + ").tex");
                Toolchain.writeCsv_Fix_m(maxValue,
                                Matrix::recursiveMultiplicationWriteThrough,
                                N, max_m,
                                ("recursive_Fix_m_increasing_dimensions(" + max_m + ")"), fixM);

                Toolchain.writeLatexTabular_Fix_m(maxValue, N,
                                Matrix::transpose, fixM,
                                "transpose_increasing_dimensions(" + max_m + ").tex");

        }
}
