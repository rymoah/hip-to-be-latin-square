package boolfun;



/**
 *
 * Utilities to convert and manipulate binary strings and numbers. The
 * conversion methods come in two versions, one using int and the other
 * using BigInteger (to convert bigger numbers). The order used in the
 * bitstrings is LSBF (Least Significant Bit First).
 * 
 * @author  Luca Mariot
 * @version 1.0
 */

import java.math.BigInteger;
import java.util.Vector;

public class BinTools {   

    /**
     * Converts a binary string in a decimal number (BigInteger version).
     * 
     * @param   bNum a binary string (LSBF order)
     * @return  dNum the conversion of bNum as a decimal number
     */
    public static BigInteger bin2DecBig(boolean[] bNum) {
        
        BigInteger dNum = new BigInteger("0");
        
        for(int i=0; i<bNum.length; i++) {
            
             if(bNum[i]) {
                 BigInteger toAdd = new BigInteger("2");
                 toAdd = toAdd.pow(i);
                 dNum = dNum.add(toAdd);
             }
             
        }

        return dNum;
        
    }

    /**
     * Converts a binary string in a decimal number(int version).
     * 
     * @param   bNum a binary string (LSBF order)
     * @return  dNum the conversion of bNum as a decimal number
     */
    public static int bin2Dec(boolean[] bNum) {
        
        int dNum = 0;
        
        for(int i=0; i<bNum.length; i++) {
            
             if(bNum[i]) {
                 dNum += Math.pow(2, i);
             }
             
        }

        return dNum;

    }

    /**
     * Converts a decimal number in a binary string (BigInteger version).
     * 
     * @param   dNum    a decimal number
     * @param   length  the length of the binary string necessary to hold dNum
     * @return  bNum    the conversion of dNum as a binary string
     */
    public static boolean[] dec2Bin(BigInteger dNum, int length) {
        
        boolean[] bNum = new boolean[length];
        BigInteger temp = dNum;
        BigInteger two = new BigInteger("2");
        int i = 0;

        while(temp.compareTo(BigInteger.ZERO) != 0) {

            BigInteger mod = temp.remainder(two);
            temp = temp.divide(two);

            if(mod.compareTo(BigInteger.ONE) == 0) {
                bNum[i] = true;
            }

            i++;

        }

        return bNum;

    }

    /**
     * Converts a decimal number in a binary string (int version).
     * 
     * @param   dNum    a decimal number
     * @param   length  the length of the binary string necessary to hold dNum
     * @return  bNum    the conversion of dNum as a binary string
     */
    public static boolean[] dec2BinMod(int dNum, int length) {
        
        boolean[] bNum = new boolean[length];
        int temp = dNum;
        int i = 0;

        while(temp!=0) {

            int mod = temp%2;
            temp = temp/2;

            if(mod==1) {
                bNum[i] = true;
            }

            i++;
        }

        return bNum;

    }
    
    /**
     * Converts a decimal number in a n-ary string (BigInteger version).
     * 
     * @param   dNum    a decimal number
     * @param   length  the length of the binary string necessary to hold dNum
     * @return  bNum    the conversion of dNum as a binary string
     */
    public static int[] dec2Nary(BigInteger dNum, int length, int n) {
        
        int[] enNum = new int[length];
        BigInteger temp = dNum;
        BigInteger en = new BigInteger(Integer.toString(n));
        int i = 0;

        while(temp.compareTo(BigInteger.ZERO) != 0) {

            BigInteger mod = temp.remainder(en);
            temp = temp.divide(en);

            //System.out.println(mod);
            enNum[i] = Integer.parseInt(mod.toString());

            i++;

        }

        return enNum;

    }
    
    /**
     * Converts a decimal number in a n-ary string (int version).
     * 
     * @param   dNum    a decimal number
     * @param   length  the length of the binary string necessary to hold dNum
     * @return  bNum    the conversion of dNum as a binary string
     */
    public static int[] dec2NaryInt(int dNum, int length, int n) {
        
        int[] enNum = new int[length];
        int temp = dNum;
        int i = 0;

        while(temp != 0) {

            int mod = temp % n;
            temp = temp / n;

            //System.out.println(mod);
            enNum[i] = mod;

            i++;

        }

        return enNum;

    }
    
    /**
     * Converts a n-ary string in a decimal number (BigInteger version).
     * 
     * @param   enNum an n-ary string (LSBF order)
     * @param   n radix
     * @return  dNum the conversion of bNum as a decimal number
     */
    public static BigInteger nary2DecBig(int[] enNum, int n) {
        
        BigInteger dNum = new BigInteger("0");
        BigInteger en = new BigInteger(Integer.toString(n));
        
        for(int i=0; i<enNum.length; i++) {

            BigInteger toAdd = en;
            toAdd = toAdd.pow(i);
            toAdd = toAdd.multiply(new BigInteger(Integer.toString(enNum[i])));
            dNum = dNum.add(toAdd);
             
        }

        return dNum;
        
    }
    
    /**
     * Converts a n-ary string in a decimal number (int version).
     * 
     * @param   enNum an n-ary string (LSBF order)
     * @param   n radix
     * @return  dNum the conversion of bNum as a decimal number
     */
    public static int nary2DecInt(int[] enNum, int n) {
        
        int dNum = 0;
        
        for(int i=0; i<enNum.length; i++) {

            dNum = dNum + enNum[i]* (int)Math.pow(n, i);
             
        }

        return dNum;
        
    }

    /**
     * Converts a binary string represented as a boolean array in a
     * corresponding string of 0s and 1s.
     * 
     * @param   boolstr the binary string represented as a boolean array
     * @return  binstr  the binary string represented as string of 0s and 1s.
     */
    public static String bool2Bin(boolean[] boolstr) {
            
        String binstr = "";

        for(int i=0; i<boolstr.length; i++) {
                
            if(boolstr[i])
                binstr += "1";
            else
                binstr += "0";
            
        }

        return binstr;
            
    }

    /**
     * Converts a single boolean value in a 0 (false) or 1 (true).
     * 
     * @param   bval a boolean value.  
     * @return  1 if bval=true, 0 otherwise
     */
    public static int singleBool2Bin(boolean bval) {
        
        if(bval)
            return 1;
        else
            return 0;
        
    }
    
  
    
    /**
     * Inverts the order of a boolean array.
     * 
     * 
     * @param   binStr  a boolean array
     * @return  rev     the reverse of binStr
     */
    public static boolean[] reverse(boolean[] binStr) {
            
        boolean[] rev = new boolean[binStr.length];

        for(int i=0; i<rev.length; i++) {

            rev[i] = binStr[binStr.length-1-i];

        }

        return rev;

    }
    
    /**
     * Computes the Hamming weight of a boolean vector.
     * 
     * @boolean[] binStr    The boolean vector whose Hamming weight has to be
     *                      computed.
     * @return              The Hamming weight of binStr.
     */
    public static int hwt(boolean[] binStr) {
        
        int weight = 0;
        
        for(int i=0; i<binStr.length; i++) {
            
            if(binStr[i])
                weight++;
            
        }
        
        return weight;
        
    }
    
    /** Compute the scalar product between two boolean vectors-
     * 
     * @param vect1
     * @param vect2
     * @return 
     */
    public static boolean scalarProduct(boolean[] vect1, boolean[] vect2) {
        
        boolean prod = false;
        
        for(int i=0; i<vect2.length; i++) {
            
            prod ^= vect1[i] && vect2[i];
            
        }
        
        return prod;
        
    }
    
    public static boolean[] String2BoolStr(String binstr) {
        
        boolean[] toRet = new boolean[binstr.length()];
        
        for(int i=0; i<binstr.length(); i++) {
            
            if(binstr.charAt(i)=='1') {
                toRet[i] = true;
            } else {
                toRet[i] = false;
            }
        }
        
        return toRet;
        
    }
    
}
