/**
 * @author Antoni Rambla
 */
package model;

public abstract class RestriccioNAria extends Restriccions{

    /**
     * Constructora de restricció n-aria
     * @param id identificador de la restricció
     */
    public RestriccioNAria(int id) {
        super(id);
    }


    /**
     * Retorna si es possible realitzar una assignació segons una restricció n-aria
     * @param assig assignatura
     * @param aula aula
     * @param dia dia
     * @param hora hora
     * @return
     */
    public abstract boolean isAble(String assig, Aula aula, int dia, int hora);


}
