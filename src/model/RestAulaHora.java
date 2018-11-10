package model;

import exceptions.NotFoundException;

public class RestAulaHora extends Restriccions {

    private int hora;
    private Aula aula;

    public RestAulaHora(int id, int hora, Aula aula) {
        super(id);
        this.hora = hora;
        this.aula = aula;
    }

    @Override
    public boolean isable() throws NotFoundException {
        return false;
    }

    public boolean isable(Aula aula, int hora){
        if(aula.identificador == this.aula.identificador && hora == this.hora) return false;
        return true;
    }
}
