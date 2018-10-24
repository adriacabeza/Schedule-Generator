package model;

import java.util.Date;

/**
 * @author Adrià Cabeza
 */
public abstract class Assignacio {

    private String diaSetmana;
    private int hora;
    private int aula;

    /**
    Crea una assignació nova amb la informació corresponent
    @param diaSetmana dia de la setmana pot ser un número del 1 al 5
    @param hora hora a la que se li assigna l'assignacio
    TODO: mirar si hem de guardar la hora de finalització
    @param aula identificador d'una aula a la que se li ha fet una assignació
     */

    public Assignacio(String diaSetmana, int hora, int aula) {
        this.diaSetmana = diaSetmana;
        this.hora = hora;
        this.aula = aula;
    }

    /********** GETTERS ********/


    /** Obtenir dia de la setmana
     * @return dia diaSetmana
     */
    public String getDiaSetmana() {
        return diaSetmana;
    }

    /** Obtenir la hora de l'assignació
     * @return hora de l'assignació
     */
    public int getHora() {
        return hora;
    }

    /** Obtenir l'aula de l'assignació
     * @return aula de l'assignació
     */
    public int getAula() {
        return aula;
    }

    /****** SETTERS ********/


    /** Actualitza el dia de la setmana
     * @param diaSetmana
     */
    public void setDiaSetmana(String diaSetmana) {
        this.diaSetmana = diaSetmana;
    }


    /** Actualitza la hora de l'assignació
     * @param hora
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /** Actualitza l'aula de l'assignació
     * @param aula
     */
    public void setAula(int aula) {
        this.aula = aula;
    }
}
