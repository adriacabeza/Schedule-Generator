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
    private Map<Integer, Grup> grups = new HashMap<>();
    private ArrayList<Assignatura> correquisit = new ArrayList<>();
    private int quadrimestre;

    /**
     * Crea una assignatura nova amb grups i la informació corresponent
     *
     * @param nom nom de l'assignatura
     * @param t   informació de les sessions de teoria
     * @param l   informació de les sessions de laboratori
     */
    public Assignatura(String nom, int quadrimestre, Teoria t, Laboratori l) {
        this.nom = nom;
        this.laboratori = l;
        this.teoria = t;
        this.quadrimestre = quadrimestre;
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
     */
    public InfoSessions getLaboratori() {
        return laboratori;
    }

    /**
     * Obtenir informació de les sessions de teoria de l'assignatura
     *
     * @return informació de teoria
     */
    public InfoSessions getTeoria() {
        return teoria;
    }

    /**
     * Obtenir tots els grups de l'assignatura
     *
     * @return grups de l'assignatura
     * @throws NotFoundException
     */
    public Map<Integer, Grup> getGrups() throws NotFoundException {
        if (hasGroups()) {
            return grups;
        } else {
            throw new NotFoundException("No hi ha grups a mostrar per l'assignatura " + this.toString());
        }
    }

    /**
     * Obtenir tots els subgrups donat l'identificador d'un grup
     *
     * @param num identificador del grup
     * @return Llista de subgrups
     * @throws NotFoundException
     */
    public ArrayList<Subgrup> getSubgrups(int num) throws NotFoundException {
        if (grups.containsKey(num)) {
            return grups.get(num).getSubgrups();
        } else {
            throw new NotFoundException("Grup not found");
        }
    }

    /**
     * Obtenir un grup concret d'aquesta assignatura
     *
     * @param num nombre del grup
     * @return grup amb número identificador sol·licitat
     * @throws NotFoundException
     */
    public Grup getGrup(int num) throws NotFoundException {
        if (grups.containsKey(num)) {
            return grups.get(num);
        } else {
            throw new NotFoundException("Grup not found");
        }
    }

    /**
     * Actualitza la informació de les sessions de laboratori
     *
     * @param numSessions     sessions de laboratori
     * @param duracioSessions duracio de les sessions
     */
    public void setLaboratori(int numSessions, int duracioSessions) {
        this.laboratori = new Teoria(numSessions, duracioSessions);
    }

    /**
     * Actualitza el nom de l'assignatura
     *
     * @param nom nom de l'assignatura
     */
    public void setNom(String nom) {
        this.nom = nom;
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
     * @throws RestriccioIntegritatException
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
     * @throws RestriccioIntegritatException
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
     * @throws NotFoundException
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
     */
    public ArrayList<Assignatura> getCorrequisits() {
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

    @Override
    public String toString() {
        return this.nom.toUpperCase();
    }
}