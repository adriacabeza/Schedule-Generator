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
    //TODO fer el docs be
    /**
     * Comprova totes les restriccions per a l'assignació d'una sessió una determinada hora, dia i aula
     *
     * @param hora    hora que hem de comprovar
     * @param dia     dia que hem de comprovar
     * @param posaula aula que hem de comprovar
     * @return true si es pot efectuar l'assignació en el dia, hora i aula
     */
    public boolean comprovarResSlotsBuits(SessioGrup ses, int hora, int dia, int posaula,int duracio, ArrayList<Aula> aules, Aula aula) {
        if(!resLim.isAble(posaula,dia,hora,ses,duracio,aula,horari)) return false; //duracio seria la llista d'assignacions que representa la assignatura que volem canviar (mirar mes avall)
        //if(!resTeo.isable(horari,hora,dia,ses,ArrayAules)) return false;
        //if(!resSub.isable(horari,hora,dia,ses,ArrayAules)) return false;
        //if(!resCapAul.isable()) return false;
        //if(!resCorr.isable()) return false;
        //if(!resNiv.isable()) return false;
        //if(!resAul.isable()) return false;


        //segurament necessitarem mes parametres per aplicar aquests, ja mirarem com ho fem


        /*for (RestriccioAulaDia r : resAulDia){
            if(!r.isable()) return false;
        }*/
        /*for (RestriccioAulaHora r : resAulHora){
            if(!r.isable()) return false;
        }*/
        /*for (RestriccioAssigMAtiTarda r : resMatitarda){
            if(!r.isable()) return false;
        }*/


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


