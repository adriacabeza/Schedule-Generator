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
    public void crearPlaEstudis(String nom, int any, String descripcio) throws RestriccioIntegritatException {
        if (plaEstudis.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix un pla d'estudis amb nom " + nom.toUpperCase());
        }
        plaEstudis.put(nom, new PlaEstudis(nom, any, descripcio));
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

        /*
        for(HashMap<String, String> res1 : rmt){
            String assignatura = rmt.get("assignatura");
            //boolean
             horari.afegeixRMT(assignatura, matitarda);
        }
        loop:
            String assignatura = rmt.get("assignatura");
            String matitarda = rmt.get("matitarda"); //passar a boolean
                                                       //but mati esta representat com mati? I mean si faig rmt.get("matitarda").equals("mati") isok?
            horari.afegeixRMT(assignatura, matitarda);
        end;

        horari.activaRestriccio...(bool)
        horari.activaRestriccio2...(bool)

         */
        //TODO mes abaix en les consultes tenim funcions semblants a les que tenim el algorisme, es necesari tenirles alla i aqui?
        for(HashMap<String, String> res2 : rad){
            int dia = Algorismes.fromDia2int(res2.get("dia"));
            Aula aula = aules.get(res2.get("aula"));
            horari.afegirRD(dia, aula);
        }
        for(HashMap<String, String> res3 : rdah){
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
        if (b) json = cIo.horariToJson(horari);
        return json;
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
     * Marca un pla d'estudis com a obsolet
     *
     * @param nom     nom del pla d'estudis
     * @param obsolet cert si volem marcar-lo com a obsolet, fals altrament
     * @throws NotFoundException si no es troba el pla d'estudis especificat
     */
    public void setObsolet(String nom, boolean obsolet) throws NotFoundException {
        if (!plaEstudis.containsKey(nom)) {
            throw new NotFoundException("No s'ha trobat el pla d'estudis " + nom.toUpperCase());
        }
        plaEstudis.get(nom).setObsolet(obsolet);
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
        //esborro tots els correquisits bijectivament
        for (String assignatura : plaEstudis.get(nomP).getAssignatures()) {
            if (assignatures.get(assignatura).esCorrequisit(nomA)) {
                esborraCorrequisit(nomA, assignatura);
            }
        }
        //esborro l'assignatura del pla
        plaEstudis.get(nomP).esborrarAssignatura(nomA);
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
     * @param nomP
     * @param quadrimestre
     * @return
     * @throws NotFoundException
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
    public void crearAssignatura(String nom, int quadrimestre, String descripcio, String abr) throws RestriccioIntegritatException {
        if (assignatures.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix una assignatura amb nom " + nom.toUpperCase());
        }
        assignatures.put(nom, new Assignatura(nom, abr, descripcio, quadrimestre));
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
    public void modificaInformacioTeoria(String nom_assig, int duracio, int num_sessions, String tAula) throws NotFoundException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig);
        }
        assignatures.get(nom_assig).setTeoria(num_sessions, duracio, Aula.stringToTipusAula(tAula));
    }

    /**
     * Permet modificar la informació sobre les sessions de laboratori d'una assignatura
     *
     * @param nom_assig    Nom de l'assignatura
     * @param duracio      Duració de les sessions de laboratori
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     * @throws NotFoundException si no existeix l'assignatura amb el nom especificat
     */
    public void modificaInformacioLaboratori(String nom_assig, int duracio, int num_sessions, String tAula) throws NotFoundException {
        if (!assignatures.containsKey(nom_assig)) {
            throw new NotFoundException("No existeix l'assignatura amb nom " + nom_assig);
        }
        assignatures.get(nom_assig).setLaboratori(num_sessions, duracio, Aula.stringToTipusAula(tAula));
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
    public void creaAula(String edifici, int planta, int aula, int capacitat, String tipusAula) throws RestriccioIntegritatException {
        Aula.TipusAula ta = Aula.stringToTipusAula(tipusAula);
        String nomAula = Aula.crearkey(edifici, planta, aula);

        if (!aules.containsKey(nomAula)) {
            aules.put(nomAula, new Aula(edifici, planta, aula, ta, capacitat));
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
    public void modificarAula(String key, int capacitat, String tipusAula) throws NotFoundException {
        TipusAula ta = Aula.stringToTipusAula(tipusAula);

        if (aules.containsKey(key)) {
            aules.get(key).setCapacitat(capacitat);
            aules.get(key).setTipusAula(ta);
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
    public String consultarAula(String key) throws NotFoundException {
        if (!aules.containsKey(key)) {
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

    public ArrayList<String> consultaGrupsAssignatura(String nomAssig) {
        return assignatures.get(nomAssig).getLlistaGrupsSubgrups();
    }

    //TODO v v v v v v v v

    /********************* PRIMERA ASSIGNATURA *********************/

    /**
     * Consulta els dies que una assignatura i un grup tenen classes assignades
     *
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero del grup o subgrup
     * @return dies que el grup de l'assignatura te assignacions
     */
    public ArrayList<String> consultaDiesPerAssignaturaGrup(String nomAssig, String numGrup) {
        Assignacio[][][] schedule = horari.getHorari();
        ArrayList<String> result = null;
        if (schedule != null) {
            Assignacio assignacio;
            for (int i = 0; i < schedule.length; ++i) {
                for (int j = 0; j < schedule[i].length; ++j) {
                    for (int k = 0; k < schedule[i][j].length; ++k) {
                        assignacio = schedule[i][j][k];
                        if (String.valueOf(assignacio.getGrup().getNum()) == numGrup && assignacio.getAssignatura().getNom() == nomAssig) {
                            result.add(getDiafromNum(i));
                        }

                    }
                }

            }

        }
        return result;
    }

    /**
     * Consulta les hores que una assignatura i un grup tenen classes assignades un dia en concret
     *
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero del grup o subgrup
     * @param dia      dia de la setmana
     * @return llista d'hores assignades al grup aquell dia
     */
    public ArrayList<String> consultaHoresPerDiaAssignaturaGrup(String nomAssig, String numGrup, String dia) {
        Assignacio[][][] schedule = horari.getHorari();
        ArrayList<String> result = null;
        if (schedule != null) {
            Assignacio assignacio;
            int numdia = Integer.parseInt(dia);
            for (int j = 0; j < schedule[numdia].length; ++j) {
                for (int k = 0; k < schedule[numdia][j].length; ++k) {
                    assignacio = schedule[numdia][j][k];
                    if (String.valueOf(assignacio.getGrup().getNum()) == numGrup && assignacio.getAssignatura().getNom() == nomAssig) {
                        result.add(String.valueOf(k + 8));
                    }

                }
            }

        }
        return result;
    }

    /**
     * Funció auxiliar que a partir d'una hora retorna l'índex que la representa en un horari
     *
     * @param hora
     * @return enter que representa l'índex de la hora
     */

    private int getHora(String hora) {
        switch (hora) {
            case "8":
                return 0;
            case "9":
                return 1;
            case "10":
                return 2;
            case "11":
                return 3;
            case "12":
                return 4;
            case "13":
                return 5;
            case "14":
                return 6;
            case "15":
                return 7;
            case "16":
                return 8;
            case "17":
                return 9;
            case "18":
                return 10;
            case "19":
                return 11;
            case "20":
                return 12;
            default:
                break;
        }
        return 0;
    }

    /**
     * Consulta l'aula en que una assignatura, un grup, data i hora tenen una assignacio
     *
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero de grup o subgrup
     * @param dia      dia de la setmana
     * @param hora     hora
     * @return aula de l'assignacio
     */
    //TODO solo devolvera una, pero va mejor que todo sean arrays
    public ArrayList<String> consultaAulaPerHoresDiaAssignaturaGrup(String nomAssig, String numGrup, String dia, String hora) {
        Assignacio[][][] schedule = horari.getHorari();
        ArrayList<String> result = null;
        if (schedule != null) {
            String subject;
            String group;
            int nhora = getHora(hora);
            int ndia = Integer.parseInt(dia);
            Assignacio assignacio;
            for (int i = 0; i < schedule[nhora][ndia].length; ++i) {
                assignacio = schedule[nhora][ndia][i];
                subject = assignacio.getAssignatura().getNom();
                group = String.valueOf(assignacio.getGrup().getNum());
                if (subject == nomAssig && numGrup == group) {
                    result.add(aules.get(i).toString());
                }
            }

        }
        return result;
    }


    /**
     * Converteix un enter en l'string del dia que representa
     *
     * @param i enter que representa un número
     * @return string del dia que representa
     */
    private String getDiafromNum(int i) {
        switch (i) {
            case 0:
                return "Dilluns";
            case 1:
                return "Dimarts";
            case 2:
                return "Dimecres";
            case 3:
                return "Dijous";
            case 4:
                return "Divendres";
            default:
                return null;
        }
    }
    /********************* SEGONA ASSIGNATURA *********************/
//TODO: CANVIAR AIXO PQ NO FA LES POSSIBLES

    /**
     * Consulta els dies que una assignatura i un grup tenen classes assignades
     *
     * @param nomAssigR nom de l'assignatura que aplica restriccions sobre la segona
     * @param numGrupR  numero del grup que aplica restriccions sobre la segona
     * @param nomAssig  nom de l'assignatura
     * @param numGrup   numero del grup o subgrup
     * @return dies que el grup de l'assignatura te assignacions
     */
    public ArrayList<String> consultaDiesPerAssignaturaGrupAmbRestr(String nomAssigR, String numGrupR, String nomAssig, String numGrup) {
        return null;
    }

    /**
     * Consulta les hores que una assignatura i un grup tenen classes assignades un dia en concret
     *
     * @param nomAssigR nom de l'assignatura que aplica restriccions sobre la segona
     * @param numGrupR  numero del grup que aplica restriccions sobre la segona
     * @param nomAssig  nom de l'assignatura
     * @param numGrup   numero del grup o subgrup
     * @param dia       dia de la setmana
     * @return llista d'hores assignades al grup aquell dia
     */
    public ArrayList<String> consultaHoresPerDiaAssignaturaGrupAmbRestr(String nomAssigR, String numGrupR, String nomAssig, String numGrup, String dia) {
        return null;
    }

    /**
     * Consulta l'aula en que una assignatura, un grup, data i hora tenen una assignacio
     *
     * @param nomAssigR nom de l'assignatura que aplica restriccions sobre la segona
     * @param numGrupR  numero del grup que aplica restriccions sobre la segona
     * @param nomAssig  nom de l'assignatura
     * @param numGrup   numero de grup o subgrup
     * @param dia       dia de la setmana
     * @param hora      hora
     * @return aula de l'assignacio
     */
    //TODO solo devolvera una, pero va mejor que todo sean arrays
    public ArrayList<String> consultaAulaPerHoresDiaAssignaturaGrupAmbRestr(String nomAssigR, String numGrupR, String nomAssig, String numGrup, String dia, String hora) {
        return null;
    }

    /********************* SLOTS BUITS *********************/

    /**
     * Consulta dies amb assignacions possibles per l'assignatura que vol intercanviar
     *
     * @param nomAssig nom de l'assignatura que vol un slot
     * @param numGrup  numero del grup o subgrup concret
     * @return llista de dies on hi ha possibilitat de canvi
     */
    //TODO esto es ya consultar las vacias, paso la asignatura y el grupo por si hace falta para comprobar restricciones
    //asumimos que es solo de una clase para ese grupo? o que es para todas las clases que ha de hacer esa asignatura,
    //si es el primer caso tmbn debemos pasar que tipo de clase es(lab, teo)

    //si te paso un grupo es la hora de teoria, si te paso un subgrupo es la de lab, solo para ese (sub)grupo concreto


    /*
     retornarem un arraylist de hashmap<string,string> on en cada posicio de la arraylist es una possible assignacio, el hashmap tindra dos keys, "dia" i "hora" i el valor d'aquestes es el dia (dilluns,dimarts etc) i la hora (10,11,12...)
     mirar el generar horari mes adalt, uso la mateixa estructura i aixi ens estalviem la funcio consultar hores lliures per dia
    */
    public ArrayList<HashMap<String,String>> consultaDiesLliures(String nomAssig, String numGrup) {

        Assignatura a = assignatures.get(nomAssig);
        int grup = Integer.parseInt(numGrup);
        Grup g = null;
        try {
            g = a.getGrup((grup/10)*10); //treiem el subgrup (si ho era)
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        Assignacio[][][] schedule = horari.getHorari();
        int duracio = 0;
        SessioGrup ses = null;
        if(grup%10 ==0){
            duracio = a.getDuracioSessionsTeo(); //clase teoria
            ses = new SessioGrup(a,new Teoria(1,1,a.getTipusAulaTeo()),g,null,0);
        }
        else {
            try {
                duracio = a.getDuracioSessionsLab();
                Subgrup sub = g.getSubgrups().get(grup);
                ses = new SessioGrup(a, new Laboratori(1,1,a.getTipusAulaLab()),g,sub,0);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        ArrayList<HashMap<String,String>> result = new ArrayList<>();

        if (schedule != null) {
            ArrayList<Aula> llistaules = new ArrayList<>();
            Aula aul = null;
            HashMap<String,String> diahora;
            Assignacio assignacio;
            for (int i = 0; i < schedule.length; ++i) {
                for (int j = 0; j < schedule[i].length; ++j) {
                    for (int k = 0; k < schedule[i][j].length; ++k) {
                        assignacio = schedule[i][j][k];
                        if (assignacio == null) {
                            //hauriem de pillar la aula en [i][j][k] i la llista de aules en [i][j] problema es es una hashmap i no podem estar segurs de pillar el mateix, hauriem d'usar un linked hash map
                            // mirar
                            // https://stackoverflow.com/questions/5237101/is-it-possible-to-get-element-from-hashmap-by-its-position
                            //un cop tenim aquesta info li pasem al horari i aquesta funcio ens diu si es bona posicio o no
                            //aul = aules.get()
                            //prodecim a mirar totes les restriccions
                            if(horari.comprovarResSlotsBuits(ses,j,i,k,duracio,llistaules,aul)) {
                                diahora = new HashMap<String,String>();
                                diahora.put("dia",Algorismes.fromInt2dia(i));
                                diahora.put("hora", String.valueOf(Algorismes.getHora(j)));
                                result.add(diahora);
                            }
                        }

                    }
                }

            }

        }
        return result;

    }
    //Este no es igual que el anterior solo que buscando solo en un dia?

    /**
     * Consulta les hores lliures d'un dia concret on una assignatura i un grup podrien encaixar
     *
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero del grup o subgrup
     * @param dia      dia de la setmana
     * @return hores disponibles
     */
    public ArrayList<String> consultaHoresLliuresPerDia(String nomAssig, String numGrup, String dia) {
        return null;
    }
    //aqui solo hay que hacer como en la poda inicial del backtracking con forward checking pero
    //solo para un dia y una hora (dandole pasadas de las restricciones adicionales)

    //si fijas dia y hora implica que la "clase" dura solo 1h

    //te fijo la hora de inicio, la duracion de las siguientes la tienes que saber tu con duracion de sesiones y darme
    //X horas seguidas disponibles

    /**
     * Consulta les aules lliures d'una hora i dia concret on una assignatura i un grup podrien encaixar
     *
     * @param nomAssig nom de l'assignatura
     * @param numGrup  numero del grup o subgrup
     * @param dia      dia de la setmana
     * @param hora     hora
     * @return aules disponibles
     */
    public ArrayList<String> consultaAulesLliuresPerDiaHora(String nomAssig, String numGrup, String dia, String hora) {
        return null;
    }

    /********************* EXCHANGE *********************/
    //el problema es que una clase pueden ser varias horas, al estilo 3 seguidas i aqui solo estamso cambiando una
    // lo mismo que arriba, si son 3 horas seguidas, busco 3 horas seguidas vacias y hare el cambio de las 3


    //conceptualment no es una assignacio que hem de fer el swap si no una llista d'assigancions ja que en cada assignacio es nomes una hora d'un dia en una aula, llavors la
    //llista representaria les hores que ocupa aquesta assignacio.
    //tambe hem de crear la llista de aules que estan ocupades en ambdues linies, en tot moment i quines assignatures/grups hi ha en marxa en aquells moments


    //pk li pases horari com a parametre quan es una variable de la clase? (mira algorismes)

    /**
     * Intercanvia dos slots compatibles
     *
     * @param dia1  dia del primer slot a intercanviar
     * @param hora1 hora del primer slot a intercanviar
     * @param aula1 aula del primer slot a intercanviar
     * @param dia2  dia del segon slot a intercanviar
     * @param hora2 hora del segon slot a intercanviar
     * @param aula2 aula del segon slot a intercanviar
     */
    public void intercanviaSlots(String dia1, String hora1, String aula1, String dia2, String hora2, String aula2) {

        Assignacio[][][] schedule = horari.getHorari();
        if (schedule != null) {
            int hora_1 = getHora(hora1);
            int hora_2 = getHora(hora2);
            int dia_1 = Integer.parseInt(dia1);
            int dia_2 = Integer.parseInt(dia2);
            int posaula1 = 0 ;
            int posaula2= 0; //HE DE PENSAR COM HO PILLO

            Assignacio a = schedule[hora_1][dia_1][posaula1];
            Assignacio b = schedule[hora_2][dia_2][posaula2];
            schedule[hora_1][dia_1][posaula1] = b;
            schedule[hora_2][dia_2][posaula2] = a;

            horari.setHorari(schedule);
        }
    }

    public void setDataDirectory(File dir) {
        cIo.setDataDirectory(dir);
    }

    public int carregaBusca() throws IOException {
        int res = cIo.buscaData();
        if (res != 0) {
            if (res%3 == 0) assignatures = cIo.carregaAssignatures();
            if (res%5 == 0) aules = cIo.carregaAules();
            if (res%7 == 0) plaEstudis = cIo.carregaPlansDEstudi();
        }
        return res;
    }
}
