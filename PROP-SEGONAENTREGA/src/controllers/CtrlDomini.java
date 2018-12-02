/**
 * @author Aina Garcia
 */

package controllers;

import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;
import model.Aula.TipusAula;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDomini {

    private static CtrlDomini ourInstance;
    private HashMap<String, Assignatura> assignatures;
    private HashMap<String, PlaEstudis> plaEstudis;
    private HashMap<String, Aula> aules;
    private Horari horari;

    //TODO pasar a horario + crear equals
    private RestriccioCorrequisit resCorr;
    private RestriccioNivell resNiv;
    private RestriccioAula resAul;
    private RestriccioGrupTeo resTeo;
    private RestriccioSubgrupLab resSub;
    private ArrayList<RestriccioAulaDia> resAulDia;
    private ArrayList<RestriccioAulaHora> resAulaHora;
    private ArrayList<RestriccioAssigMatiTarda> resMatiTarda;

    private CtrlDomini() {
        assignatures = new HashMap<>();
        plaEstudis = new HashMap<>();
        aules = new HashMap<>();

        resCorr = new RestriccioCorrequisit();
        resNiv = new RestriccioNivell();
        resAul = new RestriccioAula();
        resTeo = new RestriccioGrupTeo();
        resSub = new RestriccioSubgrupLab();
        resAulDia = new ArrayList<>();
        resAulaHora = new ArrayList<>();
        resMatiTarda = new ArrayList<>();
    }

    public static CtrlDomini getInstance() {
        if (ourInstance == null) {
            ourInstance = new CtrlDomini();
        }
        return ourInstance;
    }

    public void reload() {
        assignatures = new HashMap<>();
        plaEstudis = new HashMap<>();
        aules = new HashMap<>();
        //horari = new Horari();
    }

    /**
     * Carrega l'informacio sobre aules, assignatures i plans d'estudi desde disc
     */
    public void carrega() throws IOException{ //TODO fer be
        CtrlIO c = CtrlIO.getInstance();

        plaEstudis = c.carregaPlansDEstudi("plaestudistest.json");
        assignatures = c.carregaAssignatures("assigtest.json");
        aules = c.carregaAules("aulestest.json");
    }

    /**
     * Obte una llista amb els noms de tots els plans d'estudi
     * @return llista dels plans d'estudi
     */
    public ArrayList<String> getLlistaPlansEstudis(){
        ArrayList<String> info = new ArrayList<>();
        for (PlaEstudis p : plaEstudis.values()){
            info.add(p.getNomTitulacio());
        }
        info.sort(String::compareToIgnoreCase);
        return info;
    }

    /**
     * Obte una llista amb el nom de totes les assignatures
     * @return llista d'assignatures
     */
    public ArrayList<String> getLlistaAssignatures(){
        ArrayList<String> info = new ArrayList<>();
        for (Assignatura a : assignatures.values()){
            info.add(a.getNom());   //TODO: posar id assig o id o el pla d'estudis al que pertany? parlar-ho
        }
        info.sort(String::compareToIgnoreCase);
        return info;
    }

    /**
     * Obte una llista de totes les aules disponibles
     * @return llista de totes les aules
     */
    public ArrayList<String> getLlistaAules() {
        ArrayList<String> info = new ArrayList<>();
        for (Aula a : aules.values()){
            info.add(a.getKey());
        }
        info.sort(String::compareToIgnoreCase);
        return info;
    }

    /**
     * Crea un nou pla d'estudis no obsolet i sense cap assignatura
     *
     * @param nom Nom del pla d'estudis
     * @param any Any d'inici del nou pla d'estudis
     * @throws RestriccioIntegritatException si ja existia aquell pla d'estudis
     */
    public void crearPlaEstudis(String nom, int any) throws RestriccioIntegritatException {
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
    public void afegirAssignaturaPla(String nomP, String nomA) throws NotFoundException, RestriccioIntegritatException {
        if (!plaEstudis.containsKey(nomP)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        }
        if (!assignatures.containsKey(nomA)) {
            throw new NotFoundException("No s'ha trobat una assignatura amb nom " + nomA.toUpperCase());
        }

        for (PlaEstudis ps : plaEstudis.values()) {
            if (ps.hasAssignatura(nomA)) {
                throw new RestriccioIntegritatException("Ja esta assignada a un pla d'estudis");
            }
        }
        plaEstudis.get(nomP).afegirAssignatura(nomA);
    }

    /**
     * Esborra una assignatura continguda en el pla
     *
     * @param nomP Nom del pla d'estudis
     * @param nomA Nom de l'assignatura
     */
    public void esborrarAssignaturaPla(String nomP, String nomA) throws NotFoundException {
        if (!plaEstudis.containsKey(nomP)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        }
        if (!assignatures.containsKey(nomA)) {
            throw new NotFoundException("No s'ha trobat una assignatura amb nom " + nomA.toUpperCase());
        }
        plaEstudis.get(nomP).esborrarAssignatura(nomA);
    }

    /**
     * Consulta les assignatures d'un pla d'estudis
     *
     * @param nomP Pla d'estudis
     */
    //TODO when calling this func check that the array is not empty
    public ArrayList<String> consultarAssignaturesPlaEstudis(String nomP) throws NotFoundException {
        if (!plaEstudis.containsKey(nomP)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        }
        return plaEstudis.get(nomP).getAssignatures();
    }

    /**
     * Permet crear una nova assignatura
     *
     * @param nom          Nom de l'assignatura
     * @param quadrimestre Quadrimestre al qual pertany
     * @throws RestriccioIntegritatException si ja existia una assignatura identificada pel mateix nom
     */
    public Assignatura crearAssignatura(String nom, int quadrimestre) throws RestriccioIntegritatException {
        if (assignatures.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix una assignatura amb nom " + nom.toUpperCase());
        }
        assignatures.put(nom, new Assignatura(nom, quadrimestre));
        return null;
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

        // borrem les referencies a l'assignatura de tots els llocs
        for (PlaEstudis plaest : plaEstudis.values()) {
            if (plaest.hasAssignatura(nomA)) {
                plaest.esborrarAssignatura(nomA);
            }
        }

        for (Assignatura a : assignatures.values()) {
            if (a.esCorrequisit(nomA)) a.esborraCorrequisit(nomA);
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
    public void modificaInformacioTeoria(String nom_assig, int duracio, int num_sessions, TipusAula tAula) throws NotFoundException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig);
        }
        assignatures.get(nom_assig).setTeoria(num_sessions, duracio, tAula);
    }

    /**
     * Permet modificar la informació sobre les sessions de laboratori d'una assignatura
     *
     * @param nom_assig    Nom de l'assignatura
     * @param duracio      Duració de les sessions de laboratori
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void modificaInformacioLaboratori(String nom_assig, int duracio, int num_sessions, TipusAula tAula) throws NotFoundException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig);
        }
        assignatures.get(nom_assig).setLaboratori(num_sessions, duracio, tAula);
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
        assignatures.get(nom_a).esborraCorrequisit(nom_b);
        assignatures.get(nom_b).esborraCorrequisit(nom_a);
    }

    /**
     * Permet donar d'alta una nova aula al sistema
     *
     * @param edifici   edifici en el que es troba l'aula
     * @param planta    planta a la que es troba l'aula
     * @param aula      numero d'aula dins d'una planta i un edifici
     * @param capacitat numero de persones que caben a l'aula
     * @param tipusAula tipus d'aula, pot ser pcs, normal o laboratori
     * @throws RestriccioIntegritatException quan s'intenta crear una aula ja existent
     */
    public void creaAula(String edifici, int planta, int aula, int capacitat, TipusAula tipusAula) throws RestriccioIntegritatException {
        String nomAula = Aula.crearkey(edifici, planta, aula);

        if (!aules.containsKey(nomAula)) {
            aules.put(nomAula, new Aula(edifici, planta, aula, tipusAula, capacitat));
        } else {
            throw new RestriccioIntegritatException("Ja existeix una aula amb nom d'aula " + nomAula.toUpperCase());
        }
    }

    /**
     * Esborra una aula del sistema
     *
     * @param nomAula nom de l'aula que es vol esborrar
     * @throws NotFoundException quan s'intenta borrar una aula inexistent
     */
    public void esborrarAula(String nomAula) throws NotFoundException {
        if (aules.containsKey(nomAula)) {
            aules.remove(nomAula);
        } else {
            throw new NotFoundException("No es pot esborrar l'aula " + nomAula + " perque no existeix");
        }
    }


    /**
     * Permet modificar una aula
     *
     * @param key       nom i localitzacio de l'aula
     * @param capacitat nova capacitat de l'aula
     * @param tipusAula nou tipus d'aula per l'aula
     * @throws NotFoundException quan es vol modificar una aula inexistent
     */
    public void modificarAula(String key, int capacitat, TipusAula tipusAula) throws NotFoundException {
        if (aules.containsKey(key)) {
            aules.get(key).setCapacitat(capacitat);
            aules.get(key).setTipusAula(tipusAula);
        } else {
            throw new NotFoundException("No es pot modificar l'aula " + key + " perque no existeix");
        }
    }

    /**
     * Permet consultar una aula amb el nom d'aula especificat
     *
     * @param key nom i localitzacio de l'aula
     * @return l'aula corresponent al nom si existeix
     * @throws NotFoundException si no existeix l'aula buscada
     */
    public Aula consultarAula(String key) throws NotFoundException {
        if (!aules.containsKey(key)) {
            throw new NotFoundException("No existeix l'aula especificada");
        } else {
            return aules.get(key);
        }
    }

    /**
     * Crea l'horari mitjançant backtracking
     *
     * @return l'horari complet si s'ha pogut realitzar o buit si no es pot realitzar
     */
    public Horari crearHorari() {
        HashMap<String, Assignatura> assignatures2 = new HashMap<>();
        for (PlaEstudis plaest : plaEstudis.values()) {
            if (!plaest.isObsolet()) {
                ArrayList<String> a = plaest.getAssignatures();
                for(String aux : a) {
                    if (!assignatures2.containsKey(aux) && assignatures.containsKey(aux)) {
                        assignatures2.put(aux, assignatures.get(aux));
                    }
                }
            }
        }
        
        
        Horari newhorari = new Horari(true, assignatures2, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora, resMatiTarda);
        return newhorari;
    }


    /**
     * Crea l'horari mitjançant backtracking i forward checking
     *
     * @return l'horari complet si s'ha pogut realitzar o buit si no es pot realitzar
     */
    public Horari crearHorari2() {
        
        HashMap<String, Assignatura> assignatures2 = new HashMap<>();
        for (PlaEstudis plaest : plaEstudis.values()) {
            if (!plaest.isObsolet()) {
                ArrayList<String> a = plaest.getAssignatures();
                for(String aux : a) {
                    if (!assignatures2.containsKey(aux) && assignatures.containsKey(aux)) {
                        assignatures2.put(aux, assignatures.get(aux));
                    }
                }
            }
        }
        
        Horari newhorari = new Horari(false, assignatures2, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora, resMatiTarda);
        return newhorari;
    }


    /**
     * Borra la restricció aula dia corresponent
     * @param res la restricció aula dia que hem de borrar
     */
    public void borrar_restriccio_aula_dia(RestriccioAulaDia res) {
        resAulDia.remove(res);
    }

    /**
     * Borra la restricció aula hora corresponent
     * @param res la restricció aula hora que hem de borrar
     */
    public void borrar_restriccio_aula_hora(RestriccioAulaHora res) {
        resAulaHora.remove(res);
    }

    /**
     * Afegir la restricció aula dia corresponent
     * @param res la restricció aula dia que afegeix
     */
    public void afegir_restriccio_aula_dia(RestriccioAulaDia res){
        resAulDia.add(res);
    }


    /**
     * Afegir la restricció aula hora corresponent
     * @param res la restricció aula hora que afegeix
     */
    public void afegir_restriccio_aula_hora(RestriccioAulaDia res){
        resAulDia.add(res);
    }
}
