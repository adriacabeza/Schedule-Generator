package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioSubgrupLab extends Restriccions{

    /**
     * Crea una restricció on es comprova que en una sessió de laboratori d'un determinat subgrup no hi hagi solapaments
     */
    public RestriccioSubgrupLab(){super(5);}

    /**
     * Retorna si es possible realitzar una assignació de laboratori d'un determinat subgrup comprovant que no hi hagi solapaments
     * @param horari horari que es comprova
     * @param hora hora que es comprova
     * @param dia dia que es comprova
     * @param assig assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     */
    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2){
        int grup = assig.getSub().getNum()/10;
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if(a.getAssignatura().getNom() == assig.getAssig().getNom()) {
                if (a.getGrup().getNum() == assig.getSub().getNum())
                    return false;                           //solapament de laboratoris
                if (a.getGrup().getNum() / 10 == grup)
                    return false;                           //solapament teoria-laboratori
            }
        }
        return true;
    }
}
