/**
 * @Author Antoni Rambla
 */

package model;

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
}
