package model;

import java.util.ArrayList;
import java.util.Date;

public class PlaEstudis {
    private String nomTitulacio;
    private Date any;
    private boolean obsolet;
    private ArrayList<Quadrimestre> quadrimestres; //TODO:preguntar si hauria de ser un ArrayList de quadrimestres


    /**
     * Crea un Pla d'Estudis nou amb la seva informació corresponent
     * @param nomTitulacio nom de la titulació
     * @param any any que es crea el pla d'estudis
     * @param obsolet booleà que indica si està obsolet
     * @param quadrimestres quadrimestres que conté el pla d'estudis
     */
    public PlaEstudis(String nomTitulacio, Date any, boolean obsolet, ArrayList<Quadrimestre> quadrimestres) {
        this.nomTitulacio = nomTitulacio;
        this.any = any;
        this.obsolet = obsolet;
        this.quadrimestres = quadrimestres;
    }

    /********** GETTERS ********/

    /**
     * Obtenir el nom de la titulació
     * @return nomTitulacio
     */
    public String getNomTitulacio() {
        return nomTitulacio;
    }

    /**
     * Obtenir any del pla d'estudis
     * @return any del pla d'estudis
     */

    public Date getAny() {
        return any;
    }

    /**
     * Obtenir els quadrimestres del pla d'estudis
     * @return quadrimestres
     */

    public ArrayList<Quadrimestre> getQuadrimestres() {
        return quadrimestres;
    }

    /**
     * Et retorna si si el pla d'estudis està obsolet
     * @return obsolet
     */
    public boolean isObsolet() {
        return obsolet;
    }

    /****** SETTERS ********/


    /**
     * Actualitza el nom de la titulació
     * @param nomTitulacio
     */
    public void setNomTitulacio(String nomTitulacio) {
        this.nomTitulacio = nomTitulacio;
    }

    /**
     * Actualitza l'any del pla d'estudis
     * @param any
     */
    public void setAny(Date any) {
        this.any = any;
    }


    /**
     * Actualitza la informació de si sobre està obsolet
     * @param obsolet
     */
    public void setObsolet(boolean obsolet) {
        this.obsolet = obsolet;
    }

    /**
     * Actualitza els quadrimestres del pla d'estudis
     * @param quadrimestres
     */
    public void setQuadrimestres(ArrayList<Quadrimestre> quadrimestres) {
        this.quadrimestres = quadrimestres;
    }

}
