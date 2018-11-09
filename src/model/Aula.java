package model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Aula {

    public enum TipusAula {
        NORMAL,
        LABORATORI,
        PCS
    }

    private String edifici;
    private int planta;
    private int aula;

    private TipusAula tAula; //TODO cambiar tot el que va amb strings per TipusAula
    private int capacitat;

    @Expose(serialize = false)

    /**
     * Crea una nova aula
     *
     * @param edifici      indica el nom del edifici on esta situada l'aula
     * @param planta       indica la planta on esta situada l'aula
     * @param aula         indica el numero que tindra l'aula
     * @param tipusAula    indica el tipus que es l'aula //TODO: toni comenta que quizás no haga falta guardarlo
     * @param assignacions indica totes les assigancions que te aquesta aula //TODO no haurien d'estar
     */ //TODO no inicialitzes la capacitat?
    public Aula(String edifici, int planta, int aula, TipusAula tipusAula, int capacitat) {
        this.edifici = edifici;
        this.planta = planta;
        this.aula = aula;
        this.tAula = tipusAula;
        this.capacitat = capacitat;
    }


    /********** GETTERS ********/


    /**
     * Obtenir l'edifici de l'aula
     *
     * @return l'edifici de l'aula
     */
    public String getEdifici() {
        return edifici;
    }


    /**
     * Obtenir la planta de l'aula
     *
     * @return la planta de l'aula
     */
    public int getPlanta() {
        return planta;
    }


    /**
     * Obtenir el numero de l'aula
     *
     * @return el numero de l'aula
     */
    public int getAula() {
        return aula;
    }


    /**
     * Obtenir el tipus de aula
     *
     * @return el tipus de aula
     */
    public TipusAula getTipusAula() {
        return tAula;
    }

        /****** SETTERS ********/


    /**
     * Actualitza l'edifici de l'aula
     *
     * @param edifici el nou nom de l'edifici
     */
    public void setEdifici(String edifici) {
        this.edifici = edifici;
    }


    /**
     * Actualitza la planta on es situa l'aula
     *
     * @param planta la nova planta de l'aula
     */
    public void setPlanta(int planta) {
        this.planta = planta;
    }


    /**
     * Actualitza el n�mero de aula
     *
     * @param aula indica el nou numero d'aula
     */
    public void setAula(int aula) {
        this.aula = aula;
    }


    /**
     * Actualitza el tipus d'aula
     *
     * @param tipusaula indica el nou tipus d'aula
     */
    public void setTipusAula(TipusAula tipusaula) {
        this.tAula = tipusaula;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    public static TipusAula stringToTipusAula(String s) {
        if (s.equalsIgnoreCase("normal")) {
            return Aula.TipusAula.NORMAL;
        } else if (s.equalsIgnoreCase("pcs")) {
            return Aula.TipusAula.PCS;
        } else if (s.equalsIgnoreCase("laboratori")) {
            return Aula.TipusAula.LABORATORI;
        } else {
            return null;
        }
    }

    public static String crearkey(String edifici, int planta, int aula) {
        return edifici + String.valueOf(planta) + String.valueOf(aula);
    }

    public static String getedificifromKey(String key){
        return key.substring(0,2); //THAT MEANS THE WE ONLY HAVE BUILDING OF TWO CHARS
    }

    public static String getplantafromKey(String key){
        return key.substring(2,3);
    }

    public static String getaulafromKey(String key){
        return key.substring(3);
    }
}
