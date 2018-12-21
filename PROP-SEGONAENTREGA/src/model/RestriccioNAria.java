/**
 * @author Antoni Rambla
 */
package model;

public abstract class RestriccioNAria extends Restriccions{

    /**
     * Es crea una restricció
     *
     * @param id identificador de la restricció
     */
    public RestriccioNAria(int id) {
        super(id);
    }

    public abstract boolean isAble(String assig, Aula aula, int dia, int hora);


}
