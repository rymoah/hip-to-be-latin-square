# Hip to be (Latin) Square

GitHub repository for the source code and the experimental data of the paper:

L. Mariot: Hip to Be (Latin) Square: Maximal Period Sequences from Orthogonal Cellular Automata. Proceedings of CANDAR 2021, pp. 29-37 (2021) DOI: https://doi.org/10.1109/CANDAR53791.2021.00012

The paper has been further extended in journal version (currently submitted to Natural Computing). Preprint available at: https://arxiv.org/abs/2203.14365

## Code (Java)

NOTE: the Java code is bugged and currently being fixed. Please refer to the MAGMA code below.

### Compiling

To compile the code, just run javac *.java from inside the directory src/

### Running

There are two classes in the default package with a main method:

- TestIterateOCA: used to perform the exhaustive search over all pairs of bipermutive CA of diameter d to compute their cycle decomposition (see Section 3.2 of the paper)
  * Example: $ java TestIterateOCA 3 -> enumerates all pairs of bipermutive rules of diameter 3
- TestMaxIterateLinOCA: execute the algorithm described in Section 4 of the paper, which enumerates all pairs of polynomials of degree n=d-1, check if the corresponding linear OCA are orthogonal, and finally determines if the associated Sylvester matrix has maximum order 2^{2n-1}.
  * Example: $ java TestMaxIterateLinOCA 3 -> enumerates all pairs of linear bipermutive rules of diameter 3

## Code (MAGMA)

There are two MAGMA scripts under src:

  * enumerate_mat.m
  * enumerate_pol.m

Both of them enumerate the number of maximal length OCA up to diameter 16. The difference is that enumerate_mat.m computes the order of the Sylvester matrix formed by two orthogonal CA. The script enumerate_pol.m, on the other hand, checks the primitivity of the characteristic polynomial of the Sylvester matrix. The latter script is much faster. To run them, just type magma.exe enumerate_mat.m or magma enumerate_pol.m. The output of each script are the pairs specifying, for each diameter from 2 to 16, the corresponding number of maximal length OCA.

## Experimental Data

The experimental data described in of the paper are under experimental_data/

NOTE: these data refer to the experiments ran with the bugged Java version featured in the CANDAR paper, and are therefore wrong. Please refer to the output of the MAGMA script for the correct counts, until the Java code is fixed. 
