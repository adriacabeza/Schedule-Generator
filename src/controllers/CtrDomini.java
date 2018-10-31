package controllers;

import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CtrDomini {

    private static CtrDomini ourInstance = new CtrDomini();

    public static CtrDomini getInstance() {
        return ourInstance;
    }

    private HashMap<String, Assignatura> assignatures = new HashMap<>();
    private HashMap<String, PlaEstudis> plaEstudis = new HashMap<>();
    private HashMap<String, Aula> aules = new HashMap<>();

    private ArrayList<Assignacio> assignacions = new ArrayList<Assignacio>();
    private Restriccions r;

    private CtrDomini() {

    }

    /****************** PLA D'ESTUDIS *******************/

    /**
     * Crea un nou pla d'estudis no obsolet i sense cap assignatura
     * @param nom Nom del pla d'estudis
     * @param any Any d'inici del nou pla d'estudis
     */
    public void crearPlaEstudis(String nom, Date any) {
        plaEstudis.put(nom, new PlaEstudis(nom, any, false));
    }

    /**
     * Esborra del sistema un pla d'estudis OBSOLET
     * @param nom Nom del pla d'estudis
     */
    public void esborrarPlaEstudis(String nom){
        plaEstudis.remove(nom); //TODO comprovar que el pla en questio estigui obsolet, sino no es pot esborrar
    }

    /**
     * Consulta tota la informació d'un pla d'estudis
     * @param nom Nom del pla d'estudis
     * @return tota la informació del pla d'estudis i les seves relacions
     */
    public PlaEstudis consultarPlaEsudis(String nom){
        return plaEstudis.get(nom);
    }

    /**
     * Afegeix una assignatura anteriorment creada al pla d'estudis indicat
     * @param nomP Nom del pla d'estudis
     * @param nomA Nom de l'assignatura
     */
    public void afegirAssignaturaPla(String nomP, String nomA){
        plaEstudis.get(nomP).afegirAssignatura(assignatures.get(nomA));
    }

    public void esborrarAssignaturaPla(String nomP, String nomA) {
        plaEstudis.get(nomP).esborrarAssignatura(nomA);
    }

    public void consultarAssignaturesPlaEstudis(String nomP){
        return plaEstudis.get(nomP).getAssignatures();
    }


    /************** ASSIGNATURES ***************/

    public void crearAssignatura(String nom, int quadrimestre, Teoria t, Laboratori l) {
        assignatures.put(nom, new Assignatura(nom, quadrimestre, t, l));
    }


    public Assignatura consultarAssignatura(String nom) {
        return assignatures.get(nom);
    }

    public void esborrarAssignatura(String nomA){
        assignatures.remove(nomA);
    }




    /**
     * Permet modificar la informació sobre les sessions de teoria d'una assignatura
     * @param nom_assig Nom de l'assignatura
     * @param duracio Duració de les sessions de teoria
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     */
    public void modificaInformacioTeoria(String nom_assig, int duracio, int num_sessions){
        assignatures.get(nom_assig).setTeoria(new Teoria(num_sessions, duracio));
    }

    /**
     * Permet modificar la informació sobre les sessions de laboratori d'una assignatura
     * @param nom_assig Nom de l'assignatura
     * @param duracio Duració de les sessions de laboratori
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     */
    public void modificaInformacioLaboratori(String nom_assig, int duracio, int num_sessions){
        assignatures.get(nom_assig).setLaboratori(new Laboratori(num_sessions, duracio));
    }

    /**
     * Permet modificar els grups i subgrups d'una assignatura
     * @param nom_assig nom de l'assignatura
     * @param num_grups numero de grups  de l'assignatura
     * @param grup_cap capacitat de cada grup
     * @param sgrup_num capacitat dels subgrups
     */
    public void modificarGrups(String nom_assig, int num_grups, int grup_cap, int sgrup_num) {
        assignatures.get(nom_assig).modificarGrups(num_grups, grup_cap, sgrup_num);
    }

    /**
     * Permet afegir una relacio de correquisits entre dues assignatures
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     */
    public void afegeixCorrequisit(String nom_a, String nom_b){
        assignatures.get(nom_a).afegeixCorrequisit(assignatures.get(nom_b));
        assignatures.get(nom_b).afegeixCorrequisit(assignatures.get(nom_a));
    }

    /**
     * Esborra la relació de correqusiits entre dues assignatures
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     */
    public void esborraCorrequisit(String nom_a, String nom_b){
        assignatures.get(nom_a).esborraCorrequisit(assignatures.get(nom_b));
        assignatures.get(nom_b).esborraCorrequisit(assignatures.get(nom_a));
    }


    /******************* AULES ************************/

    public void creaAula(String edifici, int planta, int aula, String tipusAula, ArrayList<Assignacio> assignacions){
        aules.put(edifici + String.valueOf(planta) + String.valueOf(aula),new Aula(edifici, planta,aula,tipusAula,assignacions));
    }

    public void esborrarAula(String edifici, int planta, int aula){
        aules.remove(edifici + String.valueOf(planta) + String.valueOf(aula));
    }

    public void modificarAula(){

    }

    public void consultarAula(){

    }


    /** sobre assignacions */
    public void generarHorari(){

    }

    /*
    public void crearAssignacio(Aula aula, Assignatura assignatura, int grup, int hora, int dia, boolean teoria){
        Assignacio a;
        if (teoria) {
            a = new AssignacioT(dia, hora, aula, assignatura, grup);
        } else {
            a = new AssignacioL(dia, hora, aula, assignatura, grup); //on grup es un subgrup
        }
        assignacions.add(a);
    }*/

}
