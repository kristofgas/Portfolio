# Final Exam Project Applied Algorithms

## To-Do's
- [X] Implement the elementary matrix multiplication with three nested loops.
- [X] Implement in-place transpose of a matrix.
- [X] Implement a variant of the Elementary Matrix Multiplication (see Task 1) such that it assumes one of the operands transposed, and accesses both operands sequentially in the chosen memory layout.
- [X] Tiling: Implement the tiling Matrix Multiplication
- [X] Tiling: Fix n to a suitable, sufficiently large power of two
- [X] Tiling: Repeat the experiment with some other values of n 
- [X] Recursive: Implement the copying variant of the recursive algorithm
- [X] Recursive: Implement the write-through variant of the recursive Matrix Multiplication according to the pseudeocode presented on lecture slides
- [X] Fix n to a suitable value, and evaluate the best subproblem size between 0 and n/2 that the recursive algorithm of above task should fall back to elementary algorithm.
- [X] Implement Strassen's algorithm
- [ ] Fix n, and evaluate the best subproblem size where fallback from Strassen's
- [ ] Perform a horse race between the algorithms that you have implmented

## Specific Tasks regarding Reporting and Testing
- [ ] We need to be able to measure the runtime of transposing a matrix!
- [X] We need to measure the runtime of the tiling matrix multiplication for different values of n and s
- [X] We need to be able to COMPARE the runtimes of ifferent values of n for tiling matrix multiplication
- [X] We need to evaluate the best subproblem size for the recursive algorithm -> probably measure runtime
- [ ] We need to determine the subproblem size when Strassens algorithm should fall back to n^3 algorithm 
- [ ] We need to be able to compare the runtimes of all the algorithms (i.e. the time it takes to multiply the matrices)
