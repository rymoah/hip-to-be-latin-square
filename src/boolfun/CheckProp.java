package boolfun;



/**
 * Computes and prints the properties (attributes) of a BooleanFunction object.
 * 
 * @author Luca Mariot
 * @version 1.0
 * 
 */

import java.math.BigInteger;

public class CheckProp {
    
    /**
     * Computes all cryptographic properties of a boolean function, excluding the
     * statistical tests. The following attributes are supposed to be already
     * set: deccode, ttable, poltable, nvar, ninputs. Since all the transforms
     * methods modify the vectors inplace, it is necessary to make copies of
     * them before invoking those methods. The properties related to the ANF
     * and the algebraic degree are computed only if a flag is set.
     * 
     * @param boolfun a BooleanFunction instance representing the function.
     * @param indices matrix containing the indices of the vector of a given
     *        Hamming weight.
     */
    public static void computeAllCryptoProp(BooleanFunction boolfun,
            int[][] indices) {
        
        //Compute FMT-related attributes (ANF and algebraic degree)
        boolean[] anfcoeffs = new boolean[boolfun.getNinputs()];
        boolean[] ttable = boolfun.getTtable();
        System.arraycopy(ttable, 0, anfcoeffs, 0, anfcoeffs.length);
        int algdeg = BoolTransf.calcFMT(anfcoeffs, 0, anfcoeffs.length);
        boolfun.setAnfcoeffs(anfcoeffs);
        boolfun.setAlgdeg(algdeg);
        
        //Compute FWT-related attributes
        int[] whcoeffs = new int[boolfun.getNinputs()];
        int[] poltable = boolfun.getPoltable();
        System.arraycopy(poltable, 0, whcoeffs, 0, whcoeffs.length);
        int sprad = BoolTransf.calcFWT(whcoeffs, 0, whcoeffs.length);
        int nlin = BoolTransf.calcNL(sprad, boolfun.getNvar());
        int[] cid = new int[boolfun.getNvar()];
        int[] reqcid = BoolTransf.calcDevs(whcoeffs, indices);
        System.arraycopy(reqcid, 0, cid, 0, reqcid.length);
        
        if(whcoeffs[0] == 0) {
            boolfun.setIsBalanced(true);
        } else {
            boolfun.setIsBalanced(false);
        }
        boolfun.setWhcoeffs(whcoeffs);
        boolfun.setSprad(sprad);
        boolfun.setNlin(nlin);
        boolfun.setCid(cid);
        
        //Compute AC-related attributes
        int[] accoeffs = new int[boolfun.getNinputs()];
        System.arraycopy(whcoeffs, 0, accoeffs, 0, accoeffs.length);
        int acmax = BoolTransf.calcAC(accoeffs, true);
        int ssi = BoolTransf.calcSSI(accoeffs);
        int[] pcd = new int[boolfun.getNvar()];
        int[] reqpcd = BoolTransf.calcDevs(accoeffs, indices);
        System.arraycopy(reqpcd, 0, pcd, 0, reqpcd.length);
        int nzls = BoolTransf.countNZLinStruct(accoeffs);
        
        boolfun.setAccoeffs(accoeffs);
        boolfun.setAcmax(acmax);
        boolfun.setSsi(ssi);
        boolfun.setNlinstruct(nzls);
        boolfun.setPcd(pcd);
        
        //Compute chaos-related attributes
        boolean[] iperm = BoolTransf.checkPerm(accoeffs, boolfun.getNvar());
        boolfun.setIperm(iperm);
        
    }
    
    public static void computeANF(BooleanFunction boolfun) {
        
        //Compute FMT-related attributes (ANF and algebraic degree)
        boolean[] anfcoeffs = new boolean[boolfun.getNinputs()];
        boolean[] ttable = boolfun.getTtable();
        System.arraycopy(ttable, 0, anfcoeffs, 0, anfcoeffs.length);
        int algdeg = BoolTransf.calcFMT(anfcoeffs, 0, anfcoeffs.length);
        boolfun.setAnfcoeffs(anfcoeffs);
        boolfun.setAlgdeg(algdeg);
        
    }
    
    /**
     * Computes only the Walsh spectrum, balancedness, spectral radius and
     * nonlinearity of a boolean function -- useful when Hill Climbing is
     * performed together with a Genetic Algorithm.
     * 
     * @param boolfun   a BooleanFunction object representing a boolean function.
     */
    public static void computeWHProp(BooleanFunction boolfun, int[][] indices) {
        
        //Compute FWT-related attributes
        int[] whcoeffs = new int[boolfun.getNinputs()];
        int[] poltable = boolfun.getPoltable();
        System.arraycopy(poltable, 0, whcoeffs, 0, whcoeffs.length);
        int sprad = BoolTransf.calcFWT(whcoeffs, 0, whcoeffs.length);
        int nlin = BoolTransf.calcNL(sprad, boolfun.getNvar());
        
        if(whcoeffs[0] == 0) {
            boolfun.setIsBalanced(true);
        } else {
            boolfun.setIsBalanced(false);
        }
        boolfun.setWhcoeffs(whcoeffs);
        boolfun.setSprad(sprad);
        boolfun.setNlin(nlin);
        
        int[] cid = new int[boolfun.getNvar()];
        int[] reqcid = BoolTransf.calcDevs(whcoeffs, indices);
        System.arraycopy(reqcid, 0, cid, 0, reqcid.length);
        boolfun.setCid(cid);
        
    }
    
    public static void computeNlin(BooleanFunction boolfun) {
        
        //Compute FWT-related attributes
        int[] whcoeffs = new int[boolfun.getNinputs()];
        int[] poltable = boolfun.getPoltable();
        System.arraycopy(poltable, 0, whcoeffs, 0, whcoeffs.length);
        int sprad = BoolTransf.calcFWT(whcoeffs, 0, whcoeffs.length);
        int nlin = BoolTransf.calcNL(sprad, boolfun.getNvar());
        
        if(whcoeffs[0] == 0) {
            boolfun.setIsBalanced(true);
        } else {
            boolfun.setIsBalanced(false);
        }
        boolfun.setWhcoeffs(whcoeffs);
        boolfun.setSprad(sprad);
        boolfun.setNlin(nlin);
        
    }
    
    /**
     * Computes the cyptographic properties of a boolean function, excluding
     * the Walsh spectrum, balancedness and nonlinearity.
     * 
     * @param boolfun   a BooleanFunction object representing a boolean function.
     * @param indices   indices matrix containing the indices of the vector of a given
     *                  Hamming weight.
     */
    public static void computeRemProp(BooleanFunction boolfun, 
            int[][] indices) {
        
        //Compute CI deviations
        int[] whcoeffs = boolfun.getWhcoeffs();
        int[] cid = new int[boolfun.getNvar()];
        int[] reqcid = BoolTransf.calcDevs(whcoeffs, indices);
        System.arraycopy(reqcid, 0, cid, 0, reqcid.length);
        boolfun.setCid(cid);
        
        //Compute FMT-related attributes (ANF and algebraic degree)
        boolean[] anfcoeffs = new boolean[boolfun.getNinputs()];
        boolean[] ttable = boolfun.getTtable();
        System.arraycopy(ttable, 0, anfcoeffs, 0, anfcoeffs.length);
        int algdeg = BoolTransf.calcFMT(anfcoeffs, 0, anfcoeffs.length);
        boolfun.setAnfcoeffs(anfcoeffs);
        boolfun.setAlgdeg(algdeg);
        
        //Compute AC-related attributes
        int[] accoeffs = new int[boolfun.getNinputs()];
        System.arraycopy(whcoeffs, 0, accoeffs, 0, accoeffs.length);
        int acmax = BoolTransf.calcAC(accoeffs, true);
        int[] pcd = new int[boolfun.getNvar()];
        int[] reqpcd = BoolTransf.calcDevs(accoeffs, indices);
        System.arraycopy(reqpcd, 0, pcd, 0, reqpcd.length);
        int nzls = BoolTransf.countNZLinStruct(accoeffs);
        
        boolfun.setAccoeffs(accoeffs);
        boolfun.setAcmax(acmax);
        boolfun.setNlinstruct(nzls);
        boolfun.setPcd(pcd);
        
        //Compute chaos-related attributes
        boolean[] iperm = BoolTransf.checkPerm(accoeffs, boolfun.getNvar());
        boolfun.setIperm(iperm);
        
    }
    
    /**
     * Computes the cyptographic properties of a boolean function, excluding
     * the Walsh spectrum, balancedness and nonlinearity.
     * 
     * @param boolfun   a BooleanFunction object representing a boolean function.
     */
    public static void computeIPerm(BooleanFunction boolfun) {
        
        //Compute CI deviations
        int[] whcoeffs = boolfun.getWhcoeffs();
        
        //Compute AC-related attributes
        int[] accoeffs = new int[boolfun.getNinputs()];
        System.arraycopy(whcoeffs, 0, accoeffs, 0, accoeffs.length);
        int acmax = BoolTransf.calcAC(accoeffs, true);
        int nzls = BoolTransf.countNZLinStruct(accoeffs);
        
        boolfun.setAccoeffs(accoeffs);
        boolfun.setAcmax(acmax);
        
        //Compute chaos-related attributes
        boolean[] iperm = BoolTransf.checkPerm(accoeffs, boolfun.getNvar());
        boolfun.setIperm(iperm);
        
    }
    
    public static void printFuncNum(BooleanFunction boolfun) {
        
        int nvar = boolfun.getNvar();
        System.out.println("\nFunction "+boolfun.getDeccode());
        System.out.println("\nNumber of variables: "+nvar);
        
    }
    
    /**
     * Prints the truth table, Walsh spectrum and autocorrelation function
     * of a boolean function.
     * 
     * @param boolfun a BooleanFunction object representing a boolean function.
     */
    public static void printFuncTables(BooleanFunction boolfun) {
        
        //Print truth table
        int nvar = boolfun.getNvar();
        System.out.println("\nTruth table:");
        boolean[] ttable = boolfun.getTtable();
        for(int i=0; i<ttable.length; i++) {
            
            System.out.println("f("+
                    BinTools.bool2Bin(BinTools.dec2BinMod(i, nvar))+") = "+
                    BinTools.singleBool2Bin(ttable[i]));
            
        }
        
        //Print Walsh spectrum.
        System.out.println("\nWalsh spectrum:");
        int[] whcoeffs = boolfun.getWhcoeffs();
        for(int i=0; i<whcoeffs.length; i++) {
            
            System.out.println("F("+
                    BinTools.bool2Bin(BinTools.dec2BinMod(i, nvar))+") = "+
                    whcoeffs[i]);
        
        }
        
        //Print autocorrelation function.
        System.out.println("\nAutocorrelation function:");
        int[] accoeffs = boolfun.getAccoeffs();
        for(int i=0; i<accoeffs.length; i++) {
            
            System.out.println("r("+
                    BinTools.bool2Bin(BinTools.dec2BinMod(i, nvar))+") = "+
                    accoeffs[i]);
        
        }
        
    }
    
    /**
     * Prints the algebraic normal form (ANF) of a boolean function.
     * 
     * @param boolfun a BooleanFunction object representing a boolean function.
     */
    public static void printANF(BooleanFunction boolfun) {
        
        int nvar = boolfun.getNvar();
        boolean[] anfcoeffs = boolfun.getAnfcoeffs();
        
        //Find the last nonzero coefficient in the ANF
        int last = 0;
        int k = anfcoeffs.length-1;
        
        while((last == 0) && (k>=0)) {
            
            if(anfcoeffs[k])
                last = k;
            
            k--;
            
        }
        
        //Print the ANF
        //System.out.println("\nAlgebraic Normal Form:");
        System.out.print("f(");
        for(int i=0; i<nvar; i++) {
            System.out.print("x"+(i+1));
            if(i<nvar-1) {
                System.out.print(",");
            }
        }
        System.out.print(") = ");
        
        if(anfcoeffs[0]) {
            System.out.print("1 + ");
        }
        
        for(int i=1; i<=last; i++) {
            
            if(anfcoeffs[i]) {
                
                //Print the i-th variation of variables
                boolean[] input = BinTools.dec2BinMod(i,nvar);
                
                for(int j=0; j<nvar; j++) {
                    
                    if(input[j]) {
                        
                        System.out.print("x"+(j+1));
                        
                    }
                    
                }
                
                if(i<last) {
                    System.out.print(" + ");
                }
                
                
            }
            
        }
        
        //System.out.println("");
        
    }
    
    /**
     * Prints the algebraic normal form (ANF) of a boolean function.
     * 
     * @param boolfun a BooleanFunction object representing a boolean function.
     */
    public static String printANFLatex(BooleanFunction boolfun) {
        
        String toRet = "";
        int nvar = boolfun.getNvar();
        boolean[] anfcoeffs = boolfun.getAnfcoeffs();
        
        //Find the last nonzero coefficient in the ANF
        int last = 0;
        int k = anfcoeffs.length-1;
        
        while((last == 0) && (k>=0)) {
            
            if(anfcoeffs[k])
                last = k;
            
            k--;
            
        }
        
        //Print the ANF
        //System.out.println("\nAlgebraic Normal Form:");
        toRet +="$f(";
        for(int i=0; i<nvar; i++) {
            toRet +="x_"+(i+1);
            if(i<nvar-1) {
                toRet +=",";
            }
        }
        toRet +=") = ";
        
        if(anfcoeffs[0]) {
            toRet +="1 \\oplus ";
        }
        
        for(int i=1; i<=last; i++) {
            
            if(anfcoeffs[i]) {
                
                //Print the i-th variation of variables
                boolean[] input = BinTools.dec2BinMod(i,nvar);
                
                for(int j=0; j<nvar; j++) {
                    
                    if(input[j]) {
                        
                        toRet +="x_"+(j+1);
                        
                    }
                    
                }
                
                if(i<last) {
                    toRet +=" \\oplus ";
                }
                
                
            }
            
        }
        
        toRet +="$";
        
        return toRet;
        
    }
    
    public static String printCompact(BooleanFunction bf) {
        
        return "Rule: "+bf.getDeccode()+"\tNL: "+bf.getNlin()+"\tDEG: "+bf.getAlgdeg();
        
    }

    
    /**
     * Prints the polynomial associated to the linear terms of a boolean function.
     * 
     * @param boolfun a BooleanFunction object representing a boolean function.
     */
    public static String printPolynomial(BooleanFunction boolfun) {
        
        String poly = "";
        int nvar = boolfun.getNvar();
        boolean[] anfcoeffs = boolfun.getAnfcoeffs();
        
        //Find the last nonzero coefficient in the ANF
        int last = 0;
        int k = anfcoeffs.length-1;
        
        while((last == 0) && (k>=0)) {
            
            if((anfcoeffs[k]) && (BinTools.hwt(BinTools.dec2BinMod(k, nvar)) == 1))     //only care for linear terms of weight 1
                last = k;
            
            k--;
            
        }
        
        //Print the Polynomial
        //System.out.println("\nAlgebraic Normal Form:");
        poly += "P(X) = ";
        int p=-1;
        
        for(int i=1; i<=last; i++) {
            
            if((BinTools.hwt(BinTools.dec2BinMod(i, nvar)) == 1)) {
                
                p++;
                if(anfcoeffs[i]) {
                    
                    //Print the i-th variation of variables
                    if(p==0) {
                        poly += "1";
                    } else {
                        poly += "X^"+p;
                    }
                    
                    if(i<last) {
                        poly +=" + ";
                    }   
                    
                }
                
                
            }
            
        }
        
        return poly;
        
    }

}
