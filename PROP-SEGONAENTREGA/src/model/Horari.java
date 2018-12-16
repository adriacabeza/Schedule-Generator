/**
 * @Author Adria Cabeza
 */
package model;

import exceptions.NotFoundException;

import java.lang.reflect.Array;
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
}


