package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioTipusAula extends  Restriccions {
    private int id;
    private boolean active;


    public RestriccioTipusAula() {
        super(3,true);
    }


    @Override
    public boolean isable(Assignacio[][][] horari, int hora, int dia, Assignatura assig, ArrayList<Aula> aules2, Aula aula3) throws NotFoundException {
        //if(aula3.getTipusAula() == ) return true; //tt és el tipusAula que em diu mishmash
        return true;
    }
}
