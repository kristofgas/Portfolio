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
This project involves implementing the HyperLogLog algorithm, a probabilistic data structure used to estimate the number of distinct elements in a data stream. 

## Implementation Details

### Hash Function `h`
The function `h(x)` transforms a given integer into a binary vector. This binary vector is then combined with a predefined matrix `A` using modulo-2 dot product operations. The resulting binary vector serves as the hashed representation of the initial integer.

### Function `ρ`
The function `ρ(y)` takes in a binary vector (usually generated by `h(x)`) and identifies the first occurrence of the bit set to 1 from the left. We've implemented this feature using Java's built-in function `Integer.numberOfLeadingZeros()`.

### HyperLogLog Algorithm
We've developed the HyperLogLog algorithm using Java 11.01. This implementation relies on the hash function `h(x)` and the function `ρ(y)` described above.

## Quality Evaluation

### Quality of Hash Function `h`
The performance of the hash function `h` is evaluated based on the distribution of values it generates for the function `ρ(y)`. The probabilities match expected theoretical values, specifically the chance of having `ρ(y)` equal to `i` decreases exponentially with `i`.

### Adaption of Hash Function `f`
To make the HyperLogLog algorithm adaptable to different sizes of a parameter `m`, we've modified a function called `f`. This function adjusts the range of hashed integers based on the size of the register `m`. The bitshift operations in `f` are tweaked based on logarithmic transformations of `m`.

### Relation between `m` and Estimation Error
The accuracy of our HyperLogLog implementation depends on the parameter `m`. Specifically, the standard error decreases as the square root of `m`. Moreover, the estimated number of distinct elements is adjusted slightly based on the value of `m`. Experiments indicate that this relationship doesn't strictly follow a linear trend but deviates at extreme values of `m` and `n`.

## Installation & Setup
- Ensure that Java 11.01 and LaTeX are installed on your computer.
- Clone this repository.
- You can compile the LaTeX document either on Overleaf or on your local machine.

## How to Run Experiments
- Open a terminal and navigate to the project's root directory.
- Run `javac HyperLogLog.java` to compile the Java code.
- Run `java HyperLogLog` to start running the experiments.

The results will automatically be saved and can be used to generate histogram plots in the LaTeX document.

---

## Contributions
Thanks to Laurenz and Lasse for their contributions to this project.

---
