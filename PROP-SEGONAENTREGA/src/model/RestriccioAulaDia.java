/**
 * @Author Antoni Rambla
 */

package model;

public class RestriccioAulaDia extends Restriccions {

    private int dia;
    private Aula aula;


    /**
     * Crea una restricció on es comprova si es pot realitzar una assignació en una determinada aula en un determinat dia
     *
     * @param dia dia a comprovar
     */
    public RestriccioAulaDia(int dia, Aula aula) {
        super(6);
        this.dia = dia;
        this.aula = aula;
    }

    /**
     * Retorna si es possible realitzar una assignació en una determinada aula en un determinat dia
     *
     * @param aula aula que es comprova
     * @param dia  dia que es comprova
     * @return true si es pot realitzar l'assignació
     */
    public boolean isAble(Aula aula, int dia) {
        if (this.dia == dia && aula.getKey().equals(this.aula.getKey())) return false;
        return true;
    }

    /**
     * Retorna el dia d'una restricció d'aula en un dia
     *
     * @return dia que es comprova a la restrició
     */
    public int getDia() {
        return dia;
    }

    /**
     * Retorna l'aula d'una restricció d'aula en un dia
     *
     * @return aula que es comprova a la restrició
     */
    public Aula getAula() {
        return aula;
    }

}

