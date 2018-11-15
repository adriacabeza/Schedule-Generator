package model;

public class RestriccioAssigMatiTarda extends Restriccions {

    private String assig;
    private boolean mati;       //true implica mati, false implica tarda

    public RestriccioAssigMatiTarda(int id, String assig, boolean mati) {
        super(8);
        this.assig = assig;
        this.mati = mati;
    }

    public boolean isable(Assignatura assig, int hora){
        if(this.assig == assig.getNom()){
            if(mati) return hora < 6;
            return hora > 6;
        }
        return true;
    }

    public String getAssig() {return assig;}
    public boolean getMati(){return mati;}
}
