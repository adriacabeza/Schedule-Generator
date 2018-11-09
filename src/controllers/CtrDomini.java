package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;
import model.Aula.TipusAula;

import java.util.*;


public class CtrDomini {

    private HashMap<String, Assignatura> assignatures;
    private HashMap<String, PlaEstudis> plaEstudis;
    private HashMap<String, Aula> aules;
    private Assignacio[][][] horari;
    private Restriccions r;


    private static CtrDomini ourInstance;

    private ArrayList<Assignatura> assignatures2 = new ArrayList<>();
    private ArrayList<Aula> aules2 = new ArrayList<>();
    private ArrayList<AssignaturaMonosessio> mishmash;

    {
        try {
            mishmash = mishmash(assignatures2);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    //AQUESTES HAN DE PASSAR-LI
    public void crearAulesJSON(){
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            System.out.println(gson.toJson(aules));
    }

    public void crearAssignaturesJSON(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(assignatures));
    }

    public void crearPlaEstudisJSON(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(plaEstudis));
    }



    private String crearkey(String edifici, int planta, int aula) {
        return edifici + String.valueOf(planta) + String.valueOf(aula);
    }

    private String getedificifromKey(String key){
        return key.substring(0,2); //THAT MEANS THE WE ONLY HAVE BUILDING OF TWO CHARS
    }

    private String getplantafromKey(String key){
        return key.substring(2,3);
    }

    private String getaulafromKey(String key){
        return key.substring(3);
    }


    private CtrDomini() {
        assignatures = new HashMap<>();
        plaEstudis = new HashMap<>();
        aules = new HashMap<>();
        horari = new Assignacio[12][5][aules2.size()];
    }

    public static CtrDomini getInstance() {
        if(ourInstance == null){
            ourInstance = new CtrDomini();
        }
        return ourInstance;
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
        assignatures.get(nom_a).esborraCorrequisit(assignatures.get(nom_b));
        assignatures.get(nom_b).esborraCorrequisit(assignatures.get(nom_a));
    }


    /******************* AULES ************************/


    // TODO fer excepcions i comentarles, fer alguna funcio mes si cal pel controller
    public void creaAula(String edifici, int planta, int aula, int capacitat, TipusAula tipusAula, ArrayList<Assignacio> assignacions) throws RestriccioIntegritatException {
        String nomaula = crearkey(edifici, planta, aula);

        if (!aules.containsKey(nomaula)) {
            aules.put(nomaula, new Aula(edifici, planta, aula, tipusAula, capacitat, assignacions));
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

    public void modificarAula(Aula a, int capacitat, String edifici, int planta, int aula, TipusAula tipusAula, ArrayList<Assignacio> assignacions) {
        a.setEdifici(edifici);
        a.setAssignacions(assignacions);
        a.setAula(aula);
        a.setPlanta(planta);
        a.setTipusAula(tipusAula);
        a.setCapacitat(capacitat);
    }

    public void consultarAula(String edifici, int planta, int aula) throws NotFoundException {
        String key = crearkey(edifici, planta, aula);
        if (!aules.containsKey(key)) {
            throw new NotFoundException("No existeix l'aula especificada");
        } else {
            aules.get(key);
        }
    }


    /**
     * sobre assignacions
     */



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
            default :
                return 4;
        }
    }


    private int gethora(int hora){
        if(hora == 0) return 8;
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

    private void printarHorari_aula(Aula aula){
        int numAula = assignatures2.indexOf(aula);
        for(int i = 0; i < 12; ++i){
            for(int j = 0; j < 5; ++j){
               Assignacio assignacio = horari[i][j][numAula]; //S HAURIA DE PRINTAR AIXO
            }

        }
    }

    private void printarHorari_auladia(Aula aula,String dia){
        int numAula = assignatures2.indexOf(aula);
        int numdia = fromdia2int(dia);
        for(int i = 0; i < 12; ++i){
                Assignacio assignacio = horari[i][numdia][numAula]; //S HAURIA DE PRINTAR AIXO
            }

    }

    private void printarHorari_aulahora(Aula aula,int hora){
        int nhora = gethora(hora);
        int numAula = assignatures2.indexOf(aula);
        for(int i = 0; i < 5; ++i){
            Assignacio assignacio = horari[nhora][i][numAula]; // S HAURIA DE PRINTAR AIXO
        }

    }

    public void printarHorari_hora(int hora){
        int nhora = gethora(hora);
        for(int i = 0; i < 5; ++i){
            for(int j = 0; j < aules2.size(); ++j){
                Assignacio assignacio = horari[nhora][i][j]; // S HAURIA DE PRINTAR AIXO
            }

        }

    }

    public void printarHorari_hora(String dia){
        int numdia = fromdia2int(dia);
        for(int i = 0; i < 12; ++i){
            for(int j = 0; j < aules2.size(); ++j){
                Assignacio assignacio = horari[i][numdia][j]; // S'HAURIA DE PRINTAR AIXO
            }

        }

    }

    private boolean comprovar_restricciones_teoria(int aula1, Grup grup, int dia, int hora, Assignatura assig, int duracio){
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() < grup.getCapacitat()) return false;
        for(int i = 0; i < duracio; ++i)  {
            if((hora+i) >= 12) return false;
            else if(horari[hora+i][dia][aula1] != null) return false;
        }
        return true;
    }


    private boolean comprovar_restricciones_lab(int aula1, Subgrup subgrup, int dia, int hora, Assignatura assig, int duracio){
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() < subgrup.getCapacitat()) return false;
        for(int i = 0; i < duracio; ++i)  {
            if((hora+i) >= 12) return false;
            else if(horari[hora+i][dia][aula1] != null) return false;
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

    private boolean creaHorari(int i, int dia, int hora, int aula, int grup, int subgrup) throws NotFoundException {

        if (comprovarini(aula, dia, hora))
            return false; //això lo que fa es parar la recursivitat per aquesta via perquè no pot comprovar ni per un dissabte, ni per aules ni hores que no existeixen

        if (i == mishmash.size()) {
            return true; //ja que he mirat totes les asssignatures osea que DONE
        } else {
            Assignatura assig = mishmash.get(i).getAssig(); //esta es la asignatura que toca
            if (mishmash.get(i).getSessio().getClass() == Teoria.class) {
                System.out.println(grup);
                System.out.println(assig.getNom());
                Grup grup1 = mishmash.get(i).getAssig().getGrup(grup);
                int duracio = mishmash.get(i).getSessio().getDuracioSessions();
                if (comprovar_restricciones_teoria(aula, grup1, dia, hora, assig, duracio)) {
                    for (int z = 0; z < duracio; ++z) {
                        horari[hora + z][dia][aula] = new AssignacioT(fromInt2dia(dia), hora + z, aules2.get(aula), mishmash.get(i).getSessio().gettAula(), assig, grup1);
                    }
                    if (grup == (assig.getGrups().size() - 1)) //comprovar si ja és l'últim grup de l'assignatura
                        if (creaHorari(i + 1, 0, 0, 0, 0, 0)) return true;//vamos a provar pa la asignatura siguiente
                        else {
                            for (int z = 0; z < duracio; ++z) {
                                horari[hora + z][dia][aula] = null;
                            }
                            if (creaHorari(i + 1, 0, 0, 0, 0, 0))
                                return true; //si esta al final de dia passar a hora mes u i dia 0
                            else {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[hora + z][dia][aula] = null;
                                }
                                boolean b = creaHorari(i, dia + 1, hora, aula, grup, subgrup); //voy a provar para el siguiente dia
                                if (!b) {
                                    boolean b1 = creaHorari(i, 0, hora + 1, aula, grup, subgrup); //voy a provar para el siguiente hora
                                    if (!b1) {
                                        boolean b2 = creaHorari(i, dia, hora, aula + 1, grup, subgrup); //voy a provar para el siguiente aula
                                        if (b2) return false; //no se puede hacer el horario de ninguna manera
                                    }
                                } else {
                                    if (creaHorari(i, 0, 0, 0, grup + 10, subgrup)) return true;
                                }
                            }
                        }



                             else{
                                boolean b = creaHorari(i, dia + 1, hora, aula, grup, subgrup); //voy a provar para el siguiente dia
                                if (!b) {
                                    boolean b1 = creaHorari(i, 0, hora + 1, aula, grup, subgrup); //voy a provar para el siguiente hora
                                    if (!b1) {
                                        boolean b2 = creaHorari(i, dia, hora, aula + 1, grup, subgrup); //voy a provar para el siguiente aula
                                        if (b2) return false; //no se puede hacer el horario de ninguna manera
                                    }

                                }
                            }
                        }
                    else {
                        Subgrup subgrup1 = grup1.getSubgrups().get(subgrup);
                        if (comprovar_restricciones_lab(aula, subgrup1, dia, hora, assig, duracio)) { //comprovar restricciones
                            for (int z = 0; z < duracio; ++z) {
                                horari[hora + z][dia][aula] = new AssignacioL(fromInt2dia(dia), hora + z, aules2.get(aula), mishmash.get(i).getSessio().gettAula(), assig, subgrup1);
                            }
                            if (subgrup == (mishmash.get(i).getAssig().getSubgrups(grup).size() - 1)) {
                                if (grup == assig.getGrups().size()) {
                                   if(creaHorari(i + 1, 0, 0, 0, 0, 0)) return true;//vamos a provar pa la asignatura siguiente

                                } else {
                                    creaHorari(i, 0, 0, 0, grup + 10, 0);//vamos a provar pa la asignatura siguiente
                                }


                            } else creaHorari(i, 0, 0, 0, grup, subgrup + 1);
                        }


                        else {
                            boolean b = creaHorari(i, dia + 1, hora, aula, grup, subgrup); //voy a provar para el siguiente dia
                            if (!b) {
                                boolean b1 = creaHorari(i, 0, hora + 1, aula, grup, subgrup); //voy a provar para el siguiente hora
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
        }


    public boolean generaHorari(){
        assignatures2 = new ArrayList<>(assignatures.values());
        aules2 = new ArrayList<>(aules.values());
        horari = new Assignacio[12][5][aules2.size()];
        try {
            mishmash = mishmash(assignatures2);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        try {
            return creaHorari(0,0,0,0,10,11);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ArrayList<AssignaturaMonosessio> mishmash(ArrayList<Assignatura> assignatures2) throws NotFoundException {
        ArrayList<AssignaturaMonosessio> res = new ArrayList<>();
        Teoria auxteo;
        Laboratori auxlab = new Laboratori(0,0,null);
        int sesteo, seslab, valor;
        seslab = 0;             //si no comp se queja
        boolean lab;
        for (Assignatura a : assignatures2) {
            lab = false;
            try {
                auxlab = (Laboratori) a.getLaboratori();
                seslab = auxlab.getNumSessions();
                lab = true;
            }
            catch(NotFoundException e) {}
            auxteo = (Teoria) a.getTeoria();         //TODO: concretar que significa un valor de 1 a sessions i la possibilitat de un valor 0.
            sesteo = auxteo.getNumSessions();
            valor = 8;                      //TODO: heuristica a assignar
            for (int i = 0; lab && i < seslab; ++i) {
                res.add(new AssignaturaMonosessio(a, auxlab, valor));
                valor /= 2;
            }
            valor = 8;
            for (int i = 0; i < sesteo; ++i) {
                res.add(new AssignaturaMonosessio(a, auxteo, valor));
                valor /= 2;
            }
        } return res;
    }

}

//TODO quitar a las asignaturas siguientes la posibilidad de que miren los gaps(de dia, hora y aula) que ya se han asignado