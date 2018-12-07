package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Slot {

    private StringProperty slotname;
    private StringProperty dilluns;
    private StringProperty dimarts;
    private StringProperty dimecres;
    private StringProperty dijous;
    private StringProperty divendres;

    /**
     * Model de dades per les files de la taula d'horaris.
     * @param dilluns Assignatura per aquest slot del dilluns, amb format "Abbvr \n Grup \n Aula"
     * @param dimarts Assignatura per aquest slot del dilluns, amb format "Abbvr \n Grup \n Aula"
     * @param dimecres Assignatura per aquest slot del dilluns, amb format "Abbvr \n Grup \n Aula"
     * @param dijous Assignatura per aquest slot del dilluns, amb format "Abbvr \n Grup \n Aula"
     * @param divendres Assignatura per aquest slot del dilluns, amb format "Abbvr \n Grup \n Aula"
     */
    public Slot(String slotname, String dilluns, String dimarts, String dimecres, String dijous, String divendres){
        this.slotname = new SimpleStringProperty(slotname);
        this.dilluns = new SimpleStringProperty(dilluns);
        this.dimarts = new SimpleStringProperty(dimarts);
        this.dimecres = new SimpleStringProperty(dimecres);
        this.dijous = new SimpleStringProperty(dijous);
        this.divendres = new SimpleStringProperty(divendres);
    }

    public String getSlotname() {
        return slotname.get();
    }

    public String getDilluns() {
        return dilluns.get();
    }

    public String getDimarts() {
        return dimarts.get();
    }

    public String getDimecres() {
        return dimecres.get();
    }

    public String getDijous() {
        return dijous.get();
    }

    public String getDivendres() {
        return divendres.get();
    }
}
