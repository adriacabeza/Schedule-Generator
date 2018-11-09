package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioCorrequisit extends Restriccions {

    private int id;
    private boolean active;

    public RestriccioCorrequisit() {
        super(1,true);
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, Assignatura assig, ArrayList<Aula> aules2) throws NotFoundException {
        for (int j = 0; j < aules2.size(); ++j) {
                if (horari[hora][dia][j].getAssignatura().getCorrequisits().contains(assig)) return false; //ha de ser el mateix grup
        } return true;
    }

    @Override
    public boolean isable() {
        return false;
    }
}
