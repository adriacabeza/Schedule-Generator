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
    public boolean isable(Assignacio[][][] horari, int hora, int aula, int dia, ArrayList<Aula> aules2) {
        if(horari[hora][dia][aula].getAula().getTipusAula() == aules2.get(aula).getTipusAula() ) return true;
        return true;
    }
}
