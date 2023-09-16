package matrix_multiplication;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class Matrix {
    private int rows = 0;
    private int columns = 0;
    private double[] data = null; /* internal flat data array */
    private int firstIdx = 0; /* flat index of the first entry (top-left) */
    private int stride = 0; /*
                             * row length (i.e. how many indices must be advanced in the flat array to get
                             * to the same column in the next row)
                             */

    /**
     * Constructs an empty matrix (by keeping the default values on all fields)
     */
    public Matrix() {
    }

    /**
     * Construct a zero-initialized matrix of a certain size.
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        data = new double[rows * columns];
        stride = columns;
    }

    /**
     * Initialize a matrix of certain shape from a flat array. The data is *not*
     * copied.
     */
    public Matrix(double[] data, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = data;
        stride = columns;
    }

    /**
     * Initialize a submatrix view from a flat array. The data is *not* copied.
     */
    public Matrix(double[] data, int rows, int columns, int rowLength, int firstIdx) {
        this.rows = rows;
        this.columns = columns;
        this.data = data;
        stride = rowLength;
        this.firstIdx = firstIdx;
    }

    /**
     * Construct a matrix from the data given as a array of arrays (useful for
     * initializing matrix from literal data).
     */
    Matrix(double[][] initialData) {
        rows = initialData.length;
        columns = initialData[0].length;
        data = new double[rows * columns];
        stride = columns;
        firstIdx = 0;
        int k = 0;
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < columns; ++j)
                data[k++] = initialData[i][j];
    }

    /**
     * Adds the right hand operand to the left hand matrix in-place.
     */
    public void add(Matrix that) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                this.set(i, j, this.get(i, j) + that.get(i, j));
            }
    }

    /**
     * Returns a copy that is the sum of the two operands
     */
    public static Matrix add(Matrix A, Matrix B) {
        Matrix C = new Matrix(A.getColumns(), A.getColumns());
        for (int i = 0; i < A.getColumns(); i++) {
            for (int j = 0; j < A.getColumns(); j++) {
                C.set(i, j, A.get(i, j) + B.get(i, j));
            }

        }
        /* fill in the implementation */
        return C;
    }

    /**
     * Returns a copy that is the difference of the two operands
     */
    public static Matrix subtract(Matrix A, Matrix B) {
        Matrix C = new Matrix(A.getColumns(), A.getColumns());
        for (int i = 0; i < A.getColumns(); i++) {
            for (int j = 0; j < A.getColumns(); j++) {
                C.set(i, j, A.get(i, j) - B.get(i, j));
            }

        }
        /* fill in the implementation */
        return C;
    }

    /**
     * Subtracts the right hand operand from the left hand matrix in-place
     */
    public void subtract(Matrix that) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                this.set(i, j, this.get(i, j) - that.get(i, j));
            }
    }

    /**
     * Returns the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Return the element at the index.
     */
    public double get(int i, int j) {
        return data[firstIdx + i * stride + j];
    }

    /**
     * Set the element at the index.
     */
    public void set(int i, int j, double d) {
        data[firstIdx + i * stride + j] = d;
    }

    /**
     * Construct a shallow view into a matrix. Data is not copied, but the original
     * matrix can be accessed from the new Matrix object.
     */
    public Matrix getSubmatrix(int i0, int j0, int rows, int columns) {
        return new Matrix(data, rows, columns, stride, firstIdx + i0 * stride + j0);
    }

    /**
     * Equality comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Matrix))
            return false;
        Matrix that = (Matrix) o;
        if (getRows() != that.getRows())
            return false;
        if (getColumns() != that.getColumns())
            return false;
        for (int i = 0; i < getRows(); ++i)
            for (int j = 0; j < getColumns(); ++j)
                if (get(i, j) != that.get(i, j))
                    return false;
        return true;
    }

    /**
     * Returns a nice string representation of the matrix
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getRows(); ++i) {
            if (i > 0)
                sb.append('\n');
            for (int j = 0; j < getColumns(); ++j) {
                if (j > 0)
                    sb.append(" ");
                sb.append(get(i, j));
            }
        }
        return sb.toString();
    }

    /**
     * Returns a deep copy of the matrix.
     */
    public Matrix copy() {
        double[] newData = new double[rows * columns];
        int k = 0;
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < columns; ++j)
                newData[k++] = data[firstIdx + i * stride + j];
        return new Matrix(newData, rows, columns);
    }

    /**
     * Compute C = AB with three nested loops
     */
    public static Matrix elementaryMultiplication(Matrix A, Matrix B, int dummy) {
        int size = A.getColumns();
        Matrix C = new Matrix(size, size);
        double a;
        double b;
        double current;

        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                for (int j = 0; j < size; j++) {
                    a = A.get(i, k);
                    b = B.get(k, j);
                    C.set(i, j, C.get(i, j) + a * b);
                }
            }
        }
        return C;
    }

    /**
     * Transposes the matrix in-place
     */
    public static void transpose(Matrix A) {

        for (int i = 0; i < A.getRows(); i++) {
            for (int j = i; j < A.getColumns(); j++) {
                double oldval = A.get(i, j);
                A.set(i, j, A.get(j, i));
                A.set(j, i, oldval);
            }
        }

    }

    public double[] getData() {
        return data;
    }

    /**
     * Compute C = AB with three nested loops, assuming transposed B
     */
    public static Matrix elementaryMultiplicationTransposed(Matrix A, Matrix B, int dummy) {
        // TODO:Commented out since it does not seem correct...
        // transpose(B);
        // return elementaryMultiplication(A, B);
        Matrix B_T = B.copy();
        transpose(B_T);
        int size = A.getRows();
        double a;
        double b;
        double current;
        Matrix C = new Matrix(size, size);
        for (int i = 0; i < size; i++) { // ROW of A
            for (int j = 0; j < size; j++) { // j is ROW of B in this scope
                current = 0.0;
                for (int k = 0; k < size; k++) { // Column of A and B
                    a = A.get(i, k);
                    b = B_T.get(j, k);
                    current += a * b;
                }
                C.set(i, j, current); // j is column of C in this scope
            }

        }
        return C;
    }

    /**
     * Computes C=AB using (n/s)^3 multiplications of size s*s
     */
    public static Matrix tiledMultiplication(Matrix A, Matrix B, int s) {
        Matrix C = new Matrix(A.getColumns(), B.getColumns());

        int nDivided = A.getColumns() / s;
        int n = A.getColumns();

        for (int i = 0; i < n; i += nDivided) {
            for (int j = 0; j < n; j += nDivided) {
                for (int k = 0; k < n; k += nDivided) {
                    elementaryMultiplicationInPlace(
                            C.getSubmatrix(i, j, nDivided, nDivided),
                            A.getSubmatrix(i, k, nDivided, nDivided),
                            B.getSubmatrix(k, j, nDivided, nDivided));
                }
            }
        }
        return C;
    }

    /**
     * Computes C=AB by explicitly writing all intermediate results. That is, we
     * define the following matrices in terms of the operand block matrices:
     *
     * P0 = A00 P1 = A01 P2 = A00 P3 = A01 P4 = A10 P5 = A11 P6 = A10 P7 = A11 Q0 =
     * B00 Q1 = B10 Q2 = B01 Q3 = B11 Q4 = B00 Q5 = B10 Q6 = B01 Q7 = B11
     *
     * Then compute Mi = Pi*Qi by a recursive application of the function
     *
     * Followed by the integration C00 = M0 + M1 C01 = M2 + M3 C10 = M4 + M5 C11 =
     * M6 + M7
     */

    public static Matrix recursiveMultiplicationCopying(Matrix A, Matrix B, int m) {
        int n = A.getColumns();
        int nDiv = n / 2;

        if (n <= m) {
            return elementaryMultiplication(A, B,0);
        } else {
            Matrix C = new Matrix(n, n);
            Matrix[] P = new Matrix[8];
            Matrix[] Q = new Matrix[8];
            Matrix[] M = new Matrix[8];

            P[0] = A.getSubmatrix(0, 0, nDiv, nDiv);
            P[1] = A.getSubmatrix(0, nDiv, nDiv, nDiv);
            P[2] = P[0];
            P[3] = P[1];
            P[4] = A.getSubmatrix(nDiv, 0, nDiv, nDiv);
            P[5] = A.getSubmatrix(nDiv, nDiv, nDiv, nDiv);
            P[6] = P[4];
            P[7] = P[5];

            Q[0] = B.getSubmatrix(0, 0, nDiv, nDiv);
            Q[1] = B.getSubmatrix(nDiv, 0, nDiv, nDiv);
            Q[2] = B.getSubmatrix(0, nDiv, nDiv, nDiv);
            Q[3] = B.getSubmatrix(nDiv, nDiv, nDiv, nDiv);
            Q[4] = Q[0];
            Q[5] = Q[1];
            Q[6] = Q[2];
            Q[7] = Q[3];

            for (int i = 0; i < M.length; i++) {
                M[i] = recursiveMultiplicationCopying(P[i], Q[i], m);
            }

            Matrix C00 = add(M[0], M[1]);
            Matrix C01 = add(M[2], M[3]);
            Matrix C10 = add(M[4], M[5]);
            Matrix C11 = add(M[6], M[7]);

            C.getSubmatrix(0, 0, nDiv, nDiv).add(C00);
            C.getSubmatrix(0, nDiv, nDiv, nDiv).add(C01);
            C.getSubmatrix(nDiv, 0, nDiv, nDiv).add(C10);
            C.getSubmatrix(nDiv, nDiv, nDiv, nDiv).add(C11);
            return C;
        }

    }

    /**
     * An auxiliary function that computes elementary matrix multiplication in
     * place, that is, the operation is C += AB such that the product of AB is added
     * to matrix C.
     */
    public static void elementaryMultiplicationInPlace(Matrix C, Matrix A, Matrix B) {
        int n = A.getColumns();
        int m = B.getColumns();
        int p = C.getColumns();

        double a = 0.0;
        double b = 0.0;

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < p; k++) {
                for (int j = 0; j < m; j++) {
                    a = A.get(i, k);
                    b = B.get(k, j);
                    C.set(i, j, C.get(i, j) + a * b);
                }
            }
        }

    }

    /**
     * Computes C=AB recursively using a write-through strategy. That is, no
     * intermediate copies are created; the matrix C is initialized as the function
     * is first called, and all updates are done in-place in the recursive calls.
     * 
     * The parameter m controls such that when the subproblem size satisfies n <= m,
     * * an iterative cubic algorithm is called instead.
     */

    public static Matrix recursiveMultiplicationWriteThrough(Matrix A, Matrix B,
            int m) {
        int n = A.getColumns();
        Matrix C = new Matrix(n, n);
        recursiveMultiplicationWriteThroughInner(C, A, B, m);
        return C;
    }

    public static void recursiveMultiplicationWriteThroughInner(Matrix C, Matrix A, Matrix B, int m) {
        int n = A.getColumns();
        if (n <= m) {
            elementaryMultiplicationInPlace(C, A, B);
        } else {
            int nDiv = n / 2;

            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(0, 0, nDiv, nDiv), A.getSubmatrix(0, 0, nDiv, nDiv),
                    B.getSubmatrix(0, 0, nDiv, nDiv), m);
            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(0, 0, nDiv, nDiv),
                    A.getSubmatrix(0, nDiv, nDiv, nDiv), B.getSubmatrix(nDiv, 0, nDiv, nDiv), m);
            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(0, nDiv, nDiv, nDiv),
                    A.getSubmatrix(0, 0, nDiv, nDiv), B.getSubmatrix(0, nDiv, nDiv, nDiv), m);
            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(0, nDiv, nDiv, nDiv),
                    A.getSubmatrix(0, nDiv, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv), m);
            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(nDiv, 0, nDiv, nDiv),
                    A.getSubmatrix(nDiv, 0, nDiv, nDiv), B.getSubmatrix(0, 0, nDiv, nDiv), m);
            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(nDiv, 0, nDiv, nDiv),
                    A.getSubmatrix(nDiv, nDiv, nDiv, nDiv), B.getSubmatrix(nDiv, 0, nDiv, nDiv), m);
            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(nDiv, nDiv, nDiv, nDiv),
                    A.getSubmatrix(nDiv, 0, nDiv, nDiv), B.getSubmatrix(0, nDiv, nDiv, nDiv), m);
            recursiveMultiplicationWriteThroughInner(C.getSubmatrix(nDiv, nDiv, nDiv, nDiv),
                    A.getSubmatrix(nDiv, nDiv, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv), m);
        }
        // return C;
    }

    /**
     * Computes C=AB using Strassen's algorithm. The structure ought to be similar
     * to the copying recursive algorithm. The parameter m controls when the routine
     * falls back to a cubic algorithm, as the subproblem size satisfies n <= m.
     */

    public static Matrix strassen(Matrix A, Matrix B, int m) {
        int n = A.getColumns();
        int nDiv = n / 2;

        if (n <= m) {
            return elementaryMultiplication(A, B,0);
            // double data = A.get(0, 0) * B.get(0, 0);
            // return new Matrix(new double[] { data }, n, n);
        } else {
            Matrix C = new Matrix(n, n);

            Matrix[] P = new Matrix[7];
            Matrix[] Q = new Matrix[7];
            Matrix[] M = new Matrix[7];

            P[0] = add(A.getSubmatrix(0, 0, nDiv, nDiv), A.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
            P[1] = add(A.getSubmatrix(nDiv, 0, nDiv, nDiv), A.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
            P[2] = A.getSubmatrix(0, 0, nDiv, nDiv);
            P[3] = A.getSubmatrix(nDiv, nDiv, nDiv, nDiv);
            Q[0] = add(B.getSubmatrix(0, 0, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
            Q[1] = B.getSubmatrix(0, 0, nDiv, nDiv);
            Q[2] = subtract(B.getSubmatrix(0, nDiv, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
            Q[3] = subtract(B.getSubmatrix(nDiv, 0, nDiv, nDiv), B.getSubmatrix(0, 0, nDiv, nDiv));

            P[4] = add(A.getSubmatrix(0, 0, nDiv, nDiv), A.getSubmatrix(0, nDiv, nDiv, nDiv));
            P[5] = subtract(A.getSubmatrix(nDiv, 0, nDiv, nDiv), A.getSubmatrix(0, 0, nDiv, nDiv));
            P[6] = subtract(A.getSubmatrix(0, nDiv, nDiv, nDiv), A.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
            Q[4] = B.getSubmatrix(nDiv, nDiv, nDiv, nDiv);
            Q[5] = add(B.getSubmatrix(0, 0, nDiv, nDiv), B.getSubmatrix(0, nDiv, nDiv, nDiv));
            Q[6] = add(B.getSubmatrix(nDiv, 0, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv));

            for (int i = 0; i < M.length; i++) {
                M[i] = strassen(P[i], Q[i], m);
            }

            C.getSubmatrix(0, 0, nDiv, nDiv).add((subtract(add(M[0], add(M[3], M[6])), M[4])));
            C.getSubmatrix(0, nDiv, nDiv, nDiv).add(add(M[2], M[4]));
            C.getSubmatrix(nDiv, 0, nDiv, nDiv).add(add(M[1], M[3]));
            C.getSubmatrix(nDiv, nDiv, nDiv, nDiv).add((subtract(add(M[0], add(M[2], M[5])), M[1])));

            return C;
        }

    }

    public static Matrix strassenParallel(Matrix A, Matrix B, int m) {
        int n = A.getColumns();
        RecTask startup_task = new RecTask(A, B, m);
        ForkJoinPool pool = new ForkJoinPool();
        Matrix FJ = pool.invoke(startup_task);
        return FJ;
    }

    public static class RecTask extends RecursiveTask<Matrix> {

        private Matrix A;
        private Matrix B;
        private int m;

        public RecTask(Matrix A, Matrix B, int m) {

            this.A = A;
            this.B = B;
            this.m = m;

        }

        @Override
        protected Matrix compute() {
            int n = A.getColumns();
            int nDiv = n / 2;

            if (n <= m) {
                return elementaryMultiplication(this.A, this.B,0);
            } else {
                Matrix C = new Matrix(n, n);
                Matrix[] P = new Matrix[7];
                Matrix[] Q = new Matrix[7];
                Matrix[] M = new Matrix[7];
                RecTask[] task = new RecTask[7];

                P[0] = add(A.getSubmatrix(0, 0, nDiv, nDiv), A.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
                P[1] = add(A.getSubmatrix(nDiv, 0, nDiv, nDiv), A.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
                P[2] = A.getSubmatrix(0, 0, nDiv, nDiv);
                P[3] = A.getSubmatrix(nDiv, nDiv, nDiv, nDiv);
                Q[0] = add(B.getSubmatrix(0, 0, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
                Q[1] = B.getSubmatrix(0, 0, nDiv, nDiv);
                Q[2] = subtract(B.getSubmatrix(0, nDiv, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
                Q[3] = subtract(B.getSubmatrix(nDiv, 0, nDiv, nDiv), B.getSubmatrix(0, 0, nDiv, nDiv));

                P[4] = add(A.getSubmatrix(0, 0, nDiv, nDiv), A.getSubmatrix(0, nDiv, nDiv, nDiv));
                P[5] = subtract(A.getSubmatrix(nDiv, 0, nDiv, nDiv), A.getSubmatrix(0, 0, nDiv, nDiv));
                P[6] = subtract(A.getSubmatrix(0, nDiv, nDiv, nDiv), A.getSubmatrix(nDiv, nDiv, nDiv, nDiv));
                Q[4] = B.getSubmatrix(nDiv, nDiv, nDiv, nDiv);
                Q[5] = add(B.getSubmatrix(0, 0, nDiv, nDiv), B.getSubmatrix(0, nDiv, nDiv, nDiv));
                Q[6] = add(B.getSubmatrix(nDiv, 0, nDiv, nDiv), B.getSubmatrix(nDiv, nDiv, nDiv, nDiv));

                for (int i = 0; i < M.length; i++) {
                    task[i] = new RecTask(P[i], Q[i], this.m);
                }
                for (int i = 0; i < M.length; i++) {
                    task[i].fork();
                }
                for (int i = 0; i < M.length; i++) {
                    M[i] = task[i].join();
                }

                C.getSubmatrix(0, 0, nDiv, nDiv).add((subtract(add(M[0], add(M[3], M[6])), M[4])));
                C.getSubmatrix(0, nDiv, nDiv, nDiv).add(add(M[2], M[4]));
                C.getSubmatrix(nDiv, 0, nDiv, nDiv).add(add(M[1], M[3]));
                C.getSubmatrix(nDiv, nDiv, nDiv, nDiv).add((subtract(add(M[0], add(M[2], M[5])), M[1])));
                return C;

            }
        }
    }

    private static class Timer {
        private long start, spent = 0;

        public Timer() {
            play();
        }

        public double check() {
            return (System.nanoTime() - start + spent) / 1e9;
        }

        public void pause() {
            spent += System.nanoTime() - start;
        }

        public void play() {
            start = System.nanoTime();
        }
    }

    public static void main(String[] args) {
        // Matrix A = new Matrix(
        // new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0,
        // 13.0, 14.0, 15.0, 16.0 },
        // 4, 4);
        // Matrix B = new Matrix(
        // new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0,
        // 13.0, 14.0, 15.0, 16.0 },
        // 4, 4);
        //
        // System.out.println(Arrays.toString(elementaryMultiplication(A,
        // B).getData()));
        // System.out.println(Arrays.toString(tiledMultiplication(A, B, 2).getData()));
        // System.out.println(Arrays.toString(recursiveMultiplicationWriteThrough(A, B,
        // 6).getData()));
        // System.out.println(Arrays.toString(recursiveMultiplicationCopying(A,
        // B).getData()));
        // System.out.println(Arrays.toString(strassen(A, B, 2).getData()));
        // System.out.println(Arrays.toString(strassenParallel(A, B, 2).getData()));

        Matrix A = Generator.randomMatrix(4096);
        Matrix B = Generator.randomMatrix(4096);

        double runningTime = 0.0;
        Timer t = new Timer();
        Matrix C = strassenParallel(A, B, 64);
        runningTime = t.check();
        System.out.println(String.format("Running time: %.2f s", runningTime));
        //
        //
        //
        //
        //
    }
}
