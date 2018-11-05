package model;

public class AssignacioL extends Assignacio {
    private Aula.TipusAula tipusAula;
    private Subgrup subgrup;

    /**
     * Crea una assignació de laboratori
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignacio
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     * @param tipusAula  tipus d'aula
     */
    public AssignacioL(String diaSetmana, int hora, Aula aula, Aula.TipusAula tipusAula, Assignatura assignatura, Subgrup subgrup) {
        super(diaSetmana, hora, aula, assignatura);
        this.tipusAula = tipusAula;
        this.subgrup = subgrup;
    }


    /**
     * Obtenir tipus d'aula
     *
     * @return tipusAula
     */
    public Aula.TipusAula getTipusAula() {
        return tipusAula;
    }

    /**
     * Actualitza el tipus d'aula
     *
     * @param tipusAula
     */

    public void setTipusAula(Aula.TipusAula tipusAula) {
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
