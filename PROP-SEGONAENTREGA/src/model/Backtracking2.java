package model;

import exceptions.NotFoundException;

import java.util.*;

public class Backtracking2 {

    private Assignacio[][][] horari;
    private ArrayList<SessioGrup> sessions;
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
     */
    public Backtracking2(HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
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
     *
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
                        System.out.println(fromInt2dia(d) + ", a les " + getHora(h) + " a l'aula  " + horari[h][d][a].getAula().getKey() + " el grup " + horari[h][d][a].getGrup().getNum() + " fa " + horari[h][d][a].getAssignatura());
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
     * @param aula    aula de l'assignació
     * @param dia     enter que representa el dia de l'assignació
     * @param hora    enter que representa l'hora de l'assignació
     * @param assig   assignatura de l'assignació
     * @param duracio duració de la sessió que es vol assignar
     * @param posaula enter que representa l'aula de l'assignació
     * @return true si es pot realitzar l'assignació
     */
    private boolean comprovarRestriccions(Aula aula, int dia, int hora, SessioGrup assig, int duracio, int posaula) {
        if (!checkBoundaries(posaula, dia, hora, assig, duracio))
            return false;
        if (aula.getCapacitat() < assig.getGrup().getCapacitat()) return false;
        return true;
    }

    /**
     * Aquesta funció s'encarrega de fer una poda inicial de les possibilitats de cada assignació que haurem de realitzar
     *
     * @param possibilitats les possibles aules en un determinat dia i hora que podrien ser assignades per cada sessió
     * @return true si ja hi ha un cas que no es pot realitzar false si totes tenen possibles solucions
     */

    public boolean filtraRestriccions(HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) {
        for (int i = 0; i < sessions.size(); ++i) {
            ArrayList<Integer> aules_possibles = new ArrayList<>();
            for (int k = 0; k < aules.size(); ++k) {
                if (resAul.isAble(aules.get(k), sessions.get(i)) && (aules.get(k).getCapacitat() >= sessions.get(i).getGrup().getCapacitat()))
                    aules_possibles.add(k);
            }
            Assignatura assig = null;
            int indexAssig = 0;
            boolean found = false;
            for (int k = 0; k < resMatiTarda.size() && !found; ++k) {
                if (resMatiTarda.get(k).getAssig() == sessions.get(i).getAssig().getNom()) {
                    assig = sessions.get(i).getAssig();         //tenim una assignatura o de mati o de tarda
                    indexAssig = k;
                    found = true;
                }
            }
            ArrayList<ArrayList<ArrayList<Integer>>> diahoraaules = new ArrayList<>(5);
            for (int d = 0; d < 5; ++d) {
                ArrayList<ArrayList<Integer>> horaaules = new ArrayList<>(12);
                for (int h = 0; h < 12; ++h) {
                    ArrayList<Integer> aulesHora = new ArrayList<>();
                    boolean restriccioMati = true;
                    if (found)
                        restriccioMati = resMatiTarda.get(indexAssig).isAble(assig, h);        //mirem si es de mati, estiguem en el mati i el mateix per la tarda
                    for (int n = 0; n < aules_possibles.size() && restriccioMati; ++n) {
                        boolean b = true;
                        for (int j = 0; j < resAulaHora.size() && b; ++j) {
                            if (!resAulaHora.get(j).isAble(aules.get(aules_possibles.get(n)), d, h)) b = false;
                        }
                        for (int j = 0; j < resAulDia.size() && b; ++j) {
                            if (!resAulDia.get(j).isAble(aules.get(aules_possibles.get(n)), d)) b = false;
                        }
                        if (b) aulesHora.add(aules_possibles.get(n));
                    }
                    horaaules.add(h, aulesHora);
                }
                diahoraaules.add(d, horaaules);

            }
            possibilitats.put(sessions.get(i), diahoraaules);

/*          Iterator<SessioGrup> it2 = possibilitats.keySet().iterator();
            while(it2.hasNext()){
                SessioGrup a = it2.next();
                boolean pos = false;
                for(int d = 0; d < 5; ++d){
                    for(int h = 0 ; h < 12; ++h){
                        if(!possibilitats.get(a).get(d).get(h).isEmpty()) pos = true;
                    }
                }
                if(!pos) return true;
            }*/
        }
        return false;
    }

    /**
     * Aquesta funció propaga les restriccions per a efectuar forward checking i fer la poda de domini
     *
     * @param aula aula que ha de tenir en compte per a podar
     * @param dia  dia que ha de de tenir en compte per a podar
     * @param hora hora que ha de tenir en compte per a podar
     */

    public boolean propagarPossibilitats(int aula, int dia, int hora, SessioGrup sessio, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibles) { //TODO: no haurien d'anar aqui ara les comprovacions de resTo, resSub, resNiv i resCorr ja que estem treient les possibilitats que violen alguna d'aquestes
        Iterator<SessioGrup> it = possibles.keySet().iterator();
        while (it.hasNext()) {
            SessioGrup assignat = it.next();
            boolean borrat = false;
            try {
                if (!resCorr.isAble2(assignat, sessio, possibles, aula, dia, hora)) {
                    possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
                    borrat = true;
                }
            } catch (NotFoundException e) {
                e.printStackTrace();
            }

            if (!resNiv.isAble2(assignat, sessio, possibles, aula, dia, hora) && !borrat) {
                possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
                borrat = true;
            }
            if (!resTeo.isAble2(assignat, sessio, possibles, aula, dia, hora) && !borrat) {
                possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
                borrat = true;
            }

            if (!resSub.isAble2(assignat, sessio, possibles, aula, dia, hora) && !borrat) {
                possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
                borrat = true;
            }

            /*Iterator<SessioGrup> it2 = possibles.keySet().iterator();
            while(it2.hasNext()){
                SessioGrup a = it2.next();
                boolean pos = false;
                for(int d = 0; d < 5; ++d){
                    for(int h = 0 ; h < 12; ++h){
                        if(!possibles.get(a).get(d).get(h).isEmpty()) pos = true;
                    }
                }
                if(!pos) return true;
            }
        }*/
        }
        return false;
    }

    /**
     * Genera l'horari
     *
     * @param i             és l'ièssim que indica de quina sessió estem parlant
     * @param horari        és l'horari que portem assignat
     * @param possibilitats son el conjunt de possibilitats de les sessions que no hem assignat encara
     * @return retorna true si ha pogut fer l'horari i si no ha pogut false
     */

    private boolean creaHorari(int i, Assignacio[][][] horari, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) {

        if (i == (sessions.size())) return true;
        int duracio = sessions.get(i).getSessio().getDuracioSessions();
        boolean teoria = (sessions.get(i).getSessio().getClass() == Teoria.class);
        ArrayList<ArrayList<ArrayList<Integer>>> posibles = possibilitats.get(sessions.get(i));
        for (int d = 0; d < posibles.size() && posibles.get(d) != null; ++d) {
            for (int h = 0; h < posibles.get(d).size() && posibles.get(d).get(h) != null; ++h) {
                for (int a = 0; a < posibles.get(d).get(h).size(); ++a) {
                    boolean possible = true;
                    int aula = posibles.get(d).get(h).get(a);
                    if (horari[h][d][aula] == null) {
                        if (teoria) {
                            if (comprovarRestriccions(aules.get(aula), d, h, sessions.get(i), duracio, a)) {
                                HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> clon = (HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>>) possibilitats.clone();
                                for (int z = 0; z < duracio && possible; ++z) {
                                    horari[h + z][d][aula] = new AssignacioT(fromInt2dia(d), h + z, aules.get(aula), sessions.get(i).getAssig(), sessions.get(i).getGrup());
                                    if (propagarPossibilitats(aula, d, h + z, sessions.get(i), possibilitats)) {
                                        for (int j = 0; j <= z; ++j) {
                                            horari[h + j][d][aula] = null;
                                        }
                                        possible = false;
                                    }
                                    ;
                                }
                                if (possible) {
                                    possibilitats.remove(sessions.get(i));
                                    if (creaHorari(i + 1, horari, possibilitats)) return true;
                                    else {
                                        possibilitats = clon;
                                        for (int z = 0; z < duracio; ++z) {
                                            horari[h + z][d][aula] = null;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (comprovarRestriccions(aules.get(aula), d, h, sessions.get(i), duracio, a)) {
                                HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> clon = (HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>>) possibilitats.clone();
                                for (int z = 0; z < duracio; ++z) {
                                    horari[h + z][d][aula] = new AssignacioL(fromInt2dia(d), h + z, aules.get(aula), sessions.get(i).getAssig(), sessions.get(i).getSub());
                                    if (propagarPossibilitats(aula, d, h + z, sessions.get(i), possibilitats)) {
                                        for (int j = 0; j <= z; ++j) {
                                            horari[h + j][d][aula] = null;
                                        }
                                        possible = false;
                                    }
                                }
                                possibilitats.remove(sessions.get(i));
                                if (creaHorari(i + 1, horari, possibilitats)) return true;
                                else {
                                    possibilitats = clon;
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[h + z][d][aula] = null;
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

    /**
     * Genera un horari
     *
     * @return true si s'ha pogut realitzar l'horari i false si no s'ha pogut
     */
    public boolean generaHorari() {
        try {
            sessions = creaSessions(assignatures);
            Collections.sort(sessions);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats = new HashMap<>();
        if (filtraRestriccions(possibilitats)) {
            System.out.println("No es pot generar l'horari");
            return false;
        }
        ;
        return creaHorari(0, horari, possibilitats);

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
        seslab = 0;             //si no comp se queja
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
}