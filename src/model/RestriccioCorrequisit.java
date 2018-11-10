package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioCorrequisit extends Restriccions {

    public RestriccioCorrequisit() {
        super(1);
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2) throws NotFoundException {
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
                if (a.getAssignatura().getCorrequisits().contains(assig)) {
                    if(a.getClass() == AssignacioL.class && assig.getSessio().getClass() == Laboratori.class){
                        if(a.getGrup().getNum() == assig.getSub().getNum() || a.getGrup().getNum()/10 == assig.getSub().getNum()/10) // mateix subgrup o grup de teoria
                            return  false;
                    }
                    else { //un dels dos es teoria
                        int auxnum = assig.getGrup().getNum()/10;;
                        if(assig.getSessio().getClass() == Laboratori.class) auxnum = assig.getSub().getNum()/10;
                        if(a.getGrup().getNum()/10 == auxnum) return false;
                    }
                }
        } return true;
    }

    @Override
    public boolean isable() throws NotFoundException {
        return false;
    }
}
