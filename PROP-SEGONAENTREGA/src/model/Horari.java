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
     * Constructora que et crea un horari buit
     */
    public Horari(){
        //horari = new Assignacio[][][];
    }
    
    //forma de hacerlo con el crearestriccions
    /*
     public creaRestriccions(ArrayList<Boolean> checkboxes, ArrayList<String> resAula, ArrayList<String> resAulaHora, ArrayList<String> resMatiTarda){
        resAul = new RestriccioAula();
        resTeo = new RestriccioGrupTeo();
        resSub = new RestriccioSubgrupLab();
        resCapAul = new RestriccioCapacitatAula();
        resCorr = new RestriccioCorrequisit();
        resLim = new RestriccioLimits();
        resNiv = new RestriccioNivell();
        this.resAula = new ArrayList<>();
        this.resAulDia = new ArrayList<>();
        this. resMatiTarda = new ArrayList<>();
        /*for(String s : resAul){
            this.resAula.add(new RestriccioAulaDia());
        }
        for(String s : resAulaHora){
            this.resAulaHora.add(new RestriccioAulaHora());
        }
        for(String s : resMatiTarda){
            this.resMatiTarda.add(new RestriccioAssigMatiTarda());
        }
    }
    
    */


    /**
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
/*
    TLDR:

          - las restricciones deberian ser un atributo del horario, no pasadas cada vez como parametro, desde dominio te pasare
            los bools de las restricciones que se tengan que crear (leidos desde la view) y tu te puedes hacer una funcion que te
            las de de alta todas para tenerlas preparadas para generar horario mas adelante
     */
    public boolean ConstruirHorari( HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                  RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora, ArrayList<RestriccioAssigMatiTarda> resMatiTarda, RestriccioCapacitatAula resCapAul, RestriccioLimits resLim ) {

        Algorismes algoritme = new Backtracking2(assignatures,aules,resCorr,resNiv,resAul,resTeo,resSub,resAulDia,resAulaHora,resMatiTarda,resCapAul,resLim);
        try {
            boolean b = ((Backtracking2) algoritme).generaHorari();
            if (!b) return false;
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        horari = algoritme.getHorari();
        return true;
    }

/*
    public boolean ConstruirHorari( HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules) {

        Algorismes algoritme = new Backtracking2(assignatures,aules,resCorr,resNiv,resAul,resTeo,resSub,resAulDia,resAulaHora,resMatiTarda,resCapAul,resLim);
        try {
            boolean b = ((Backtracking2) algoritme).generaHorari();
            if (!b) return false;
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        horari = algoritme.getHorari();
        return true;
    */
}


