package stubs;


import model.Assignacio;
import model.Restriccions;
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
        return true;
    }

    public boolean isAble2(model.SessioGrup check, model.SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora){
        return true;
    }


}
