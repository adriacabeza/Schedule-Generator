package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;
import model.Aula.TipusAula;

import java.util.*;

public class CtrlDomini {

    private static CtrlDomini ourInstance;
    private HashMap<String, Assignatura> assignatures;
    private HashMap<String, PlaEstudis> plaEstudis;
    private HashMap<String, Aula> aules;
    private Assignacio[][][] horari; //TODO esto va a estar aqui?
    private Restriccions r;
    private HashMap<String,Restriccions> restriccions_unaries;

    // TODO esto tiene que estar aqui o puedo simplemente pasarlo donde se necesite como parametro? si esta aqui es
    // TODO mas dificil de actualitzar porque tengo que ir haciendolo en paralelo con el map
    private ArrayList<Assignatura> assignatures2;
    private ArrayList<Aula> aules2;
    private ArrayList<AssignaturaMonosessio> mishmash;

    private CtrlDomini() {
        assignatures = new HashMap<>();

        plaEstudis = new HashMap<>();
        aules = new HashMap<>();
        assignatures2 = new ArrayList<>();
        aules2 = new ArrayList<>();
        horari = new Assignacio[12][5][aules2.size()];

        try {
            mishmash = mishmash(assignatures2);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static CtrlDomini getInstance() {
        if (ourInstance == null) {
            ourInstance = new CtrlDomini();
        }
        return ourInstance;
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


    //TODO fix dis
    /**
     * Afegeix una assignatura anteriorment creada al pla d'estudis indicat
     *
     * @param nomP Nom del pla d'estudis
     * @param nomA Nom de l'assignatura
     */
    public void afegirAssignaturaPla(String nomP, String nomA) {
        plaEstudis.get(nomP).afegirAssignatura(assignatures.get(nomA).getNom());
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
    public ArrayList<String> consultarAssignaturesPlaEstudis(String nomP) {
        return plaEstudis.get(nomP).getAssignatures();
    }

    /**
     * Permet crear una nova assignatura
     *
     * @param nom          Nom de l'assignatura
     * @param quadrimestre Quadrimestre al qual pertany
     * @throws RestriccioIntegritatException si ja existia una assignatura identificada pel mateix nom
     */
    public void crearAssignatura(String nom, int quadrimestre) throws RestriccioIntegritatException {
        if (assignatures.containsKey(nom)) {
            throw new RestriccioIntegritatException("Ja existeix una assignatura amb nom " + nom.toUpperCase());
        }
        assignatures.put(nom, new Assignatura(nom, quadrimestre));
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

    // TODO fer excepcions i comentarles, fer alguna funcio mes si cal pel controller

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
     * @param edifici edifici en el que es troba l'aula
     * @param planta  planta a la que es troba l'aula
     * @param aula    numero d'aula dins d'una planta i un edifici
     * @throws NotFoundException quan s'intenta borrar una aula inexistent
     */
    public void esborrarAula(String edifici, int planta, int aula) throws NotFoundException {
        String nomAula = Aula.crearkey(edifici, planta, aula);

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

    //func generar horari
    //consultar restriccions

    //algo mes?
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //TODO vvvvvvvvvvvv HASTA AQUI ES CONTROLADOR DE DOMINIO, LO DEMAS SE TIENE QUE MOVER vvvvvvvvvvvv

    /**
     * A CLASSE HORARI, RESTRICCIONS, DOMINI I ALGORITME
     */

    private String fromInt2dia(int dia) {
        if (dia == 0) return "Dilluns";
        else if (dia == 1) return "Dimarts";
        else if (dia == 2) return "Dimecres;";
        else if (dia == 3) return "Dijous";
        else return "Divendres";

    }

    private int fromdia2int(String dia) {
        switch (dia) {
            case "Dilluns":
                return 0;
            case "Dimarts":
                return 1;
            case "Dimecres":
                return 2;
            case "Dijous":
                return 3;
            default:
                return 4;
        }
    }

    void creaRestriccionsUnaries(){

    }


    private int gethora(int hora) {
        if (hora == 0) return 8;
        else if (hora == 1) return 9;
        else if (hora == 2) return 10;
        else if (hora == 3) return 11;
        else if (hora == 4) return 12;
        else if (hora == 5) return 13;
        else if (hora == 6) return 14;
        else if (hora == 7) return 15;
        else if (hora == 8) return 16;
        else if (hora == 9) return 17;
        else if (hora == 10) return 18;
        else return 19;
    }


    private boolean comprovarini(int aula, int dia, int hora) {
        if (aula > aules2.size() || dia > 4 || hora > 11) return true;

        return false;
    }

    private void printarHorari_aula(Aula aula) {
        int numAula = assignatures2.indexOf(aula);
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 5; ++j) {
                Assignacio assignacio = horari[i][j][numAula]; //S HAURIA DE PRINTAR AIXO
            }

        }
    }

    private void printarHorari_auladia(Aula aula, String dia) {
        int numAula = assignatures2.indexOf(aula);
        int numdia = fromdia2int(dia);
        for (int i = 0; i < 12; ++i) {
            Assignacio assignacio = horari[i][numdia][numAula]; //S HAURIA DE PRINTAR AIXO
        }

    }

    private void printarHorari_aulahora(Aula aula, int hora) {
        int nhora = gethora(hora);
        int numAula = assignatures2.indexOf(aula);
        for (int i = 0; i < 5; ++i) {
            Assignacio assignacio = horari[nhora][i][numAula]; // S HAURIA DE PRINTAR AIXO
        }

    }

    public void printarHorari_hora(int hora) {
        int nhora = gethora(hora);
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < aules2.size(); ++j) {
                Assignacio assignacio = horari[nhora][i][j]; // S HAURIA DE PRINTAR AIXO
            }

        }

    }

    public void printarHorari_hora(String dia) {
        int numdia = fromdia2int(dia);
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < aules2.size(); ++j) {
                Assignacio assignacio = horari[i][numdia][j]; // S'HAURIA DE PRINTAR AIXO
            }

        }

    }

    private boolean comprovar_restricciones_teoria(int aula1, Grup grup, int dia, int hora, Assignatura assig, int duracio) {
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() < grup.getCapacitat()) return false;
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12) return false;
            else if (horari[hora + i][dia][aula1] != null) return false;
        }
        return true;
    }


    private boolean comprovar_restricciones_lab(int aula1, Subgrup subgrup, int dia, int hora, Assignatura assig, int duracio) {
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() < subgrup.getCapacitat()) return false;
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12) return false;
            else if (horari[hora + i][dia][aula1] != null) return false;
        }
        return true;
    }

    /*private void ferHorari(){
        HashMap<Grup,HashMap<Integer,Set< HashMap<Integer,Set<Aula> >>>> Shrek;
        filtra_restriccions(Shrek);
    }
    private boolean crearHorari2(){



    }
   private void filtra_restriccions(HashMap<Grup, HashMap<Integer, Set<HashMap<Integer, Set<Aula>>>>> Shrek){
        for(int i = 0; i < 5; ++i){
            for(int j = 0; j < 12; ++j){

            }
        }
    }*/


  /*  private boolean creaHorari(Domini domini, Assignacio[][][] horari, AssignaturaMonosessio mishmash, ArrayList<Aula> aules2){
        //a horari tinc l'horari buit, o el que s'ha anat fent
        //a domini tinc les possibilitats de cada grup, osea els dies als que pot anar, les hores a les
        //que pot anar i les aules a les que pot anar
        //a mishmash tinc a cada slot una assignatura i la seva informació de la sessió, ho he de fer per cada grup i subgrup de
        //l'assignatura
        //a aules2 tinc el total d'aules
        if (mishmash.size() == 0){
            return true; //all the groups were placed
        }
        else {
            //pillar primera opció valida
            horari[][][] = new Assignacio()
                    //fer copia domini
            podar_opcions(domini);
            if(creaHorari()) return true;
            else{
                //pillar següent opció valida
                creaHorari()
            }

        }
    }*/


    public boolean generaHorari() {
        assignatures2 = new ArrayList<>(assignatures.values());
        aules2 = new ArrayList<>(aules.values());
        horari = new Assignacio[12][5][aules2.size()];
        try {
            mishmash = mishmash(assignatures2);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        try {
            //return creaHorari(0, 0, 0, 0, 10, 11);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return false;

        Domini domini = new Domini(HashMap<Integer, HashMap<Integer, Set<Aula>>> domini);
        domini.aplica_restriccions_unaries(restriccions_unaries);
    }

    private ArrayList<AssignaturaMonosessio> mishmash(ArrayList<Assignatura> assignatures2) throws NotFoundException {
        ArrayList<AssignaturaMonosessio> res = new ArrayList<>();
        Teoria auxteo;
        Laboratori auxlab = new Laboratori(0, 0, null, null);
        int sesteo, seslab, valor;
        Map<Integer, Grup> grups;
        Grup g;
        HashMap<Integer, Subgrup> subgrups;
        seslab = 0;             //si no comp se queja
        boolean lab;
        for (Assignatura a : assignatures2) {
            lab = false;
            try {
                auxlab = (Laboratori) a.getLaboratori();
                seslab = auxlab.getNumSessions();
                lab = true;
            } catch (NotFoundException e) {}
            auxteo = (Teoria) a.getTeoria();         //TODO: concretar que significa un valor de 1 a sessions i la possibilitat de un valor 0.
            sesteo = auxteo.getNumSessions();
            valor = 8;                      //TODO: heuristica a assignar
            for (int i = 0; lab && i < seslab; ++i) {
                grups = a.getGrups();
                for (int key : grups.keySet()){
                    g = grups.get(key);
                    subgrups = g.getSubgrups();
                    for (int subg : subgrups.keySet()){
                        res.add(new AssignaturaMonosessio(a, auxlab, g, subgrups.get(subg), valor));
                    }

                }

                valor /= 2;
            }
            valor = 8;
            for (int i = 0; i < sesteo; ++i) {
                grups = a.getGrups();
                for (int key : grups.keySet()){
                    res.add(new AssignaturaMonosessio(a, auxteo, grups.get(key), null, valor));
                }

                valor /= 2;
            }
        }
        return res;
    }

}



    /**
     * AL CTRL IO
     */

//AQUESTES HAN DE PASSAR-LI
    public void crearAulesJSON() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(aules));
    }

    public void crearAssignaturesJSON() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(assignatures));
    }

    public void crearPlaEstudisJSON() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(plaEstudis));
    }


//    public static void crearJSON(){
//        JSONObject obj = new JSONObject();
//
//        obj.put("");
//
//        JSONArray aules = new JSONArray();
//        aules.add()
//        try {
//            FileWriter file = new FileWriter("data.json");
//            file.write(obj.toJSONString());
//            file.flush();
//            file.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.print(obj.toJSONString());
//    }