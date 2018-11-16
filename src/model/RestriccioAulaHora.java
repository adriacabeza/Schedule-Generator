/**
 * @Author Antoni Rambla
 */

package model;

public class RestriccioAulaHora extends Restriccions {

    private int dia;
    private int hora;
    private Aula aula;

    /**
     * Crea una restricció on es comprova si es pot realitzar una assignació en una determinada aula en una determinada hora d'un determinat dia
     *
     * @param dia  dia a comprovar
     * @param hora hora a comprovar
     * @param aula aula a comprovar
     */
    public RestriccioAulaHora(int dia, int hora, Aula aula) {
        super(7);
        this.dia = dia;
        this.hora = hora;
        this.aula = aula;
    }

    /**
     * Retorna si es possible realitzar una assignació en una determinada aula en una determinada hora d'un determinat dia
     *
     * @param aula aula que es comprova
     * @param dia  dia que es comprova
     * @param hora hora que es comprova
     * @return true si es pot realitzar l'assignació
     */
    public boolean isAble(Aula aula, int dia, int hora) {
        if (aula.getKey().equals(this.aula.getKey()) && dia == this.dia && hora == this.hora) return false;
        return true;
    }

    /**
     * Retorna el dia d'una restricció d'aula en una hora en un dia determinat
     * @return dia que es comprova a la restrició
     */
    public int getDia(){
        return dia;
    }

    /**
     * Retorna la hora d'una restricció d'aula en una hora en un dia determinat
     * @return dia que es comprova a la restrició
     */
    public int getHora(){
        return hora;
    }


    /**
     * Retorna l'aula d'una restricció d'aula en una hora
     * @return aula que es comprova a la restrició
     */
    public Aula getAula(){
        return aula;
    }

}
