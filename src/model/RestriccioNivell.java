package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioNivell extends Restriccions {


    public RestriccioNivell() {
        super(2);
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2) {
        for(int i = 0; i<aules2.size(); ++i){
            Assignacio a = horari[hora][dia][i];
            if(horari[hora][dia][i] != null) {
                if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getAssig().getQuadrimestre()) {
                    if (a.getClass() == AssignacioL.class && assig.getSessio().getClass() == Laboratori.class) {
                        if (a.getGrup().getNum() == assig.getSub().getNum() || a.getGrup().getNum() / 10 == assig.getSub().getNum() / 10) // mateix subgrup o grup de teoria
                            return false;
                    } else { //un dels dos es teoria
                        int auxnum = assig.getGrup().getNum() / 10;
                        ;
                        if (assig.getSessio().getClass() == Laboratori.class) auxnum = assig.getSub().getNum() / 10;
                        if (a.getGrup().getNum() / 10 == auxnum) return false;
                    }
                }
            }
        }
        return true;
    }


}
