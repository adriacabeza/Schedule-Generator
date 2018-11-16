/**
 * @Author Antoni Rambla
 */
package model;

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

    /**
     * Crea una nova aula
     *
     * @param edifici   indica el nom del edifici on esta situada l'aula
     * @param planta    indica la planta on esta situada l'aula
     * @param aula      indica el numero que tindra l'aula
     * @param tipusAula indica el tipus que es l'aula
     */
    public Aula(String edifici, int planta, int aula, TipusAula tipusAula, int capacitat) {
        this.edifici = edifici;
        this.planta = planta;
        this.aula = aula;
        this.tAula = tipusAula;
        this.capacitat = capacitat;
    }

    /**
     * Obtenir l'edifici de l'aula
     *
     * @return l'edifici de l'aula
     */
    public String getEdifici() {
        return edifici;
    }


    /**
     * Obtenir la planta de l'aula
     *
     * @return la planta de l'aula
     */
    public int getPlanta() {
        return planta;
    }


    /**
     * Obtenir el numero de l'aula
     *
     * @return el numero de l'aula
     */
    public int getAula() {
        return aula;
    }


    /**
     * Obtenir el tipus de aula
     *
     * @return el tipus de aula
     */
    public TipusAula getTipusAula() {
        return tAula;
    }

    /**
     * Actualitza l'edifici de l'aula
     *
     * @param edifici el nou nom de l'edifici
     */
    public void setEdifici(String edifici) {
        this.edifici = edifici;
    }


    /**
     * Actualitza la planta on es situa l'aula
     *
     * @param planta la nova planta de l'aula
     */
    public void setPlanta(int planta) {
        this.planta = planta;
    }


    /**
     * Actualitza el n�mero de aula
     *
     * @param aula indica el nou numero d'aula
     */
    public void setAula(int aula) {
        this.aula = aula;
    }


    /**
     * Actualitza el tipus d'aula
     *
     * @param tipusaula indica el nou tipus d'aula
     */
    public void setTipusAula(TipusAula tipusaula) {
        this.tAula = tipusaula;
    }

    /**
     * Obtenir la capacitat d'una aula
     *
     * @return capacitat indica la capacitat d'una aula
     */

    public int getCapacitat() {
        return capacitat;
    }

    /**
     * Actualitza la capacitat d'una aula
     *
     * @param capacitat indica la nova capacitat de l'aula
     */

    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    /**
     * Obtenir un TipusAula
     *
     * @param s indica en un string el tipus d'aula
     * @return tipusaula definida pel paràmetre
     */

    public static TipusAula stringToTipusAula(String s) {
        if (s.equalsIgnoreCase("normal")) {
            return Aula.TipusAula.NORMAL;
        } else if (s.equalsIgnoreCase("pcs")) {
            return Aula.TipusAula.PCS;
        } else if (s.equalsIgnoreCase("laboratori")) {
            return Aula.TipusAula.LABORATORI;
        } else {
            return null;
        }
    }

    /**
     * Crea la key d'una aula a partir de l'edifici, la planta
     * i l'aula
     *
     * @param edifici indica l'edifici
     * @param planta  indica el número de planta
     * @param aula    indica una aula
     * @return la key formada pels paràmetres d'entrada
     */

    public static String crearkey(String edifici, int planta, int aula) {
        return edifici + String.valueOf(planta) + String.valueOf(aula);
    }

    /**
     * Obtenir la key de l'aula a partir de l'edifici, la planta i
     * l'aula
     *
     * @return la key de l'aula
     */

    public String getKey() {
        return edifici + String.valueOf(planta) + String.valueOf(aula);
    }

    /**
     * Obtenir l'edifici a partir d'una aula
     *
     * @param key indica una aula
     * @return retorna l'edifici del paràmetre d'entrada
     */
    public static String getedificifromKey(String key) {
        return key.substring(0, 2); //THAT MEANS THE WE ONLY HAVE BUILDING OF TWO CHARS
    }

    /**
     * Obtenir la planta a partir de l'aula
     *
     * @param key indica una aula
     * @return la planta de l'aula
     */
    public static String getplantafromKey(String key) {
        return key.substring(2, 3);
    }

    /**
     * Obtenir el número d'aula a partir de l'identificador d'aula
     *
     * @param key indica una aula
     * @return el número d'aula
     */
    public static String getaulafromKey(String key) {
        return key.substring(3);
    }

    @Override
    public boolean equals(Object obj) {
        Aula a = (Aula) obj;
        return a.getKey().equals(this.getKey());
    }

}
