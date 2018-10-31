package model;

public class AssignacioT extends Assignacio {
    private String tipusAula;
    //TODO GUARDAR AQUI EL GRUP posarlo a la constructora

    /**
     * Crea una assignació de teoria
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignació
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     */

    public AssignacioT(String diaSetmana, int hora, int aula, String tipusAula) {
        super(diaSetmana, hora, aula);
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
}
