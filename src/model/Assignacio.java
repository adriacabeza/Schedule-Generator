/**
 * @Author Adria Cabeza
 */
package model;


public abstract class Assignacio {

    protected String diaSetmana;
    protected int hora;
    protected Aula aula;
    protected Assignatura assignatura;

    /**
     * Crea una assignació nova amb la informació corresponent
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignacio
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     */

    public Assignacio(String diaSetmana, int hora, Aula aula, Assignatura assignatura) {
        this.diaSetmana = diaSetmana;
        this.hora = hora;
        this.aula = aula;
        this.assignatura = assignatura;
    }

    /**
     * Obtenir dia de la setmana
     *
     * @return dia diaSetmana
     */
    public String getDiaSetmana() {
        return diaSetmana;
    }

    /**
     * Obtenir la hora de l'assignació
     *
     * @return hora de l'assignació
     */
    public int getHora() {
        return hora;
    }

    /**
     * Obtenir l'aula de l'assignació
     *
     * @return aula de l'assignació
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * Obtenir una assignatura d'una assignació
     *
     * @return assignatura
     */

    public Assignatura getAssignatura() {
        return assignatura;
    }


    /**
     * Obtenir el grup
     *
     * @return grup
     */
    public abstract Grup getGrup();


    /**
     * Actualitza el dia de la setmana
     *
     * @param diaSetmana nou dia de la setmana per a l'assignació
     */
    public void setDiaSetmana(String diaSetmana) {
        this.diaSetmana = diaSetmana;
    }


    /**
     * Actualitza la hora de l'assignació
     *
     * @param hora nova hora de l'assignació
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * Actualitza l'aula de l'assignació
     *
     * @param aula nova aula de l'assignació
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }


    /**
     * Actualitza l'assignatura d'una assignació
     *
     * @param assignatura nova assignatura de l'assignació
     */
    public void setAssignatura(Assignatura assignatura) {
        this.assignatura = assignatura;
    }

    /*    @Override*/

    /**
     * Defineix l'igualtat per a dues assignacions
     *
     * @param obj assignació a la que comparar
     * @return true si les dues assignacions son iguals i false si no ho son
     */
    public boolean equals(Assignacio obj) {
        return (this.diaSetmana.equals(obj.getDiaSetmana()) && (this.hora == obj.getHora()) && (this.aula == obj.getAula()) && (this.assignatura == obj.getAssignatura()));
    }
}
