package stubs;


import model.Assignacio;
import model.Restriccions;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioNivell extends Restriccions {

    public RestriccioNivell() {
        super(2);
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) {
        return true;
    }

    public boolean isAble2(SessioGrup check, SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora)  {
        return true;
    }

}
