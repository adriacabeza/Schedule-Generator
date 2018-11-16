package stubs;

import model.Aula;
import model.Restriccions;

public class RestriccioAula extends Restriccions {

    public RestriccioAula() {
        super(3);
    }

    public boolean isAble(Aula aula, SessioGrup assig) {
        return true;
    }


}


