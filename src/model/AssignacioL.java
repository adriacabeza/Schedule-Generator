package model;

public class AssignacioL extends Assignacio {
    private String tipusAula;
    private Subgrup subgrup;

    /**
     * Crea una assignació de laboratori
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignacio
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     * @param tipusAula  tipus d'aula
     */
    public AssignacioL(String diaSetmana, int hora, int aula, String tipusAula, Assignatura assignatura, Subgrup subgrup) {
        super(diaSetmana, hora, aula, assignatura);
        this.tipusAula = tipusAula;
        this.subgrup = subgrup;
    }

    public AssignacioL(){
        super();
        this.tipusAula = null;
        this.subgrup = new Subgrup();
    }

    /**
     * Obtenir tipus d'aula
     *
     * @return tipusAula
     */
    public String getTipusAula() {
        return tipusAula;
    }

    /**
     * Actualitza el tipus d'aula
     *
     * @param tipusAula
     */

    public void setTipusAula(String tipusAula) {
        this.tipusAula = tipusAula;
    }


    /**
     * Obtenir el subgrup
     * @return subgrup de l'assignació
     */
    public Subgrup getSubgrup() {
        return subgrup;
    }

    /**
     * Actualitza el subgrup
     * @param subgrup de l'assignació
     */
    public void setSubgrup(Subgrup subgrup) {
        this.subgrup = subgrup;
    }
}
