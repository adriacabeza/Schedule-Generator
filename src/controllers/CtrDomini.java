package controllers;

import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CtrDomini {

    private static CtrDomini ourInstance = new CtrDomini();

    public static CtrDomini getInstance() {
        return ourInstance;
    }


    private HashMap<String, Assignatura> assignatures;
    private HashMap<String, PlaEstudis> plaEstudis;
    private HashMap<String, Aula> aules;
    private Assignacio[][][] horari;
    private Restriccions r;


    private String crearkey(String edifici, int planta, int aula) {
        return edifici + String.valueOf(planta) + String.valueOf(aula);
    } //TODO there should be a decode key function too


    private CtrDomini() {
        assignatures = new HashMap<String, Assignatura>();
        plaEstudis = new HashMap<String, PlaEstudis>();
        aules = new HashMap<String, Aula>();
        horari = new Assignacio[12][5][aules.size()];
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
    public ArrayList<Assignatura> consultarAssignaturesPlaEstudis(String nomP) {
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
    public void creaAula(String edifici, int planta, int aula, Aula.TipusAula tipusAula, ArrayList<Assignacio> assignacions) throws RestriccioIntegritatException {
        String nomaula = crearkey(edifici, planta, aula);

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

    public void modificarAula(Aula a, int capacitat, String edifici, int planta, int aula, Aula.TipusAula tipusAula, ArrayList<Assignacio> assignacions) {
        a.setEdifici(edifici);
        a.setAssignacions(assignacions);
        a.setAula(aula);
        a.setPlanta(planta);
        a.setTipusAula(tipusAula);
        a.setCapacitat(capacitat);
    }

    public void consultarAula(String edifici, int planta, int aula) throws NotFoundException {
        String key = crearkey(edifici,planta,aula);
        if (!aules.containsKey(key)) {
            throw new NotFoundException("No existeix l'aula especificada");
        } else {
            aules.get(key);
        }
    }


    /**
     * sobre assignacions
     */


    //TODO quitar a las asignaturas siguientes la posibilidad de que miren los gaps(de dia, hora y aula) que ya se han asignado
    public String fromInt2dia(int dia) {
        if (dia == 0) return "Dilluns";
        else if (dia == 1) return "Dimarts";
        else if (dia == 2) return "Dimecres;";
        else if (dia == 3) return "Dijous";
        else if (dia == 4) return "Divendres";

    }

    public boolean comprovarini(int aula, int dia, int hora) {
        if (aula > aules2.size()) {
            return true;
        } else if (dia > 4) {
            return true;
        } else if (hora > 11) {
            return true;
        }
    }


    private ArrayList<Assignatura> assignatures2 = new ArrayList<Assignatura>(assignatures.values()); //TODO arreglar chapuza
    private ArrayList<Aula> aules2 = new ArrayList<Aula>(aules.values()); //TODO arreglar chapuza
    private ArrayList<AssignaturaMonosessio> mishmash = mishmash(assignatures2); //TODO arreglar chapuza

    public boolean comprovar_restricciones_teoria(int aula1, Grup grup, int dia, int hora, Assignatura assig) throws NotFoundException {
        Aula aula = aules2.get(aula1);

        if (aula.getCapacitat() < grup.getCapacitat())
            return false; //restricció que mira si la capacitat és la adequada

        for (int i = 0; i < aules.size(); ++i) {
            if (horari[hora][dia][i].getAssignatura().getCorrequisits().contains(assig)) return false;
        } //restricció que mira si ja està posada una assignatura correquisit en aquesta hora


        for (int i = 0; i < aules.size(); ++i) {
            if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getQuadrimestre()) return false;
        } //restricció que mira si ja està posat una assignatura del mateix nivell


/*
        if (aula.getTipusAula() == "laboratori") return false; TONI DICE QUE NO
*/


        return true;
    }


    public boolean comprovar_restricciones_lab(int aula1, Subgrup subgrup, int dia, int hora, Assignatura assig) throws NotFoundException {
        Aula aula = aules2.get(aula1);

        if (aula.getCapacitat() < subgrup.getCapacitat())
            return false; //restricció que mira si la capacitat és la adequada


        //AQUÍ SE COMPLICAN LAS RESTRICCIONES
        /*for (int i = 0; i < aules.size(); ++i) {
            if (horari[hora][dia][i].getAssignatura().getCorrequisits().contains(assig)) return false;
        }
            if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getQuadrimestre()) return false;
        }

        for(int i = 0; i< aules.size(); ++i) {
            if (horari[hora][dia][i].getAssignatura() == assig && horari[hora][dia][i].getAula().getTipusAula() == "teoria")
        }*/


        if (aula.getTipusAula() == "teoria") return false;

        return true;
    }


    public boolean creaHorari(int i, int dia, int hora, int aula, int grup, int subgrup) throws NotFoundException {

        if (comprovarini(aula, dia, hora))
            return false; //això lo que fa es parar la recursivitat per aquesta via perquè no pot comprovar ni per un dissabte, ni per aules ni hores que no existeixen

        if (i == mishmash.size()) {
            return true; //ja que he mirat totes les asssignatures osea que DONE
        } else {
            Assignatura assig = assignatures2.get(i); //esta es la asignatura que toca
            if (mishmash.get(i).getAssig().get) { //saber si es teoria o no
                Grup grup1 = mishmash.get(i).getAssig().getGrup(grup);
                if (comprovar_restricciones_teoria(aula, grup1, dia, hora, assig)) {
                    horari[hora][dia][aula] = new AssignacioT(fromInt2dia(dia), hora, aules2.get(aula), "teoria", assig, grup1);
                    if (grup == assig.getGrups().size()) //comprovar si ja és l'últim grup de l'assignatura
                        creaHorari(i + 1, 0, 0, 0, 0, 0);//vamos a provar pa la asignatura siguiente
                    else creaHorari(i, 0, 0, 0, grup + 10, subgrup); //vamos a buscarle sitio al siguiente grupo

                } else {
                    boolean b = creaHorari(i, dia + 1, hora, aula, grup, subgrup); //voy a provar para el siguiente dia
                    if (!b) {
                        boolean b1 = creaHorari(i, dia, hora + 1, aula, grup, subgrup); //voy a provar para el siguiente hora
                        if (!b1) {
                            boolean b2 = creaHorari(i, dia, hora, aula + 1, grup, subgrup); //voy a provar para el siguiente aula
                            if (b2) return false; //no se puede hacer el horario de ninguna manera
                        }

                    }
                }
            } else {
                Grup grup1 = assig.getGrup(grup);
                Subgrup subgrup1 = grup1.getSubgrups().get(subgrup);
                if (comprovar_restricciones_lab(aula, subgrup1, dia, hora, assig)) { //comprovar restricciones
                    horari[hora][dia][aula] = new AssignacioL(fromInt2dia(dia), hora, aules2.get(aula), "laboratori", assig, subgrup1);
                    if (subgrup == mishmash.get(i).getAssig().getSubgrups(grup).size())
                        creaHorari(i + 1, 0, 0, 0, 0, 0);//vamos a provar pa la asignatura siguiente
                    else creaHorari(i, 0, 0, 0, grup, subgrup + 1);
                } else {
                    boolean b = creaHorari(i, dia + 1, hora, aula, grup, subgrup); //voy a provar para el siguiente dia
                    if (!b) {
                        boolean b1 = creaHorari(i, dia, hora + 1, aula, grup, subgrup); //voy a provar para el siguiente hora
                        if (!b1) {
                            boolean b2 = creaHorari(i, dia, hora, aula + 1, grup, subgrup); //voy a provar para el siguiente aula
                            if (b2) return false; //no se puede hacer el horario de ninguna manera
                        }
                    }
                }
            }
        }
        return false; //en teoria no tendría que llegar nunca aquí
    }


    public ArrayList<AssignaturaMonosessio> mishmash(ArrayList<Assignatura> assignatures2) {
        ArrayList<AssignaturaMonosessio> res = new ArrayList<>();
        Teoria auxteo;
        Laboratori auxlab;
        int sesteo, seslab, valor;
        for (Assignatura a : assignatures2) {
            auxlab = a.getLaboratori();     //TODO: mirar com fer per a que no es throwee exception en cas de null, maybe alguna fucnio per poder mirar abans?
            seslab = auxlab.getNumSessions();
            auxlab.setNumSessions(1);
            auxteo = a.getTeoria();         //TODO: concretar que significa un valor de 1 a sessions i la possibilitat de un valor 0.
            sesteo = auxteo.getNumSessions();
            auxteo.setNumSessions(1);
            valor = 8;                      //TODO: hauristica a assignar
            for (int i = 0; i < seslab; ++i) {
                res.add(new AssignaturaMonosessio(a, auxlab, valor));
                valor /= 2;
            }
            valor = 8;
            for (int i = 0; i < sesteo; ++i) {
                res.add(new AssignaturaMonosessio(a, auxteo, valor));
                valor /= 2;
            }
        }
    }
