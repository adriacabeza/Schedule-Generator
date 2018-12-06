/**
 * @Author Adria Cabeza
 */

package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Backtracking extends Algorismes{


    public Backtracking(HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                        RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora, ArrayList<RestriccioAssigMatiTarda> resMatiTarda, RestriccioCapacitatAula resCapAul, RestriccioLimits resLim ) {
        super(assignatures,aules,resCorr,resNiv,resAul,resTeo,resSub,resAulDia,resAulaHora,resMatiTarda,resCapAul,resLim);
    }





    /**
     * S'encarrega de comprovar totes les restriccions per a fer assignacions correctes a l'horari
     *
     * @param aula    aula de l'assignació
     * @param dia     enter que representa el dia de l'assignació
     * @param hora    enter que representa l'hora de l'assignació
     * @param assig   assignatura de l'assignació
     * @param duracio duració de la sessió que es vol assignar
     * @param posaula enter que representa l'aula de l'assignació
     * @return true si es pot realitzar l'assignació
     */
    private boolean comprovarRestriccions(Aula aula, int dia, int hora, SessioGrup assig, int duracio, int posaula) {
        if (!resLim.isAble(posaula, dia, hora, assig, duracio, horari))
            return false; //ens passem o de hores de dia o hi ha una altre classe mes endavant
        if (!resNiv.isable(horari, hora, dia, assig, aules)) return false; //violem la restriccio de nivell
        try {
            if (!resCorr.isable(horari, hora, dia, assig, aules)) return false; //violem restriccio de correquisit
        } catch (NotFoundException e) {
        }
        if (!resCapAul.isAble(aula, assig)) return false;

        //  if (!resAul.isAble(aula1, assig)) return false; //violem restriccio de aula
        if (!resTeo.isable(horari, hora, dia, assig, aules)) return false; //violem restriccio de clases de teoria
        if (!resSub.isable(horari, hora, dia, assig, aules)) return false;
        for (RestriccioAulaDia ad : resAulDia)
            if (!ad.isAble(aula, dia)) return false; //en aquesta aula no pot haber clase avui
        for (RestriccioAulaHora ah : resAulaHora)
            if (!ah.isAble(aula, dia, hora))
                return false; //en aquesta aula no pot haver clase a aquesta hora*/
        return true;
    }


    /**
     * Prepara la estructura de dades necessaria per a crear l'horari i crida a la generació de l'horari
     *
     * @return true si s'ha pogut fer l'horari
     */
    public boolean generaHorari() {
        try {
            sessions = creaSessions(assignatures);
            ordena_mishamash();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        boolean b = creaHorari(0, horari);
        if (!b) System.out.println("No es pot realitzar l'horari");
        return b;
    }




    /**
     * Crea l'horari
     *
     * @param i      iterador per a tots els grups que s'han d'assignar
     * @param horari horari que s'ha d'emplenar
     * @return true si s'ha pogut realitzar l'horari
     */
    private boolean creaHorari(int i, Assignacio[][][] horari) {

        if (i == (sessions.size())) return true;
        int duracio = sessions.get(i).getSessio().getDuracioSessions();
        boolean teoria = (sessions.get(i).getSessio().getClass() == Teoria.class);
        for (int d = 0; d < 5; ++d) {
            for (int h = 0; h < 12; ++h) {
                for (int a = 0; a < aules.size(); ++a) {
                    if (horari[h][d][a] == null) {
                        if (teoria) {
                            if (comprovarRestriccions(aules.get(a), d, h, sessions.get(i), duracio, a) && aules.get(a).getTipusAula() == sessions.get(i).getSessio().gettAula()) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[h + z][d][a] = new AssignacioT(fromInt2dia(d), h + z, aules.get(a), sessions.get(i).getAssig(), sessions.get(i).getGrup());
                                }
                                if (creaHorari(i + 1, horari)) return true;
                                else {
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[h + z][d][a] = null;
                                    }
                                }

                            }
                        } else {
                            if (comprovarRestriccions(aules.get(a), d, h, sessions.get(i), duracio, a)) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[h + z][d][a] = new AssignacioL(fromInt2dia(d), h + z, aules.get(a), sessions.get(i).getAssig(), sessions.get(i).getSub());
                                }
                                if (creaHorari(i + 1, horari)) return true;
                                else {
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[h + z][d][a] = null;
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        return false;
    }
}
