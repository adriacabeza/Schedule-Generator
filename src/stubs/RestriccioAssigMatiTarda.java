package stubs;

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
        if(this.assig.equals(assig.getNom())){
            if(mati) return (hora <= 6);
            return (hora > 6);
        }
        return true;
    }

    public String getAssig() {
        return "AC";
    }


    public boolean getMati(){
        return true;
    }
}

