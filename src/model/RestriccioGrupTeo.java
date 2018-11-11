package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioGrupTeo extends Restriccions {

    /**
     * Crea una restricció on es comprova que en una sessió de teoria d'un determinat grup no hi hagi solapaments
     */
    public RestriccioGrupTeo (){super (4);}


    /**
     * Retorna si es possible realitzar una assignació de teoria d'un determinat grup comprovant que no hi hagi solapaments
     * @param horari horari que es comprova
     * @param hora hora que es comprova
     * @param dia dia que es comprova
     * @param assig assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     */
    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2){ //nomes hauriem d'executar aixo si assig es de teoria
        int grup = assig.getGrup().getNum()/10;
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if(a != null) {
                if (a.getAssignatura().getNom() == assig.getAssig().getNom() && a.getGrup().getNum() / 10 == grup)
                    return false;                       //solapament teories o teoria-qualsevol laboratori
            }
        }
        return true;
    }

}
