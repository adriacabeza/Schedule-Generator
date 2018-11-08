package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioNivell extends Restriccions {
    private int id;
    private boolean active;

    @Override
    public Restriccions() {
        this.id = 2;
        this.active = true;
    }


    @Override
    public boolean isable(Assignacio[][][] horari, int hora, int dia, Assignatura assig, ArrayList<Aula> aules2) {
        for(int i = 0; i<aules2.size(); ++i) if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getQuadrimestre()) return false;
        return true;
    }
}
