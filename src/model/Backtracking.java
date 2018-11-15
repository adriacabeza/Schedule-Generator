package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Backtracking {

    private Assignacio[][][] horari;
    private ArrayList<AssignaturaMonosessio> mishmash;
    private ArrayList<Assignatura> assignatures2;
    private ArrayList<Aula> aules2;
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
        this.assignatures2 = new ArrayList<>(assignatures.values());
        this.aules2 = new ArrayList<>(aules.values());
        this.horari = new Assignacio[12][5][aules2.size()];
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
     * Printa per consola l'horari assignat d'una determinada aula
     *
     * @param aula
     */
    private void printarHorari_aula(Aula aula) {
        int numAula = assignatures2.indexOf(aula);
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 5; ++j) {
                System.out.println(horari[i][j][numAula].getAula().getKey()); //S HAURIA DE PRINTAR AIXO
            }
        }
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
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < aules2.size(); ++k)
                    if (horari[i][j][k] == null) System.out.println("ESPAI BUIT");
                    else {
                        ++count;
                        System.out.println(fromInt2dia(j)+", a les "+gethora(i)+" a l'aula " + horari[i][j][k].getAula().getKey() +" el grup " + horari[i][j][k].getGrup().getNum()+ " fa " + horari[i][j][k].getAssignatura());
                    }
            }
        }
        System.out.println("S'han assignat " + count + (" sessions."));
    }

    /**
     * Printa per consola l'horari assignat d'una determinada aula en un determinat dia
     *
     * @param aula aula escollida a printar
     * @param dia  dia escollit a printar
     */
    private void printarHorari_auladia(Aula aula, String dia) {
        int numAula = assignatures2.indexOf(aula);
        int numdia = fromdia2int(dia);
        for (int i = 0; i < 12; ++i) {
            System.out.println("Aula: " + horari[i][numdia][numAula].getAula().getKey() + " es fa assignatura: " + horari[i][numdia][numAula].getAssignatura());
        }

    }

    /**
     * Printa per consola l'horari assingat d'una determinada aula en una determinada hora
     *
     * @param aula aula escollida a printar
     * @param hora hora escollida a printar
     */
    private void printarHorari_aulahora(Aula aula, int hora) {
        int nhora = gethora(hora);
        int numAula = assignatures2.indexOf(aula);
        for (int i = 0; i < 5; ++i) {
            System.out.println("Aula: " + horari[nhora][i][numAula].getAula().getKey() + " es fa assignatura: " + horari[nhora][i][numAula].getAssignatura());
        }

    }

    /**
     * Printa per consola l'horari assignat una determinada hora
     *
     * @param hora hora escollida a printar
     */
    public void printarHorari_hora(int hora) {
        int nhora = gethora(hora);
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < aules2.size(); ++j) {
                System.out.println(horari[nhora][i][j]); // S HAURIA DE PRINTAR AIXO
                System.out.println("Aula: " + horari[nhora][i][j].getAula().getKey() + " es fa assignatura: " + horari[nhora][i][j].getAssignatura());
            }
        }
    }

    /**
     * Printa per consola l'horari d'un determinat dia
     *
     * @param dia dia escollit a printar
     */

    public void printarHorari_dia(String dia) {
        int numdia = fromdia2int(dia);
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < aules2.size(); ++j) {
                System.out.println("Aula: " + horari[i][numdia][j].getAula().getKey() + " es fa assignatura: " + horari[i][numdia][j].getAssignatura());
            }

        }

    }

    /**
     * Converteix un string que representa un dia a enter
     *
     * @param dia string que representa un dia
     * @return enter que representa el dia
     */
    private int fromdia2int(String dia) {
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
    private int gethora(int hora) {
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
    private boolean check_boundaries(int posaula, int dia, int hora, AssignaturaMonosessio assig, int duracio) {
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12) {
                //      System.out.println("Se pasa del horario");
                return false;
            } else if (horari[hora + i][dia][posaula] != null) {
                //       System.out.println("Con la assignatura " + assig.getAssig().getNom() + " fallo.");
                //      System.out.println("Ya está puesta la hora " + (hora + i) + ", el dia " + fromInt2dia(dia));
                return false;
            }
        }
        return true;
    }


    /**
     * S'encarrega de comprovar totes les restriccions per a fer assignacions correctes a l'horari
     *
     * @param aula1   aula de l'assignació
     * @param dia     enter que representa el dia de l'assignació
     * @param hora    enter que representa l'hora de l'assignació
     * @param assig   assignatura de l'assignació
     * @param duracio duració de la sessió que es vol assignar
     * @param posaula enter que representa l'aula de l'assignació
     * @return true si es pot realitzar l'assignació
     */
    private boolean comprovarRestriccions(Aula aula1, int dia, int hora, AssignaturaMonosessio assig, int duracio, int posaula) {
        if (!check_boundaries(posaula, dia, hora, assig, duracio))
            return false; //ens passem o de hores de dia o hi ha una altre classe mes endavant
        if (!resNiv.isable(horari, hora, dia, assig, aules2)) return false; //violem la restriccio de nivell
        try {
            if (!resCorr.isable(horari, hora, dia, assig, aules2)) return false; //violem restriccio de correquisit
        } catch (NotFoundException e) {
        }
        if(aula1.getCapacitat() < assig.getGrup().getCapacitat()) return false;
        if (!resAul.isable(aula1, assig)) return false; //violem restriccio de aula
        if (!resTeo.isable(horari, hora, dia, assig, aules2)) return false; //violem restriccio de clases de teoria
        if (!resSub.isable(horari, hora, dia, assig, aules2)) return false;
        for (RestriccioAulaDia ad : resAulDia)
            if (!ad.isable(aula1, dia)) return false; //en aquesta aula no pot haber clase avui
        for (RestriccioAulaHora ah : resAulaHora)
            if (!ah.isable(aula1, dia, hora))
                return false; //en aquesta aula no pot haver clase a aquesta hora
        return true;
    }


    /**
     * Prepara la estructura de dades necessaria per a crear l'horari i crida a la generació de l'horari
     *
     * @return true si s'ha pogut fer l'horari
     */
    public boolean generaHorari() {
        try {
            mishmash = mishmash(assignatures2);
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
        Collections.sort(mishmash);
    }

    /**
     * Crea una estructura de dades amb un grup o subgrup, una sessió i una assignatura
     *
     * @param assignatures2 conjunt de totes les assignatures que s'han d'assignar
     * @return l'estructura de dades creada
     * @throws NotFoundException
     */

    private ArrayList<AssignaturaMonosessio> mishmash(ArrayList<Assignatura> assignatures2) throws NotFoundException {
        ArrayList<AssignaturaMonosessio> res = new ArrayList<>();
        Teoria auxteo;
        Laboratori auxlab = new Laboratori(0, 0, null);
        int sesteo, seslab, valor;
        Map<Integer, Grup> grups;
        Grup g;
        HashMap<Integer, Subgrup> subgrups;
        seslab = 0;             //si no comp se queja
        boolean lab;
        for (Assignatura a : assignatures2) {
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
            valor = 8;                      //TODO: heuristica a assignar
            for (int i = 0; lab && i < seslab; ++i) {
                for (int key : grups.keySet()) {
                    g = grups.get(key);
                    subgrups = g.getSubgrups();
                    for (int subg : subgrups.keySet()) {
                        res.add(new AssignaturaMonosessio(a, auxlab, g, subgrups.get(subg), valor));
                    }
                }
                valor /= 2;
            }
            valor = 8;
            for (int i = 0; i < sesteo; ++i) {
                for (int key : grups.keySet()) {
                    res.add(new AssignaturaMonosessio(a, auxteo, grups.get(key), null, valor));
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

        if (i == (mishmash.size())) return true;
        int duracio = mishmash.get(i).getSessio().getDuracioSessions();
        boolean teoria = (mishmash.get(i).getSessio().getClass() == Teoria.class);
        for (int l = 0; l < 5; ++l) {
            for (int m = 0; m < 12; ++m) {
                for (int k = 0; k < aules2.size(); ++k) {
                    if (horari[m][l][k] == null) {
                        if (teoria) {
                           if (comprovarRestriccions(aules2.get(k), l, m, mishmash.get(i), duracio, k)) {
                            //   System.out.println(mishmash.get(i).getSessio().gettAula());
                             //  System.out.println(aules2.get(k).getTipusAula());

                                for (int z = 0; z < duracio; ++z) {
                                   horari[m + z][l][k] = new AssignacioT(fromInt2dia(l), m + z, aules2.get(k), mishmash.get(i).getAssig(), mishmash.get(i).getGrup());
                                   // System.out.println(mishmash.get(i).getAssig().getNom() + " ficada a les " + gethora(m + z) + " el " + fromInt2dia(l));
                               }
                               if (creaHorari(i + 1, horari)) return true;
                               else {
                                   //no se ha podido hacer, borramos lo que hemos puesto
                                   for (int z = 0; z < duracio; ++z) {
                                       horari[m + z][l][k] = null;
                                   }
                               }

                            }
                        } else {
                           if (comprovarRestriccions(aules2.get(k), l, m, mishmash.get(i), duracio, k)) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[m + z][l][k] = new AssignacioL(fromInt2dia(l), m + z, aules2.get(k), mishmash.get(i).getAssig(), mishmash.get(i).getSub());
                                    // System.out.println(mishmash.get(i).getAssig().getNom() + " ficada a les " + gethora(m + z) + " el " + fromInt2dia(l));
                                }
                                if (creaHorari(i + 1, horari)) return true;
                                else {
                                    //no se ha podido hacer, borramos lo que hemos puesto
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[m + z][l][k] = null;
                                    }
                                }
                           }

                        }
                    }
                }
            }
        }

        //System.out.println("no se ha podido hacer el horario de esta manera, let's backtrack");
        return false;

    }
}
