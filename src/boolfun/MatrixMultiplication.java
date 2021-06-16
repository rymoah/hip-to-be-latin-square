package boolfun;



/**
 * Tools for binary matrix multiplication and exponentiation
 * 
 * @author Luca Mariot
 */

public class MatrixMultiplication {
    
    public static boolean[][] multSqMatrix(boolean[][] matrix1, 
            boolean[][] matrix2) {
        
        int n = matrix1.length; //the two matrices are square of the same order n
        
        boolean[][] res = new boolean[n][n];
        
        for(int i=0; i<n; i++) {
            
            boolean[] row = matrix1[i];
            //Calculate the i-th row of the result as the scalar product of the
            //i-th row of the first matrix and all columns of the second
            for(int j=0; j<n; j++) {
                
                //Extract the j-th column
                boolean[] col = new boolean[matrix2.length];
                for(int k=0; k<n; k++) {
                    col[k] = matrix2[k][j];
                }
                
                //Compute the scalar product
                res[i][j] = BinTools.scalarProduct(row, col);
                
            }
            
        }
        
        return res;
        
    }
    
    /**
     * Square matrix exponentiation using the square-and-multiply algorithm
     * @param matrix
     * @param exp
     * @param length
     * @return 
     */
    public static boolean[][] expSqMatrix(boolean[][] matrix, int exp, int length) {
        
        int n = matrix.length;
        boolean[][] res = new boolean[n][n];
        //Initialize the result to the identity matrix
        for(int i=0; i<n; i++) {
            res[i][i] = true;
        }
        
        //Get the binary representation of exp
        boolean[] binexp = BinTools.dec2BinMod(exp, length);
        
        for(int i=length-1; i>=0; i--) {
            
            //Square the current result
            res = multSqMatrix(res, res);
            
            //Multiply the current result by the matrix only if the current bit
            //of the exponent is 0
            if(binexp[i]) {
                res = multSqMatrix(res, matrix);
            }
            
        }
        
        return res;
        
    }
    
    /**
     * Square matrix exponentiation using the naive algorithm
     * @param matrix
     * @param exp
     * @return 
     */
    public static boolean[][] expSqMatrixNaive(boolean[][] matrix, int exp) {
        
        int n = matrix.length;
        boolean[][] res = new boolean[n][n];
        //Initialize the result to the identity matrix
        for(int i=0; i<n; i++) {
            res[i][i] = true;
        }
        
        for(int i=0; i<exp; i++) {
            res = multSqMatrix(res, matrix);
        }
        
        return res;
        
    }
    
    /**
     * Check if a given matrix is the identity
     * @param matrix
     * @return 
     */
    public static boolean isIdentity(boolean[][] matrix) {
        
        int n = matrix.length;
        
        int i = 0;
        int j = 0;
        boolean isid = true;
        while(i<n && isid) {
            
            while(j<n && isid) {
                
                if(i==j)
                    isid = (matrix[i][j]==true);
                else
                    isid = (matrix[i][j]==false);
                
                j++;
                
            }
            
            i++;
            
        }
        
        return isid;
        
    }
    
}
