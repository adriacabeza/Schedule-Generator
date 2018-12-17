/**
 * @Author Adria Cabeza
 */
package model;

import exceptions.NotFoundException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Horari {

    private Assignacio[][][] horari;
    private RestriccioAula resAul;
    private RestriccioGrupTeo resTeo;
    private RestriccioSubgrupLab resSub;
    private ArrayList<RestriccioAulaDia> resAula;
    private ArrayList<RestriccioAulaHora> resAulaHora;
    private ArrayList<RestriccioAssigMatiTarda> resMatiTarda;
    private RestriccioCapacitatAula resCapAul;
    private RestriccioCorrequisit resCorr;
    private RestriccioLimits resLim;
    private RestriccioNivell resNiv;

    /**
     * Obtenir l'horari
     *
     * @return horari
     */
    public Assignacio[][][] getHorari() {
        return horari;
    }

    /**
     * Actualitza l'horari
     *
     * @param horari l'horari
     */
    public void setHorari(Assignacio[][][] horari) {
        this.horari = horari;
    }

    /**
     * Constructora que et crea un horari buit
     */
    public Horari() {
        //horari = new Assignacio[][][];
    }

    public void afegirRMT(String assig, boolean mati) {
        resMatiTarda.add(new RestriccioAssigMatiTarda(assig, mati));
    }

    public void afegirRD(int dia, Aula aula) {
        resAula.add(new RestriccioAulaDia(dia, aula));
    }

    public void afegirRDH(int hora, int dia, Aula aula) {
        resAulaHora.add(new RestriccioAulaHora(dia, hora, aula));
    }

    public void activaRC(boolean bool){
        resCorr = new RestriccioCorrequisit(bool);
    }

    public ArrayList<HashMap<String,String>> consultaDiesLliures (Assignatura a, String numGrup, HashMap<String, Aula> aules){
        int grup = Integer.parseInt(numGrup);
        Grup g = null;
        try {
            g = a.getGrup((grup/10)*10); //treiem el subgrup (si ho era)
        } catch (NotFoundException e) {
            e.printStackTrace();
        }


        int duracio = 0;
        SessioGrup ses = null;
        if(grup%10 ==0){
            duracio = a.getDuracioSessionsTeo(); //clase teoria
            ses = new SessioGrup(a,new Teoria(1,1,a.getTipusAulaTeo()),g,null,0);
        }
        else {
            try {
                duracio = a.getDuracioSessionsLab();
                Subgrup sub = g.getSubgrups().get(grup);
                ses = new SessioGrup(a, new Laboratori(1,1,a.getTipusAulaLab()),g,sub,0);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        ArrayList<HashMap<String,String>> result = new ArrayList<>();

        if (horari != null) {
            ArrayList<Aula> llistaules = new ArrayList<>();
            Aula aul = null;
            HashMap<String,String> diahora;
            Assignacio assignacio;
            for (int i = 0; i < horari.length; ++i) {
                for (int j = 0; j < horari[i].length; ++j) {
                    for (int k = 0; k < horari[i][j].length; ++k) {
                        assignacio = horari[i][j][k];
                        if (assignacio == null) {
                            //hauriem de pillar la aula en [i][j][k] i la llista de aules en [i][j] problema es es una hashmap i no podem estar segurs de pillar el mateix, hauriem d'usar un linked hash map
                            // mirar
                            // https://stackoverflow.com/questions/5237101/is-it-possible-to-get-element-from-hashmap-by-its-position
                            //un cop tenim aquesta info li pasem al horari i aquesta funcio ens diu si es bona posicio o no
                            //aul = aules.get()
                            //prodecim a mirar totes les restriccions
                            try {
                                if(comprovarResSlotsBuits(ses,j,i,k,duracio,llistaules,aul)) {
                                    diahora = new HashMap<String,String>();
                                    diahora.put("dia",Algorismes.fromInt2dia(i));
                                    diahora.put("hora", String.valueOf(Algorismes.getHora(j)));
                                    result.add(diahora);
                                }
                            } catch (NotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }

            }

        }
        return result;
    }


    /**
     * Consulta les hores lliures d'un dia concret on una assignatura i un grup podrien encaixar
     *
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero del grup o subgrup
     * @param dia      dia de la setmana
     * @return hores disponibles
     * */

    public ArrayList<String> consultaHoresLliuresPerDia(String nomAssig, String numGrup, int dia){
        //AQUI TONI XD XD
        //esto es lo de arriba leñe
    }



    //TODO fer el docs be
    /**
     * Comprova totes les restriccions per a l'assignació d'una sessió una determinada hora, dia i aula
     *
     * @param hora    hora que hem de comprovar
     * @param dia     dia que hem de comprovar
     * @param posaula aula que hem de comprovar
     * @return true si es pot efectuar l'assignació en el dia, hora i aula
     */
    public boolean comprovarResSlotsBuits(SessioGrup ses, int hora, int dia, int posaula,int duracio, ArrayList<Aula> aules, Aula aula) throws NotFoundException {
        if(!resLim.isAble(posaula,dia,hora,ses,duracio,aula,horari)) return false; //duracio seria la llista d'assignacions que representa la assignatura que volem canviar (mirar mes avall)
        //mirem si el que volem posar es forbidden
        for (RestriccioAulaDia r : resAula){
            if(!r.isAble2(null,null,aula,null,0,dia,0)) return false;
        }
        for (RestriccioAssigMatiTarda r : resMatiTarda){ //fem la crida amb la ultima hora ja que si aquesta entra en el interval de tardes no hem de poder afegirla
            if(!r.isAble(ses.getAssig().getNom(),aula,dia,hora+duracio)) return false;
        }
        //Hem vist que en la duracio que te pot estar(no colisiona, ara hem de mirar que en tota la duracio d'aquest no hi hagi problemes)
        for (int i = 0; i<duracio; ++i){
            if(!resTeo.isable(horari,hora,dia,ses,aules)) return false;
            if(!resSub.isable(horari,hora,dia,ses,aules)) return false;
            if(!resCapAul.isAble(0,dia,hora,ses,duracio,aula,horari)) return false;
            if(!resCorr.isable(horari,hora,dia,ses,aules)) return false;
            if(!resNiv.isable(horari,hora,dia,ses,aules)) return false;
            if(!resAul.isAble(0,dia,hora,ses,duracio,aula,horari)) return false;
            for (RestriccioAulaHora r : resAulaHora){
                if(!r.isAble(ses.getAssig().getNom(),aula,dia,hora)) return false;
            }
        }
        return true;
        //TODO: pensar com fer-la
    }



    //TODO una vez acabado todo el javadocs
    /*
     * Construeix l'horari
     *
     * @param assignatures conjunt d'assignatures del pla d'estudis
     * @param aules        conjunt d'aules
     * @param resCorr      conjunt de restriccions de correquisit
     * @param resNiv       restricció de nivell
     * @param resAul       restricció d'aula
     * @param resTeo       restricció de sessió de teoria
     * @param resSub       restricció de de sessió de laboratori
     * @param resAulDia    conjunt de restriccions d'una aula en un dia
     * @param resAulaHora  conjunt de restriccions d'una aula en una hora
     * @param resMatiTarda conjunt de restriccions d'una assignatura de matins o tardes
     * @param resCapAul    restricció de capacitat d'una aula
     * @param resLim       restricció de límits d'hores
     * @return true si es pot construir l'horari i false si no es pot
     */

    public boolean ConstruirHorari(HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules) {
        resAul = new RestriccioAula();
        resTeo = new RestriccioGrupTeo();
        resSub = new RestriccioSubgrupLab();
        resCapAul = new RestriccioCapacitatAula();
        resLim = new RestriccioLimits();
        resNiv = new RestriccioNivell();
        Algorismes algoritme = new Backtracking2(assignatures, aules, resCorr, resNiv, resAul, resTeo, resSub, resAula, resAulaHora, resMatiTarda, resCapAul, resLim);
        try {
            boolean b = ((Backtracking2) algoritme).generaHorari();
            if (!b) return false;
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        horari = algoritme.getHorari();
        return true;
    }


    /**
     * Consulta l'aula en que una assignatura, un grup, data i hora tenen una assignacio
     *
     * @param result resultat
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero de grup o subgrup
     * @param ndia      dia de la setmana
     * @param nhora     hora
     * @return aula de l'assignacio
     */

    public ArrayList<String> consultaAulaPerHoresDiaAssignaturaGrup(HashMap<String, Aula> aules,ArrayList<String> result,  String nomAssig, String numGrup, int nhora, int ndia) {
        String subject;
        String group;
        Assignacio assignacio;
        for (int i = 0; i < horari[nhora][ndia].length; ++i) {
            assignacio = horari[nhora][ndia][i];
            subject = assignacio.getAssignatura().getNom();
            group = String.valueOf(assignacio.getGrup().getNum());
            if (subject == nomAssig && numGrup == group) {
                result.add(aules.get(i).toString());
            }
        }
        return result;
    }

    /**
     * Consulta les hores que una assignatura i un grup tenen classes assignades un dia en concret
     *
     * @param result resultat
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero del grup o subgrup
     * @param numdia      dia de la setmana
     * @return llista d'hores assignades al grup aquell dia
     */


    public ArrayList<String> consultaHoresPerDiaAssignaturaGrup(ArrayList<String> result, String nomAssig, String numGrup, int numdia){
        Assignacio assignacio;
        for (int j = 0; j < horari[numdia].length; ++j) {
            for (int k = 0; k < horari[numdia][j].length; ++k) {
                assignacio = horari[numdia][j][k];
                if (String.valueOf(assignacio.getGrup().getNum()) == numGrup && assignacio.getAssignatura().getNom() == nomAssig) {
                    result.add(String.valueOf(k));
                }

            }
        }
        return result;
    }



    /**
     * Consulta els dies que una assignatura i un grup tenen classes assignades
     *
     * @param result resultat
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero del grup o subgrup
     * @return dies que el grup de l'assignatura te assignacions
     */

    public ArrayList<String> consultaDiesPerAssignaturaGrup(ArrayList<String> result , String nomAssig, String numGrup) {
        Assignacio assignacio;
        for (int i = 0; i < horari.length; ++i) {
            for (int j = 0; j < horari[i].length; ++j) {
                for (int k = 0; k < horari[i][j].length; ++k) {
                    assignacio = horari[i][j][k];
                    if (String.valueOf(assignacio.getGrup().getNum()) == numGrup && assignacio.getAssignatura().getNom() == nomAssig) {
                        result.add(Algorismes.fromInt2dia(i));
                    }

                }
            }

        }
        return result;
    }


}


