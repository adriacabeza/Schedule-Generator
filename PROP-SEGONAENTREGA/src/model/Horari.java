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
        int posaula = aules.indexOf(a1.getAula());
        int posaula2 = aules.indexOf(a2.getAula());
        Assignacio aux;
        if(a1.getAssignatura() == null){ //si la primera assignació és null
            SessioGrup ses = null;
            boolean lab = true;
            if((a2.getClass() == AssignacioT.class)) {
                duracio = a2.getAssignatura().getDuracioSessionsTeo();
                lab = false;
            }
            else duracio = a2.getAssignatura().getDuracioSessionsLab();
            ses = new SessioGrup(a2.getAssignatura(), lab ? new Laboratori(0,duracio,a2.getAula().getTipusAula()) : new Teoria(0,duracio, a2.getAula().getTipusAula()),lab ? new Grup((a2.getGrup().getNum() / 10) * 10,a2.getGrup().getCapacitat(),0 ) : a2.getGrup(), lab ? (Subgrup) a2.getGrup() : null, 0);
            if(comprovarResSlotsBuits(ses, a1.getHora(),Algorismes.fromDia2int(a1.getDiaSetmana()),posaula,duracio,aules,a1.getAula())){
                aux = horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][posaula2];
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][posaula2]  = horari[a1.getHora()][Algorismes.fromDia2int(a1.getDiaSetmana())][posaula];
                horari[a1.getHora()][Algorismes.fromDia2int(a1.getDiaSetmana())][posaula] = aux;
                return true;
            }
        }
        else if(a2.getAssignatura() == null){ //si la segona assignació és null
            SessioGrup ses = null;
            boolean lab = true;
            if((a1.getClass() == AssignacioT.class)) {
                duracio = a1.getAssignatura().getDuracioSessionsTeo();
                lab = false;
            }
            else duracio = a1.getAssignatura().getDuracioSessionsLab();
            ses = new SessioGrup(a1.getAssignatura(), lab ? new Laboratori(0,duracio,a1.getAula().getTipusAula()) : new Teoria(0,duracio, a1.getAula().getTipusAula()),lab ? new Grup((a1.getGrup().getNum() / 10) * 10,a2.getGrup().getCapacitat(),0 ) : a1.getGrup(), lab ? (Subgrup) a1.getGrup() : null, 0);
            if(comprovarResSlotsBuits(ses, a2.getHora(),Algorismes.fromDia2int(a2.getDiaSetmana()),posaula2,duracio,aules, a2.getAula())) {
                aux = horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][posaula2];
                horari[a2.getHora()][Algorismes.fromDia2int(a2.getDiaSetmana())][posaula2]  = horari[a1.getHora()][Algorismes.fromDia2int(a1.getDiaSetmana())][posaula];
                horari[a1.getHora()][Algorismes.fromDia2int(a1.getDiaSetmana())][posaula] = aux;
                return true;
            }
        }
        else{ //cap dels dos és null
            SessioGrup ses = null;
            boolean lab1 = true;
            boolean lab2 = true;
            int duracio2;
            if((a2.getClass() == AssignacioT.class)) {
                duracio2 = a2.getAssignatura().getDuracioSessionsTeo();
                lab2 = false;
            }
            else duracio2 = a2.getAssignatura().getDuracioSessionsLab();
            if((a1.getClass() == AssignacioT.class)) {
                duracio = a1.getAssignatura().getDuracioSessionsTeo();
                lab1 = false;
            }
            else duracio = a1.getAssignatura().getDuracioSessionsLab();
            ArrayList<Assignacio> clase1 = new ArrayList<>(duracio);
            int dia1 = Algorismes.fromDia2int(a1.getDiaSetmana());
            //guardem la primera clase en una arraylist i mirem si es pot posar la segona
            for(int i = 0; i < duracio; ++i){
                clase1.set(i,horari[dia1][a1.getHora() + i][posaula]);
                horari[dia1][a1.getHora() + i][posaula] = null;
            }
            //creem la sessio de la segona i mirem si es pot posar en la posicio de la primera
            ses = new SessioGrup(a2.getAssignatura(), lab2 ? new Laboratori(0,duracio2,a2.getAula().getTipusAula()) : new Teoria(0,duracio2, a2.getAula().getTipusAula()),lab2 ? new Grup((a2.getGrup().getNum() / 10) * 10,a2.getGrup().getCapacitat(),0 ) : a2.getGrup(), lab2 ? (Subgrup) a2.getGrup() : null, 0);
            if(comprovarResSlotsBuits(ses,a1.getHora(),dia1,posaula,duracio2,aules,aules.get(posaula))){
                //la 2a assignatura pot anar en la posicio de la primera ara hem de mirar que la primera pugui anar on la 2a
                ArrayList<Assignacio> clase2 = new ArrayList<>(duracio2);
                int dia2 = Algorismes.fromDia2int(a2.getDiaSetmana());
                //ara guardem la segona clase
                for(int i = 0; i < duracio2; ++i){
                    clase2.set(i,horari[dia2][a2.getHora() + i][posaula]);
                    horari[dia2][a2.getHora() + i][posaula] = null;
                }
                //creem la sesio de la primera i mirem si es pot posar en la posicio de la segona
                ses = new SessioGrup(a1.getAssignatura(), lab1 ? new Laboratori(0,duracio,a1.getAula().getTipusAula()) : new Teoria(0,duracio, a1.getAula().getTipusAula()),lab1 ? new Grup((a1.getGrup().getNum() / 10) * 10,a2.getGrup().getCapacitat(),0 ) : a1.getGrup(), lab1 ? (Subgrup) a1.getGrup() : null, 0);
                if(comprovarResSlotsBuits(ses,a2.getHora(),dia2,posaula2,duracio,aules,aules.get(posaula2))){
                    for(int i = 0; i < clase1.size(); ++i){
                        horari[dia2][a2.getHora() + i][posaula] = clase1.get(i);
                    }
                    for(int i = 0; i < clase2.size(); ++i){
                        horari[dia1][a1.getHora() + i][posaula] = clase2.get(i);
                    }
                    return true;
                }
                //no es pot posar la assignatura -> revertim cambis
                for(int i = 0; i < clase2.size(); ++i){
                    horari[dia2][a2.getHora() + i][posaula] = clase2.get(i);
                }
            }
            //no es pot posar la assignatura -> revertim cambis
            for(int i = 0; i < clase1.size(); ++i){
                horari[dia1][a1.getHora() + i][posaula] = clase1.get(i);
            }
        }
        return false;
    }
}



