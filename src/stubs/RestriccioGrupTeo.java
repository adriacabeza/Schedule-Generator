package stubs;


import model.Assignacio;
import model.Teoria;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @Author Adria Cabeza
 */


public class RestriccioGrupTeo extends Restriccions {

    public RestriccioGrupTeo() {
        super(4);
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, model.SessioGrup assig, ArrayList<Aula> aules2) {
        int grup = assig.getGrup().getNum() / 10;
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if (a != null) {
                if (a.getAssignatura().getNom() == assig.getAssig().getNom() && a.getGrup().getNum() / 10 == grup)
                    return false;
            }
        }
        return true;
    }

    public boolean isAble2(model.SessioGrup check, model.SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora){
        if (pos.get(check).get(dia).get(hora).contains(aula)) {
            if (check.getAssig().getNom().equals(assignat.getAssig().getNom()) && (check.getSessio().getClass() == Teoria.class)) {
                if (check.getGrup().getNum() == assignat.getGrup().getNum()) return false;
            }
        }
        return true;
    }


}
