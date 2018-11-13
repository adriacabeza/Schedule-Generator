package model;

import java.util.ArrayList;

public class RestriccioNivell extends Restriccions {

    /**
     * Crea una restricció on es comprova que en no hi hagi solapaments de sessions del mateix nivell
     */
    public RestriccioNivell() {
        super(2);
    }

    /**
     * Retorna si es possible realitzar una assignació comprovant que no hi hagi solapaments per nivell
     *
     * @param horari horari que es comprova
     * @param hora   hora que es comprova
     * @param dia    dia que es comprova
     * @param assig  assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     */
    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2) {
        for (int i = 0; i < aules2.size(); ++i) {
            Assignacio a = horari[hora][dia][i];
            if (horari[hora][dia][i] != null) {
                if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getAssig().getQuadrimestre()) {
                    if (a.getClass() == AssignacioL.class && assig.getSessio().getClass() == Laboratori.class) {
                        if (a.getGrup().getNum() == assig.getSub().getNum() || a.getGrup().getNum() / 10 == assig.getSub().getNum() / 10) // mateix subgrup o grup de teoria
                            return false;
                    } else { //un dels dos es teoria
                        int auxnum = assig.getGrup().getNum() / 10;
                        ;
                        if (assig.getSessio().getClass() == Laboratori.class) auxnum = assig.getSub().getNum() / 10;
                        if (a.getGrup().getNum() / 10 == auxnum) return false;
                    }
                }
            }
        }
        return true;
    }


}
