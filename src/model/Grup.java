package model;
import java.util.ArrayList;

public class Grup{


    private int capacitat;
    private String nom;
    private ArrayList<AssignacioT> teories;
    private ArrayList<Subgrup> subgrups;
    
    
    
    /**
    * Crea un nou grup amb la informació pertinent
    * @param capacitat capacitat del grup
    * @param nom nom del grup
    * @param teo conjunt de classes de teoria que te el grup
    * @param subgrups subgrups del qual es composa el grup
    */
    public Grup(int capacitat, String nom, ArrayList<AssignacioT> teo, ArrayList<Subgrup> subgrups){
        this.capacitat = capacitat;
        this.nom = nom;
        this.teories = teo;
        this.subgrups = subgrups;
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
    * @return nom del grup
    */
    public String getNom(){
        return nom;
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
    public ArrayList<Subgrup> getSubgrups(){
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
    * @param nom nou nom del grup
    */
    public void setNom(String nom){
        this.nom = nom;
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
    public void setSubgrups(ArrayList<Subgrup> s){
        this.subgrups = s;
    }
    
}
