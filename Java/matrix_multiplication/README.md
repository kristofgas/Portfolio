# Matrix multiplication experiments

This Project demonstrates my ability to implement, evaluate, and test algorithms, including the application of multicore processing.


## Table of Contents
1. [Introduction](#Introduction)
2. [Specifications, Testing, and Input Generation](#Specifications,-Testing,-and-Input-Generation)
3. [Implementation of the Algorithms](#Implementation-of-the-Algorithms)
4. [Results of the Horse Race](#Results-of-the-Horse-Race)
5. [Discussion and Conclusions](#Discussion-and-Conclusions)

---

## 1. Introduction

This project evaluates different matrix multiplication algorithms with a focus on improving efficiency. Matrix multiplication is critical in various applications like image processing, seismic analysis, and financial modeling. We explore the following algorithms:
- Elementary Matrix Multiplication
- Tiled Matrix Multiplication
- Recursive Matrix Multiplication
- Strassen's Algorithm

## 2. Specifications, Testing, and Input Generation

### 2.1 Implementation

We use Java for implementing the algorithms. For benchmarking, we use a toolchain updated for Matrix multiplication tasks, leveraging a `TriConsumer<Matrix,Matrix,Integer>` interface. The tests are conducted on a Lenovo ThinkPad X230 running OpenJDK 11.

### 2.2 Testing

We use JUnit for testing algorithm correctness, with tests against the EJML library. We test for properties like associativity and commutativity.

### 2.3 Input Matrix Generation

Matrices are generated using Java's standard library for random number generation.

---

## 3. Implementation of the Algorithms

### 3.1 Elementary Matrix Multiplication

We implemented the basic algorithm with a running time of \( O(n^3) \). We also experimented with loop ordering for cache optimization.

### 3.2 Tiled Matrix Multiplication

The algorithm divides the matrix into \( (n/s)^2 \) submatrices for computation. We experimented to find the optimal tile size \( s \).

### 3.3 Recursive Matrix Multiplication

Benchmarking was conducted to find the optimal size \( m \) for the recursive function stop condition.

### 3.4 Strassen's Algorithm

We found that Strassen's algorithm performs best with a stop condition \( m \) between 32 and 256. Parallelization was achieved using the Fork/Join Framework.

---

## 4. Results of the Horse Race

Strassen's algorithm performed the best in our benchmarks, followed by the Elementary Matrix Multiplication algorithm. The Tiled algorithm surprisingly performed slower than expected.

---

## 5. Discussion and Conclusions

We found that Strassen's algorithm significantly outperformed all other methods. The Tiled algorithm was slower than expected, possibly due to the overhead of submatrix operations. Future work could explore the limitations of our findings and perform a deeper analysis of the cache behavior.

---

For code and data files, please refer to the attached supplementary material.

## Contributions
Thanks to Lasse, for contributing to this project.

