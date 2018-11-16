package stubs;

import exceptions.NotFoundException;
import model.Assignacio;
import model.AssignacioL;
import model.Laboratori;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioCorrequisit extends Restriccions {


    public RestriccioCorrequisit() {
        super(1);
    }


    public boolean isable(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) throws NotFoundException {
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if (a != null) {
                if (a.getAssignatura().getCorrequisits().contains(assig)) {
                    if (a.getClass() == AssignacioL.class && assig.getSessio().getClass() == model.Laboratori.class) {
                        if (a.getGrup().getNum() == assig.getSub().getNum() || a.getGrup().getNum() / 10 == assig.getSub().getNum() / 10) // mateix subgrup o grup de teoria
                            return false;
                    } else { //un dels dos es teoria
                        int auxnum = assig.getGrup().getNum() / 10;
                        if (assig.getSessio().getClass() == Laboratori.class) auxnum = assig.getSub().getNum() / 10;
                        if (a.getGrup().getNum() / 10 == auxnum) return false;
                    }
                }
            }
        }
        return true;
    }



    public boolean isAble2(SessioGrup check, SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora) throws NotFoundException {
        if (assignat.getAssig().getCorrequisits().contains(check.getAssig().getNom()) &&  assignat.getGrup().getNum() == check.getGrup().getNum()) {
            if (pos.get(check).get(dia).get(hora).contains(aula)){
                return false;
            }
        }
        return true;
    }


}
