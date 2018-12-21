/**
 * @Author Antoni Rambla
 */

package model;

import java.util.ArrayList;

public class Subgrup extends Grup {

    /**
     * Crea un nou subgrup
     *
     * @param capacitat capacitat del grup
     * @param num       nom del grup
     * @param subgrups  subgrups del qual es composa el grup
     */
    public Subgrup(int num, int capacitat, int subgrups) {
        super(num, capacitat, subgrups);
    }


    /**
     * Retorna la llista de subgrups d'un grup
     * @return la llista de subgrups
     */
    @Override
    public ArrayList<String> getLlistaSubgrups() {
        return null;
    }
}
