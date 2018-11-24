/**
 * @author Aina Garcia
 */

package stubs;

import java.util.ArrayList;

public class PlaEstudis {
    private String nomTitulacio;
    private int any;
    private ArrayList<String> assignatures;
    private boolean obsolet;

    public PlaEstudis(String nomTitulacio, int any, boolean obsolet) {
        this.nomTitulacio = "Informatica";
        this.any = 2010;
        this.obsolet = false;
        this.assignatures = new ArrayList<>();
        this.assignatures.add("AC");
    }

    public String getNomTitulacio() {
        return "Informatica";
    }

    public int getAny() {
        return 2010;
    }

    public boolean isObsolet() {
        return false;
    }

    public void setNomTitulacio(String nomTitulacio) {
    }

    public void setAny(int any) {
    }


    public void setObsolet(boolean obsolet) {

    }


    public void afegirAssignatura(String a) {
    }

    public boolean hasAssignatura(String nomA) {
        return false;
    }

    public void esborrarAssignatura(String nomA) {
    }

    public ArrayList<String> getAssignatures() {
        return assignatures;
    }

    @Override
    public boolean equals(Object obj) {
        PlaEstudis ps = (PlaEstudis) obj;
        return ps.getNomTitulacio().equals(this.nomTitulacio) && this.any == ps.getAny();
    }
}
