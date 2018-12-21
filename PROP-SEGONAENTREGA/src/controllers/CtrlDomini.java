/**
 * @author Aina Garcia
 */

package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;
import model.Aula.TipusAula;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDomini {

    private static CtrlDomini ourInstance;
    private CtrlSerDes cIo;
    private HashMap<String, Assignatura> assignatures;
    private HashMap<String, PlaEstudis> plaEstudis;
    private HashMap<String, Aula> aules;
    private Horari horari;

    private CtrlDomini() {
        assignatures = new HashMap<>();
        plaEstudis = new HashMap<>();
        aules = new HashMap<>();
        horari = new Horari();
        cIo = CtrlSerDes.getInstance();
    }

    public static CtrlDomini getInstance() {
        if (ourInstance == null) {
            ourInstance = new CtrlDomini();
        }
        return ourInstance;
    }

    /**
     * Reinicia els valors dels parametres interns
     */
    public void reload() {
        assignatures = new HashMap<>();
        plaEstudis = new HashMap<>();
        aules = new HashMap<>();
    }

    /**
     * Carrega l'informacio sobre aules, assignatures i plans d'estudi desde disc
     */
    public boolean carrega() throws IOException { //TODO fer be
        CtrlSerDes c = CtrlSerDes.getInstance();

        boolean defaultExists = c.comprovaDefaultFilepath();
        if (defaultExists) {
            plaEstudis = c.carregaPlansDEstudi();
            assignatures = c.carregaAssignatures();
            aules = c.carregaAules();
        }
        return defaultExists;
    }

    /**
     * Obte una llista amb els noms de tots els plans d'estudi
     *
     * @return llista dels plans d'estudi
     */
    public ArrayList<String> getLlistaPlansEstudis() {
        ArrayList<String> info = new ArrayList<>();
        for (PlaEstudis p : plaEstudis.values()) {
            info.add(p.getNomTitulacio());
        }
        info.sort(String::compareToIgnoreCase);
        return info;
    }

    /**
     * Obte una llista amb el nom de totes les assignatures
     *
     * @return llista d'assignatures
     */
    public ArrayList<String> getLlistaAssignatures() {
        ArrayList<String> info = new ArrayList<>();
        for (Assignatura a : assignatures.values()) {
            info.add(a.getNom());   //TODO: posar id assig o id o el pla d'estudis al que pertany? parlar-ho
        }
        info.sort(String::compareToIgnoreCase);
        return info;
    }

    /**
     * Obte una llista de totes les aules disponibles
     *
     * @return llista de totes les aules
     */
    public ArrayList<String> getLlistaAules() {
        ArrayList<String> info = new ArrayList<>();
        for (Aula a : aules.values()) {
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
    public void crearPlaEstudis(String nom, int any, String descripcio) throws RestriccioIntegritatException, IOException {
        if (plaEstudis.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix un pla d'estudis amb nom " + nom.toUpperCase());
        }
        plaEstudis.put(nom, new PlaEstudis(nom, any, descripcio));
        cIo.guardaPlaDEstudis(plaEstudis);
    }

    /**
     * Genera una llista de les assignatures candidates a ser correquisit de l'especificada, es a dir, formen part del mateix pla d'estudis, no son ella mateixa i formen part del mateix quadrimestre
     *
     * @param nomAssig nom de l'assignatura de la qual volem generar la llista de correquisits
     * @return llista de les possibles assignatures correquisit
     */
    public ArrayList<String> correquisitsPossibles(String nomAssig) {
        ArrayList<String> assignatures_candidates = new ArrayList<>();
        for (PlaEstudis p : plaEstudis.values()) {
            if (p.hasAssignatura(nomAssig)) {
                for (String assig : p.getAssignatures()) {
                    Assignatura aux = assignatures.get(assig);
                    //si tenen mateix quadrimestre i formen part del mateix pla i no son ja correquisits
                    if (aux.getQuadrimestre() == assignatures.get(nomAssig).getQuadrimestre()
                            && !aux.getNom().equalsIgnoreCase(nomAssig) && !aux.esCorrequisit(nomAssig)) {
                        assignatures_candidates.add(aux.getNom());
                    }
                }
            }
        }
        return assignatures_candidates;
    }

    //todo de moment no te en compte restriccions i tenim codi duplicat

    /**
     * Genera un horari amb les restriccions especificades i el retorna per tal de ser mostrat per pantalla
     *
     * @return Horari generat si es pot, null altrament
     */
    public String generaHorari(ArrayList<HashMap<String, String>> rmt, ArrayList<HashMap<String, String>> rdah, ArrayList<HashMap<String, String>> rad, boolean rc, boolean rgt) {
        String json = null;
        horari = new Horari();

        for (HashMap<String, String> res1 : rmt) {
            String assignatura = res1.get("assignatura");
            //todo mirad las funciones de handleAdd* de CtrlHorariView para ver como esta definido
            horari.afegirRMT(assignatura, res1.get("matitarda").equalsIgnoreCase("Mati") );
        }

        /*
        horari.activaRestriccio...(bool)
        horari.activaRestriccio2...(bool) //todo restriccio 2
         */

        for (HashMap<String, String> res2 : rad) {
            int dia = Algorismes.fromDia2int(res2.get("dia"));
            Aula aula = aules.get(res2.get("aula"));
            horari.afegirRD(dia, aula);
        }
        for (HashMap<String, String> res3 : rdah) {
            int dia = Algorismes.fromDia2int(res3.get("dia"));
            Aula aula = aules.get(res3.get("aula"));
            int hora = Integer.parseInt(res3.get("hora"));         //esta pasat de 8 a 19 o de 0 a 10 ??
            horari.afegirRDH(hora, dia, aula);
        }
        horari.activaRC(rc);
        //volem incloure nomes les assignatures de plans no obsolets i que estiguin en algun pla d'estudis vigent
        HashMap<String, Assignatura> ass = new HashMap<>();
        for (PlaEstudis plaest : plaEstudis.values()) {
            if (!plaest.isObsolet()) {
                ArrayList<String> a = plaest.getAssignatures();
                for (String aux : a) {
                    if (!ass.containsKey(aux) && assignatures.containsKey(aux)) {
                        ass.put(aux, assignatures.get(aux));
                    }
                }
            }
        }

        boolean b = horari.ConstruirHorari(ass, aules);
        if (b) {
            json = cIo.horariToJson(horari);
            System.out.println("horari fet");
        } else System.out.println("Horari no sha podido hacer");
        System.out.println(json);
        return json;
    }

    /**
     * Esborra del sistema un pla d'estudis OBSOLET
     *
     * @param nom Nom del pla d'estudis
     * @throws NotFoundException             si no existeix el pla d'estudis especificat
     * @throws RestriccioIntegritatException si el pla d'estudis no està obsolet
     */
    public void esborrarPlaEstudis(String nom) throws NotFoundException, RestriccioIntegritatException, IOException {
        if (!plaEstudis.containsKey(nom)) {
            throw new NotFoundException("No s'ha trobat el pla d'estudis " + nom.toUpperCase());
        }
        if (!plaEstudis.get(nom).isObsolet()) {
            throw new RestriccioIntegritatException("No es pot esborrar un pla d'estudis no obsolet");
        }
        plaEstudis.remove(nom);
        cIo.guardaPlaDEstudis(plaEstudis);
    }

    /**
     * Marca un pla d'estudis com a obsolet
     *
     * @param nom     nom del pla d'estudis
     * @param obsolet cert si volem marcar-lo com a obsolet, fals altrament
     * @throws NotFoundException si no es troba el pla d'estudis especificat
     */
    public void setObsolet(String nom, boolean obsolet) throws NotFoundException, IOException {
        if (!plaEstudis.containsKey(nom)) {
            throw new NotFoundException("No s'ha trobat el pla d'estudis " + nom.toUpperCase());
        }
        plaEstudis.get(nom).setObsolet(obsolet);
        cIo.guardaPlaDEstudis(plaEstudis);
    }

    /**
     * Consulta tota la informació d'un pla d'estudis
     *
     * @param nom Nom del pla d'estudis
     * @return tota la informació del pla d'estudis i les seves relacions
     * @throws NotFoundException si no existex el pla d'estudis especificat
     */
    public String consultarPlaEsudis(String nom) throws NotFoundException {
        if (!plaEstudis.containsKey(nom)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nom.toUpperCase());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(plaEstudis.get(nom));
        return json;
    }

    /**
     * Afegeix una assignatura anteriorment creada al pla d'estudis indicat
     *
     * @param nomP Nom del pla d'estudis
     * @param nomA Nom de l'assignatura
     */
    public void afegirAssignaturaPla(String nomP, String nomA) throws NotFoundException, RestriccioIntegritatException, IOException {
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
        cIo.guardaPlaDEstudis(plaEstudis);
    }

    /**
     * Esborra una assignatura continguda en el pla
     *
     * @param nomP Nom del pla d'estudis
     * @param nomA Nom de l'assignatura
     */
    public void esborrarAssignaturaPla(String nomP, String nomA) throws NotFoundException, IOException {
        if (!plaEstudis.containsKey(nomP)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        }
        if (!assignatures.containsKey(nomA)) {
            throw new NotFoundException("No s'ha trobat una assignatura amb nom " + nomA.toUpperCase());
        }
        //esborro tots els correquisits bijectivament
        for (String assignatura : plaEstudis.get(nomP).getAssignatures()) {
            if (assignatures.get(assignatura).esCorrequisit(nomA)) {
                esborraCorrequisit(nomA, assignatura);
            }
        }
        //esborro l'assignatura del pla
        plaEstudis.get(nomP).esborrarAssignatura(nomA);
        cIo.guardaPlaDEstudis(plaEstudis);
        cIo.guardaAssignatures(assignatures);
    }

    /**
     * Consulta les assignatures d'un pla d'estudis
     *
     * @param nomP Pla d'estudis
     */
    public ArrayList<String> consultarAssignaturesPlaEstudis(String nomP) throws NotFoundException {
        if (plaEstudis.isEmpty())
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        else if (!plaEstudis.containsKey(nomP)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        }
        return plaEstudis.get(nomP).getAssignatures();
    }

    /**
     * Consulta les assignatures d'un quadrimestre i un pla d'estudis concrets
     *
     * @param nomP         Nom del pla d'estudis
     * @param quadrimestre número del quadrimestre
     * @return llista d'assignatures del pla i quadrimestre si n'hi ha
     * @throws NotFoundException si no troba un pla d'estudis amb el nom especificat
     */
    public ArrayList<String> consultarAssigPlaEstQuadri(String nomP, int quadrimestre) throws NotFoundException {
        if (plaEstudis.isEmpty())
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        else if (!plaEstudis.containsKey(nomP)) {
            throw new NotFoundException("No existeix un pla d'estudis amb nom " + nomP.toUpperCase());
        }
        ArrayList<String> assigPla = plaEstudis.get(nomP).getAssignatures();
        ArrayList<String> output = new ArrayList<>();
        for (String assig : assigPla) {
            if (assignatures.get(assig).getQuadrimestre() == quadrimestre) {
                output.add(assig);
            }
        }
        return output;
    }

    /**
     * Retorna el nom del pla d'estudis que conte una assignatura concreta
     *
     * @param nomAssig nom de l'assignatura
     * @return nom del pla d'estudis que la conté
     */
    public String getPlaEstudisContains(String nomAssig) {
        for (PlaEstudis plaest : plaEstudis.values()) {
            if (plaest.hasAssignatura(nomAssig)) {
                return plaest.getNomTitulacio();
            }
        }
        return "";
    }

    /**
     * Permet crear una nova assignatura
     *
     * @param nom          Nom de l'assignatura
     * @param quadrimestre Quadrimestre al qual pertany
     * @param abr          Abreviació del nom de l'assignatura
     * @throws RestriccioIntegritatException si ja existia una assignatura identificada pel mateix nom
     */
    public void crearAssignatura(String nom, int quadrimestre, String descripcio, String abr) throws RestriccioIntegritatException, IOException {
        if (assignatures.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix una assignatura amb nom " + nom.toUpperCase());
        }
        assignatures.put(nom, new Assignatura(nom, abr, descripcio, quadrimestre));
        cIo.guardaAssignatures(assignatures);
    }

    /**
     * Permet consultar una assignatura identificada pel seu nom
     *
     * @param nom Nom de l'assignatura
     * @return Assignatura
     * @throws NotFoundException si l'assignatura demanada no existeix
     */
    public String consultarAssignatura(String nom) throws NotFoundException {
        if (!assignatures.containsKey(nom)) {
            throw new NotFoundException("No s'ha trobat una assignatura amb nom " + nom.toUpperCase());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(assignatures.get(nom));
        return json;
    }

    /**
     * Obté l'abreviació de l'assignatura a partir del nom
     *
     * @param nom Nom de l'assignatura
     * @return Abreviació
     * @throws NotFoundException Si no troba l'assignatura
     */
    public String obtenirAbreviacioAssig(String nom) throws NotFoundException {
        if (!assignatures.containsKey(nom)) {
            throw new NotFoundException("No s'ha trobat una assignatura amb nom " + nom.toUpperCase());
        }
        return assignatures.get(nom).getAbr();
    }

    /**
     * Obté l'assignatura a partir de l'abreviació
     *
     * @param abbvr Abreviacio de l'assignatura
     * @return Nom
     * @throws NotFoundException Si no troba l'assignatura
     */
    public String obtenirAssigAbreviacio(String abbvr) throws NotFoundException {
        for (Assignatura a : assignatures.values()) {
            if (a.getAbr().equalsIgnoreCase(abbvr)) {
                return a.getNom();
            }
        }
        throw new NotFoundException("No s'ha trobat una assignatura amb abreviació " + abbvr);
    }

    /**
     * Esborra una assignatura amb el nom especificat
     *
     * @param nomA Nom de l'assignatura a borrar
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void esborrarAssignatura(String nomA) throws NotFoundException, IOException {
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
        cIo.guardaAssignatures(assignatures);
        cIo.guardaPlaDEstudis(plaEstudis);
    }

    /**
     * Permet modificar la informació sobre les sessions de teoria d'una assignatura
     *
     * @param nom_assig    Nom de l'assignatura
     * @param duracio      Duració de les sessions de teoria
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void modificaInformacioTeoria(String nom_assig, int duracio, int num_sessions, String tAula) throws NotFoundException, IOException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig);
        }
        assignatures.get(nom_assig).setTeoria(num_sessions, duracio, Aula.stringToTipusAula(tAula));
        cIo.guardaAssignatures(assignatures);
    }

    /**
     * Permet modificar la informació sobre les sessions de laboratori d'una assignatura
     *
     * @param nom_assig    Nom de l'assignatura
     * @param duracio      Duració de les sessions de laboratori
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void modificaInformacioLaboratori(String nom_assig, int duracio, int num_sessions, String tAula) throws NotFoundException, IOException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig);
        }
        assignatures.get(nom_assig).setLaboratori(num_sessions, duracio, Aula.stringToTipusAula(tAula));
        cIo.guardaAssignatures(assignatures);
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
    public void modificarGrups(String nom_assig, int num_grups, int grup_cap, int sgrup_num) throws NotFoundException, IOException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig.toUpperCase());
        }
        assignatures.get(nom_assig).modificarGrups(num_grups, grup_cap, sgrup_num);
        cIo.guardaAssignatures(assignatures);
    }

    /**
     * Permet afegir una relacio de correquisits entre dues assignatures
     *
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     * @throws NotFoundException             si no existeix una de les dues assignatures
     * @throws RestriccioIntegritatException si les dues assignatures es diuen igual, son del mateix quadrimestre o ja eren correquists
     */
    public void afegeixCorrequisit(String nom_a, String nom_b) throws NotFoundException, RestriccioIntegritatException, IOException {

        if (!assignatures.containsKey(nom_a)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_a.toUpperCase());
        }
        if (!assignatures.containsKey(nom_b)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_b.toUpperCase());
        }

        assignatures.get(nom_a).afegeixCorrequisit(assignatures.get(nom_b));
        assignatures.get(nom_b).afegeixCorrequisit(assignatures.get(nom_a));
        cIo.guardaAssignatures(assignatures);
    }

    /**
     * Esborra la relació de correqusiits entre dues assignatures
     *
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     * @throws NotFoundException si no existeix alguna de les dues assignatures
     */
    private void esborraCorrequisit(String nom_a, String nom_b) throws NotFoundException, IOException {
        if (!assignatures.containsKey(nom_a)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_a.toUpperCase());
        }
        if (!assignatures.containsKey(nom_b)) {
            throw new NotFoundException("No existeix l'assignatura " + nom_b.toUpperCase());
        }
        assignatures.get(nom_a).esborraCorrequisit(nom_b);
        assignatures.get(nom_b).esborraCorrequisit(nom_a);
        cIo.guardaAssignatures(assignatures);
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
    public void creaAula(String edifici, int planta, int aula, int capacitat, String tipusAula) throws RestriccioIntegritatException, IOException {
        Aula.TipusAula ta = Aula.stringToTipusAula(tipusAula);
        String nomAula = Aula.crearkey(edifici, planta, aula);

        if (!aules.containsKey(nomAula.toUpperCase())) {
            aules.put(nomAula.toUpperCase(), new Aula(edifici, planta, aula, ta, capacitat));
        } else {
            throw new RestriccioIntegritatException("Ja existeix una aula amb nom d'aula " + nomAula.toUpperCase());
        }
        cIo.guardaAules(aules);
    }

    /**
     * Esborra una aula del sistema
     *
     * @param nomAula nom de l'aula que es vol esborrar
     * @throws NotFoundException quan s'intenta borrar una aula inexistent
     */
    public void esborrarAula(String nomAula) throws NotFoundException, IOException {
        if (aules.containsKey(nomAula.toUpperCase())) {
            aules.remove(nomAula.toUpperCase());
        } else {
            throw new NotFoundException("No es pot esborrar l'aula " + nomAula + " perque no existeix");
        }
        cIo.guardaAules(aules);
    }

    /**
     * Permet modificar una aula
     *
     * @param key       nom i localitzacio de l'aula
     * @param capacitat nova capacitat de l'aula
     * @param tipusAula nou tipus d'aula per l'aula
     * @throws NotFoundException quan es vol modificar una aula inexistent
     */
    public void modificarAula(String key, int capacitat, String tipusAula) throws NotFoundException, IOException {
        TipusAula ta = Aula.stringToTipusAula(tipusAula);

        if (aules.containsKey(key.toUpperCase())) {
            aules.get(key.toUpperCase()).setCapacitat(capacitat);
            aules.get(key.toUpperCase()).setTipusAula(ta);
        } else {
            throw new NotFoundException("No es pot modificar l'aula " + key + " perque no existeix");
        }
        cIo.guardaAules(aules);
    }

    /**
     * Permet consultar una aula amb el nom d'aula especificat
     *
     * @param key nom i localitzacio de l'aula
     * @return l'aula corresponent al nom si existeix
     * @throws NotFoundException si no existeix l'aula buscada
     */
    public String consultarAula(String key) throws NotFoundException {
        if (!aules.containsKey(key.toUpperCase())) {
            throw new NotFoundException("No existeix l'aula especificada");
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(aules.get(key));
            return json;
        }
    }

    /**
     * Genera una llista de les assignatures que poden ser afegides a un pla d'estudis perquè no estan assignades a cap
     *
     * @return llista de les assignatures lliures
     */
    public ArrayList<String> consultarAssignaturesLliures() {
        ArrayList<String> possibles = new ArrayList<>();
        for (String a : assignatures.keySet()) {
            boolean trobat = false;
            for (PlaEstudis p : plaEstudis.values()) {
                if (p.hasAssignatura(a)) trobat = true;
            }
            if (!trobat) possibles.add(a);
        }
        return possibles;
    }


    /**
     * Intenta intercanviar dos slots horaris segons les restriccions anteriorment donades d'alta
     *
     * @param slot1 informació sobre el primer slot, assignatura, aula, dia, hora i grup
     * @param slot2 idem pel segon slot
     * @return cert si s'ha fet el canvi, fals altrament
     */

    public boolean intercanviaSlots(HashMap<String, String> slot1, HashMap<String, String> slot2) {
        Assignacio a1 = null, a2 = null;
        if (!slot1.containsKey("grup")) {
            a1 = new AssignacioT(Algorismes.fromInt2dia(Integer.parseInt(slot1.get("dia"))), Integer.parseInt(slot1.get("hora")), aules.get(slot1.get("aula")), null, null);
        } else {
            if (Integer.parseInt(slot1.get("grup")) % 10 == 0) {
                try {
                    a1 = new AssignacioT(slot1.get("dia"), Integer.parseInt(slot1.get("hora")), aules.get(slot1.get("aula")), assignatures.get(slot1.get("assignatura")), assignatures.get(slot1.get("assignatura")).getGrup(Integer.parseInt(slot1.get("grup"))));
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    a1 = new AssignacioL(slot1.get("dia"), Integer.parseInt(slot1.get("hora")), aules.get(slot1.get("aula")), assignatures.get(slot1.get("assignatura")), assignatures.get(slot1.get("assignatura")).getGrup((Integer.parseInt(slot1.get("grup")) / 10 * 10)).getSubgrups().get(Integer.parseInt(slot1.get("grup"))));
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!slot2.containsKey("grup")) {
            a2 = new AssignacioT(slot1.get("dia"), Integer.parseInt(slot2.get("hora")), aules.get(slot2.get("aula")), null, null);
        } else {
            if (Integer.parseInt(slot2.get("grup")) % 10 == 0) {
                try {
                    a2 = new AssignacioT(slot2.get("dia"), Integer.parseInt(slot2.get("hora")), aules.get(slot2.get("aula")), assignatures.get(slot2.get("assignatura")), assignatures.get(slot2.get("assignatura")).getGrup(Integer.parseInt(slot2.get("grup"))));
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    a2 = new AssignacioL(slot2.get("dia"), Integer.parseInt(slot2.get("hora")), aules.get(slot2.get("aula")), assignatures.get(slot2.get("assignatura")), assignatures.get(slot2.get("assignatura")).getGrup((Integer.parseInt(slot2.get("grup")) / 10 * 10)).getSubgrups().get(Integer.parseInt(slot2.get("grup"))));
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            return horari.intercanviaSlots(a1, a2, new ArrayList<Aula> (aules.values()));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Notifica a la capa de dades quin es el directori arrel que l'usuari ha decidit fer servir per carregar les dades de disc
     *
     * @param dir nom del directori
     */
    public void setDataDirectory(File dir) {
        cIo.setDataDirectory(dir);
    }

    /**
     * Busca en el directori especificat anteriorment com a arrel els arxius json necessaris per la correcta inicialització de disc
     *
     * @return valor informatiu sobre quins arxius han estat carregats i quins no
     * @throws IOException si hi ha problemes de lectura
     */
    public int carregaBusca() throws IOException {
        int res = cIo.buscaData();
        if (res != 0) {
            if (res % 3 == 0) assignatures = cIo.carregaAssignatures();
            if (res % 5 == 0) aules = cIo.carregaAules();
            if (res % 7 == 0) plaEstudis = cIo.carregaPlansDEstudi();
        } else {
            cIo.setDefaultPaths();
        }
        return res;
    }

    /**
     * Permet llegir un horari desde qualsevol punt del sistema de fitxers, no només desde el workspace
     *
     * @param filepath cami al fitxer
     * @return informació de l'horari llegit
     * @throws IOException si hi ha hagut algun error de lectura
     */
    public String llegeixHorari(String filepath) throws IOException {
        return cIo.carregaHorari(filepath);
    }

    /**
     * Permet guardar un horari a qualsevol punt del sistema de fitxers, no només desde el workspace
     *
     * @param content  L'horari en format json
     * @param filepath cami al fitxer
     * @throws IOException si hi ha hagut algun error de escriptura
     */
    public void escriuHorari(String content, String filepath) throws IOException {
        cIo.guardaHorari(content, filepath);
    }
}
