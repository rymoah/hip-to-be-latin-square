package boolfun;



/**
 * Methods to define the enumerative encoding for bipermutive rules.
 * 
 * @author Luca Mariot
 * @version 1.0
 */

import java.math.BigInteger;
public class BoolFunReps {
    
    /**
     * Given the code of a bipermutive boolean function, returns its truth
     * table.
     * 
     * @param graphconf a boolean array representing the code of a bipermutive
     *                  boolean function.
     * @param nvar      the number of variables of the boolean function.
     * @return func     the decoded truth table of the bipermutive boolean
     *                  function.
     */
    public static boolean[] decodeBipFunc(boolean[] graphconf, int nvar) {
        
        int funclength = (int)Math.pow(2,nvar);
        boolean[] func = new boolean[funclength];
        int half = (int)Math.pow(2, nvar-1);
        
        for(int j=0; j<graphconf.length; j++) {
                
            //Instantiate nodes on the j-th graph
            func[2*j] = graphconf[j];
            func[(2*j)+half+1] = graphconf[j];
            func[(2*j)+1] = !graphconf[j];
            func[(2*j)+half] = !graphconf[j];

        }
        
        return func;
        
    }
    
    /**
     * Given a bipermutive boolean function, returns the binary configuration
     * of its graph.
     * 
     * @param boolfun   a BooleanFunction object representing a bipermutive
     *                  boolean function.
     * @return          the binary configuration of the graph of the function.
     */
    public static boolean[] encodeBipFunc(BooleanFunction boolfun) {
        
        int nvar = boolfun.getNvar();
        //System.out.println(nvar);
        boolean[] ttable = boolfun.getTtable();
        int ngraphs = (int)Math.pow(2,nvar-2);
        boolean[] graphconf = new boolean[ngraphs];
        //System.out.println(graphconf.length);
        
        for(int i=0; i<graphconf.length; i++) {
            
            //The i-th bit of the graph configuration is the value of the
            //function corresponding to the input built by setting the
            //leftmost and rightmost coordinates to 0, and the central
            //coordinates are the binary representation of i.
            boolean[] input = new boolean[nvar];
            boolean[] centr = BinTools.dec2BinMod(i, nvar-2);
            System.arraycopy(centr, 0, input, 1, centr.length);
            int decinput = BinTools.bin2Dec(input);
            graphconf[i] = ttable[decinput];
            
        }
        
        return graphconf;
        
    }
    
}
