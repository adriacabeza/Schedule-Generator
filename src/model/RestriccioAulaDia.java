package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioAulaDia extends Restriccions {

    private int dia;
    private Aula aula;
    //TODO: porque guardar una aula entera en restricció aula i no un identificador?
    public RestriccioAulaDia(int dia) {
        super(6);
        this.dia = dia;

    }

    public boolean isable( Aula aula, int dia){
        if(this.dia == dia && aula.getKey() == this.aula.getKey()) return false;
        return true;
    }

}

