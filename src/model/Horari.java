package model;
import java.util.*;

public class Horari {

    private Assignacio[][][] horari;

    /**
     * Obtenir l'horari
     *
     * @return horari
     */
    public Assignacio[][][] getHorari() {
        return horari;
    }


    /**
     * Crea un horari mitjançant backtracking cronològic
     */
    public Horari(boolean b, HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                  RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora) {
        if(b) {
            Backtracking backtracking = new Backtracking(assignatures, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora);
            backtracking.generaHorari();
            horari = backtracking.getHorari();
        }
        else {
            Backtracking2 backtracking2 = new Backtracking2(assignatures, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora);
            backtracking2.generaHorari();
            horari = backtracking2.getHorari();
        }
    }
    //PRUEBAS
    //1 A 1 3 1 NORMAL 8 1 1 2 2   LP 2 2 2 LABORATORI 9 1 1 1 2  G 3 3 1 LABORATORI 5 1 1 1 2  TC 2 2 1 LABORATORI 4 1 1 1 1   2 POLLA 1998 1     3 A 4 2 NORMAL 50 2  A 3 2 LABORATORI 50 1 1 LI 1 2 3 NORMAL 2 30 3 0 3 1 PCS 1 3 C 1 2 PCS 50 1
    //1 LP 2 2 2 LABORATORI 9 1 1 1 1   2 POLLA 1998 1     3 A 4 2 NORMAL 50 2  A 3 2 LABORATORI 0 1
}
