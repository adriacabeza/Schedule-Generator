package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Algorismes {

    protected Assignacio[][][] horari;
    protected ArrayList<SessioGrup> sessions;
    protected ArrayList<Assignatura> assignatures;
    protected ArrayList<Aula> aules;
    protected RestriccioCorrequisit resCorr;
    protected RestriccioNivell resNiv;
    protected RestriccioAula resAul;
    protected RestriccioGrupTeo resTeo;
    protected RestriccioSubgrupLab resSub;
    protected ArrayList<RestriccioAulaDia> resAulDia;
    protected ArrayList<RestriccioAulaHora> resAulaHora;
    protected ArrayList<RestriccioAssigMatiTarda> resMatiTarda;
    protected RestriccioCapacitatAula resCapAul;
    protected RestriccioLimits resLim;
    /**
     * Construeix un horari un buit amb totes les dades que es necessitarien per a generar-lo
     *
     * @param assignatures assignatures que tindrà que tenir l'horari
     * @param aules        aules que tindrà que tenir l'horari
     * @param resCorr      restricció de correquísit
     * @param resNiv       restricció de nivell
     * @param resAul       restricció d'aules
     * @param resTeo       restricció de les sessions de teoria
     * @param resSub       restricció de les sessions de laboratori
     * @param resAulDia    restricció de dia i aula
     * @param resAulaHora  restricció d'aula i hora
     * @param resMatiTarda restriccio de assignatures de matins i tardes
     * @param resCapAul    restriccio de limit de persones en una clase
     * @param resLim       restriccio de si una assignacio colisiona amb una altre o es pasa de les hores del dia
     */
    public Algorismes(HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                      RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora, ArrayList<RestriccioAssigMatiTarda> resMatiTarda, RestriccioCapacitatAula resCapAul, RestriccioLimits resLim ) {
        this.assignatures = new ArrayList<>(assignatures.values());
        this.aules = new ArrayList<>(aules.values());
        this.horari = new Assignacio[12][5][this.aules.size()];
        this.resCorr = resCorr;
        this.resNiv = resNiv;
        this.resAul = resAul;
        this.resTeo = resTeo;
        this.resSub = resSub;
        this.resAulDia = resAulDia;
        this.resAulaHora = resAulaHora;
        this.resMatiTarda = resMatiTarda;
        this.resCapAul = resCapAul;
        this.resLim = resLim;
    }

    /**
     * Ordena una estructura de dades per a realitzar assignacions més distribuides
     */
    protected void ordena_mishamash() {
        Collections.sort(sessions);
    }

    /**
     * Crea una estructura de dades amb un grup o subgrup, una sessió i una assignatura
     *
     * @param assignatures conjunt de totes les assignatures que s'han d'assignar
     * @return l'estructura de dades creada
     * @throws NotFoundException
     */

    protected ArrayList<SessioGrup> creaSessions(ArrayList<Assignatura> assignatures) throws NotFoundException {
        ArrayList<SessioGrup> res = new ArrayList<>();
        Teoria auxteo;
        Laboratori auxlab = new Laboratori(0, 0, null);
        int sesteo, seslab, valor;
        Map<Integer, Grup> grups;
        Grup g;
        HashMap<Integer, Subgrup> subgrups;
        seslab = 0;
        boolean lab;
        for (Assignatura a : assignatures) {
            lab = false;
            try {
                auxlab = (Laboratori) a.getLaboratori();
                seslab = auxlab.getNumSessions();
                lab = true;
            } catch (NotFoundException e) {
            }
            auxteo = (Teoria) a.getTeoria();
            sesteo = auxteo.getNumSessions();
            grups = a.getGrups();
            valor = 8;
            for (int i = 0; lab && i < seslab; ++i) {
                for (int key : grups.keySet()) {
                    g = grups.get(key);
                    subgrups = g.getSubgrups();
                    for (int subg : subgrups.keySet()) {
                        res.add(new SessioGrup(a, auxlab, g, subgrups.get(subg), valor));
                    }
                }
                valor /= 2;
            }
            valor = 8;
            for (int i = 0; i < sesteo; ++i) {
                for (int key : grups.keySet()) {
                    res.add(new SessioGrup(a, auxteo, grups.get(key), null, valor));
                }

                valor /= 2;
            }
        }
        return res;
    }




    /**
     * Converteix un enter que representa un número en el seu pertinent dia en string
     *
     * @param dia enter que representa el dia
     * @return string que representa el dia
     */
    public static String fromInt2dia(int dia) {
        if (dia == 0) return "Dilluns";
        else if (dia == 1) return "Dimarts";
        else if (dia == 2) return "Dimecres";
        else if (dia == 3) return "Dijous";
        else return "Divendres";

    }




    /**
     * Obté l'horari
     *
     * @return l'horari
     */
    public Assignacio[][][] getHorari() {
        return horari;
    }

    /**
     * Converteix un string que representa un dia a enter
     *
     * @param dia string que representa un dia
     * @return enter que representa el dia
     */
    public static int fromDia2int(String dia) {
        switch (dia) {
            case "Dilluns":
                return 0;
            case "Dimarts":
                return 1;
            case "Dimecres":
                return 2;
            case "Dijous":
                return 3;
            default:
                return 4;
        }
    }

    /**
     * Converteix un número que representa una hora a l'hora que representa
     *
     * @param hora enter que representa una hora
     * @return hora que representa
     */
    public static int getHora(int hora) {
        if (hora == 0) return 8;
        else if (hora == 1) return 9;
        else if (hora == 2) return 10;
        else if (hora == 3) return 11;
        else if (hora == 4) return 12;
        else if (hora == 5) return 13;
        else if (hora == 6) return 14;
        else if (hora == 7) return 15;
        else if (hora == 8) return 16;
        else if (hora == 9) return 17;
        else if (hora == 10) return 18;
        else return 19;
    }


    /**
     * Genera l'horari
     * @param i és l'ièssim que indica de quina sessió estem parlant
     * @param horari és l'horari que portem assignat
     * @param possibilitats son el conjunt de possibilitats de les sessions que no hem assignat encara
     * @return retorna true si ha pogut fer l'horari i si no ha pogut false
     */

    public abstract boolean creaHorari(int i, Assignacio[][][] horari, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) throws NotFoundException;
}
