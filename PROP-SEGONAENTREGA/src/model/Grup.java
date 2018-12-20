/**
 * @Author Antoni Rambla
 */

package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Grup {


    protected int capacitat;
    protected int num;
    private HashMap<Integer, Subgrup> subgrups = new HashMap<Integer, Subgrup>();

    /**
     * Crea un nou grup amb la informació pertinent
     *
     * @param capacitat capacitat del grup
     * @param num       nom del grup
     * @param subgrups  subgrups del qual es composa el grup
     */
    public Grup(int num, int capacitat, int subgrups) {
        this.num = num;
        //if(num %10 != 0) throw new IllegalArgumentException("El número de grup ha de ser múltiple de 10");
        this.capacitat = capacitat;
        for (int j = 1; j <= subgrups; j++) {
            this.subgrups.put(num + j, new Subgrup(num + j, capacitat / subgrups, 0));
        }
    }

    /**
     * Obtenir la capacitat del grup
     *
     * @return capacitat del grup
     */
    public int getCapacitat() {
        return capacitat;
    }


    /**
     * Obtenir el número del grup
     *
     * @return num el número del grup
     */
    public int getNum() {
        return num;
    }


    /**
     * Obtenir els subgrups del grup
     *
     * @return subgrups els subgrups del grup
     */
    public HashMap<Integer, Subgrup> getSubgrups() {
        return subgrups;
    }


    /**
     * Actualitza la capacitat del grup
     *
     * @param capacitat nova capacitat del grup
     */
    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }


    /**
     * Actualitza el nom del grup
     *
     * @param num nou nom del grup
     */
    public void setNum(int num) {
        this.num = num;
    }


    /**
     * Obtenir el grup en format String
     *
     * @return el grup en format string
     */
    @Override
    public String toString() {
        return "id: " + num + " capacitat: " + capacitat;
    }

    /**
     * Obtenir els subgrups del grup
     *
     * @return la llista de subgrups
     */
    public ArrayList<String> getLlistaSubgrups() {
        ArrayList<String> output = new ArrayList<>();
        for (Subgrup s: subgrups.values()) {
            output.add(String.valueOf(s.getNum()));
        }
        return output;
    }
}

