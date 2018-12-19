package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Obté slotname en mostrar-ho a la taula
     *
     * @return Nom del slot de temps
     */
    public String getSlotname() {
        return slotname.get();
    }

    /**
     * Obté el contingut de la cel·la per aquest Slot els dilluns
     *
     * @return El contingut de la cel·la
     */
    public String getDilluns() {
        return dilluns.get();
    }

    /**
     * Obté el contingut de la cel·la per aquest Slot els dimarts
     *
     * @return El contingut de la cel·la
     */
    public String getDimarts() {
        return dimarts.get();
    }

    /**
     * Obté el contingut de la cel·la per aquest Slot els dimecres
     *
     * @return El contingut de la cel·la
     */
    public String getDimecres() {
        return dimecres.get();
    }

    /**
     * Obté el contingut de la cel·la per aquest Slot els dijous
     *
     * @return El contingut de la cel·la
     */
    public String getDijous() {
        return dijous.get();
    }

    /**
     * Obté el contingut de la cel·la per aquest Slot els divendres
     *
     * @return El contingut de la cel·la
     */
    public String getDivendres() {
        return divendres.get();
    }

    /**
     * Fixa el contingut de la cel·la els dilluns
     *
     * @param value El contingut de la cel·la
     */
    private void setDilluns(String value) {
        this.dillunsIsSet = true;
        this.dilluns.set(value);
    }

    /**
     * Fixa el contingut de la cel·la els dimarts
     *
     * @param value El contingut de la cel·la
     */
    private void setDimarts(String value) {
        this.dimartsIsSet = true;
        this.dimarts.set(value);
    }

    /**
     * Fixa el contingut de la cel·la els dimecres
     *
     * @param value El contingut de la cel·la
     */
    private void setDimecres(String value) {
        this.dimecresIsSet = true;
        this.dimecres.set(value);
    }

    /**
     * Fixa el contingut de la cel·la els dijous
     *
     * @param value El contingut de la cel·la
     */
    private void setDijous(String value) {
        this.dijousIsSet = true;
        this.dijous.set(value);
    }

    /**
     * Fixa el contingut de la cel·la els divendres
     *
     * @param value El contingut de la cel·la
     */
    private void setDivendres(String value) {
        this.divendresIsSet = true;
        this.divendres.set(value);
    }

    /**
     * Funció externa per fixar un dia concret a partir d'un String obtingut del JSON.
     * En cas de que ja hi hagi una assignatura en aquella cel·la, fem un append,
     * en cas contrari, fixem el valor inicial de la cel·la
     *
     * @param dia        El nom del dia, obtingut del JSON
     * @param abreviacio L'abreviació de l'assignatura
     * @param grup       El grup per a aquesta assignació
     * @param aula       L'aula per a aquesta assignació
     */
    public void setDia(String dia, String abreviacio, String grup, String aula) {
        switch (dia.toUpperCase()) {
            case "DILLUNS":
                if (dillunsIsSet) {
                    setDilluns(dilluns.get() + "\n" + formatSlotText(abreviacio, grup, aula, false));
                } else {
                    setDilluns(formatSlotText(abreviacio, grup, aula, true));
                }
                break;
            case "DIMARTS":
                if (dimartsIsSet) {
                    setDimarts(dimarts.get() + "\n" + formatSlotText(abreviacio, grup, aula, false));
                } else {
                    setDimarts(formatSlotText(abreviacio, grup, aula, true));
                }
                break;
            case "DIMECRES":
                if (dimecresIsSet) {
                    setDimecres(dimecres.get() + "\n" + formatSlotText(abreviacio, grup, aula, false));
                } else {
                    setDimecres(formatSlotText(abreviacio, grup, aula, true));
                }
                break;
            case "DIJOUS":
                if (dijousIsSet) {
                    setDijous(dijous.get() + "\n" + formatSlotText(abreviacio, grup, aula, false));
                } else {
                    setDijous(formatSlotText(abreviacio, grup, aula, true));
                }
                break;
            case "DIVENDRES":
                if (divendresIsSet) {
                    setDivendres(divendres.get() + "\n" + formatSlotText(abreviacio, grup, aula, false));
                } else {
                    setDivendres(formatSlotText(abreviacio, grup, aula, true));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Funció que converteix les dades de l'assignatura en el format que s'espera dins del Slot
     *
     * @param abreviacio Abreviació de l'assignatura
     * @param grup       Grup per aquesta assignació
     * @param aula       Aula on es realitzarà l'assignatura per aquell grup
     * @param full       Modificador per generar un String complet o curt
     * @return Un String formatejat correctament per introduïr a la cel·la
     */
    private static String formatSlotText(String abreviacio, String grup, String aula, boolean full) {
        StringBuilder sb = new StringBuilder();
        String grupaula = String.join(" ", "(", "G" + grup, ")", aula);
        for (int i = 0; i <= grupaula.length() / 2 + 1; i++) {
            sb.append(" ");
        }
        sb.append(abreviacio);
        if (full) {
            return sb.append("\n").append(grupaula).toString();
        } else {
            return grupaula;
        }
    }

    /**
     * Funció per extreure el grup i l'aula a partir d'una de les linies generades per mostrar a les cel·les de la taula
     * @param text El conjunt grup i aula d'una cel·la: ( G10 ) A3002
     * @return Un HashMap amb keys "grup" i "aula" contenint ambdós valors
     */
    public static HashMap<String, String> grupAulaExtractor(String text){
        Pattern p = Pattern.compile("\\( G(\\d+) \\) (\\w+)");
        Matcher m = p.matcher(text);
        if(m.matches()) {
            String grup = m.group(1);
            String aula = m.group(2);
            HashMap<String, String> result = new HashMap<>();
            result.put("grup", grup);
            result.put("aula", aula);
            return result;
        }else{
            return null;
        }
    }
}
