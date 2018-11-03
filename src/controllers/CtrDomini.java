package controllers;

import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CtrDomini {

    private static CtrDomini ourInstance = new CtrDomini();

    public static CtrDomini getInstance() {
        return ourInstance;
    }



    private HashMap<String, Assignatura> assignatures = new HashMap<String,Assignatura>();
    private HashMap<String, PlaEstudis> plaEstudis = new HashMap<String,PlaEstudis>();
    private HashMap<String, Aula> aules = new HashMap<String,Aula>();
    private Assignacio [][][] horari = new Assignacio [12][5][aules.size()];
    private Restriccions r;


    private String crearkey(String edifici, int planta, int aula){
        return edifici + String.valueOf(planta)+ String.valueOf(aula);
    }


    private CtrDomini() {

    }



    /****************** PLA D'ESTUDIS *******************/

    /**
     * Crea un nou pla d'estudis no obsolet i sense cap assignatura
     *
     * @param nom Nom del pla d'estudis
     * @param any Any d'inici del nou pla d'estudis
     * @throws RestriccioIntegritatException si ja existia aquell pla d'estudis
     */
    public void crearPlaEstudis(String nom, Date any) throws RestriccioIntegritatException {
        if (plaEstudis.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix un pla d'estudis amb nom " + nom.toUpperCase());
        }
        plaEstudis.put(nom, new PlaEstudis(nom, any, false));
    }

    /**
     * Esborra del sistema un pla d'estudis OBSOLET
     *
     * @param nom Nom del pla d'estudis
     * @throws NotFoundException             si no existeix el pla d'estudis especificat
     * @throws RestriccioIntegritatException si el pla d'estudis no està obsolet
     */
    public void esborrarPlaEstudis(String nom) throws NotFoundException, RestriccioIntegritatException {
        if (!plaEstudis.containsKey(nom)) {
            throw new NotFoundException("No s'ha trobat el pla d'estudis " + nom.toUpperCase());
        }
        if (!plaEstudis.get(nom).isObsolet()) {
            throw new RestriccioIntegritatException("No es pot esborrar un pla d'estudis no obsolet");
        }
        plaEstudis.remove(nom);
    }

    /**
     * Consulta tota la informació d'un pla d'estudis
     *
     * @param nom Nom del pla d'estudis
     * @return tota la informació del pla d'estudis i les seves relacions
     * @throws NotFoundException si no existex el pla d'estudis especificat
     */
    public PlaEstudis consultarPlaEsudis(String nom) throws NotFoundException {
        if (!plaEstudis.containsKey(nom)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nom.toUpperCase());
        }
        return plaEstudis.get(nom);
    }

    /**
     * Afegeix una assignatura anteriorment creada al pla d'estudis indicat
     *
     * @param nomP Nom del pla d'estudis
     * @param nomA Nom de l'assignatura
     */
    public void afegirAssignaturaPla(String nomP, String nomA) {
        plaEstudis.get(nomP).afegirAssignatura(assignatures.get(nomA));
    }

    /**
     * Esborra una assignatura continguda en el pla
     *
     * @param nomP Nom del pla d'estudis
     * @param nomA Nom de l'assignatura
     */
    public void esborrarAssignaturaPla(String nomP, String nomA) {
        plaEstudis.get(nomP).esborrarAssignatura(nomA);
    }

    /**
     * Consulta les assignatures d'un pla d'estudis
     *
     * @param nomP Pla d'estudis
     */
    public void consultarAssignaturesPlaEstudis(String nomP) {
        return plaEstudis.get(nomP).getAssignatures();
    }


    /************** ASSIGNATURES ***************/

    /**
     * Permet crear una nova assignatura
     *
     * @param nom          Nom de l'assignatura
     * @param quadrimestre Quadrimestre al qual pertany
     * @param t            Informacio de les sessions de teoria, si en te, null altrament
     * @param l            Informacio de les sessions de laboratori, si en te, null altrament
     * @throws RestriccioIntegritatException si ja existia una assignatura identificada pel mateix nom
     */
    public void crearAssignatura(String nom, int quadrimestre, Teoria t, Laboratori l) throws RestriccioIntegritatException {
        if (assignatures.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix una assignatura amb nom " + nom.toUpperCase());
        }
        assignatures.put(nom, new Assignatura(nom, quadrimestre, t, l));
    }

    /**
     * Permet consultar una assignatura identificada pel seu nom
     *
     * @param nom Nom de l'assignatura
     * @return Assignatura
     * @throws NotFoundException si l'assignatura demanada no existeix
     */
    public Assignatura consultarAssignatura(String nom) throws NotFoundException {
        if (!assignatures.containsKey(nom)) {
            throw new NotFoundException("No s'ha trobat una assignatura amb nom " + nom.toUpperCase());
        }
        return assignatures.get(nom);
    }

    /**
     * Esborra una assignatura amb el nom especificat
     *
     * @param nomA Nom de l'assignatura a borrar
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void esborrarAssignatura(String nomA) throws NotFoundException {
        if (!assignatures.containsKey(nomA)) {
            throw new NotFoundException("No es pot esborrar l'assignatura " + nomA.toUpperCase() + " perque no existeix");
        }
        assignatures.remove(nomA);
    }

    /**
     * Permet modificar la informació sobre les sessions de teoria d'una assignatura
     *
     * @param nom_assig    Nom de l'assignatura
     * @param duracio      Duració de les sessions de teoria
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void modificaInformacioTeoria(String nom_assig, int duracio, int num_sessions) throws NotFoundException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig.toString());
        }
        assignatures.get(nom_assig).setTeoria(num_sessions, duracio);
    }

    /**
     * Permet modificar la informació sobre les sessions de laboratori d'una assignatura
     *
     * @param nom_assig    Nom de l'assignatura
     * @param duracio      Duració de les sessions de laboratori
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void modificaInformacioLaboratori(String nom_assig, int duracio, int num_sessions) throws NotFoundException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig.toString());
        }
        assignatures.get(nom_assig).setLaboratori(num_sessions, duracio);
    }

    /**
     * Permet modificar els grups i subgrups d'una assignatura
     *
     * @param nom_assig nom de l'assignatura
     * @param num_grups numero de grups  de l'assignatura
     * @param grup_cap  capacitat de cada grup
     * @param sgrup_num capacitat dels subgrups
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void modificarGrups(String nom_assig, int num_grups, int grup_cap, int sgrup_num) throws NotFoundException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig.toUpperCase());
        }
        assignatures.get(nom_assig).modificarGrups(num_grups, grup_cap, sgrup_num);
    }

    /**
     * Permet afegir una relacio de correquisits entre dues assignatures
     *
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     * @throws NotFoundException             si no existeix una de les dues assignatures
     * @throws RestriccioIntegritatException si les dues assignatures es diuen igual, son del mateix quadrimestre o ja eren correquists
     */
    public void afegeixCorrequisit(String nom_a, String nom_b) throws NotFoundException, RestriccioIntegritatException {

        if (!assignatures.containsKey(nom_a)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_a.toUpperCase());
        }
        if (!assignatures.containsKey(nom_b)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_b.toUpperCase());
        }

        assignatures.get(nom_a).afegeixCorrequisit(assignatures.get(nom_b));
        assignatures.get(nom_b).afegeixCorrequisit(assignatures.get(nom_a));
    }

    /**
     * Esborra la relació de correqusiits entre dues assignatures
     *
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     * @throws NotFoundException si no existeix alguna de les dues assignatures
     */
    public void esborraCorrequisit(String nom_a, String nom_b) throws NotFoundException {
        if (!assignatures.containsKey(nom_a)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_a.toUpperCase());
        }
        if (!assignatures.containsKey(nom_b)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_b.toUpperCase());
        }
        assignatures.get(nom_a).esborraCorrequisit(assignatures.get(nom_b));
        assignatures.get(nom_b).esborraCorrequisit(assignatures.get(nom_a));
    }


    /******************* AULES ************************/


    // TODO fer excepcions i comentarles, fer alguna funcio mes si cal pel controller
    public void creaAula(String edifici, int planta, int aula, String tipusAula, ArrayList<Assignacio> assignacions) throws RestriccioIntegritatException {
        String nomaula = crearkey(edifici , planta , aula);

        if (!aules.containsKey(nomaula)) {
            aules.put(nomaula, new Aula(edifici, planta, aula, tipusAula, assignacions));
        } else {
            throw new RestriccioIntegritatException("Ja existeix una aula amb nom d'aula " + nomaula.toUpperCase());
        }
    }

    public void esborrarAula(String edifici, int planta, int aula) throws NotFoundException {
        String nomaula = edifici + planta + aula;

        if (aules.containsKey(nomaula)) {
            aules.remove(nomaula);
        } else {
            throw new NotFoundException("No es pot esborrar l'aula " + nomaula + " perque no existeix");
        }
    }

    public void modificarAula() {

    }

    public void consultarAula() {

    }


    /**
     * sobre assignacions
     */



    //TODO quitar a las asignaturas siguientes la posibilidad de que miren los gaps(de dia, hora y aula) que ya se han asignado

    public String fromInt2dia(int dia){
        if(dia == 1) return "Dilluns";
        else if(dia == 2) return "Dimarts";
        else if(dia == 3) return "Dimecres;";
        else if (dia == 4) return "Dijous";
        else if (dia == 5) return "Divendres";

    }

    public boolean comprovarini(int aula, int dia, int hora) {
        if (aula > aules2.size()) {
            return true;
        } else if (dia > 5) {
            return true;
        } else if (hora > 6) {
            return true;
        }
    }



    private ArrayList<Assignatura> assignatures2 = new ArrayList<Assignatura>(assignatures.values()); //TODO arreglar chapuza
    private ArrayList<Aula> aules2 = new ArrayList<Aula>(aules.values()); //TODO arreglar chapuza


    public boolean creaHorari(int i, int dia, int hora, int aula) {

        if(comprovarini(aula,dia,hora)) return false; //això lo que fa es parar la recursivitat per aquesta via perquè no pot comprovar ni per un dissabte, ni per aules ni hores que no existeixen

        if (i == assignatures.size()) {
            return true; //ja que he mirat totes les asssignatures osea que DONE
        }


        else {
            Assignatura assig = assignatures2.get(i); //esta es la asignatura que toca

            if(toca hacer_teoria){
                if (biene) { //comprovar restriciones
                    horari[hora][dia][aula] = new AssignacioT(hora, fromInt2dia(dia), aula, "teoria", );
                    creaHorari(i + 1, 0, 0, 0);//vamos a provar pa la asignatura siguiente

                } else {
                    creaHorari(i, dia + 1, hora, aula);
                    creaHorari(i, dia, hora + 1, aula);
                    creaHorari(i, dia, hora, aula + 1);
                }
            }

            else { //toca los de laboratorio
              if(biene){ //comprovar restricciones
                  horari[hora][dia][aula] =  new AssignacioL(hora, fromInt2dia(dia), aula, "laboratori", );
                  creaHorari(i + 1, 0, 0, 0);//vamos a provar pa la asignatura siguiente
              }
              else{
                  creaHorari(i, dia + 1, hora, aula);
                  creaHorari(i, dia, hora + 1, aula);
                  creaHorari(i, dia, hora, aula + 1);
              }
            }
        }
    }

/**
 * mirar si la duració de la infosessió (de teoria o de pràtica) que és un atribut d'assignatura + la hroa a la que ho volem posar ens passaríem o no
 * en tota la duració de infosessió l'aula estigui lliure, que no es solapin teoria i laboratori durant tota la llarga de la sessió , contador de número de sessions que estem fent
 * sobretot per el backtracking
 */




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
