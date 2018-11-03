/**
 * @author Aina Garcia
 */

package model;

import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;

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
     *
     * @param nom nom de l'assignatura
     * @param t   informació de les sessions de teoria, pot ser null
     * @param l   informació de les sessions de laboratori, pot ser null
     */
    public Assignatura(String nom, int quadrimestre, Teoria t, Laboratori l) {
        this.nom = nom;
        this.laboratori = l;
        this.teoria = t;
        this.quadrimestre = quadrimestre;
        grups = new HashMap<>();
        correquisit = new ArrayList<>();
    }

    /********** GETTERS ********/

    /**
     * Obtenir nom de l'assignatura
     *
     * @return nom de l'assignatura
     */
    public String getNom() {
        return nom;
    }

    public int getQuadrimestre() {
        return quadrimestre;
    }

    /**
     * Obtenir informació de les sessions de laboratori de l'assignatura
     *
     * @return informació de laboratori
     * @throws NotFoundException si no te sessions de laboratori
     */
    public InfoSessions getLaboratori() throws NotFoundException {
        if (laboratori == null) {
            throw new NotFoundException("No existeixen sessions de laboratori per l'assignatura " + this.toString());
        }
        return laboratori;
    }

    /**
     * Obtenir informació de les sessions de teoria de l'assignatura
     *
     * @return informació de teoria
     * @throws NotFoundException si no te sessions de teoria
     */
    public InfoSessions getTeoria() throws NotFoundException {
        if (teoria == null) {
            throw new NotFoundException("No existeixen sessions de teoria per l'assignatura " + this.toString());
        }
        return teoria;
    }

    /**
     * Obtenir tots els grups de l'assignatura
     *
     * @return grups de l'assignatura
     * @throws NotFoundException si no existeixen grups per l'assignatura
     */
    public Map<Integer, Grup> getGrups() throws NotFoundException {
        if (!hasGroups()) {
            throw new NotFoundException("No hi ha grups a mostrar per l'assignatura " + this.toString());
        }
        return grups;
    }

    /**
     * Obtenir la quantitat de grups que hi ha a una assignatura
     * @return grups.size()
     */
    public int GetSizeGrups(){
        return  grups.size();
    }

    /**
     * Obtenir tots els subgrups donat l'identificador d'un grup
     *
     * @param num identificador del grup
     * @return Llista de subgrups
     * @throws NotFoundException si no existeixen subgrups del grup de l'assignatura
     */
    public ArrayList<Subgrup> getSubgrups(int num) throws NotFoundException {
        if (!grups.containsKey(num)) {
            throw new NotFoundException("Grup not found");
        }
        return grups.get(num).getSubgrups();
    }

    /**
     * Obtenir un grup concret d'aquesta assignatura
     *
     * @param num nombre del grup
     * @return grup amb número identificador sol·licitat
     * @throws NotFoundException si no existeix el grup especificat
     */
    public Grup getGrup(int num) throws NotFoundException {
        if (!grups.containsKey(num)) {
            throw new NotFoundException("Grup not found");
        }
        return grups.get(num);
    }

    /**
     * Actualitza la informació de les sessions de laboratori
     *
     * @param numSessions     sessions de laboratori
     * @param duracioSessions duracio de les sessions
     */
    public void setLaboratori(int numSessions, int duracioSessions) {
        this.laboratori = new Laboratori(numSessions, duracioSessions);
    }

    /**
     * Actualitza la informació de les sessions de teoria
     *
     * @param numSessions     numero de sessions setmanals
     * @param duracioSessions duracio de cada sessio
     */
    public void setTeoria(int numSessions, int duracioSessions) {
        this.teoria = new Teoria(numSessions, duracioSessions);
    }

    /**
     * Actualitza el quadrimestre al qual forma part una assignatura
     *
     * @param quadrimestre quadrimestre al que forma part aquesta
     * @throws RestriccioIntegritatException si l'assignatura te correquisits
     */
    public void setQuadrimestre(int quadrimestre) throws RestriccioIntegritatException {
        if (this.quadrimestre != quadrimestre && !this.correquisit.isEmpty()) {
            throw new RestriccioIntegritatException("No pots cambiar el quadrimestre d'una assignatura amb correquisits");
        } else {
            this.quadrimestre = quadrimestre;
        }
    }

    /**
     * Modifica el nombre de grups disponibles, així com la seva capacitat i el numero de subgrups si escau
     *
     * @param num_grups nombre de grups que es vol tenir per l'assignatura
     * @param grup_cap  capacitat d'alumnes per cada grup
     * @param sgrup_num nombre de subgrups que es vol tenir per cada grup
     */
    public void modificarGrups(int num_grups, int grup_cap, int sgrup_num) {
        this.grups = new HashMap<Integer, Grup>();
        for (int i = 10; i <= num_grups; i += 10) {
            grups.put(i, new Grup(i, grup_cap, sgrup_num));
        }
    }

    /**
     * Assigna a una assignatura una altra assignatura com a correquisit
     *
     * @param a Assignatura correquisit de self
     * @throws RestriccioIntegritatException si l'assignacio ja existeix, si l'assignatura es ella mateixa o si formen part de quadrimestres diferents
     */
    public void afegeixCorrequisit(Assignatura a) throws RestriccioIntegritatException {

        if (correquisit.contains(a)) {
            throw new RestriccioIntegritatException("L'assignatura " + a.toString() + " ja està assignada com a correquisit");
        }

        if (a.getNom().equals(this.nom)) {
            throw new RestriccioIntegritatException("L'assignatura " + a.toString() + " no pot ser correquisit d'ella mateixa");
        }

        if (this.quadrimestre != a.getQuadrimestre()) {
            throw new RestriccioIntegritatException("Les assignatures han de formar part del mateix quadrimestre per ser correquisits");
        }
        correquisit.add(a);
    }

    /**
     * Esborra una assignatura com a correquisit d'aquesta
     *
     * @param a Assignatura a esborrar de self
     * @throws NotFoundException si l'assignatura no es correquisit
     */
    public void esborraCorrequisit(Assignatura a) throws NotFoundException {
        if (correquisit.contains(a)) {
            correquisit.remove(a);
        } else {
            throw new NotFoundException(this.toString() + " no conté " + a.toString() + " com a correquisit");
        }
    }

    /**
     * Retorna una llista dels correquisits de l'assignatura
     *
     * @return llista de correquisits
     * @throws NotFoundException si l'assignatura no te correquisits
     */
    public ArrayList<Assignatura> getCorrequisits() throws NotFoundException {
        if (this.correquisit.isEmpty()) {
            throw new NotFoundException("L'assignatura " + this.toString() + " no te correquisits");
        }
        return this.correquisit;
    }

    /**
     * Diu si una assignatura és correquisit de la primera
     *
     * @param a assignatura a comparar
     * @return cert si és correquisit, fals altrament
     */
    public boolean esCorrequisit(Assignatura a) {
        return this.correquisit.contains(a);
    }

    /**
     * Retorna cert si una assignatura ja te grups creats
     *
     * @return Cert si te grups creats, fals altrament
     */
    public boolean hasGroups() {
        return !this.grups.isEmpty();
    }

    /**
     * Override del metode toString per la representacio de la classe de forma textual
     *
     * @return nom de la classe
     */
    @Override
    public String toString() {
        return this.nom.toUpperCase();
    }
}