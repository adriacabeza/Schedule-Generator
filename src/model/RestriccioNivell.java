package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioNivell extends Restriccions {
    private int id;
    private boolean active;


    public RestriccioNivell() {
        super(2,true);
    }

    @Override
    public boolean isable(Assignacio[][][] horari, int hora, int dia, Assignatura assig, ArrayList<Aula> aules2, Aula aula3) {
        for(int i = 0; i<aules2.size(); ++i) if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getQuadrimestre()) return false;
        return true;
    }
}
