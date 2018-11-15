package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioCorrequisit extends Restriccions {

    /**
     * Crea una restricció on es comprova que una sessió d'una assignatura d'un determinat grup no hi hagi conflictes amb correquisits
     */
    public RestriccioCorrequisit() {
        super(1);
    }

    /**
     * Retorna si es possible realitzar una assignació d'una assignatura d'un determinat grup comprovant que no hi hagi conflictes amb els correquisits
     *
     * @param horari horari que es comprova
     * @param hora   hora que es comprova
     * @param dia    dia que es comprova
     * @param assig  assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     * @throws NotFoundException
     */
    public boolean isable(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) throws NotFoundException {
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if (a != null) {
                if (a.getAssignatura().getCorrequisits().contains(assig)) {
                    if (a.getClass() == AssignacioL.class && assig.getSessio().getClass() == Laboratori.class) {
                        if (a.getGrup().getNum() == assig.getSub().getNum() || a.getGrup().getNum() / 10 == assig.getSub().getNum() / 10) // mateix subgrup o grup de teoria
                            return false;
                    } else { //un dels dos es teoria
                        int auxnum = assig.getGrup().getNum() / 10;
                        if (assig.getSessio().getClass() == Laboratori.class) auxnum = assig.getSub().getNum() / 10;
                        if (a.getGrup().getNum() / 10 == auxnum) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retorna si es possible realitzar una assignació d'una assignatura d'un determinat grup comprovant que no hi hagi conflictes amb els correquisits d'una altra assignació
     * @param check assignació a comprovar
     * @param assignat assignació acabada d'inserir al horari
     * @param pos possibles aules que pot tenir l'assignació a comprovar
     * @param aula aula que es comprova
     * @param hora hora que es comprova
     * @param dia  dia que es comprova
     * @return true si l'assignació seria compatible amb l'assignació acabada d'inserir a l'horari segons els correquisits
     * @throws NotFoundException
     */

    public boolean isable2(SessioGrup check, SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora) throws NotFoundException {
        if (assignat.getAssig().getCorrequisits().contains(check.getAssig().getNom()) &&  assignat.getGrup() == check.getGrup()) {
            if (pos.get(check).get(dia).get(hora).contains(aula)){
                return false;
            }
        }
        return true;
    }


}
