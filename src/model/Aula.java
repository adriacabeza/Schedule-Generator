package model;

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
    private ArrayList<Assignacio> assignacions;

    /**
     * Crea una nova aula
     *
     * @param edifici      indica el nom del edifici on esta situada l'aula
     * @param planta       indica la planta on esta situada l'aula
     * @param aula         indica el numero que tindra l'aula
     * @param tipusAula    indica el tipus que es l'aula //TODO: toni comenta que quizás no haga falta guardarlo
     * @param assignacions indica totes les assigancions que te aquesta aula //TODO no haurien d'estar
     */ //TODO no inicialitzes la capacitat?
    public Aula(String edifici, int planta, int aula, TipusAula tipusAula, ArrayList<Assignacio> assignacions) {
        this.edifici = edifici;
        this.planta = planta;
        this.aula = aula;
        this.tAula = tipusAula;
        this.assignacions = assignacions;
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

    /**
     * Obtenir les assignacions
     *
     * @return assignacions
     */
    public ArrayList<Assignacio> getAssignacions() {
        return assignacions;
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

    /**
     * Actualitza les assignacions
     *
     * @param assignacions indica les noves assignacions
     */
    public void setAssignacions(ArrayList<Assignacio> assignacions) {
        this.assignacions = assignacions;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }
}
