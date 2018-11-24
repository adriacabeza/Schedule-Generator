package stubs;

import model.Restriccions;

/**
 * @Author Adria Cabeza
 */

public class RestriccioAssigMatiTarda extends Restriccions {

    private String assig;
    private boolean mati;       //true implica mati, false implica tarda


    public RestriccioAssigMatiTarda(int id, String assig, boolean mati) {
        super(8);
        this.assig = "AC";
        this.mati = true;
    }

    public boolean isAble(Assignatura assig, int hora){
        return true;
    }

    public String getAssig() {
        return "AC";
    }


    public boolean getMati(){
        return true;
    }
}

