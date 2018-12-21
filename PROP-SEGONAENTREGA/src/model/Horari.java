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

    /**
     * Afegeix una restriccio de mati/tarda
     * @param assig assignatura que definim
     * @param mati boolea que indica si es de mati o tarda
     */
    public void afegirRMT(String assig, boolean mati) {
        resMatiTarda.add(new RestriccioAssigMatiTarda(assig, mati));
    }

    /**
     * Afegeix una restriccio de dia en una aula
     * @param dia dia en el cual no es podra fer clase
     * @param aula aula en la cual no es podra fer clase
     */
    public void afegirRD(int dia, Aula aula) {
        resAula.add(new RestriccioAulaDia(dia, aula));
    }

    /**
     * Afegeix una restriccio de dia i hora en una aula
     * @param hora hora en la cual no es podra fer clase
     * @param dia dia en el cual no es podra fer clase
     * @param aula aula en la cual no es podra fer clase
     */
    public void afegirRDH(int hora, int dia, Aula aula) {
        resAulaHora.add(new RestriccioAulaHora(dia, hora, aula));
    }

    /**
     * activa la restriccio de assignatures per correquisits
     * @param bool indica si s'activa la restriccio o no
     */
    public void activaRC(boolean bool){
        resCorr = new RestriccioCorrequisit(bool);
    }



    /**
     * Comprova totes les restriccions per a l'assignació d'una sessió una determinada hora, dia i aula
     * @param ses sessio que mirem si pot anar
     * @param hora hora que hem de comprovar
     * @param dia dia que hem de comprovar
     * @param posaula posicio de la aula que hem de comprovar dins de la llista
     * @param duracio duracio de la sesio
     * @param aules llista d'aules que tenim
     * @param aula aula que hem de comprovar
     * @return true si es pot efectuar l'assignació en el dia, hora i aula
     * @throws NotFoundException
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
     * @return boolea que indica si es pot construir l'horari
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
     primera assignació que hem d'intercanviar
        segona assignació que hem d'intercanviar
     * @return retorna true si s'ha pogut efectuar l'intercanvi
     */
    public boolean intercanviaSlots(HashMap<String, String> slot1, HashMap<String, String> slot2, HashMap<String, Aula> aules, HashMap<String, Assignatura> assig) throws NotFoundException {
        ArrayList<Aula> aulesaux = new ArrayList<>(aules.values());
        int duracio;
        Assignatura a = null;
        Aula aul = null;
        SessioGrup ses = null;
        boolean lab = true;
        int grupnum;
        if(slot1.get("grup").equals("-")){ //si la primera assignació és null
            a = assig.get(slot2.get("assignatura"));
            grupnum = Integer.parseInt(slot2.get("grup"));
            if(grupnum %10 == 0) {
                duracio = a.getDuracioSessionsTeo();
                lab = false;
            }
            else duracio = a.getDuracioSessionsLab();
            aul = aules.get(slot2.get("aula"));
            if(lab){
                Subgrup sub = a.getGrup((grupnum / 10) * 10).getSubgrups().get(grupnum);
                ses = new SessioGrup(a,new Laboratori(0,duracio,aul.getTipusAula()), new Grup((grupnum / 10) * 10,sub.getCapacitat(),0 ),sub, 0);
            }
            else{
                ses = new SessioGrup(a, new Teoria(0,duracio, aul.getTipusAula()), a.getGrup(grupnum), null, 0);
            }
            int dia = Algorismes.fromDia2int(slot1.get("dia"));
            int hora =Integer.parseInt(slot1.get("hora"));
            int posaula = aulesaux.indexOf(aules.get(slot1.get("aula")));
            int dia2 = Algorismes.fromDia2int(slot2.get("dia"));
            int hora2=Integer.parseInt(slot2.get("hora"));
            int posaula2 = aulesaux.indexOf(aules.get(slot2.get("aula")));
            if(comprovarResSlotsBuits(ses, hora,dia,posaula,duracio,aulesaux,aules.get(slot1.get("aula")))){
                for(int i = 0; i<duracio; ++i){
                    horari[hora+i][dia][posaula] = horari[hora2+i][dia2][posaula2];
                    horari[hora2+i][dia2][posaula2] = null;
                }
                return true;
            }
        }
        else if(slot2.get("grup").equals("-")){ //si la segona assignació és null
            a = assig.get(slot1.get("assignatura"));
            grupnum = Integer.parseInt(slot1.get("grup"));
            if(grupnum %10 == 0) {
                duracio = a.getDuracioSessionsTeo();
                lab = false;
            }
            else duracio = a.getDuracioSessionsLab();
            aul = aules.get(slot1.get("aula"));
            if(lab){
                Subgrup sub = a.getGrup((grupnum / 10) * 10).getSubgrups().get(grupnum);
                ses = new SessioGrup(a,new Laboratori(0,duracio,aul.getTipusAula()), new Grup((grupnum / 10) * 10,sub.getCapacitat(),0 ),sub, 0);
            }
            else{
                ses = new SessioGrup(a, new Teoria(0,duracio, aul.getTipusAula()), a.getGrup(grupnum), null, 0);
            }
            int dia = Algorismes.fromDia2int(slot2.get("dia"));
            int hora =Integer.parseInt(slot2.get("hora"));
            int posaula = aulesaux.indexOf(aules.get(slot2.get("aula")));
            int dia2 = Algorismes.fromDia2int(slot1.get("dia"));
            int hora2=Integer.parseInt(slot1.get("hora"));
            int posaula2 = aulesaux.indexOf(aules.get(slot1.get("aula")));
            if(comprovarResSlotsBuits(ses, hora,dia,posaula,duracio,aulesaux,aules.get(slot2.get("aula")))){
                for(int i = 0; i<duracio; ++i){
                    horari[hora+i][dia][posaula] = horari[hora2+i][dia2][posaula2];
                    horari[hora2+i][dia2][posaula2] = null;
                }
                return true;
            }
        }
        else{ //cap dels dos és null

            SessioGrup ses2 = null;
            int grupnum2;
            boolean lab2 = true;
            Aula aul2;
            Assignatura a2;
            int duracio2 = 0;
            a = assig.get(slot1.get("assignatura"));
            a2 = assig.get(slot2.get("assignatura"));
            grupnum2 = Integer.parseInt(slot2.get("grup"));
            if(grupnum2 %10 == 0) {
                duracio2 = a2.getDuracioSessionsTeo();
                lab2 = false;
            }
            else duracio2 = a2.getDuracioSessionsLab();
            aul2 = aules.get(slot2.get("aula"));
            if(lab2){
                Subgrup sub = a2.getGrup((grupnum2 / 10) * 10).getSubgrups().get(grupnum2);
                ses2 = new SessioGrup(a2,new Laboratori(0,duracio2,aul2.getTipusAula()), new Grup((grupnum2 / 10) * 10,sub.getCapacitat(),0 ),sub, 0);
            }
            else{
                ses2 = new SessioGrup(a2, new Teoria(0,duracio2, aul2.getTipusAula()), a2.getGrup(grupnum2), null, 0);
            }
            a = assig.get(slot1.get("assignatura"));
            grupnum = Integer.parseInt(slot1.get("grup"));
            if(grupnum %10 == 0) {
                duracio = a.getDuracioSessionsTeo();
                lab = false;
            }
            else duracio = a.getDuracioSessionsLab();
            aul = aules.get(slot1.get("aula"));
            if(lab){
                Subgrup sub = a.getGrup((grupnum / 10) * 10).getSubgrups().get(grupnum);
                ses = new SessioGrup(a,new Laboratori(0,duracio,aul.getTipusAula()), new Grup((grupnum / 10) * 10,sub.getCapacitat(),0 ),sub, 0);
            }
            else{
                ses = new SessioGrup(a, new Teoria(0,duracio, aul.getTipusAula()), a.getGrup(grupnum), null, 0);
            }


            int dia = Algorismes.fromDia2int(slot1.get("dia"));
            int hora =Integer.parseInt(slot1.get("hora"));
            int posaula = aulesaux.indexOf(aules.get(slot1.get("aula")));
            int dia2 = Algorismes.fromDia2int(slot2.get("dia"));
            int hora2=Integer.parseInt(slot2.get("hora"));
            int posaula2 = aulesaux.indexOf(aules.get(slot2.get("aula")));


            ArrayList<Assignacio> clase2 = new ArrayList<>();

            for(int i = 0; i<duracio2; ++i ){
                clase2.add(horari[hora2+i][dia2][posaula2]);
                horari[hora2+i][dia2][posaula2] = null;
            }

            if(comprovarResSlotsBuits(ses,hora2,dia2,posaula2,duracio,aulesaux,aules.get(slot2.get("aula")))){
                ArrayList<Assignacio> clase1 = new ArrayList<>();
                for(int i = 0; i<duracio; ++i ){
                    clase1.add(horari[hora+i][dia][posaula]);
                    horari[hora+i][dia][posaula] = null;
                }
                if(comprovarResSlotsBuits(ses2,hora,dia,posaula,duracio2,aulesaux,aules.get(slot1.get("aula")))){
                    for(int i = 0; i < duracio; ++i){
                        horari[hora2+i][dia2][posaula2] = clase1.get(i);
                    }
                    for(int i = 0; i < duracio2; ++i){
                        horari[hora+i][dia][posaula] = clase2.get(i);
                    }

                    return true;
                }
                for(int i = 0; i<duracio; ++i ){
                    horari[hora+i][dia][posaula] = clase1.get(i);
                }
            }

            for(int i = 0; i<duracio2; ++i ){
                horari[hora2+i][dia2][posaula2] = clase2.get(i);
            }

        }
        return false;
    }
}



