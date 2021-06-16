# Hip to be (Latin) Square

GitHub repository for the source code and the experimental data of the paper:

L. Mariot: Hip to Be (Latin) Square: Maximal Period Sequences from Orthogonal Cellular Automata.

Preprint available at: https://arxiv.org/abs/2106.07750

## Compiling

To compile the code, just run javac *.java from inside the directory src/

## Running

There are two classes in the default package with a main method:

- TestIterateOCA: used to perform the exhaustive search over all pairs of bipermutive CA of diameter d to compute their cycle decomposition (see Section 3.2 of the paper)
  * Example: $ java TestIterateOCA 3 -> enumerates all pairs of bipermutive rules of diameter 3
- TestMaxIterateLinOCA: execute the algorithm described in Section 4 of the paper, which enumerates all pairs of polynomials of degree n=d-1, check if the corresponding linear OCA are orthogonal, and finally determines if the associated Sylvester matrix has maximum order 2^{2n-1}.
  * Example: $ java TestMaxIterateLinOCA 3 -> enumerates all pairs of linear bipermutive rules of diameter 3
  
# Experimental Data

The experimental data described in of the paper are under experimental_data/, respectively exhaustive_search/ for those discussed in Section 3.2 and enumeration_algorithm/ for those discussed in Section 4.
