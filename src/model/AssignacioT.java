package model;

public class AssignacioT extends Assignacio {
    private Grup grup;

    /**
     * Crea una assignació de teoria
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignació
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     */

    public AssignacioT(String diaSetmana, int hora, Aula aula, Assignatura assignatura, Grup grup) {
        super(diaSetmana, hora, aula,assignatura);
        this.grup = grup;
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
