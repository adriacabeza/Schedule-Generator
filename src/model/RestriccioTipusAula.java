package model;

import java.util.ArrayList;

public class RestriccioTipusAula extends  Restriccions {
    private int id;
    private boolean active;


    public RestriccioTipusAula() {
        super(3,true);
    }

    public boolean isable(Assignacio[][][] horari, int hora, int aula, int dia, Aula.TipusAula tt) {
        if(horari[hora][dia][aula].getAula().getTipusAula() == tt) return true; //tt és el tipusAula que em diu mishmash
        return true;
    }

    @Override
    public boolean isable() {
        return false;
    }
}
