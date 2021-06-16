package oca;


import boolfun.BinTools;



/**
 * Methods to build latin squares using CA.
 * 
 * @author Luca Mariot (l.mariot@campus.unimib.it)
 */

public class BuildLatSqCA {
    
    /**
     * Build a square matrix from a one-dimensional CA. Starting from a
     * configuration of 2*blocklen bits, the CA is iterated forward until a
     * configuration of blocklen bits is reached. The initial configuration is
     * partitioned in 2 vectors of equal length, A and B, determined by the
     * position of the initial block (parameter pos). The final configuration is
     * denoted by C. The matrix is then filled by considering the three blocks
     * respectively as row, column and value.
     * 
     * @param ca an instance of a one-dimensional CA
     * @param blocklen the length of the block which determines the order of the
     *                 matrix (the range of values is 0 to 2^blocklen - 1). Must
     *                 be a multiple of 2*radius and less than or equal to 15.
     * @return 
     */
    public static int[][] buildSqMatCA(OneDimCellAut ca, int blocklen) {
        
        int n = (int)Math.pow(2,blocklen);
        int[][] matrix = new int[n][n];
        
        for(int i=0; i<(n*n); i++) {
            //Encode the current index in binary form, set it as a CA conf and
            //partition it with respect to the position parameter.
            boolean[] initconf = BinTools.dec2BinMod(i, 2*blocklen);
            ca.setCells(initconf);
            boolean[] block1 = new boolean[blocklen];
            boolean[] block2 = new boolean[blocklen];
            
            System.arraycopy(initconf, 0, block1, 0, blocklen);
            System.arraycopy(initconf, blocklen, block2, 0, blocklen);
            
            //Evolve the CA
            boolean[] mask = new boolean[ca.getCells().length-ca.getNbr()+1];
            ca.nextAsyncConfNoBdAsym(mask);
            
            boolean[] lastconf = ca.getCells();
            int val1 = BinTools.bin2Dec(block1);
            int val2 = BinTools.bin2Dec(block2);
            int val3 = BinTools.bin2Dec(lastconf);
            //System.out.println(val3);
            
            matrix[val1][val2] = val3+1;
            
        }
        
        return matrix;
        
    }
    
    public static void main(String[] args)  {
        
        boolean[] rule = BinTools.dec2BinMod(42330, 16);
        OneDimCellAut ca = new OneDimCellAut(6,rule,4,0);
        
        int[][] matrix = buildSqMatCA(ca,3);
        
        for(int j=0; j<matrix[0].length; j++) {
            
            for(int i=0; i<matrix[j].length; i++) {
                System.out.print(matrix[j][i]+"\t");
            }
            
            System.out.println("");
            
        }
        
        System.out.println("\nLatin square: "+LatinSquareTools.checkLatSquare(matrix));
        
    }
    
} 
