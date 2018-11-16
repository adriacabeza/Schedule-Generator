/**
 * @Author Adria Cabeza
 */

package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Backtracking {

    private Assignacio[][][] horari;
    private ArrayList<SessioGrup> sessio;
    private ArrayList<Assignatura> assignatures;
    private ArrayList<Aula> aules;
    private RestriccioCorrequisit resCorr;
    private RestriccioNivell resNiv;
    private RestriccioAula resAul;
    private RestriccioGrupTeo resTeo;
    private RestriccioSubgrupLab resSub;
    private ArrayList<RestriccioAulaDia> resAulDia;
    private ArrayList<RestriccioAulaHora> resAulaHora;
    private ArrayList<RestriccioAssigMatiTarda> resMatiTarda;


    /**
     * Construeix un horari un buit amb totes les dades que es necessitarien per a generar-lo
     *  @param assignatures assignatures que tindrà que tenir l'horari
     * @param aules        aules que tindrà que tenir l'horari
     * @param resCorr      restricció de correquísit
     * @param resNiv       restricció de nivell
     * @param resAul       restricció d'aules
     * @param resTeo       restricció de les sessions de teoria
     * @param resSub       restricció de les sessions de laboratori
     * @param resAulDia    restricció de dia i aula
     * @param resAulaHora  restricció d'aula i hora
     * @param resMatiTarda restriccio de assignatures de matins i tardes
     */
    public Backtracking(HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                        RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora, ArrayList<RestriccioAssigMatiTarda> resMatiTarda) {
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
    }

    /**
     * Obté l'horari
     * @return l'horari
     */
    public Assignacio[][][] getHorari() {
        return horari;
    }

    /**
     * Converteix un enter que representa un número en el seu pertinent dia en string
     *
     * @param dia enter que representa el dia
     * @return string que representa el dia
     */
    private String fromInt2dia(int dia) {
        if (dia == 0) return "Dilluns";
        else if (dia == 1) return "Dimarts";
        else if (dia == 2) return "Dimecres";
        else if (dia == 3) return "Dijous";
        else return "Divendres";

    }

    /**
     * Printa per consola tot l'horari
     */

    public void printarHoraritot() {
        int count = 0;
        for (int h = 0; h < 12; ++h) {
            for (int d = 0; d < 5; ++d) {
                for (int a = 0; a < aules.size(); ++a)
                    if (horari[h][d][a] == null) System.out.println("ESPAI BUIT");
                    else {
                        ++count;
                        System.out.println(fromInt2dia(d)+", a les "+getHora(h)+" a l'aula  " + horari[h][d][a].getAula().getKey() +" el grup " + horari[h][d][a].getGrup().getNum()+ " fa " + horari[h][d][a].getAssignatura());
                    }
            }
        }
        System.out.println("S'han assignat " + count + (" sessions."));
    }


    /**
     * Converteix un string que representa un dia a enter
     *
     * @param dia string que representa un dia
     * @return enter que representa el dia
     */
    private int fromDia2int(String dia) {
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
    private int getHora(int hora) {
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
     * Comprova que a l'hora de fer una assignació no es passi dels límits de l'horari
     *
     * @param posaula enter que representa l'aula de l'assignació
     * @param dia     enter que representa el dia de l'assignació
     * @param hora    enter que representa l'hora de l'assignació
     * @param assig   enter que representa l'assignatura de l'assignació
     * @param duracio duració de la sessió que es vol assignar
     * @return
     */
    private boolean checkBoundaries(int posaula, int dia, int hora, SessioGrup assig, int duracio) {
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12) {
                return false;
            } else if (horari[hora + i][dia][posaula] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * S'encarrega de comprovar totes les restriccions per a fer assignacions correctes a l'horari
     *
     * @param aula   aula de l'assignació
     * @param dia     enter que representa el dia de l'assignació
     * @param hora    enter que representa l'hora de l'assignació
     * @param assig   assignatura de l'assignació
     * @param duracio duració de la sessió que es vol assignar
     * @param posaula enter que representa l'aula de l'assignació
     * @return true si es pot realitzar l'assignació
     */
    private boolean comprovarRestriccions(Aula aula, int dia, int hora, SessioGrup assig, int duracio, int posaula) {
        if (!checkBoundaries(posaula, dia, hora, assig, duracio))
            return false; //ens passem o de hores de dia o hi ha una altre classe mes endavant
        if (!resNiv.isable(horari, hora, dia, assig, aules)) return false; //violem la restriccio de nivell
        try {
            if (!resCorr.isable(horari, hora, dia, assig, aules)) return false; //violem restriccio de correquisit
        } catch (NotFoundException e) {
        }
        if(aula.getCapacitat() < assig.getGrup().getCapacitat()) return false;

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
            sessio = creaSessions(assignatures);
            ordena_mishamash();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        boolean b = creaHorari(0, horari);
        if (!b) System.out.println("No es pot realitzar l'horari");
        return b;
    }

    /**
     * Ordena una estructura de dades per a realitzar assignacions més distribuides
     */
    private void ordena_mishamash() {
        Collections.sort(sessio);
    }

    /**
     * Crea una estructura de dades amb un grup o subgrup, una sessió i una assignatura
     *
     * @param assignatures conjunt de totes les assignatures que s'han d'assignar
     * @return l'estructura de dades creada
     * @throws NotFoundException
     */

    private ArrayList<SessioGrup> creaSessions(ArrayList<Assignatura> assignatures) throws NotFoundException {
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
     * Crea l'horari
     *
     * @param i  iterador per a tots els grups que s'han d'assignar
     * @param horari horari que s'ha d'emplenar
     * @return true si s'ha pogut realitzar l'horari
     */
    private boolean creaHorari(int i, Assignacio[][][] horari) {

        if (i == (sessio.size())) return true;
        int duracio = sessio.get(i).getSessio().getDuracioSessions();
        boolean teoria = (sessio.get(i).getSessio().getClass() == Teoria.class);
        for (int d = 0; d < 5; ++d) {
            for (int h = 0; h < 12; ++h) {
                for (int a = 0; a < aules.size(); ++a) {
                    if (horari[h][d][a] == null) {
                        if (teoria) {
                           if (comprovarRestriccions(aules.get(a), d, h, sessio.get(i), duracio, a) && aules.get(a).getTipusAula() == sessio.get(i).getSessio().gettAula()) {
                                for (int z = 0; z < duracio; ++z) {
                                   horari[h + z][d][a] = new AssignacioT(fromInt2dia(d), h + z, aules.get(a), sessio.get(i).getAssig(), sessio.get(i).getGrup());
                                }
                               if (creaHorari(i + 1, horari)) return true;
                               else {
                                   for (int z = 0; z < duracio; ++z) {
                                       horari[h + z][d][a] = null;
                                   }
                               }

                            }
                        } else {
                           if (comprovarRestriccions(aules.get(a), d, h, sessio.get(i), duracio, a)) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[h + z][d][a] = new AssignacioL(fromInt2dia(d), h + z, aules.get(a), sessio.get(i).getAssig(), sessio.get(i).getSub());
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
