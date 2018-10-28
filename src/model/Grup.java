package model;
import java.util.ArrayList;
import java.util.Map;

public class Grup{


    protected int capacitat;
    protected int num;
    protected ArrayList<AssignacioT> teories;
    protected Map<Integer, Grup> subgrups;
    
    
    
    /**
    * Crea un nou grup amb la informació pertinent
    * @param capacitat capacitat del grup
    * @param num num del grup
    * @param num_subgrups subgrups del qual es composa el grup
    */
    public Grup(int num, int capacitat, int num_subgrups){
        this.capacitat = capacitat;
        this.num = num;
        for (int j = 1; j<=num_subgrups; j++){
            subgrups.put(num+j, new Subgrup(num+j, capacitat/num_subgrups,this));
        }
    }
    
    
    
    /********** GETTERS ********/
    
    
    /**
    * Obtenir la capacitat del grup
    * @return capacitat del grup
    */
    public int getCapacitat(){
        return capacitat;
    }
    
    
    /**
    * Obtenir el nom del grup
    * @return num del grup
    */
    public int getNum(){
        return num;
    }
    
    
    /**
    * Obtenir les sessions de teoria del grup
    * @return les sessions de teoria del grup
    */
    public ArrayList<AssignacioT> getTeoria(){
        return teories;
    }
    
    
    /**
    * Obtenir els subgrups del grup
    * @return els subgrups del grup
    */
    public Map<Integer, Grup> getSubgrups(){
        return subgrups;
    }
    
    
    
    /****** SETTERS ********/
    
    
    
    /**
    * Actualitza la capacitat del grup
    * @param capacitat nova capacitat del grup
    */
    public void setCapacitat(int capacitat){
        this.capacitat = capacitat;
    }
    
    
    /**
    * Actualitza el nom del grup
    * @param num nou nom del grup
    */
    public void setNom(int num){
        this.num = num;
    }
    
    
    /**
    * Actualitza les sessions de teoria del grup
    * @param teories noves sessions de teoria
    */
    public void setAssignacioT(ArrayList<AssignacioT> teories){
        this.teories = teories;
    }
    
    
    /**
    * Actualitza els subgrups del grup
    * @param s nous subgrups
    */
    public void setSubgrups(Map<Integer, Grup> s){
        this.subgrups = s;
    }
    
}
/* 
grup (int i, grup cap, num sub){
  name = i;
  grupcap = grupcap
    for (int j = 1; j<=numsub; j++){
		subgrup.insert(i+j, new subgrup(i+j, cap/sub,this));
    }
} 
                       
                       
                       subgrup (int i, subgrupcap, Grup pare);

*/
