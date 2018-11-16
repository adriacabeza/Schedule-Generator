/**
 * @Author Antoni Rambla
 */
package model;

public abstract class Restriccions {

    protected int id;
//que restriccions torni un arraylist dels dies i les horesque podria la susodicha asignatura

    /**
     * Es crea una restricció
     *
     * @param id identificador de la restricció
     */
    public Restriccions(int id) {
        this.id = id;
    }

    /**
     * Obtenir identificador de la restricció
     *
     * @return identificador de la restrcicció
     */
    public int getId() {
        return id;
    }

    /**
     * Actualitza l'identificador de la restricció
     *
     * @param id nou identificador de la restricció
     */
    public void setId(int id) {
        this.id = id;
    }


}
