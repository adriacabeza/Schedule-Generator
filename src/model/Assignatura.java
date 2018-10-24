package model;

import java.util.HashMap;
import java.util.Map;

public class Assignatura {

    private String nom;
    private Quadrimestre quadrimestre;
    private InfoSessions teoria;
    private InfoSessions laboratori;
    private Map<Integer, Grup> grups;

    /**
     * Crea una assignatura nova amb grups i la informació corresponent
     * @param nom nom de l'assignatura
     * @param q quadrimestre al qual pertany l'assignatura
     * @param grup_num nombre de grups que té l'assignatura
     * @param grup_cap capacitat per grup
     * @param sgrup_num nombre de subgrups per cada grup
     * @param t informació de les sessions de teoria
     * @param l informació de les sessions de laboratori
     */
    public Assignatura(String nom, Quadrimestre q, int grup_num, int grup_cap, int sgrup_num, Teoria t, Laboratori l){
        this.nom = nom;
        this.quadrimestre = q;
        this.laboratori = l;
        this.teoria = t;
        modificarGrups(grup_num, grup_cap, sgrup_num);
    }

    /********** GETTERS ********/

    /**
     * Obtenir nom de l'assignatura
     * @return nom de l'assignatura
     */
    public String getNom(){
        return nom;
    }

    /**
     * Obtenir quadrimestre del qual forma part de l'assignatura
     * @return quadrimestre
     */
    public Quadrimestre getQuadrimestre() {
        return quadrimestre;
    }

    /**
     * Obtenir informació de les sessions de laboratori de l'assignatura
     * @return informació de laboratori
     */
    public InfoSessions getLaboratori() {
        return laboratori;
    }

    /**
     * Obtenir informació de les sessions de teoria de l'assignatura
     * @return informació de teoria
     */
    public InfoSessions getTeoria() {
        return teoria;
    }

    /**
     * Obtenir tots els grups de l'assignatura
     * @return grups de l'assignatura
     */
    public Map<Integer, Grup> getGrups() {
        return grups;
    }

    /**
     * Obtenir un grup concret d'aquesta assignatura
     * @param num nombre del grup
     * @return grup amb número identificador sol·licitat
     */
    public Grup getGrup(int num){
        return grups.get(num);
    }

    /****** SETTERS ********/

    /**
     * Actualitza la informació de les sessions de laboratori
     * @param laboratori
     */
    public void setLaboratori(InfoSessions laboratori) {
        this.laboratori = laboratori;
    }

    /**
     * Actualitza el nom de l'assignatura
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Actualitza el quadrimestre del qual forma part l'assignatura
     * @param quadrimestre
     */
    public void setQuadrimestre(Quadrimestre quadrimestre) {
        this.quadrimestre = quadrimestre;
    }

    /**
     * Actualitza la informació de les sessions de teoria
     * @param teoria
     */
    public void setTeoria(InfoSessions teoria) {
        this.teoria = teoria;
    }

    /******* OTHER ******/

    /**
     * Modifica el nombre de grups disponibles, així com la seva capacitat i el numero de subgrups si escau
     * @param num_grups nombre de grups que es vol tenir per l'assignatura
     * @param grup_cap capacitat d'alumnes per cada grup
     * @param sgrup_num nombre de subgrups que es vol tenir per cada grup
     */
    public void modificarGrups(int num_grups, int grup_cap, int sgrup_num){
        this.grups = new HashMap<Integer, Grup>();
        for(int i = 10; i <= num_grups; i+=10){
            grups.put(i, new Grup(i, grup_cap, sgrup_num));
        }
        // quan es faci la UI caldra carregar com a valor per defecte de capacitat i num_sgrups el
        // valor que ja tenien els antics grups i subgrups en cas que existissin!

        // restriccio: un cop estan generats els horaris no es poden modificar els grups
        // modificar els grups implica borrar qualsevol assignació que aquests tinguin
    }
}
