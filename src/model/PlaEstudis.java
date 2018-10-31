package model;

import java.util.ArrayList;
import java.util.Date;

public class PlaEstudis {
    private String nomTitulacio;
    private Date any;
    private ArrayList<Assignatura> assignatures;
    private boolean obsolet;


    //TODO: void afegirAssignatura(Assignatura a)
    //TODO: void esborrarAssignatura(String nomA)
    //TODO: ArrayLsit<Assignatures> getAssignatures()

    /**
     * Crea un Pla d'Estudis nou amb la seva informació corresponent
     *
     * @param nomTitulacio nom de la titulació
     * @param any          any que es crea el pla d'estudis
     * @param obsolet      booleà que indica si està obsolet
     */
    public PlaEstudis(String nomTitulacio, Date any, boolean obsolet) {
        this.nomTitulacio = nomTitulacio;
        this.any = any;
        this.obsolet = obsolet;
    }

    /********** GETTERS ********/

    /**
     * Obtenir el nom de la titulació
     *
     * @return nomTitulacio
     */
    public String getNomTitulacio() {
        return nomTitulacio;
    }

    /**
     * Obtenir any del pla d'estudis
     *
     * @return any del pla d'estudis
     */

    public Date getAny() {
        return any;
    }

    /**
     * Et retorna si si el pla d'estudis està obsolet
     *
     * @return obsolet
     */
    public boolean isObsolet() {
        return obsolet;
    }

    /****** SETTERS ********/


    /**
     * Actualitza el nom de la titulació
     *
     * @param nomTitulacio
     */
    public void setNomTitulacio(String nomTitulacio) {
        this.nomTitulacio = nomTitulacio;
    }

    /**
     * Actualitza l'any del pla d'estudis
     *
     * @param any
     */
    public void setAny(Date any) {
        this.any = any;
    }


    /**
     * Actualitza la informació de si sobre està obsolet
     *
     * @param obsolet
     */
    public void setObsolet(boolean obsolet) {
        this.obsolet = obsolet;
    }

}
