package model;

import java.util.ArrayList;

public class PlaEstudis {
    private String nomTitulacio;
    private int any;
    private ArrayList<String> assignatures;
    private boolean obsolet;

    /**
     * Crea un Pla d'Estudis nou amb la seva informació corresponent
     *
     * @param nomTitulacio nom de la titulació
     * @param any          any que es crea el pla d'estudis
     * @param obsolet      booleà que indica si està obsolet
     */
    public PlaEstudis(String nomTitulacio, int any, boolean obsolet) {
        this.nomTitulacio = nomTitulacio;
        this.any = any;
        this.obsolet = obsolet;
        this.assignatures = new ArrayList<>();
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

    public int getAny() {
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
    public void setAny(int any) {
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

    /**
     * Afegeix una assignatura al pla d'estudis
     *
     * @param a que és l'objecte assignatura
     */
    public void afegirAssignatura(String a) {
        this.assignatures.add(a);
    }

    public boolean hasAssignatura(String nomA) {
        return assignatures.contains(nomA);
    }

    /**
     * Esborra una assignatura del pla d'estudis
     *
     * @param nomA que és el nom de l'assignatura
     */
    public void esborrarAssignatura(String nomA) {
        this.assignatures.remove(nomA);
    }

    /**
     * Retorna el conjunt d'assignatures del pla d'estudis
     *
     * @return assignatures
     */
    public ArrayList<String> getAssignatures() {
        return assignatures;
    }

    @Override
    public boolean equals(Object obj) {
        PlaEstudis ps = (PlaEstudis) obj;
        return ps.getNomTitulacio().equals(this.nomTitulacio) && this.any == ps.getAny();
    }
}
