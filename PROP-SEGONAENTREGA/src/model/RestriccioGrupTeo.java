/**
 * @Author Adria Cabeza
 */
package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioGrupTeo extends RestriccioBinaria {

    /**
     * Crea una restricció on es comprova que en una sessió de teoria d'un determinat grup no hi hagi solapaments
     */
    public RestriccioGrupTeo() {
        super(4);
    }


    /**
     * Retorna si es possible realitzar una assignació de teoria d'un determinat grup comprovant que no hi hagi solapaments
     *
     * @param horari horari que es comprova
     * @param hora   hora que es comprova
     * @param dia    dia que es comprova
     * @param assig  assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     */
    public boolean isable(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) {
        int grup = assig.getGrup().getNum() / 10;
        for (int j = 0; j < aules2.size(); ++j) {
            Assignacio a = horari[hora][dia][j];
            if (a != null) {
                if (a.getAssignatura().getNom() == assig.getAssig().getNom() && a.getGrup().getNum() / 10 == grup)
                    return false;                       //solapament teories o teoria-qualsevol laboratori
            }
        }
        return true;
    }

    /**
     * Retorna si es possible realitzar una assignació de teoria d'un determinat grup comprovant que no hi hagi solapaments
     *
     * @param check    sessio de la que mirem si pot haber solapaments
     * @param assignat sessio que acabem d'assignar
     * @param pos      possibles aules que pot tenir l'assignació a comprovar
     * @param aulaIndex index de l'aula que es comprova
     * @param hora     hora que es comprova
     * @param dia      dia que es comprova
     */

    @Override
    public boolean isAble2(SessioGrup check, SessioGrup assignat, Aula aula, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos, int aulaIndex, int dia, int hora) throws NotFoundException {
        if (pos.get(check).get(dia).get(hora).contains(aulaIndex)) {
            if (check.getAssig().getNom().equalsIgnoreCase(assignat.getAssig().getNom()) && check.getSessio().getClass() == Teoria.class) {
                if (check.getGrup().getNum() == assignat.getGrup().getNum())
                    return false;      //solapament teories o labs
            }
        }

        return true;
    }
}
