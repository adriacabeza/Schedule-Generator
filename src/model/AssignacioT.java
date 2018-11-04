package model;

public class AssignacioT extends Assignacio {
    private String tipusAula;
    private Grup grup;

    /**
     * Crea una assignació de teoria
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignació
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     */

    public AssignacioT(String diaSetmana, int hora, Aula aula, String tipusAula, Assignatura assignatura, Grup grup) {
        super(diaSetmana, hora, aula,assignatura);
        this.grup = grup;
        this.tipusAula = tipusAula;
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
     * Obtenir el grup
     * @return grup
     */
    public Grup getGrup() {
        return grup;
    }

    /**
     * Actualitza el grup de l'assignació
     * @param grup
     */
    public void setGrup(Grup grup) {
        this.grup = grup;
    }
}
