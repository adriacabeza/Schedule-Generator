package model;

import exceptions.NotFoundException;

import java.util.*;

public class Backtracking2 extends Algorismes {


    public Backtracking2(HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
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
    private boolean NotForaLimit(Aula aula, int dia, int hora, SessioGrup assig, int duracio, int posaula) {
        if (!resLim.isAble(posaula, dia, hora, assig, duracio, null, horari))
            return false;
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
                if (resAul.isAble(0,0,0,sessions.get(i),0, aules.get(k),null) && resCapAul.isAble(0,0,0, sessions.get(i),0,aules.get(k),null))
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
                        restriccioMati = resMatiTarda.get(indexAssig).isAble(assig.getNom(),null,0, h);        //mirem si es de mati, estiguem en el mati i el mateix per la tarda
                    for (int n = 0; n < aules_possibles.size() && restriccioMati; ++n) {
                        boolean b = true;
                       for (int j = 0; j < resAulaHora.size() && b; ++j) {
                            if (!resAulaHora.get(j).isAble(null,aules.get(aules_possibles.get(n)), d, h)) b = false;
                        }
                        for (int j = 0; j < resAulDia.size() && b; ++j) {
                            if (!resAulDia.get(j).isAble2(null,null,aules.get(aules_possibles.get(n)),null,0, d,0)) b = false;
                        }
                        if (b) aulesHora.add(aules_possibles.get(n));
                    }
                    horaaules.add(h, aulesHora);
                }
                diahoraaules.add(d, horaaules);

            }
            possibilitats.put(sessions.get(i), diahoraaules);

         boolean pos;
          Iterator<SessioGrup> it2 = possibilitats.keySet().iterator();
            while(it2.hasNext()){
                SessioGrup a = it2.next();
                pos = true;
                for(int d = 0; d < 5; ++d){
                    for(int h = 0 ; h < 12; ++h){
                        if(!possibilitats.get(a).get(d).get(h).isEmpty()) pos = false;
                    }
                }
                if(pos) return true;
            }
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

    public boolean propagarPossibilitats(int aula, int dia, int hora, SessioGrup sessio, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibles) throws NotFoundException {
        Iterator<SessioGrup> it = possibles.keySet().iterator();
        while (it.hasNext()) {
            SessioGrup assignat = it.next();
            boolean borrat = false;
            if (!resCorr.isAble2(assignat, sessio,null, possibles, aula, dia, hora)) {
                possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
                borrat = true;
            }

            if (!resNiv.isAble2(assignat, sessio,null, possibles, aula, dia, hora) && !borrat) {
                possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
                borrat = true;
            }
            if (!resTeo.isAble2(assignat, sessio,null, possibles, aula, dia, hora) && !borrat) {
                possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
                borrat = true;
            }

            if (!resSub.isAble2(assignat, sessio,null, possibles, aula, dia, hora) && !borrat) {
                possibles.get(assignat).get(dia).get(hora).remove(possibles.get(assignat).get(dia).get(hora).indexOf(aula));
            }

            boolean pos = true;

            for(int d = 0; d <5; ++d){
                for(int h = 0; h < 12; ++h){
                    if (!possibles.get(assignat).get(d).get(h).isEmpty()) pos= false;
                }
            }
            if(pos) return true;

        }
        return false;
    }





    /**
     * Clona l'horari que se li passa per paràmetre
     * @param horari que ha de clonar
     * @return horari clonat
     */
    HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> clonar(HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> horari){
        HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> clon = new HashMap<>();

        for(Map.Entry<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> cursor: horari.entrySet()){
            clon.put(cursor.getKey(), new ArrayList<>(cursor.getValue()));
        }
        return clon;

    }

    /**
     * Genera l'horari
     * @param i és l'ièssim que indica de quina sessió estem parlant
     * @param horari és l'horari que portem assignat
     * @param possibilitats son el conjunt de possibilitats de les sessions que no hem assignat encara
     * @return retorna true si ha pogut fer l'horari i si no ha pogut false
     */

    public boolean creaHorari(int i, Assignacio[][][] horari, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) throws NotFoundException {

        if (i == (sessions.size())) return true;
        int duracio = sessions.get(i).getSessio().getDuracioSessions();
        boolean teoria = (sessions.get(i).getSessio().getClass() == Teoria.class);
        ArrayList<ArrayList<ArrayList<Integer>>> posibles = possibilitats.get(sessions.get(i));
        for (int d = 0; d < posibles.size() && posibles.get(d) != null; ++d) {
            for (int h = 0; h < posibles.get(d).size() &&  posibles.get(d).get(h) != null; ++h) {
                for (int a = 0; a < posibles.get(d).get(h).size(); ++a) {
                    boolean possible = true;
                    int aula = posibles.get(d).get(h).get(a);
                    if (horari[h][d][aula] == null) {
                        if (teoria) {
                            if (NotForaLimit(aules.get(aula), d, h, sessions.get(i), duracio, a)) {
                                HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> clon = clonar(possibilitats);
                                for (int z = 0; z < duracio && possible; ++z) {
                                    horari[h+z][d][aula] = new AssignacioT(fromInt2dia(d), h + z, aules.get(aula), sessions.get(i).getAssig(), sessions.get(i).getGrup());
                                   if(propagarPossibilitats(aula,d,h+z, sessions.get(i),possibilitats)){
                                        for(int j = 0 ; j <= z; ++j){
                                            horari[h+j][d][aula] = null;
                                        }
                                        possible = false;
                                    }
                                }
                                if(possible) {
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
                            if (NotForaLimit(aules.get(aula), d, h, sessions.get(i), duracio, a)) {
                                HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> clon = clonar(possibilitats);
                                for (int z = 0; z < duracio; ++z) {
                                    horari[h + z][d][aula] = new AssignacioL(fromInt2dia(d), h + z, aules.get(aula), sessions.get(i).getAssig(), sessions.get(i).getSub());
                                   if(propagarPossibilitats(aula,d,h+z, sessions.get(i),possibilitats)){
                                        for(int j = 0 ; j <= z; ++j){
                                            horari[h+j][d][aula] = null;
                                        }
                                        possible = false;
                                    }
                                }
                            if(possible) {
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
        }
        return false;
    }

    /**
     * Genera un horari
     *
     * @return true si s'ha pogut realitzar l'horari i false si no s'ha pogut
     */
    public boolean generaHorari() throws NotFoundException {
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

        return creaHorari(0, horari, possibilitats);

    }

}
