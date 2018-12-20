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
        resMatiTarda = new ArrayList<>();
        resAula = new ArrayList<>();
        resAulaHora = new ArrayList<>();
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
    }


    /**
     * Construeix l'horari
     * @param assignatures conjunt d'assignatures del pla d'estudis
     * @param aules        conjunt d'aules
     * @return
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
     * Fa un intercanvi entre dues assignacions
     * @param a1 primera assignació que hem d'intercanviar
     * @param a2 segona assignació que hem d'intercanviar
     * @return retorna true si s'ha pogut efectuar l'intercanvi
     */
    public boolean intercanviaSlots(Assignacio a1, Assignacio a2, ArrayList<Aula> aules) throws NotFoundException {
        int duracio;
        Assignacio aux;
        if(a1.getAssignatura() == null){
            if((a2.getClass() == AssignacioT.class)) duracio = a2.getAssignatura().getDuracioSessionsTeo();
            else duracio = a2.getAssignatura().getDuracioSessionsLab();
            if(comprovarResSlotsBuits(null, a2.getHora(),Algorismes.fromDia2int(a2.getDiaSetmana()),,duracio,aules,a2.getAula())){
                aux = horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][]
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][0]  = horari[a1.getHora()][Algorismes.fromDia2int(a1.getDiaSetmana())][];
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][0] = aux;
                return true;
            }
        }
        else if(a2.getAssignatura() == null){
            if((a1.getClass() == AssignacioT.class)) duracio = a1.getAssignatura().getDuracioSessionsTeo();
            else duracio = a1.getAssignatura().getDuracioSessionsLab();
            if(comprovarResSlotsBuits(null, a1.getHora(),Algorismes.fromDia2int(a1.getDiaSetmana()),,duracio,aules, a1.getAula())) {
                aux = horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][]
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][0]  = horari[a1.getHora()][Algorismes.fromDia2int(a1.getDiaSetmana())][];
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][0] = aux;
                return true;
            }
        }
        else{ //cap dels dos és null
            if((a1.getClass() == AssignacioT.class)) duracio = a1.getAssignatura().getDuracioSessionsTeo();
            else duracio = a1.getAssignatura().getDuracioSessionsLab();
            if(COMPROVARSIESPOTFERAMBELSDOS){
                aux = horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][]
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][0]  = horari[a1.getHora()][Algorismes.fromDia2int(a1.getDiaSetmana())][];
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][0] = aux;
                return true;
            }
        }
        return false;
    }

}



