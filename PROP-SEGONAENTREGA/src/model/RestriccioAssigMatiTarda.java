/**
 * @Author Adria Cabeza
 */

package model;

public class RestriccioAssigMatiTarda extends Restriccions {

    private String assig;
    private boolean mati;       //true implica mati, false implica tarda


    /**
     * Crea una restricció on es comprova que en una sessió d'una determinada assignatura és realitzi de matí o de tarda
     *
     * @param assig assignatura a comprovar
     * @param mati  booleà que indica si es de matí o tarda, true es matí, false és tarda
     */
    public RestriccioAssigMatiTarda(String assig, boolean mati) {
        super(8);
        this.assig = assig;
        this.mati = mati;
    }

    /**
     * Retorna si es possible realitzar una assignació d'una assignatura determinada en una hora
     *
     * @param assig assignatura de l'assignació
     * @param hora  hora de l'assignació
     * @return true si es pot realitzar
     */
    public boolean isAble(Assignatura assig, int hora) {
        if (this.assig.equals(assig.getNom())) {
            if (mati) return (hora <= 6);
            return (hora > 6);
        }
        return true;
    }


    /**
     * Obtenir el nom de l'assignatura d'una restricció matí o tarda
     *
     * @return el nom de l'assignatura
     */
    public String getAssig() {
        return assig;
    }

    /**
     * Obtenir sí una restricció és de matí o de tarda
     *
     * @return booleà true si és de matí o false si és de tarda
     */
    public boolean getMati() {
        return mati;
    }
}
