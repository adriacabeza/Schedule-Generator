package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioSubgrupLab extends Restriccions{

    public RestriccioSubgrupLab(){super(5);}

    @Override
    public boolean isable() throws NotFoundException {
        return false;
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2){
        int grup = assig.getSub().getNum()/10;
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if(a.getAssignatura().getNom() == assig.getAssig().getNom()) {
                if (a.getGrup().getNum() == assig.getSub().getNum())
                    return false;                           //solapament de laboratoris
                if (a.getGrup().getNum() / 10 == grup)
                    return false;                           //solapament teoria-laboratori
            }
        }
        return true;
    }
}
