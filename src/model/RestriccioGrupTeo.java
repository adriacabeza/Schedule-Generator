package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioGrupTeo extends Restriccions {

    public RestriccioGrupTeo (){super (4);}

    @Override
    public boolean isable() throws NotFoundException {
        return false;
    }

    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2){ //nomes hauriem d'executar aixo si assig es de teoria
        int grup = assig.getGrup().getNum()/10;
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if(a.getAssignatura().getNom() == assig.getAssig().getNom() && a.getGrup().getNum()/10 == grup) return false;                       //solapament teories o teoria-qualsevol laboratori
        }
        return true;
    }

}
