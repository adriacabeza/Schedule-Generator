package model;

import java.util.HashMap;

public class Grup {


    protected int capacitat;
    protected int num;
    protected HashMap<Integer, Subgrup> subgrups = new HashMap<Integer, Subgrup>();
    //TODO: mirar si hay que mantener la lista de Assignacio desde grupo tmbn

    /**
     * Crea un nou grup amb la informació pertinent
     *
     * @param capacitat capacitat del grup
     * @param num       nom del grup
     * @param subgrups  subgrups del qual es composa el grup
     */
    public Grup(int num, int capacitat, int subgrups) {    //TODO: throwear exception si no es multiplo 10? ie: 10,20,30?
        this.num = num;
        this.capacitat = capacitat;
        for (int j = 1; j<=subgrups; j++){
            this.subgrups.put(num+j, new Subgrup(num+j, capacitat/subgrups,0));
        }
    }


    /********** GETTERS ********/


    /**
     * Obtenir la capacitat del grup
     *
     * @return capacitat del grup
     */
    public int getCapacitat() {
        return capacitat;
    }


    /**
     * Obtenir el nom del grup
     *
     * @return nom del grup
     */
    public int getNum() {
        return num;
    }




    /**
     * Obtenir els subgrups del grup
     *
     * @return els subgrups del grup
     */
    public HashMap<Integer, Subgrup> getSubgrups() {
        return subgrups;
    }


    /****** SETTERS ********/       //TODO: al modificar aquestes caracteristiques tmb hauriem de modificar els subgrups


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
    public void setNum(int num){
        this.num = num;
    }   //TODO: modificar num identificador de subgrups

    @Override
    public String toString() {
        return "id: " + num + " capacitat: " + capacitat;
    }
}

