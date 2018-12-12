/**
 * @Author Adria Cabeza
 */

package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioSubgrupLab extends RestriccioBinaria {

    /**
     * Crea una restricció on es comprova que en una sessió de laboratori d'un determinat subgrup no hi hagi solapaments
     */
    public RestriccioSubgrupLab() {
        super(5);
    }

    /**
     * Retorna si es possible realitzar una assignació de laboratori d'un determinat subgrup comprovant que no hi hagi solapaments per al bactracking cronològic
     *
     * @param horari horari que es comprova
     * @param hora   hora que es comprova
     * @param dia    dia que es comprova
     * @param assig  assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     */
    public boolean isable(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) {
        if (assig.getSub() != null) {
            int grup = assig.getSub().getNum() / 10;
            for (int j = 0; j < aules2.size(); ++j) {
                Assignacio a = horari[hora][dia][j];
                if (a != null) {
                    if (a.getAssignatura().getNom() == assig.getAssig().getNom()) {
                        if (a.getGrup().getNum() == assig.getSub().getNum())
                            return false;                           //solapament de laboratoris
                        if (a.getGrup().getNum() / 10 == grup)
                            return false;                           //solapament teoria-laboratori
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retorna si es possible realitzar una assignació de laboratori d'un determinat grup comprovant que no hi hagi solapaments
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
            if (check.getAssig().getNom().equalsIgnoreCase(assignat.getAssig().getNom()) && check.getSessio().getClass() == Laboratori.class) {
                if (assignat.getSessio().getClass() == Laboratori.class) {
                    return (assignat.getSub().getNum() != check.getSub().getNum());     //solapament laboratori¡
                } else {
                    return (assignat.getGrup().getNum() /10 != check.getGrup().getNum()/10);   //solapament laboratori-teoria
                }
            }
        }
        return true;
    }
}
