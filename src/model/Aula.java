package model;
import java.util.ArrayList;

public class Aula {
	
	private String edifici;
	private int planta;
	private int aula;
	private String tipusAula;
	private ArrayList<Assignacio> assignacions;
	
	
	/**
	 * Crea una nova aula
	 * @param edifici indica el nom del edifici on esta situada l'aula
	 * @param planta indica la planta on esta situada l'aula
	 * @param aula indica el numero que tindra l'aula
	 * @param tipusAula indica el tipus que es l'aula
	 * @param assignacions indica totes les assigancions que te aquesta aula
	 */
	public Aula(String edifici, int planta, int aula, String tipusAula, ArrayList<Assignacio> assignacions) {
		this.edifici = edifici;
		this.planta = planta;
		this.aula = aula;
		this.tipusAula = tipusAula;
		this.assignacions = assignacions;
	}
	

    
    
    /********** GETTERS ********/
    
    
    
	/**
	 * Obtenir l'edifici de l'aula
	 * @return l'edifici de l'aula
	 */
	public String getEdifici() {
		return edifici;
	}
	
	
	/**
	 * Obtenir la planta de l'aula
	 * @return la planta de l'aula
	 */
	public int getPlanta() {
		return planta;
	}
	
	
	/**
	 * Obtenir el numero de l'aula
	 * @return el numero de l'aula 
	 */
	public int getAula() {
		return aula;
	}
	
	
	/**
	 * Obtenir el tipus de aula
	 * @return el tipus de aula
	 */
	public String getTipusAula() {
		return tipusAula;
	}
	
	
	/**
	 * Obtenir les assigancions que t� aquesta aula
	 * @return les assignacions que t� l'aula
	 */
	public ArrayList<Assignacio> getAssignacions(){
		return assignacions;
	}
	
    
    
    /****** SETTERS ********/
    
    
    
	/**
	 * Actualitza l'edifici de l'aula
	 * @param edifici el nou nom de l'edifici
	 */
	public void setEdifici(String edifici) {
		this.edifici = edifici;
	}
	
	
	/**
	 * Actualitza la planta on es situa l'aula
	 * @param planta la nova planta de l'aula
	 */
	public void setPlanta(int planta) {
		this.planta = planta;
	}
	
	
	/**
	 * Actualitza el n�mero de aula
	 * @param aula indica el nou numero d'aula
	 */
	public void setAula(int aula) {
		this.aula = aula;
	}
	
	
	/**
	 * Actualitza el tipus d'aula
	 * @param tipusaula indica el nou tipus d'aula
	 */
	public void setTipusAula(String tipusaula) {
		this.tipusAula = tipusaula;
	}
	
	
	/**
	 * Actualitza les assignacions corresponents a una aula
	 * @param assignacions indica les noves assignacions a la aula
	 */
	public void setAssignacions(ArrayList<Assignacio> assignacions) {
		this.assignacions = assignacions;
	}
	
}
