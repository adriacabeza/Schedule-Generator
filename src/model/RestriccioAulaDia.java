package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioAulaDia extends Restriccions {

    private int dia;
    private Aula aula;

    public RestriccioAulaDia(int dia) {
        super(4);
        this.dia = dia;

    }

    @Override
    public boolean isable() throws NotFoundException {
        return false;
    }


    public boolean isable( Aula aula, int dia){
        if(this.dia == dia && aula.getKey() == this.aula.getKey()) return false;
        return true;
    }

}

