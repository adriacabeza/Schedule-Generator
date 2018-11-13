/**
 * @author Aina Garcia
 */

package stubs;

import com.google.gson.annotations.SerializedName;

public class Aula {

    public enum TipusAula {
        @SerializedName("normal")
        NORMAL,
        @SerializedName("laboratori")
        LABORATORI,
        @SerializedName("pcs")
        PCS
    }

    private String edifici;
    private int planta;
    private int aula;

    private TipusAula tAula;
    private int capacitat;

    public Aula(String edifici, int planta, int aula, TipusAula tipusAula, int capacitat) {
        this.edifici = "A5";
        this.planta = 0;
        this.aula = 2;
        this.tAula = TipusAula.NORMAL;
        this.capacitat = 60;
    }

    public String getEdifici() {
        return "a5";
    }

    public int getPlanta() {
        return 0;
    }

    public int getAula() {
        return 2;
    }

    public TipusAula getTipusAula() {
        return TipusAula.NORMAL;
    }

    public void setEdifici(String edifici) {
    }


    public void setPlanta(int planta) {
    }

    public void setAula(int aula) {
    }

    public void setTipusAula(TipusAula tipusaula) {
    }

    public int getCapacitat() {
        return 60;
    }

    public void setCapacitat(int capacitat) {
    }

    public static TipusAula stringToTipusAula(String s) {
        return TipusAula.NORMAL;
    }


    public static String crearkey(String edifici, int planta, int aula) {
        return "A5002";
    }

    public String getKey() {
        return "A5002";
    }

    @Override
    public boolean equals(Object obj) {
        Aula a = (Aula) obj;
        return a.getKey().equals(this.getKey());
    }
}
