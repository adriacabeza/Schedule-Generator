/**
 * @author Antoni Rambla
 */
package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RestriccioBinaria extends Restriccions {
    /**
     * Constructora de la restricció binària
     * @param id identificador de la restricció
     */
    public RestriccioBinaria(int id) {
        super(id);
    }

    /**
     * Retorna si es possible realitzar una assignació segons una restricció binària
     * @param check sessió que s'ha de comprovar
     * @param assignat l'altre sessió grup que s'ha de comprovar
     * @param aula aula
     * @param pos conjunt de possibles ubicacions que té una sessió
     * @param aulaIndex enter que indica la posició de l'aula
     * @param dia dia
     * @param hora hora
     * @return true si es pot assignar segons la restricció binària i false si no es pot
     * @throws NotFoundException
     */
    public abstract boolean isAble2(SessioGrup check, SessioGrup assignat, Aula aula, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos, int aulaIndex, int dia, int hora) throws NotFoundException;
}
