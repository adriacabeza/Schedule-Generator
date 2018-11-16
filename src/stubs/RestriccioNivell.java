package stubs;

import model.Assignacio;
import model.AssignacioL;
import model.Laboratori;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioNivell extends Restriccions {

    public RestriccioNivell() {
        super(2);
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) {
        for (int i = 0; i < aules2.size(); ++i) {
            Assignacio a = horari[hora][dia][i];
            if (horari[hora][dia][i] != null) {
                if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getAssig().getQuadrimestre()) {
                    if (a.getClass() == AssignacioL.class && assig.getSessio().getClass() == Laboratori.class) {
                        if (a.getGrup().getNum() == assig.getSub().getNum() || a.getGrup().getNum() / 10 == assig.getSub().getNum() / 10) // mateix subgrup o grup de teoria
                            return false;
                    } else { //un dels dos es teoria
                        int auxnum = assig.getGrup().getNum() / 10;
                        if (assig.getSessio().getClass() == model.Laboratori.class) auxnum = assig.getSub().getNum() / 10;
                        if (a.getGrup().getNum() / 10 == auxnum) return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isAble2(SessioGrup check, SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora)  {
        if (assignat.getAssig().getQuadrimestre() == check.getAssig().getQuadrimestre() && assignat.getGrup().getNum() == check.getGrup().getNum()) {
            if (pos.get(check).get(dia).get(hora).contains(aula)){
                return false;
            }
        }
        return true;
    }

}
