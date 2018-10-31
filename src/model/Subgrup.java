package model;

import java.util.ArrayList;

public class Subgrup extends Grup {
	
	private Grup grup;
	// private ArrayList<AssignacioL> laboratoris; //TODO erase
	
	
	/**
	 * Crea un nou subgrup 
	 * @param capacitat capacitat del grup
     * @param nom nom del grup
     * @param teo conjunt de classes de teoria que te el grup
     * @param subgrups subgrups del qual es composa el grup
     * @param laboratoris laboratoris que t� el subgrup
	 */
	public Subgrup(int capacitat, String nom, ArrayList<AssignacioT> teo, ArrayList<Subgrup> subgrups, ArrayList<AssignacioL> laboratoris ) {
		super(capacitat,nom,teo,subgrups);
		//this.laboratoris = laboratoris; //TODO erase
	}
}
