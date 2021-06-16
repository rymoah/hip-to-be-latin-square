package oca;

import boolfun.BinTools;
import java.math.BigInteger;
import java.util.Vector;

/**
 * Various methods for Latin squares.
 * 
 * @author Luca Mariot
 */

public class LatinSquareTools {
    
    /**
     * Check whether an array of length n is a permutation of {1,...,n}.
     * 
     * @param vect An array of integers.
     * @return true if vect is a permutation, false otherwise
     */
    public static boolean checkPerm(int[] vect) {
        
        //Vector for marked elements
        boolean[] mark = new boolean[vect.length];
        
        //For each element in the array, check if it has already
        //been marked. If so return false, otherwise mark it.
        for(int i=0; i<vect.length; i++) {
            
            if(mark[vect[i]-1]) {
                return false;
            } else {
                mark[vect[i]-1] = true;
            }
            
        }
        
        return true;
        
    }
    
    /**
     * Check whether a square matrix is a Latin square (each row and each
     * column is a permutation).
     * 
     * @param matrix a square matrix of integers.
     * @return true if mat is a latin square, false otherwise.
     */
    public static boolean checkLatSquare(int[][] matrix) {
        
        //Check the rows
        for(int i=0; i<matrix.length; i++) {
            
            if(!checkPerm(matrix[i])) {
                return false;
            }
            
        }
        
        //Check the columns
        for(int j=0; j<matrix[0].length; j++) {
            
            int[] col = new int[matrix.length];
            
            for(int i=0; i<col.length; i++) {
                col[i] = matrix[i][j];
            }
            
            if(!checkPerm(matrix[j])) {
                return false;
            }
            
        }
        
        return true;
        
    }
    
    /**
     * Check whether two latin squares are orthogonal, that is by superposing
     * them each ordered pair of symbols appears exactly one time.
     * @param ls1 first latin square
     * @param ls2 second latin square. Must be of the same dimension of ls1
     * @return true if ls1 and ls2 are orthogonal, false otherwise
     */
    public static boolean checkOrthogLatSquare(int[][] ls1, int[][] ls2) {
        
        boolean[][] mark = new boolean[ls1.length][ls1[0].length];
        
        for(int i=0; i<ls1.length; i++) {
            
            for(int j=0; j<ls1[i].length; j++) {
                
                //If the couple in position (i,j) has already been marked,
                //return false, otherwise mark it
                if(mark[ls1[i][j]-1][ls2[i][j]-1]) {
                    return false;
                } else {
                   mark[ls1[i][j]-1][ls2[i][j]-1] = true;                 
                }  
                
            }
            
        }
        
        return true;
        
    }
    
    /**
     * Compute the cardinality of each pair (0,0), (1,0), (0,1), (1,1) by
     * juxtaposing the truth tables of two boolean functions
     * 
     * @param table1
     * @param table2
     * @return 
     */
    public static int[] checkPairsTable(boolean[] table1, boolean[] table2) {
        
        int[] card = new int[4];
        
        for(int i=0; i<table1.length; i++) {
            
            boolean[] curpair = {table1[i], table2[i]};
            int numpair = BinTools.bin2Dec(curpair);
            
            card[numpair]++;
            
        }
        
        return card;
        
    }
    
    /**
     * Given two (orthogonal) Latin squares and a pair of coordinates row/col,
     * return the superimposed entry at those coordinates
     * 
     * @param square1   first latin square
     * @param square2   second latin square
     * @param row       row coordinate
     * @param col       column coordinate
     * @return          the superposed entry at the coordinates (row,col)
     */
    public static int[] evaluateOLS(int[][] square1, int[][] square2, int row,
            int col) {
        
        int[] entry = {square1[row][col], square2[row][col]};
        //System.out.println(entry[0]+" "+entry[1]);
        
        return entry;
        
    }
    
    /**
     * Iterate a pair of (orthogonal) Latin squares of side n for n^2 steps
     * starting from a specific coordinate row/col
     * 
     * @param square1
     * @param square2
     * @param startrow
     * @param startcol
     * @return 
     */
    public static int[][] iterateOLS(int[][] square1, int[][] square2,
            int startrow, int startcol) {
        
        int n = square1.length;
        int n2 = n*n;
        int[][] orbit = new int[n2][2];
        
        int row = startrow;
        int col = startcol;
        
        for(int i=0; i<n2; i++) {
            
            orbit[i] = evaluateOLS(square1, square2, row, col);
            row = orbit[i][0];
            col = orbit[i][1];
            
        }
        
        return orbit;
        
    }
    
    /**
     * Return the position of an OLSEntry into a vector (-1 if not found)
     * 
     * @param val
     * @param vect
     * @return 
     */
    public static int positionOf(OLSEntry val, Vector<OLSEntry> vect) {
        
        for(int i=0; i<vect.capacity(); i++) {
            
            if(val.getEntry()[0] == vect.elementAt(i).getEntry()[0] &&
                    val.getEntry()[1] == vect.elementAt(i).getEntry()[1]) {
                
                return i;
                
            }
            
        }
        
        return -1;
        
    }
    
    /**
     * Print the cycle decomposition obtained by iteration of two OLS
     * 
     * @param square1 
     * @param square2 
     */
    public static void decomposeOLSCycles(int[][] square1, int[][] square2) {
        
        int n = square1.length;
        Vector<OLSEntry> pairs = new Vector<OLSEntry>();
        
        //fill the initial list of ordered pairs
        for(int i=1; i<=n; i++) {
            
            for(int j=1; j<=n; j++) {
                
                OLSEntry curpair = new OLSEntry(i,j);
                pairs.add(curpair);
                
            }
            
        }
        
        pairs.trimToSize();
        
        //Until the list of pairs is not empty, build the cycle decomposition
        while(pairs.capacity() != 0) {
            
            //Initialize the current cycle with the first element taken from
            //the list of remaining entries in pairs
            OLSEntry startentry = pairs.elementAt(0);
            OLSEntry newentry = null;
            int[] coord = new int[2];
            //the entries are over 1..n, but the coordinates for the arrays in need to be over 0..n-1
            coord[0] = startentry.getEntry()[0]-1;
            coord[1] = startentry.getEntry()[1]-1;
            pairs.removeElementAt(0);
            pairs.trimToSize();
            Vector<OLSEntry> curcycle = new Vector<OLSEntry>();
            curcycle.add(startentry);
            
            do {
                
                //Iterate the OLS
                newentry = new OLSEntry(evaluateOLS(square1, square2,
                        coord[0], coord[1]));
                
                
                //Condition to continue the iteration: the new entry differs
                //from the starting one
                if((newentry.getEntry()[0] != startentry.getEntry()[0]) || (newentry.getEntry()[1] != startentry.getEntry()[1])) {
                    
                    int entrypos = positionOf(newentry, pairs);
                    //System.out.println("Remove entry ("+newentry.getEntry()[0]+","+newentry.getEntry()[1]+") at position "+entrypos);
                    pairs.removeElementAt(entrypos);
                    pairs.trimToSize();
                    curcycle.add(newentry);
                    coord[0] = newentry.getEntry()[0]-1;
                    coord[1] = newentry.getEntry()[1]-1;
                    
                }
                
            } while((newentry.getEntry()[0] != startentry.getEntry()[0]) || (newentry.getEntry()[1] != startentry.getEntry()[1]));
            
            //When the current cycle finished, print it along with its length
            curcycle.trimToSize();
            System.out.print("Cycle of length "+curcycle.capacity()+" : [ ");
            for(int i=0; i<curcycle.capacity(); i++) {
                
                int[] curcoord = curcycle.elementAt(i).getEntry();
                System.out.print("("+curcoord[0]+","+curcoord[1]+") ");
                
            }
            System.out.println("]");
            
        }
        
    }
    
}
