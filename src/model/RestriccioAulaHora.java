package model;

import exceptions.NotFoundException;

public class RestriccioAulaHora extends Restriccions {

    private int dia;
    private int hora;
    private Aula aula;


    public RestriccioAulaHora(int dia, int hora, Aula aula) {
        super(7);
        this.dia = dia;
        this.hora = hora;
        this.aula = aula;
    }

    public boolean isable(Aula aula, int dia,  int hora){
        if(aula.getKey() == this.aula.getKey() && dia == this.dia && hora == this.hora) return false;
        return true;
    }
}
