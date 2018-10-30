package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assignatura {

    private String nom;
    private InfoSessions teoria;
    private InfoSessions laboratori;
    private Map<Integer, Grup> grups;
    private ArrayList<Assignatura> correquisit;
    private int quadrimestre;

    /**
     * Crea una assignatura nova amb grups i la informació corresponent
     * @param nom nom de l'assignatura
     * @param grup_num nombre de grups que té l'assignatura
     * @param grup_cap capacitat per grup
     * @param sgrup_num nombre de subgrups per cada grup
     * @param t informació de les sessions de teoria
     * @param l informació de les sessions de laboratori
     */
    public Assignatura(String nom, int grup_num, int grup_cap, int sgrup_num, int quadrimestre, Teoria t, Laboratori l){
        this.nom = nom;
        this.laboratori = l;
        this.teoria = t;
        this.quadrimestre = quadrimestre;
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

    public int getQuadrimestre() { return quadrimestre; }

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
     * Actualitza la informació de les sessions de teoria
     * @param teoria
     */
    public void setTeoria(InfoSessions teoria) {
        this.teoria = teoria;
    }

    public void setQuadrimestre(int quadrimestre) { this.quadrimestre = quadrimestre; }

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
    }


    /**
     * Assigna a una assignatura una altra assignatura com a correquisit
     * @param a Assignatura correquisit de self
     */
    public void afegeixCorrequisit(Assignatura a){
        correquisit.add(a);
    }

    /**
     * Esborra una assignatura com a correquisit d'aquesta
     * @param a Assignatura a esborrar de self
     */
    public void esborraCorrequisit(Assignatura a){
        correquisit.remove(a);
    }


    /**
     * Retorna una llista dels correquisits de l'assignatura
     * @return llista de correquisits
     */
    public ArrayList<Assignatura> getCorrequisits() {
        return correquisit;
    }

    /**
     * Diu si una assignatura és correquisit de la primera
     * @param a assignatura a comparar
     * @return cert si és correquisit, fals altrament
     */
    public boolean esCorrequisit(Assignatura a){
        return correquisit.contains(a);
    }
}
