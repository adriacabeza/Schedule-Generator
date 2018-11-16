/**
 * @Author Adria Cabeza
 */
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
     * Construeix l'horari
     * @param b booleà que indica quin algorisme fer servir
     * @param assignatures conjunt d'assignatures del pla d'estudis
     * @param aules conjunt d'aules
     * @param resCorr conjunt de restriccions de correquisit
     * @param resNiv restricció de nivell
     * @param resAul restricció d'aula
     * @param resTeo restricció de sessió de teoria
     * @param resSub restricció de de sessió de laboratori
     * @param resAulDia conjunt de restriccions d'una aula en un dia
     * @param resAulaHora conjunt de restriccions d'una aula en una hora
     * @param resMatiTarda conjunt de restriccions d'una assignatura de matins o tardes
     */
    public Horari(boolean b, HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                  RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora, ArrayList<RestriccioAssigMatiTarda> resMatiTarda) {
        if (b) {
            Backtracking backtracking = new Backtracking(assignatures, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora, resMatiTarda);
            backtracking.generaHorari();
            backtracking.printarHoraritot();
            horari = backtracking.getHorari();
        } else {
            Backtracking2 backtracking2 = new Backtracking2(assignatures, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora, resMatiTarda);
            backtracking2.generaHorari();
            backtracking2.printarHoraritot();
            horari = backtracking2.getHorari();
        }
    }
}


