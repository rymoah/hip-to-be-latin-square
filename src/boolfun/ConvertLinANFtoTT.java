package boolfun;



/**
 * Convert the ANF of a linear boolean function to its corresponding truth table.
 * 
 * @author Luca Mariot (l.mariot@campus.unimib.it)
 */

import java.math.BigInteger;

public class ConvertLinANFtoTT {
    
    /**
     * Convert the ANF of a linear boolean function in n variables, specified
     * by its control vector in the scalar product, to the corresponding
     * truth table.
     * 
     * @param vect The control vector of the function in the scalar product
     * @return The truth table of the function 
     */
    public static boolean[] convAnf2Tt(boolean[] vect) {
        
        int tablen = (int)Math.pow(2,vect.length);
        boolean[] ttable = new boolean[tablen];
        
        for(int i=0; i<tablen; i++) {
            
            //Convert the current input vector in boolean form
            boolean[] input = BinTools.dec2BinMod(i, vect.length);
            
            //Compute the scalar product between the control vector
            //and the input vector
            ttable[i] = BinTools.scalarProduct(vect,input);
            
            //If the last bit of the control vector is set, complement the
            //bit of the truth table
            /*if(vect[vect.length-1]) {
                ttable[i] ^= true;
            }*/
                
            
        }
        
        return ttable;
        
    }
    
}
