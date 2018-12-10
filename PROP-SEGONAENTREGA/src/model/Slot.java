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
     */
    public Slot(String slotname) {
        this.slotname = new SimpleStringProperty(slotname);
        this.dilluns = new SimpleStringProperty("Buit");
        this.dimarts = new SimpleStringProperty("Buit");
        this.dimecres = new SimpleStringProperty("Buit");
        this.dijous = new SimpleStringProperty("Buit");
        this.divendres = new SimpleStringProperty("Buit");
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

    public void setDilluns(String dilluns) {
        this.dilluns.set(dilluns);
    }

    public void setDimarts(String dimarts) {
        this.dimarts.set(dimarts);
    }

    public void setDimecres(String dimecres) {
        this.dimecres.set(dimecres);
    }

    public void setDijous(String dijous) {
        this.dijous.set(dijous);
    }

    public void setDivendres(String divendres) {
        this.divendres.set(divendres);
    }

    public void setDia(String dia, String value) {
        switch (dia.toUpperCase()) {
            case "DILLUNS":
                setDilluns(value);
                break;
            case "DIMARTS":
                setDimarts(value);
                break;
            case "DIMECRES":
                setDimecres(value);
                break;
            case "DIJOUS":
                setDijous(value);
                break;
            case "DIVENDRES":
                setDivendres(value);
                break;
            default:
                break;
        }
    }

    /**
     * Formatter class for Slot
     *
     * @param abbvr
     * @param grup
     * @param aula
     * @return
     */
    public static String formatSlotText(String abbvr, String grup, String aula) {
        return String.join("\n", abbvr, grup, aula);
    }
}
