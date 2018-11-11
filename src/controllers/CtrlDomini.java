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
    private RestriccioCorrequisit resCorr;
    private RestriccioNivell resNiv;
    private RestriccioAula resAul;
    private RestriccioGrupTeo resTeo;
    private RestriccioSubgrupLab resSub;
    private ArrayList<RestriccioAulaDia> resAulDia;
    private ArrayList<RestriccioAulaHora> resAulaHora;

    // TODO mas dificil de actualitzar porque tengo que ir haciendolo en paralelo con el map


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


    public Assignacio[][][] crearHorari() {
        Horari newhorari = new Horari(assignatures, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora);
        newhorari.generaHorari();
        newhorari.printarHoraritot();
        return newhorari.getHorari();
    }
}
