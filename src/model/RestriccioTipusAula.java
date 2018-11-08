package model;

import java.util.ArrayList;

public class RestriccioTipusAula extends  Restriccions {
    private int id;
    private boolean active;


    @Override
    public Restriccions() {
        this.id = 3;
        this.active = true;
    }

    @Override
    public boolean isable(Assignacio[][][] horari, int hora, int aula, int dia, Aula.TipusAula tt) {
        if(horari[hora][dia][aula].getAula().getTipusAula() == tt) return true; //tt és el tipusAula que em diu mishmash
        return true;
    }
}
