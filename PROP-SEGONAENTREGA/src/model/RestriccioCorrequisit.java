/**
 * @Author Adria Cabeza
 */
package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioCorrequisit extends RestriccioBinaria {
    boolean activat;
    /**
     * Crea una restricció on es comprova que una sessió d'una assignatura d'un determinat grup no hi hagi conflictes amb correquisits
     */
    public RestriccioCorrequisit(boolean activat) {
        super(1);
        this.activat = activat;
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
        if(activat) {
            for (int j = 0; j < aules2.size(); ++j) {
                Assignacio a = horari[hora][dia][j];
                if (a != null) {
                    if (a.getAssignatura().getCorrequisits().contains(assig.getAssig().getNom())) {
                        if (a.getGrup().getNum() == assig.getGrup().getNum())
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retorna si es possible realitzar una assignació d'una assignatura d'un determinat grup comprovant que no hi hagi conflictes amb els correquisits d'una altra assignació
     *
     * @param check    assignació a comprovar
     * @param assignat assignació acabada d'inserir al horari
     * @param pos      possibles aules que pot tenir l'assignació a comprovar
     * @param aulaIndex index de l'aula que es comprova
     * @param hora     hora que es comprova
     * @param dia      dia que es comprova
     * @return true si l'assignació seria compatible amb l'assignació acabada d'inserir a l'horari segons els correquisits
     * @throws NotFoundException
     */


    @Override
    public boolean isAble2(SessioGrup check, SessioGrup assignat, Aula aula, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos, int aulaIndex, int dia, int hora) throws NotFoundException {
        if (activat) {
            if (assignat.getAssig().getCorrequisits().contains(check.getAssig().getNom()) && assignat.getGrup().getNum() == check.getGrup().getNum()) {
                if (pos.get(check).get(dia).get(hora).contains(aulaIndex)) {
                    return false;
                }
            }
            return true;
        }
        else return true;
    }
}
