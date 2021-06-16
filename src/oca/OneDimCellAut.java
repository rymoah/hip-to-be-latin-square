package oca;


import boolfun.BinTools;

/**
 * Implementation of a 1-dimensional cellular automata, with generic radius r
 * and periodic boundary conditions.
 * 
 * @author Luca Mariot
 * @version 1.0
 */

public class OneDimCellAut {
    
    private boolean[] cells;        //cells array
    private boolean[] rule;         //local rule (represented in LSBF order).
    private boolean[] rule2;         //2nd local rule (switched) (represented in LSBF order).
    private int radius;             //CA radius (rule.length = 2*radius+1)
    private int nbr;            //CA neighborhood
    private int offset;             //center of the neighborhood (cell to update)

    /**
     * Computes the transition function of a single cell of the CA, given in
     * input its neighborhood.
     * 
     * @param nbrhood   the neighborhood of the cell
     * @return          the value of the local rule corresponding to nbrhood
     */
    private boolean delta(boolean[] nbrhood) {
        
        int index = BinTools.bin2Dec(nbrhood);
        boolean value = false;

        value = rule[index];
         
        return value;

    }
    
    /**
     * Computes the second (switched) transition function of a single cell of
     * the CA, given in input its neighborhood.
     * 
     * @param nbrhood   the neighborhood of the cell
     * @return          the value of the local rule corresponding to nbrhood
     */
    private boolean delta2(boolean[] nbrhood) {
        
        int index = BinTools.bin2Dec(nbrhood);
        boolean value = false;

        value = rule2[index];
         
        return value;

    }

    /**
     * Class constructor. 
     * 
     * @param nCells    the number of the cells of the CA
     * @param rule      the local rule of the CA
     * @param radius    the radius of the CA
     */
    public OneDimCellAut(int nCells, boolean[] rule, int radius) {
        
        cells = new boolean[nCells];
        this.rule = rule;
        this.radius = radius;

    }
    
    public OneDimCellAut(int nCells, boolean[] rule, boolean[] rule2, int radius) {
        
        cells = new boolean[nCells];
        this.rule = rule;
        this.rule2 = rule;
        this.radius = radius;

    }
    
    public OneDimCellAut(int nCells, boolean[] rule, int nbr, int offset) {
        
        cells = new boolean[nCells];
        this.rule = rule;
        this.nbr = nbr;
        this.offset = offset;

    }
    
    public OneDimCellAut(int nCells, boolean[] rule, boolean[] rule2, int nbr, int offset) {
        
        cells = new boolean[nCells];
        this.rule = rule;
        this.rule2 = rule2;
        this.nbr = nbr;
        this.offset = offset;

    }    
    
    /**
     * Evolves the CA from its current configuration to the next, with no
     * boundary conditions. This method shrinks the configuration length by
     * 2r bits
     */
    public void nextConfigNoBound() {
        
        int n = cells.length;
        int r = radius;
        
        //Check whether there are enough bits to apply the local rule.
        if(n > 2*r) {
            
            boolean[] nextConf = new boolean[cells.length-(2*r)];
            
            for(int i=0; i<nextConf.length; i++) {
                
                //Build the neighborhood of the i-th cell
                boolean[] nbrhood = new boolean[(2*r)+1];
                for(int j=0; j<r; j++) {

                    nbrhood[j] = cells[i+j];
                    nbrhood[nbrhood.length-1-j] = cells[i+nbrhood.length-1-j];

                }
                
                //Update the state of the i-th cell. The neighborhood has to
                //be reversed, since the representation of the truth table is
                //in Least Significant Bit First order.
                nbrhood[r] = cells[i+r];
                nextConf[i] = delta(BinTools.reverse(nbrhood));
                
            }
            
            cells=nextConf;
            
        }
        
    }
    
    /**
     * Evolves the CA from its current configuration to the next one, except
     * for the cells specified in the parameter array which are not updated
     * 
     * @param blocked positions of the blocked cells (must have the same length
     *                as the cellular array minus the neighborhood plus 1)
     */
    public void nextAsyncConfNoBdAsym(boolean[] blocked) {

        boolean[] nextConf = new boolean[cells.length-nbr+1];
        int n = cells.length;
        
        //Check whether there are at least nbr cells to apply the local rule
        if(n >= nbr) {
            
            for(int i=0; i<blocked.length; i++) {
                
                if(!blocked[i]) {
                    //Apply local rule as usual
                    //Build the neighborhood of the i-th cell
                    boolean[] nbrhood = new boolean[nbr];
                    for(int j=0; j<nbr; j++) {

                        nbrhood[j] = cells[i+j];

                    }

                    //Update the state of the i-th cell. The neighborhood has to
                    //be reversed, since the representation of the truth table is
                    //in Least Significant Bit First order.
                    nextConf[i] = delta(nbrhood);
                    
                } else {
                    
                    //Copy the value of the cell as is.
                    nextConf[i] = cells[i+offset];
                    
                }
                
            }
            
        }

        cells=nextConf;

    }

    //Getters and setters methods
    
    public boolean[] getCells() {
        return cells;
    }

    public void setCells(boolean[] cells) {
        this.cells = cells;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean[] getRule() {
        return rule;
    }

    public void setRule(boolean[] rule) {
        this.rule = rule;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean[] getRule2() {
        return rule2;
    }

    public void setRule2(boolean[] rule2) {
        this.rule2 = rule2;
    }   
    
    
}
