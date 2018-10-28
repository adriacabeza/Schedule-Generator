package model;

import java.util.ArrayList;

public class Subgrup extends Grup {
	
	private Grup grup;
	private ArrayList<AssignacioT> laboratoris;
	/**
	 * Crea un nou subgrup 
	 * @param capacitat capacitat del grup
     * @param num num del subgrup
     * @param pare grup pare del subgrup
	 */
	public Subgrup(int num, int capacitat, Grup pare ) {
		this.num = num;
		this.capacitat = capacitat;
		this.grup = pare;
	}
}
