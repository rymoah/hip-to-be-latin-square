package oca;



/**
 * Class representing an entry in the superposition of two squares (i.e., a pair
 * of integers)
 * 
 * @author Luca Mariot
 */

public class OLSEntry {
    
    private int[] entry;
    
    public OLSEntry(int en1, int en2) {
        
        entry = new int[2];
        entry[0] = en1;
        entry[1] = en2;
        
    }

    public OLSEntry(int[] entry) {
        this.entry = entry;
    }

    public int[] getEntry() {
        return entry;
    }

    public void setEntry(int[] entry) {
        this.entry = entry;
    }
    
    
    
}
