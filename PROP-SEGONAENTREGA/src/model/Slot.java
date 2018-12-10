package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Slot {

    private StringProperty slotname;
    private StringProperty dilluns;
    private StringProperty dimarts;
    private StringProperty dimecres;
    private StringProperty dijous;
    private StringProperty divendres;
    private boolean dillunsIsSet = false;
    private boolean dimartsIsSet = false;
    private boolean dimecresIsSet = false;
    private boolean dijousIsSet = false;
    private boolean divendresIsSet = false;

    /**
     * Model de dades per les files de la taula d'horaris.
     */
    public Slot(String slotname) {
        this.slotname = new SimpleStringProperty(slotname);
        this.dilluns = new SimpleStringProperty("-");
        this.dimarts = new SimpleStringProperty("-");
        this.dimecres = new SimpleStringProperty("-");
        this.dijous = new SimpleStringProperty("-");
        this.divendres = new SimpleStringProperty("-");
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
        this.dillunsIsSet = true;
        this.dilluns.set(dilluns);
    }

    public void setDimarts(String dimarts) {
        this.dimartsIsSet = true;
        this.dimarts.set(dimarts);
    }

    public void setDimecres(String dimecres) {
        this.dimecresIsSet = true;
        this.dimecres.set(dimecres);
    }

    public void setDijous(String dijous) {
        this.dijousIsSet = true;
        this.dijous.set(dijous);
    }

    public void setDivendres(String divendres) {
        this.divendresIsSet = true;
        this.divendres.set(divendres);
    }

    public void setDia(String dia, String value) {
        switch (dia.toUpperCase()) {
            case "DILLUNS":
                if(dillunsIsSet){
                    setDilluns(dilluns.get() + "\n" + value);
                }else{
                    setDilluns(value);
                }
                break;
            case "DIMARTS":
                if(dimartsIsSet){
                    setDimarts(dimarts.get() + "\n" + value);
                }else{
                    setDimarts(value);
                }
                break;
            case "DIMECRES":
                if(dimecresIsSet){
                    setDimecres(dimecres.get() + "\n" + value);
                }else{
                    setDimecres(value);
                }
                break;
            case "DIJOUS":
                if(dijousIsSet){
                    setDijous(dijous.get() + "\n" + value);
                }else{
                    setDijous(value);
                }
                break;
            case "DIVENDRES":
                if(divendresIsSet){
                    setDivendres(divendres.get() + "\n" + value);
                }else{
                    setDivendres(value);
                }
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
    public static String formatSimpleSlotText(String abbvr, String grup, String aula) {
        return String.join("\n", abbvr + "(" + grup + ")", aula);
    }

}
