package matrix_multiplication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.ejml.simple.*;
import java.util.stream.*;

class MatrixTest {
  public static IntStream sizeProvider() {
    return IntStream.range(1, 9).map(x -> (int) Math.pow(2.0, (double) x));
  }

  public static boolean eqToEJML(Matrix A, SimpleMatrix B) {
    int size = A.getRows();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (!(A.get(i, j) == B.get(i, j)))
          return false;
      }
    }
    return true;
  }

  int size = 128; /// i.e. 128² elements

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class Inplace_matrix_addition {

    @Test
    public void should_be_comutative() {
      // Arrange
      Matrix A1 = Generator.matrixOfNumber(size, 6.0);
      Matrix A2 = Generator.matrixOfNumber(size, 6.0);
      Matrix B1 = Generator.matrixOfNumber(size, 12.0);
      Matrix B2 = Generator.matrixOfNumber(size, 12.0);

      // Act
      A1.add(B1);
      B2.add(A2);

      // Assert
      assertTrue(A1.equals(B2));

    }

    @Test
    public void should_be_assoiciative() {
      // Arrange
      Matrix A1 = Generator.matrixOfNumber(size, 6.0);
      Matrix A2 = Generator.matrixOfNumber(size, 6.0);
      Matrix B1 = Generator.matrixOfNumber(size, 12.0);
      Matrix B2 = Generator.matrixOfNumber(size, 12.0);
      Matrix C1 = Generator.matrixOfNumber(size, 2.0);
      Matrix C2 = Generator.matrixOfNumber(size, 2.0);

      // Act
      B1.add(C1); // A + (B + C)
      A1.add(B1);

      A2.add(B2); // (A + B) + C
      C2.add(A2);

      // Assert
      assertTrue(A1.equals(C2));

    }

    @Test
    public void should_produce_a_zero_matrix_when_added_to_its_negative_counterpart() {
      // Arrange
      Matrix[] ms = Generator.randomMatrixSet(10);
      Matrix zeroM = Generator.matrixOfNumber(10, 0.0);

      // Act
      ms[0].add(ms[1]);

      // Assert
      assertTrue(zeroM.equals(ms[0]));

    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_have_same_result_as_EJML_library(int n) {
      // Arrange
      double[] data = Generator.randArray(n);
      Matrix A = new Matrix(data, n, n);
      SimpleMatrix B = new SimpleMatrix(n, n, true, data);

      // Act
      A.add(A);
      SimpleMatrix C = new SimpleMatrix(B.plus(B));

      // Assert
      assertTrue(eqToEJML(A, C));

    }

  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class AuxilaryMatrix_matrix_addition {

    int size = 128;

    @Test
    public void should_be_commutative() {
      // Arrange
      Matrix A = Generator.matrixOfNumber(size, 6.0);
      Matrix B = Generator.matrixOfNumber(size, 12.0);

      // Act
      Matrix C1 = Matrix.add(A, B);
      Matrix C2 = Matrix.add(B, A);

      // Assert
      assertTrue(C1.equals(C2));
    }

    @Test
    public void should_be_associative() {
      // Arrange
      Matrix A = Generator.matrixOfNumber(size, 6.0);
      Matrix B = Generator.matrixOfNumber(size, 12.0);
      Matrix C = Generator.matrixOfNumber(size, 2.0);

      // Act
      Matrix D1 = Matrix.add(A, Matrix.add(B, C));
      Matrix D2 = Matrix.add(C, Matrix.add(A, B));

      // Assert
      assertTrue(D1.equals(D2));
    }

    @Test
    public void should_produce_a_zero_matrix_when_subtracted_from_it_self() {
      // Arrange
      Matrix[] ms = Generator.randomMatrixSet(10);
      Matrix zeroM = Generator.matrixOfNumber(10, 0.0);
      Matrix result = new Matrix();

      // Act
      result = Matrix.add(ms[0], ms[1]);

      // Assert
      assertTrue(zeroM.equals(result));

    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_have_same_result_as_EJML_library(int n) {
      // Arrange
      double[] data = Generator.randArray(n);
      Matrix A = new Matrix(data, n, n);
      SimpleMatrix B = new SimpleMatrix(n, n, true, data);

      // Act
      Matrix C1 = Matrix.add(A, A);
      SimpleMatrix C2 = new SimpleMatrix(B.plus(B));

      // Assert
      assertTrue(eqToEJML(C1, C2));
    }
  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class Inplace_matrix_subtraction {

    @Test
    public void should_NOT_be_comutative() {
      // Arrange
      Matrix A1 = Generator.matrixOfNumber(size, 6.0);
      Matrix A2 = Generator.matrixOfNumber(size, 6.0);
      Matrix B1 = Generator.matrixOfNumber(size, 12.0);
      Matrix B2 = Generator.matrixOfNumber(size, 12.0);

      // Act
      A1.subtract(B1);
      B2.subtract(A2);

      // Assert
      assertFalse(A1.equals(B2));

    }

    @Test
    public void should_NOT_be_assoiciative() {
      // Arrange
      Matrix A1 = Generator.matrixOfNumber(size, 6.0);
      Matrix A2 = Generator.matrixOfNumber(size, 6.0);
      Matrix B1 = Generator.matrixOfNumber(size, 12.0);
      Matrix B2 = Generator.matrixOfNumber(size, 12.0);
      Matrix C1 = Generator.matrixOfNumber(size, 2.0);
      Matrix C2 = Generator.matrixOfNumber(size, 2.0);

      // Act
      B1.subtract(C1); // A - (B - C)
      A1.subtract(B1);

      A2.subtract(B2); // (A - B) - C
      A2.subtract(C2);

      // Assert
      assertFalse(A1.equals(C2));

    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_have_same_result_as_EJML_library(int n) {
      // Arrange
      double[] data1 = Generator.randArray(n);
      double[] data2 = Generator.randArray(n);
      Matrix A = new Matrix(data1, n, n);
      Matrix B = new Matrix(data2, n, n);
      SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
      SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

      // Act
      A.subtract(B);
      SimpleMatrix C = new SimpleMatrix(A_ejml.minus(B_ejml));

      // Assert
      assertTrue(eqToEJML(A, C));

    }

  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class AuxilaryMatrix_matrix_subtraction {

    @Test
    public void should_NOT_be_commutative() {
      // Arrange
      Matrix A = Generator.matrixOfNumber(size, 6.0);
      Matrix B = Generator.matrixOfNumber(size, 12.0);

      // Act
      Matrix C1 = Matrix.subtract(A, B);
      Matrix C2 = Matrix.subtract(B, A);

      // Assert
      assertFalse(C1.equals(C2));
    }

    @Test
    public void should_NOT_be_associative() {
      // Arrange
      Matrix A = Generator.matrixOfNumber(size, 6.0);
      Matrix B = Generator.matrixOfNumber(size, 12.0);
      Matrix C = Generator.matrixOfNumber(size, 2.0);

      // Act
      Matrix D1 = Matrix.subtract(A, Matrix.subtract(B, C));
      Matrix D2 = Matrix.subtract(C, Matrix.subtract(A, B));

      // Assert
      assertFalse(D1.equals(D2));
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_have_same_result_as_EJML_library(int n) {
      // Arrange
      double[] data1 = Generator.randArray(n);
      double[] data2 = Generator.randArray(n);
      Matrix A = new Matrix(data1, n, n);
      Matrix B = new Matrix(data2, n, n);
      SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
      SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

      // Act
      Matrix C = Matrix.subtract(A, B);
      SimpleMatrix C_ejml = new SimpleMatrix(A_ejml.minus(B_ejml));

      // Assert
      assertTrue(eqToEJML(C, C_ejml));
    }
  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class Inplace_matrix_transpose {

    @Test
    public void should_be_the_same_when_transposed_twice() {
      // Arrange
      Matrix A = Generator.randomMatrix(size);
      Matrix A_TT = A.copy();

      // Act
      Matrix.transpose(A_TT);
      Matrix.transpose(A_TT);

      // Assert
      assertTrue(A.equals(A_TT));
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_have_same_result_as_EJML_library(int n) {
      // Arrange
      double[] data1 = Generator.randArray(n);
      Matrix A = new Matrix(data1, n, n);
      SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);

      // Act
      Matrix.transpose(A);

      // Assert
      assertTrue(eqToEJML(A, A_ejml.transpose()));
    }

  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class elementary_matrix_multiplication {

    @Test
    public void should_NOT_be_commutative() {
      // Arrange
      Matrix A = Generator.randomMatrix(size);
      Matrix B = Generator.randomMatrix(size);

      // Act
      Matrix C1 = Matrix.elementaryMultiplication(A, B,0);
      Matrix C2 = Matrix.elementaryMultiplication(B, A,0);

      // Assert
      assertFalse(C1.equals(C2));
    }

    @Test
    public void should_be_associative_ejml() {
      // Arrange
      double[] data1 = Arrays.stream(Generator.randArray(size)).map(x -> (int) x).map(x -> (double) x).toArray();
      double[] data2 = Arrays.stream(Generator.randArray(size)).map(x -> (int) x).map(x -> (double) x).toArray();
      double[] data3 = Arrays.stream(Generator.randArray(size)).map(x -> (int) x).map(x -> (double) x).toArray();
      SimpleMatrix A = new SimpleMatrix(size, size, true, data1);
      SimpleMatrix B = new SimpleMatrix(size, size, true, data2);
      SimpleMatrix C = new SimpleMatrix(size, size, true, data3);

      // Act
      SimpleMatrix BC = B.mult(C);
      SimpleMatrix D1 = A.mult(BC); // A * (B * C)
      SimpleMatrix AB = A.mult(B);
      SimpleMatrix D2 = AB.mult(C);

      // Assert
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          assertTrue(D1.get(i, j) == D2.get(i, j));
        }
      }
    }

    @Test
    public void should_be_associative() {
      // Arrange
      double[] data1 = Arrays.stream(Generator.randArray(size)).map(x -> (int) x).map(x -> (double) x).toArray();
      double[] data2 = Arrays.stream(Generator.randArray(size)).map(x -> (int) x).map(x -> (double) x).toArray();
      double[] data3 = Arrays.stream(Generator.randArray(size)).map(x -> (int) x).map(x -> (double) x).toArray();
      Matrix A = new Matrix(data1, size, size);
      Matrix B = new Matrix(data2, size, size);
      Matrix C = new Matrix(data3, size, size);

      // Act
      Matrix D1 = Matrix.elementaryMultiplication(A, Matrix.elementaryMultiplication(B, C,0),0); // A * (B * C)
      Matrix D2 = Matrix.elementaryMultiplication(Matrix.elementaryMultiplication(A, B,0), C,0); // (A*B) * C

      // Assert
      assertTrue(D1.equals(D2));
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_give_same_result_as_EJML_library(int n) {
      double[] data1 = Generator.randArray(n);
      double[] data2 = Generator.randArray(n);

      Matrix A = new Matrix(data1, n, n);
      Matrix B = new Matrix(data2, n, n);
      SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
      SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

      Matrix C = Matrix.elementaryMultiplication(A, B,0);
      SimpleMatrix C_ejml = A_ejml.mult(B_ejml);

      assertTrue(eqToEJML(C, C_ejml));
    }
  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class transposed_elementary_matrix_multiplication {

    @Test
    public void should_NOT_be_commutative() {
      // Arrange
      Matrix A = Generator.randomMatrix(size);
      Matrix B = Generator.randomMatrix(size);

      // Act
      Matrix C1 = Matrix.elementaryMultiplicationTransposed(A, B,0);
      Matrix C2 = Matrix.elementaryMultiplicationTransposed(B, A,0);

      // Assert
      assertFalse(C1.equals(C2));
    }

    @Test
    public void should_be_associative() {
      // Arrange
      double[] data1 = Generator.randArray(size);
      double[] data2 = Generator.randArray(size);
      double[] data3 = Generator.randArray(size);
      Matrix A = new Matrix(data1, size, size);
      Matrix B = new Matrix(data2, size, size);
      Matrix C = new Matrix(data3, size, size);

      // Act
      Matrix D1 = Matrix.elementaryMultiplicationTransposed(A, Matrix.elementaryMultiplicationTransposed(B, C,0),0); // A *
                                                                                                                 // (B *
                                                                                                                 // C)
      Matrix D2 = Matrix.elementaryMultiplicationTransposed(Matrix.elementaryMultiplicationTransposed(A, B,0), C,0); // (A*B)
                                                                                                                 // * C

      // Assert
      assertTrue(D1.equals(D2));
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_give_same_result_as_elementary_multiplication(int n) {
      // Arrange
      Matrix A = Generator.randomMatrix(n);
      Matrix B = Generator.randomMatrix(n);

      // Act
      Matrix C1 = Matrix.elementaryMultiplicationTransposed(A, B,0);
      Matrix C2 = Matrix.elementaryMultiplication(A, B,0);

      // Assert
      assertTrue(C1.equals(C2));
    }
  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class tiled_matrix_multiplication {

    @Test
    public void should_NOT_be_commutative() {
      for (int s = 1; s <= (size / 2); s *= 2) {
        // Arrange
        Matrix A = Generator.randomMatrix(size);
        Matrix B = Generator.randomMatrix(size);

        // Act
        Matrix C1 = Matrix.tiledMultiplication(A, B, s);
        Matrix C2 = Matrix.tiledMultiplication(B, A, s);

        // Assert
        assertFalse(C1.equals(C2));
      }
    }

    @Test
    public void should_be_associative() {
      for (int s = 1; s <= (size / 2); s = s * 2) {
        // Arrange
        double[] data1 = Generator.randArray(size);
        double[] data2 = Generator.randArray(size);
        double[] data3 = Generator.randArray(size);
        Matrix A = new Matrix(data1, size, size);
        Matrix B = new Matrix(data2, size, size);
        Matrix C = new Matrix(data3, size, size);

        // Act
        Matrix D1 = Matrix.tiledMultiplication(A, Matrix.tiledMultiplication(B, C, s), s); // A * (B * C)
        Matrix D2 = Matrix.tiledMultiplication(Matrix.tiledMultiplication(A, B, s), C, s); // (A*B) * C

        // Assert
        assertTrue(D1.equals(D2));
      }
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_give_same_result_as_EJML_library(int n) {

      for (int s = 1; s <= (n / 2); s = 2 * s) {
        double[] data1 = Generator.randArray(n);
        double[] data2 = Generator.randArray(n);

        Matrix A = new Matrix(data1, n, n);
        Matrix B = new Matrix(data2, n, n);
        SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
        SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

        Matrix C = Matrix.tiledMultiplication(A, B, s);
        SimpleMatrix C_ejml = A_ejml.mult(B_ejml);

        assertTrue(eqToEJML(C, C_ejml));
      }
    }
  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class recursive_copying_matrix_multiplication {
    @Test
    public void should_NOT_be_commutative() {
      int m= 64;
      // Arrange
      Matrix A = Generator.randomMatrix(size);
      Matrix B = Generator.randomMatrix(size);
      // Act
      Matrix C1 = Matrix.recursiveMultiplicationCopying(A, B,m);
      Matrix C2 = Matrix.recursiveMultiplicationCopying(B, A,m);
      // Assert
      assertFalse(C1.equals(C2));
    }

    @Test
    public void should_be_associative() {
      int m= 64;
      // Arrange
      Matrix A = Generator.randomMatrix(size);
      Matrix B = Generator.randomMatrix(size);
      Matrix C = Generator.randomMatrix(size);

      // Act
      Matrix BC = Matrix.recursiveMultiplicationCopying(B, C,m);
      Matrix D1 = Matrix.recursiveMultiplicationCopying(A, BC,m); // A * (B * C)
      Matrix AB = Matrix.recursiveMultiplicationCopying(A, B,m);
      Matrix D2 = Matrix.recursiveMultiplicationCopying(AB, C,m); // (A*B) * C

      // Assert
      assertTrue(D1.equals(D2));
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_give_same_result_as_EJML_library(int n) {
      int m = 64;
      double[] data1 = Generator.randArray(n);
      double[] data2 = Generator.randArray(n);

      Matrix A = new Matrix(data1, n, n);
      Matrix B = new Matrix(data2, n, n);
      SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
      SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

      Matrix C = Matrix.recursiveMultiplicationCopying(A, B,m);
      SimpleMatrix C_ejml = A_ejml.mult(B_ejml);

      assertTrue(eqToEJML(C, C_ejml));
    }
  }

  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class recursive_write_through_matrix_multiplication {
    @Test
    public void should_NOT_be_commutative() {
      for (int m = 1; m <= size / 2; m *= 2) {
        // Arrange
        Matrix A = Generator.randomMatrix(size);
        Matrix B = Generator.randomMatrix(size);
        // Act
        Matrix C1 = Matrix.recursiveMultiplicationWriteThrough(A, B, m);
        Matrix C2 = Matrix.recursiveMultiplicationWriteThrough(B, A, m);
        // Assert
        assertFalse(C1.equals(C2));
      }
    }

    @Test
    public void should_be_associative() {
      for (int m = 1; m <= size / 4; m *= 2) {
        // Arrange
        Matrix A = Generator.randomMatrix(size);
        Matrix B = Generator.randomMatrix(size);
        Matrix C = Generator.randomMatrix(size);

        // Act
        Matrix BC = Matrix.recursiveMultiplicationWriteThrough(B, C, m);
        Matrix D1 = Matrix.recursiveMultiplicationWriteThrough(A, BC, m); // A * (B * C)
        Matrix AB = Matrix.recursiveMultiplicationWriteThrough(A, B, m);
        Matrix D2 = Matrix.recursiveMultiplicationWriteThrough(AB, C, m); // (A*B) * C

        // Assert
        assertTrue(D1.equals(D2));
      }
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_give_same_result_as_EJML_library(int n) {
      for (int m = 1; m <= n / 2; m *= 2) {
        double[] data1 = Generator.randArray(n);
        double[] data2 = Generator.randArray(n);

        Matrix A = new Matrix(data1, n, n);
        Matrix B = new Matrix(data2, n, n);
        SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
        SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

        Matrix C = Matrix.recursiveMultiplicationWriteThrough(A, B, m);
        SimpleMatrix C_ejml = A_ejml.mult(B_ejml);

        assertTrue(eqToEJML(C, C_ejml));
      }
    }
  }






  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class strassen_matrix_multiplication {
    @Test
    public void should_NOT_be_commutative() {
      for (int m = 1; m <= size / 2; m *= 2) {
        // Arrange
        Matrix A = Generator.randomMatrix(size);
        Matrix B = Generator.randomMatrix(size);
        // Act
        Matrix C1 = Matrix.strassen(A, B, m);
        Matrix C2 = Matrix.strassen(B, A, m);
        // Assert
        assertFalse(C1.equals(C2));
      }
    }

    @Test
    public void should_be_associative() {
      for (int m = 1; m <= size / 4; m *= 2) {
        // Arrange
        Matrix A = Generator.randomMatrix(size);
        Matrix B = Generator.randomMatrix(size);
        Matrix C = Generator.randomMatrix(size);

        // Act
        Matrix BC = Matrix.strassen(B, C, m);
        Matrix D1 = Matrix.strassen(A, BC, m); // A * (B * C)
        Matrix AB = Matrix.strassen(A, B, m);
        Matrix D2 = Matrix.strassen(AB, C, m); // (A*B) * C

        // Assert
        assertTrue(D1.equals(D2));
      }
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_give_same_result_as_EJML_library(int n) {
      for (int m = 1; m <= n / 2; m *= 2) {
        double[] data1 = Generator.randArray(n);
        double[] data2 = Generator.randArray(n);

        Matrix A = new Matrix(data1, n, n);
        Matrix B = new Matrix(data2, n, n);
        SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
        SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

        Matrix C = Matrix.strassen(A, B, m);
        SimpleMatrix C_ejml = A_ejml.mult(B_ejml);

        assertTrue(eqToEJML(C, C_ejml));
      }
    }
  }




//  @Disabled
  @Nested
  @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
  class strassenParallel_matrix_multiplication {
    @Test
    public void should_NOT_be_commutative() {
      for (int m = 1; m <= size / 2; m *= 2) {
        // Arrange
        Matrix A = Generator.randomMatrix(size);
        Matrix B = Generator.randomMatrix(size);
        // Act
        Matrix C1 = Matrix.strassenParallel(A, B, m);
        Matrix C2 = Matrix.strassenParallel(B, A, m);
        // Assert
        assertFalse(C1.equals(C2));
      }
    }

    @Test
    public void should_be_associative() {
      for (int m = 1; m <= size / 4; m *= 2) {
        // Arrange
        Matrix A = Generator.randomMatrix(size);
        Matrix B = Generator.randomMatrix(size);
        Matrix C = Generator.randomMatrix(size);

        // Act
        Matrix BC = Matrix.strassenParallel(B, C, m);
        Matrix D1 = Matrix.strassenParallel(A, BC, m); // A * (B * C)
        Matrix AB = Matrix.strassenParallel(A, B, m);
        Matrix D2 = Matrix.strassenParallel(AB, C, m); // (A*B) * C

        // Assert
        assertTrue(D1.equals(D2));
      }
    }

    @ParameterizedTest
    @MethodSource("matrix_multiplication.MatrixTest#sizeProvider")
    public void should_give_same_result_as_EJML_library(int n) {
      for (int m = 1; m <= n / 2; m *= 2) {
        double[] data1 = Generator.randArray(n);
        double[] data2 = Generator.randArray(n);

        Matrix A = new Matrix(data1, n, n);
        Matrix B = new Matrix(data2, n, n);
        SimpleMatrix A_ejml = new SimpleMatrix(n, n, true, data1);
        SimpleMatrix B_ejml = new SimpleMatrix(n, n, true, data2);

        Matrix C = Matrix.strassenParallel(A, B, m);
        SimpleMatrix C_ejml = A_ejml.mult(B_ejml);

        assertTrue(eqToEJML(C, C_ejml));
      }
    }
  }
}
