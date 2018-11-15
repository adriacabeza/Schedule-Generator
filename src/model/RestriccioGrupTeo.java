package model;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioGrupTeo extends Restriccions {

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
    public boolean isAble(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) { //nomes hauriem d'executar aixo si assig es de teoria
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


    public boolean isAble2(SessioGrup check, SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora){
        if (pos.get(check).get(dia).get(hora).contains(aula)) {
            if (check.getAssig().getNom() == assignat.getAssig().getNom()) {
                int grup = assignat.getGrup().getNum() / 10;
                if (check.getGrup().getNum() / 10 == grup) return false;      //solapament teories
                if (check.getSub() != null)
                    if (check.getSub().getNum() / 10 == grup) return false;   //solapament de lab amb teoria
            }
        }

        return true;
    }

}
//TODO: fer makefile
//TODO: arreglar el backtracking gros
//TODO: mirar tonteries
//TODO: fer refactor