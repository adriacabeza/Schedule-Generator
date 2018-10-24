package model;

public class AssignacioT extends Assignacio {

    /**
     * Crea una assignació de teoria
      @param diaSetmana dia de la setmana pot ser un número del 1 al 5
      @param hora hora a la que se li assigna l'assignació
      @param aula identificador d'una aula a la que se li ha fet una assignació
     */

    public AssignacioT(String diaSetmana, int hora, int aula) {
        super(diaSetmana, hora, aula);
    }
}
