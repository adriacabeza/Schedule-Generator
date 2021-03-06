/**
 * @Author Adria Cabeza
 */
package model;

public class RestriccioAula extends RestriccioUnaria {

    /**
     * Crea una restricció on es comproven la capacitat i tipus d'una aula
     */
    public RestriccioAula() {
        super(3);

    }

    /**
     * Retorna si és possible assignar una determinada aula a un grup segons la capacitat i tipus
     *
     * @param aula  aula que es comprova
     * @param assig assignatura que es comprova
     * @return true si es pot realitzar la assignació a l'aula
     */
    @Override
    public boolean isAble(int posaula, int dia, int hora, SessioGrup assig, int duracio, Aula aula, Assignacio[][][] horari) {
        return (aula.getTipusAula() == assig.getSessio().gettAula());
    }
}
