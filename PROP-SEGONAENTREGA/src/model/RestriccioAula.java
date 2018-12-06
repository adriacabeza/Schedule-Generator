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
    @Override   //why tf is override wrong?
    public boolean isAble(Aula aula, SessioGrup assig) {
        return (aula.getTipusAula() == assig.getSessio().gettAula());
    }
}
