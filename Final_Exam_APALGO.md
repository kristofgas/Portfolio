<a name="br1"></a> 

Exam Report Applied Algorithms

Kristof Gasior and Lasse Bach Andersen

16-12-2021

1 Introduction

Matrix multiplication plays an important role in scientiﬁc and real-life

applications. It is used for computations in different areas such as image

processing, seismic analysis or even ﬁnancial analysis [\[3\].](#br12)[ ](#br12)When the size of

matrices grows, the need for efﬁcient algorithms becomes apparent. Hence,

we focus on the evaluation of different matrix multiplication algorithms

in this work, namely an elementary implementation, tiling and recursive

variants and the well-known Strassen’s algorithm.

In section [3](#br2)[ ](#br2)we describe the implementations and improvements we

have conducted. In section [4](#br9)[ ](#br9)we then describe the results of run-time

experiments and conclude our ﬁndings in section [5.](#br9)

2 Speciﬁcations, Testing and Input Generation

2\.1 Implementation

For implementing the matrix multiplications algorithms we used the Java

library provided as a template. Our algorithms operates on Java doubles, i.e.

we perform matrix multiplication on the double-precision ﬂoating-point

format.

For benchmarking we used our toolchain, and updated it for the Matrix

multiplications, using a TriConsumer<Matrix,Matrix,Integer>[\[1\]](#br12)[ ](#br12)intefer-

face

All experiments were run in OpenJDK Runtime Environment AdoptOpenJDK-

11\.0.11+9 (build 11.0.11+9) on a Lenovo ThinkPad X230, 2.9 GHz Intel Core

i7, 8 GB 1600 MHz DDR3 with cache sizes 32 KiB; 256 KiB; 4 MiB of L1d, L2

and L3 respectively.

1



<a name="br2"></a> 

2\.2 Testing

The implementations were tested for correctness using the JUnit frame-

[1](#br2)

work. Tests were performed against the EJML library for correctness. The

add method was tested for associativity and commutativity, and likewise

the subtract method for the opposite. Further the transpose method

was tested for being equal to the input matrix when transposed twice, i.e.

ꢀ

ꢁ

A = A⊤ <sup>⊤</sup>, and the different matrix multiplication implementations were

tested for associativity and non-commutativity, for different sizes of n, m

(and s).

2\.3 Input Matrix Generation

To generate input matrices a random number generator from the Java

standard library was used. During testing the random number genera-

tor had a seed value of 100 (to ensure the ability to reproduce possible

bugs), but during bench marking, random doubles were generated using

the System.currentTimeMillis() as seed. The generated doubles were

limited to a certain range to prevent overﬂow. Using Eq. [1](#br2)[ ](#br2)the maximum

allowable value of a matrice element d<sub>max</sub> can be calculated depending on

the side length of the matrix, n.

p

d<sub>max</sub>(n) < 2<sup>53</sup>/n

(1)

3 Implementation of the Algorithms

3\.1 Elementary Matrix Multiplication

The naive implementation of matrix multiplication is, as known, three

ꢀ ꢁ

3

nested loops with a running time of O n . However, due to spatial locality

we may observe that the running time can be decreased by interchanging

the loop order from i, j, k to e.g. i, k, j as stated in [\[2,](#br12)[ ](#br12)pg. 88]. I.e.

the program can be written as seen in Listing [1,](#br3)[ ](#br3)of which the last version

results in better spatial localitty as depicted in Table [1.](#br3)

3\.2 The Tiling Matrix Multiplication

Another variant is the Tiling matrix multiplication, very similar to the

elementary algorithm, but instead it divides the matrix into (n/s)<sup>2</sup> sub-

<sup>1<http://ejml.org/wiki/index.php?title=Main_Page></sup>

2



<a name="br3"></a> 

Matrix

block

block

block

A

B

\+ +

\+

\-

\+ + + + + + + + +

\-

\+

\-

\-

\+

\-

\-

\-

\+

\-

\-

C

×

A

B

×

\+

\+ +

\+ +

\+ + + + + + + + +

\+ + + + + + + + +

C

\+

Table 1: Optimizing spatial locality by interchanging loop order

(\* loop order i, j, k \*)

for (int i = 0 ; i < n ; i++)

for (int j = 0 ; j < n ; j++)

for (int k = 0 ; k < n ; k++)

C[i,j] = C[i,j] + A[i,k]\*B[k,j]

(\* loop order i, k, j \*)

for (int i = 0 ; i < n ; i++)

for (int k = 0 ; k < n ; k++)

for (int j = 0 ; j < n ; j++)

C[i,j] = C[i,j] + A[i,k]\*B[k,j]

Figure 1: Interchanging loop order to optimize cache locality

3



<a name="br4"></a> 

matrices. To reason about the optimal tiling size s we might consider two

approaches, i.e. calculation and experimentation.

Cache Size Unit Est. s

L1

L2

L3

32 KiB

256 KiB

4 MiB

32

64

256

Table 2: Cache sizes and estimations on the maximum s value suitable to

perform matrix multiplication within the memory boundary of the caches.

Calculation

Since L3 is the slowest cache, lets try to estimate how big matrices we can

multiply based on its size. To perform efﬁcient matrix multiplication, it

is needed to ﬁt three matrices into the cache memory. A calculation that

roughly indicates the maximum side length, n, of a n × n matrix is shown

in Eq. [2.](#br4)[ ](#br4)This is an indication that if we want to stay within L3 we should

probably not choose tiles that are larger than s = 256. The same indications

can be calculated for L1 and L2. See Table [2](#br4)[ ](#br4)for the cache sizes of this

speciﬁc machine and the corresponding estimations for the tiling size, s,

such that a s × s tile ﬁts within the speciﬁed cache.

j

ꢂ

ꢃk

√

k

n = 2 where, k = log

δ/3

and δ = L3/d

(2)

2

Variabel Value Unit Description

d

L3

δ

k

n

8 B

4 MiB Size of slowest cache

524288 Amount of doubles ﬁtting in L3

n = 2<sup>k</sup>

Three n × n matrices that ﬁts in L3

Size of a double

8

256

Table 3: Variables of Eq. [2](#br4)

Experimental

Another approach is to experimentally determine the cache size. To do this

8

a small program that performs 10 random look-ups in a single dimensional

4



<a name="br5"></a> 

60

55

50

45

40

35

30

25

20

15

10

Look up time [ns]

Size of array, n

Figure 2: Time in [ns] for single lookup for different sizes of single dimen-

sional arrays

array of doubles was implemented. The program runs with input arrays

of sizes n = {2, 4, 8, 16, . . . } and measures the time, T, it took to perform

the lookups for each array. This time was then divided by 10<sup>8</sup> to get the

time spend per lookup, i.e. t = T/10<sup>8</sup>. The lookup times can be seen in

Fig. [2.](#br5)[ ](#br5)Notice, the radical increment in time pr. lookup from n = 2<sup>18</sup> to

19

n = 2 . Recall that the L3 cache size has 4 MiB of memory and notice that

19

2 doubles corresponds to 4 MiB of data. We argue that the radical increase

in time per look up is a direct indication of crossing the memory boundary

of the L3 cache. To stay withing L3 we choose 2<sup>18</sup> as the limit. Now, reuse

Eq. [2,](#br4)[ ](#br4)with δ = 2<sup>18</sup> to calculate the biggest side length n such that the

matrices ﬁt within the L3 cache, which gives us the result of n = 256.

Determine the optimal tiling size s

The preliminary work, indicates that the optimal tiling size should be

smaller than 256. However, it does not indicate the optimal tiling size,

which we need to determine. In order to determine the tiling size s we run

the implementation with matrix dimensions of n = {4, 8, 16, ..., 2048} and

varying sizes of s = {2, 4, 8, ..., n/2}. We set n ≤ 2048 to stay within rea-

sonable running times. Running the experiments, the the average running

time and standard deviation was calculated by repeating the process 20

5



<a name="br6"></a> 

times for each size of s. Our ﬁndings, which are depicted in Fig. [3](#br7)[ ](#br7)indicate

that the optimal tiling size of s is between 4 and 8, best at 4 so we set it

to 4. One might ﬁnd the result quite surprising when taking the initial

estimation into account.

3\.3 The Recursive Matrix Multiplication

Finding the optimal size of m for the recursive implementation a bench

mark on sizes of n = {4, 8, 16, ..., 2048} were run, with different sizes of

m = {2, 4, 8, ..., n/2}. We set n ≤ 2048 to stay within reasonable running

times. For both the copying and write through implementation the optimal

value of m to was around 64, since run times start to grow from around

64 and down. A bit larger m could also be argued for, but this would

force the implementations to always perform elementary multiplication for

relatively big matrices.

3\.4 Strassen’s Algortihm

In Fig. [5,](#br8)[ ](#br8)we can evaluate that Strassen’s algorithm performs best with an

m between 32 and 256. By the same argument as above we consider the

optimal size of m to be 64.

Strassen in parallel experiment

We used the Fork/Join Framework for parallelizing Strassen’s algorithm,

building a task pool from recursively created subtasks. This framework is

an implementation of the ExecutorService interface, making it possible to

distribute the work over different processors.

We observe it to be approximately 3 times faster than the single threaded

Strassen implementation. Parallelizing Strassens each recursive task will

follow each recursion from strassen, and interleavings are avoided since

each worker thread will automatically work an an exclusive part of the

matrix.

This experiment was made to investigate improvements in runtime,

and we are not including a deeper concurrency analysis. The run times can

be seen in Fig. [6](#br8)

6



<a name="br7"></a> 

s = 4

s = 8

s = 16

s = 32

s = 64

s = 128

s = 256

s = 512

s = 1024

Tiled

10

1

0\.1

256

512

1024

2048

Size of n

Figure 3: Run times for tiled matrix multiplication, with matrices of side

length n and tile size s.

m = 4

m = 8

m = 16

m = 32

m = 64

m = 128

m = 256

m = 512

m = 1024

Recursive multiplication, writethrough

10

1

256

512

1024

2048

Size of n

Figure 4: Run times for recursive writethrough matrix multiplication, with

matrices of side length n and stop condition m.

7



<a name="br8"></a> 

m = 4

m = 8

m = 16

m = 32

m = 64

m = 128

m = 256

m = 512

m = 1024

Strassen

10

1

0\.1

256

512

1024

2048

Size of n

Figure 5: Run times for Strassen’s matrix multiplication, with matrices of

side length n and stop condition m.

m = 4

m = 8

m = 16

m = 32

m = 64

m = 128

m = 256

m = 512

m = 1024

Strassen, parallel

10

1

0\.1

256

512

1024

2048

Size of n

Figure 6: Run times for Strassen’s matrix multiplication parallelized, with

matrices of side length n and stop condition m.

8



<a name="br9"></a> 

4 Results of the Horse Race

As seen in Fig. [7,](#br10)[ ](#br10)the Strassen implementation clearly beats the other im-

plementations. The horse race was run with s = 4 for the Tiled algorithm,

and m = 64 for both the Recursive algorithms, as well as both Strassen’s.

Surprisingly the Tiled implementation is slower than the Elementary im-

plementation, however it seems more predictable in its behaviour as can

be seen in Fig. [8.](#br10)[ ](#br10)Not surprisingly The Recursive implementations as well

as Elementary transposed are slower than the Elementary implementation.

In Fig. [9](#br11)[ ](#br11)we observe that Strassen Parallel has a slow time per operation

for low sizes of n, which is probably caused by the expense of initializing

all threads in this framework, from start. Overall we believe that the two

implementations of Strassen’s would dominate if the race was to continue.

5 Discussion and Conclusions

As we’ve observed in Sec. 4 it is only the two implementations of the

Strassen algorithms that are faster than the elementary implementations.

We had expected the Tiled implementaion to be faster, and we wonder

whether it would have been the case if we had not interchanged the loop

order. It seems that the overhead connected with dividing the matrices into

submatrices is higher than what is to be gained in regards of cache misses.

We are also surprised to ﬁnd that s = 4 should be optimal, since this is well

below the boundary of the L1 cache size. On a a matrice with side length

n = 2048 our horse race showed us that the Elementary implementation

used around 9.4 seconds while the Strassen implementation used around

ꢂ

ꢃ

√

2\.7

7\.3 seconds, which ﬁts quite well with theory, i.e.

3

9\.4

≈ 7.5. Even

though we did not manage to show any effect of tiling, interchanging the

loop order was around 30 times faster when performing elementary matrix

multiplication on matrices with side length n = 4096, which clearly shows

that if we take the hardware architecture into consideration we can gain

tremendous performance improvements. Tiling could still help us gain

performance if e.g. we parallelized the multiplication of the tiles as seen in

the parallel implementaion of Strassen’s. Further Strassen’s implementation

shows us, that choosing the correct stop condition m, to divide the task

into sub tasks of a certain size that ﬁts into a speciﬁc cache, improves

performance quite noticeably.

9



<a name="br10"></a> 

Run time for n = 2048

12

11

10

9

8

7

6

5

4

Elem. Elem. trans. Tiled

Rec. cop. Rec. write StrassenStrassen par.

Size of n

Figure 7: Running times of the horse race

Elementary

Elem. transp.

Horse race

Tiled

10000

1000

100

10

1

0\.1

4

8

16

32

64

128

256

512 1024 2048

Size of n

Figure 8: The elementary, transposed elementary and tiled matrix multipli-

cation implementations

10



<a name="br11"></a> 

Rec. cop.

Rec. write.

Strassen

Strassen par.

Horse race

10000

1000

100

10

1

0\.1

4

8

16

32

64

128

256

512 1024 2048

Size of n

Figure 9: The recursive copying and writethrough implementation along

with Strassen’s and the latter parallelized.

Elementary

Elem. transp.

Tiled

Rec. write.

Strassen

Strassen par.

Rec. cop.

Horse race

10000

1000

100

10

1

0\.1

4

8

16

32

64

128

256

512 1024 2048

Size of n

Figure 10: A plot of all the bench marked matrix multiplication implemen-

tations

11



<a name="br12"></a> 

References

[1] Daniel S. Dickstein. In: (2014). DOI: [10.1109/ITI.2008.4588528](https://doi.org/10.1109/ITI.2008.4588528).

[2] J.L. Hennessy and D.A. Patterson. Computer Architecture: A Quantitative

Approach. ISSN. Elsevier Science, 2012. ISBN: 9780123838728.

[3] Halil Snopce and Lavdrim Elmazi. “The importance of using the lin-

ear transformation matrix in determining the number of processing

elements in 2-dimensional systolic array for the algorithm of matrix-

matrix multiplication”. In: (2008), pp. 885–892. DOI: [10](https://doi.org/10.1109/ITI.2008.4588528)[ ](https://doi.org/10.1109/ITI.2008.4588528)[.](https://doi.org/10.1109/ITI.2008.4588528)[ ](https://doi.org/10.1109/ITI.2008.4588528)[1109](https://doi.org/10.1109/ITI.2008.4588528)[ ](https://doi.org/10.1109/ITI.2008.4588528)[/](https://doi.org/10.1109/ITI.2008.4588528)[ ](https://doi.org/10.1109/ITI.2008.4588528)[ITI](https://doi.org/10.1109/ITI.2008.4588528)[ ](https://doi.org/10.1109/ITI.2008.4588528)[.](https://doi.org/10.1109/ITI.2008.4588528)

[2008.4588528](https://doi.org/10.1109/ITI.2008.4588528).

12


