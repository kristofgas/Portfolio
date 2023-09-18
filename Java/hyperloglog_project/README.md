# HyperLogLog Algorithm Experiments

## Table of Contents
1. [Introduction](#introduction)
2. [Implementation Details](#implementation-details)
    - [Hash Function `h`](#hash-function-h)
    - [Function `ρ`](#function-ρ)
    - [HyperLogLog Algorithm](#hyperloglog-algorithm)
3. [Quality Evaluation](#quality-evaluation)
    - [Quality of Hash Function `h`](#quality-of-hash-function-h)
    - [Adaption of Hash Function `f`](#adaption-of-hash-function-f)
    - [Relation between `m` and Estimation Error](#relation-between-m-and-estimation-error)
4. [Installation & Setup](#installation-setup)
5. [How to Run Experiments](#how-to-run-experiments)
6. [Contributions](#contributions)

## Introduction
This project implements the HyperLogLog algorithm to estimate the number of distinct elements in a data stream. The document was created using Overleaf with LaTeX. The implementation is based on the pseudocode found in the introduction paper and verified through CodeJudge assignments. 

## Implementation Details

### Hash Function `h`
The hash function `h(x)` transforms an integer `x` to a binary vector. It then computes a modulo-2 dot product between the binary vector and a given matrix `A`. The result is another binary vector which represents the hashed value of `x`.

The mathematical formulation of `h(x)` is:

\[
h(x) = A \vec{x} \mod 2
\]

### Function `ρ`
The function `ρ(y)` takes the binary vector produced by `h(x)` and returns the position of the first bit set to 1 from the left side. The built-in function `Integer.numberOfLeadingZeros()` was used to implement this functionality. 

### HyperLogLog Algorithm
The HyperLogLog algorithm was implemented in Java 11.01. It is based on the `h(x)` and `ρ(y)` functions mentioned above. 

## Quality Evaluation

### Quality of Hash Function `h`

We evaluated the quality of our hash function `h` by studying the distribution of `ρ`-values it produces. The probability of `ρ(y)=i` was found to be \(2^{-i}\), adhering to expectations, confirming the quality of our hash function.

\[
Pr[\rho(y) = i] = 2^{-i}
\]

### Adaption of Hash Function `f`

To adapt the HyperLogLog algorithm for different sizes of `m`, the function `f` was adjusted to incorporate `m` in its bitshift operations. The function ensures that the range of hashed integers `[0,1,...,j]` will always be in accordance with the register size `m`. Specifically, the bitshift was set to \(31-\lfloor\log_2(m)\rfloor\).

### Relation between `m` and Estimation Error

The standard error in estimating the number of distinct elements is given by:

\[
\sigma = \frac{1.04}{\sqrt{m}}
\]

And the estimated number \( \hat{n} \) is:

\[
\hat{n} = n\left(1+\frac{1.04}{\sqrt{m}}\right)
\]

Experiments revealed that the relationship between `m` and `σ` does not strictly follow a linear pattern, particularly for very small or very large values of `m` and `n`.

## Installation & Setup
- Make sure you have Java 11.01 and LaTeX installed.
- Clone the repository.
- Compile the LaTeX document on Overleaf or your local machine.

## How to Run Experiments
- Open a terminal and navigate to the project directory.
- Run `javac HyperLogLog.java` to compile.
- Run `java HyperLogLog` to start the experiments.

The results will be automatically saved and can be used to produce the histogram plots in the LaTeX document.


---

## Contributions
Thanks to Laurenz and Lasse for contributing to this project
 
