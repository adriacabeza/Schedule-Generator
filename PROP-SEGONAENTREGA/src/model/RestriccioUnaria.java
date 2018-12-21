/**
 * @author Adria Cabeza
 */
package model;

public abstract class RestriccioUnaria extends Restriccions {
    /**
     * Es crea una restricció
     *
     * @param id identificador de la restricció
     */
    public RestriccioUnaria(int id) {
        super(id);
    }

    public abstract boolean  isAble (int posaula, int dia, int hora, SessioGrup assig, int duracio, Aula aula, Assignacio[][][] horari);
}
