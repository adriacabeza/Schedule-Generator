package model;
import java.util.ArrayList;

public class Quadrimestre {

    private int nivell;
    private String especialitat; 
    private PlaEstudis plaestudis;
    private ArrayList<Assignatura> assignatures;
    
    /**
    * Crea un nou quadrimestre amb el nivell, especialitat i informació pertinent
    * @param nivell nivell del quadrimestre
    * @param especialitat especialitat del cuadrimestre
    * @param p pla d'estudis al qual pertany el quadrimestre
    * @param a conjunt d'assignatures del qual es composa el quadrimestre
    */
    public Quadrimestre(int nivell, String especialitat, PlaEstudis p, ArrayList<Assignatura> a){
        this.nivell = nivell;
        this.especialitat = especialitat;
        this.plaestudis = p;
        this.assignatures = a;              //mirar si assignatures[].size() >= 4 ?
        
    }
    
    
    
    /********** GETTERS ********/
    
    
    
    /**
    * Obtenir el nivell del quadrimestre
    * @return nivell del quadrimestre
    */
    public int getNivell(){
        return nivell;
    }
    
    
    /**
    * Obtenir la especialitat del Quadrimestre
    * @return especialitat del Quadrimestre
    */
    public String getEspecialitat(){
        return especialitat;
    }
    
    
    /**
    * Obtenir el pla d'estudis del Qadrimestre
    * @return Pla d'Estudis
    */
    public PlaEstudis getPlaEstudis(){
        return plaestudis;
    }
    
    
    /**
    * Obtenir les assignatures del Qaudrimestre
    * @return Assignatures del Quadrimestre
    */
    public ArrayList<Assignatura> getAssignatures(){
        return assignatures;
    }
    
    
    
    /****** SETTERS ********/
    
    
    
    /**
    * Actualitza el nivell del Quadrimestre
    * @param nivell el nou nivell del Quadrimestre
    */
    public void setNivell(int nivell){
        this.nivell = nivell;
    }
    
    
    /**
    * Actualitza la especialitat del Quadrimestre
    * @param especialitat nom de la nova especialitat
    */
    public void setEspecialitat(String especialitat){
        this.especialitat = especialitat;
    }
    
    
    /**
    * Actualitza el pla d'estudis al cual pertany el Quadrimestre
    * @param p pla d'estudis nou
    */
    public void setPlaEstudis(PlaEstudis p){
        this.plaestudis = p;
    }
    
    
    /**
    * Actualitza les assignatures del Quadrimestre
    * @param a noves assignatures
    */
    public void setAssignatures(ArrayList<Assignatura> a){      //mirar si assignatures.size() >= 4??
        this.assignatures = a;
    }
}
