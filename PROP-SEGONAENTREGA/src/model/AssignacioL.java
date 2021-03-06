/**
 * @Author Adria Cabeza
 */
package model;

public class AssignacioL extends Assignacio {
    private Subgrup subgrup;

    /**
     * Crea una assignació de laboratori
     *
     * @param diaSetmana dia de la setmana pot ser un número del 1 al 5
     * @param hora       hora a la que se li assigna l'assignacio
     * @param aula       identificador d'una aula a la que se li ha fet una assignació
     */
    public AssignacioL(String diaSetmana, int hora, Aula aula, Assignatura assignatura, Subgrup subgrup) {
        super(diaSetmana, hora, aula, assignatura);
        this.subgrup = subgrup;
    }

    /**
     * Obtenir el subgrup
     *
     * @return subgrup de l'assignació
     */
    @Override
    public Subgrup getGrup() {
        return subgrup;
    }

    /**
     * Actualitza el subgrup
     *
     * @param subgrup de l'assignació
     */
    public void setSubgrup(Subgrup subgrup) {
        this.subgrup = subgrup;
    }
}
