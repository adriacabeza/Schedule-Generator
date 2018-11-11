package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class RestriccioCorrequisit extends Restriccions {

    /**
     * Crea una restricció on es comprova que una sessió d'una assignatura d'un determinat grup no hi hagi conflictes amb correquisits
     */
    public RestriccioCorrequisit() {
        super(1);
    }

    /**
     * Retorna si es possible realitzar una assignació d'una assignatura d'un determinat grup comprovant que no hi hagi conflictes amb els correquisits
     * @param horari horari que es comprova
     * @param hora hora que es comprova
     * @param dia dia que es comprova
     * @param assig assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     * @throws NotFoundException
     */
    public boolean isable(Assignacio[][][] horari, int hora, int dia, AssignaturaMonosessio assig, ArrayList<Aula> aules2) throws NotFoundException {
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if(a != null) {
                if (a.getAssignatura().getCorrequisits().contains(assig)) {
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
        } return true;
    }

}
