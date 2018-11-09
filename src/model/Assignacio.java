package model;



public abstract class Assignacio {

    protected String diaSetmana;
    protected Aula.TipusAula tipusAula;
    protected int hora;
    protected Aula aula;
    protected Assignatura assignatura;

    /**
     * Crea una assignació nova amb la informació corresponent
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignacio
     *                   TODO: mirar si hem de guardar la hora de finalització TONI APPROVES
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     */

    public Assignacio(String diaSetmana, int hora, Aula aula, Assignatura assignatura) {
        this.diaSetmana = diaSetmana;
        this.hora = hora;
        this.aula = aula;
        this.assignatura = assignatura;

    }

    /********** GETTERS ********/


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
     * @return assigntura de l'assignació
     */

    public Assignatura getAssignatura() {
        return assignatura;
    }

    /**
     * Obtenir tipus d'aula
     *
     * @return tipusAula
     */
    public Aula.TipusAula getTipusAula() {
        return tipusAula;
    }



    /****** SETTERS ********/


    /**
     * Actualitza el dia de la setmana
     *
     * @param diaSetmana
     */
    public void setDiaSetmana(String diaSetmana) {
        this.diaSetmana = diaSetmana;
    }


    /**
     * Actualitza la hora de l'assignació
     *
     * @param hora
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * Actualitza l'aula de l'assignació
     *
     * @param aula
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }


    /**
     * Actualitza l'assignatura d'una assignació
     * @param assignatura
     */
    public void setAssignatura(Assignatura assignatura) {
        this.assignatura = assignatura;
    }

    /**
     * Actualitza el tipus d'aula
     *
     * @param tipusAula
     */

    public void setTipusAula(Aula.TipusAula tipusAula) {
        this.tipusAula = tipusAula;
    }


/*    @Override*/
    public boolean equals(Assignacio obj) {
        return (this.diaSetmana.equals(obj.getDiaSetmana()) && (this.hora == obj.getHora()) && (this.aula == obj.getAula())  && (this.assignatura == obj.getAssignatura()));
    }
}
