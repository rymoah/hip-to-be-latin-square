

/**
 * Same as TestIterateLinOCA but check only if two orthogonal rules have
 * maximum period
 * 
 * @author Luca Mariot
 */

import oca.BuildLatSqCA;
import oca.LatinSquareTools;
import oca.OneDimCellAut;
import boolfun.ConvertLinANFtoTT;
import boolfun.CheckProp;
import boolfun.BooleanFunction;
import boolfun.BinTools;
import boolfun.MatrixMultiplication;
import java.math.BigInteger;
import java.util.Vector;

public class TestMaxIterateLinOCA {
    
    public static boolean[][] buildSylvesterMatrix(boolean[] pol1, boolean[] pol2) {
        
        int n = pol1.length-1;
        boolean[][] sylvmat = new boolean[2*n][2*n];
        
        for(int i=0; i<n; i++) {
            
            //Build the first half of the matrix by shifting the coefficients of
            //the first polynomial from left to right, the second half by
            //shifting the coefficients of the second polynomial
            //Notice that j goes up to d because d=pol1.length-1
            for(int j=0; j<n+1; j++) {
                sylvmat[i][i+j] = pol1[j];
                sylvmat[i+n][i+j] = pol2[j];
            }
            
        }
        
        return sylvmat;
        
    }
    
    /**
     * Check if the order of an invertible matrix in the general linear group
     * is the maximum period 2^{2n}-1
     */
    public static int findOrder(boolean[][] matrix, Vector<Integer> div) {
        
        int i=0;
        int n = matrix.length;
        int max = (int)Math.pow(2, n) - 1;
        int ord = -1;
        boolean found = false;
        while(i<div.capacity() && !found) {
            
            boolean[][] exp = MatrixMultiplication.expSqMatrix(matrix, div.elementAt(i), n);
            found = MatrixMultiplication.isIdentity(exp);
            
            if(found) {
                
                ord = div.elementAt(i);
                
            }
            
            i++;
            
        }
        
        return ord;
        
    }
    
    public static void main(String[] args) {
        
        if(args.length!= 1) {
            
            System.err.println("Usage: java lowlevelfunc.oa.TestMaxIterateLinOCA"
                                +" diameter");
            System.exit(1);
            
        }
        
        int d = Integer.parseInt(args[0]);  //diameter of the local rules
        int n = d-1;                        //degree of the polynomials
        int numlin = (int)Math.pow(2,d-2);  //number of linear bipermutive rules    
        
        int maxord = (int)Math.pow(2,2*n) - 1;  //maximum period attainable by a linear orthogonal
        //Compute the list of divisors of the order of GL(2,2n), up to 2^{2n}-1
        Vector<Integer> div = new Vector<Integer>();
        for(int i=1; i<=maxord; i++) {
            
            if(maxord%i == 0) {
                div.add(i);
            }
            
        }
        
        div.trimToSize();

        //Instantiate the CA
        OneDimCellAut ca = new OneDimCellAut(2*(d-1), null, d, 0);
        
        System.out.println("Diameter: "+d);
        System.out.println("Degree: "+n);
        System.out.println("Number of linear rules: "+numlin);
        System.out.println("Maximum period attainable by linear orthogonal sequences: "+maxord);
        System.out.print("Divisors of the maximum order: ");
        for(int i=0; i<div.capacity(); i++)
            System.out.print(div.elementAt(i)+" ");
        System.out.println("\n");

        //Main loop: cycle over all pairs of linear bipermutive rules of diameter d
        for(int l=0; l<numlin-1; l++) {
            
            boolean[] coeffl = BinTools.dec2BinMod(l, d-2);
            boolean[] fullcoeffl = new boolean[d];
            for(int p=1; p<fullcoeffl.length-1; p++) {
                fullcoeffl[p] = coeffl[p-1];
            }
            fullcoeffl[0] = true;
            fullcoeffl[d-1] = true;
            boolean[] biprule1 = ConvertLinANFtoTT.convAnf2Tt(fullcoeffl);
            
            for(int m=l+1; m<numlin; m++) {

                //Encode the current indexes as a pair of bipermutive rules.
                boolean[] coeffm = BinTools.dec2BinMod(m, d-2);
                boolean[] fullcoeffm = new boolean[d];
                for(int p=1; p<fullcoeffm.length-1; p++) {
                    fullcoeffm[p] = coeffm[p-1];
                }
                fullcoeffm[0] = true;
                fullcoeffm[d-1] = true;
                boolean[] biprule2 = ConvertLinANFtoTT.convAnf2Tt(fullcoeffm);
                
                BigInteger biprulenum1 = BinTools.bin2DecBig(biprule1);
                BigInteger biprulenum2 = BinTools.bin2DecBig(biprule2);

                //Build the corresponding two Latin squares and check for orthogonality
                ca.setRule(biprule1);
                int[][] matrix1 = BuildLatSqCA.buildSqMatCA(ca,d-1);

                ca.setRule(biprule2);
                int[][] matrix2 = BuildLatSqCA.buildSqMatCA(ca,d-1);

                boolean orthog = LatinSquareTools.checkOrthogLatSquare(
                            matrix1, matrix2);

                //If orthogonal, print info
                if(orthog) {

                    BooleanFunction bf1 = new BooleanFunction(biprule1,d);
                    CheckProp.computeANF(bf1);

                    BooleanFunction bf2 = new BooleanFunction(biprule2,d);
                    CheckProp.computeANF(bf2);

                    int[] card = LatinSquareTools.checkPairsTable(biprule1, biprule2);

                    System.out.print("Rule: "+biprulenum1+" NL: "+
                            bf1.getNlin()+"; POL: "+CheckProp.printPolynomial(bf1)+"; ");
                    System.out.print("Rule: "+biprulenum2+" NL: "+
                            bf2.getNlin()+"; POL: "+CheckProp.printPolynomial(bf2)+"; ");

                    System.out.print("Order: ");
                    //Compute the order of the Sylvester matrix
                    boolean[][] syl = buildSylvesterMatrix(fullcoeffl, fullcoeffm);
                    int order = findOrder(syl, div);
                    System.out.println(order);
                    
                    
                }
                
            }
            
        }
        
        
    }
    
}
