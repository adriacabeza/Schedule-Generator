package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioAula extends  Restriccions {

    public RestriccioAula() {
        super(3);

    }

    @Override
    public boolean isable() throws NotFoundException {
        return false;
    }


    public boolean isable( Aula aula, AssignaturaMonosessio assig) {
        return (aula.getCapacitat() >= assig.getGrup().getCapacitat() && aula.getTipusAula() == assig.getSessio().gettAula());
    }


}
