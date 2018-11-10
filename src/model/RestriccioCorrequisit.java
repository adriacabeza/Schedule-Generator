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
            Assignacio a = horari[hora][dia][j];
                if (a.getAssignatura().getCorrequisits().contains(assig)) {
                    if(a.getClass() == AssignacioL.class){
                        if(a.getSubgrup())
                    }
                    else {

                    }
                }
        } return true;
    }

    @Override
    public boolean isable() throws NotFoundException {
        return false;
    }
}
