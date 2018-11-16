package stubs;

import model.Aula;

public class RestriccioAula extends Restriccions {

    public RestriccioAula() {
        super(3);
    }

    public boolean isAble(Aula aula, SessioGrup assig) {
        return (aula.getTipusAula() == assig.getSessio().gettAula());
    }


}


