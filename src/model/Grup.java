package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Grup {


    private int capacitat;
    private int num;
//    private HashMap<int, Subgrup> subgrups;
    private ArrayList<Subgrup> subgrups = new ArrayList<>(); //TODO hacerlo hashmap o algo con key un int que sea el numero de subgrupo


    /**
     * Crea un nou grup amb la informació pertinent
     *
     * @param capacitat capacitat del grup
     * @param num       nom del grup
     * @param teo       conjunt de classes de teoria que te el grup
     * @param subgrups  subgrups del qual es composa el grup
     */ //TODO hacer bien la constructora y crear desde aqui los subgrupos
    public Grup(int capacitat, int num, ArrayList<AssignacioT> teo, ArrayList<Subgrup> subgrups) {
        this.capacitat = capacitat;
        this.num = num;
        //this.teories = teo;
        this.subgrups = subgrups;
    }

    public Grup(){
        capacitat = -1;
        num = -1;
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
    public ArrayList<Subgrup> getSubgrups() {
        return subgrups;
    }


    /****** SETTERS ********/


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
    }
}
/* 
grup (int i, grup cap, num sub){
  name = i;
  grupcap = grupcap
    for (int j = 1; j<=numsub; j++){
		subgrup.insert(i+j, new subgrup(i+j, grupcap/numsub,this));
    }
} 
                       
                       
                       subgrup (int i, subgrupcap, Grup pare);

*/
