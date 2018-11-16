package stubs;

import exceptions.NotFoundException;
import model.Assignacio;

import java.util.ArrayList;
import java.util.HashMap;


public class RestriccioCorrequisit extends Restriccions {


    public RestriccioCorrequisit() {
        super(1);
    }


    public boolean isable(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) throws NotFoundException {
        return true;
    }



    public boolean isAble2(SessioGrup check, SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora) throws NotFoundException {
        return true;
    }


}
