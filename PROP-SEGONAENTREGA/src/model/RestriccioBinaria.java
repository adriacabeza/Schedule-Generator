/**
 * @author Antoni Rambla
 */
package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RestriccioBinaria extends Restriccions {
    /**
     * Es crea una restricció
     *
     * @param id identificador de la restricció
     */
    public RestriccioBinaria(int id) {
        super(id);
    }

    public abstract boolean isAble2(SessioGrup check, SessioGrup assignat, Aula aula, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos, int aulaIndex, int dia, int hora) throws NotFoundException;
}
