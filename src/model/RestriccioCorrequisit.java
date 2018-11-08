package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioCorrequisit extends Restriccions {

    private int id;
    private boolean active;

    public RestriccioCorrequisit() {
        this.id = 1;
        this.active = true;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean isable(Assignacio[][][] horari, int hora, int dia, Assignatura assig, ArrayList<Aula> aules2) {
        for (int j = 0; j < aules2.size(); ++j) {
            try {
                if (horari[hora][dia][j].getAssignatura().getCorrequisits().contains(assig)) return false;
            } catch (NotFoundException e) {
                return true;
            }
        } return true;
    }
}
