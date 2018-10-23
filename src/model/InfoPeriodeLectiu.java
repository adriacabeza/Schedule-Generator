package model;

import java.util.Date;

public class InfoPeriodeLectiu {

    private static InfoPeriodeLectiu ourInstance = new InfoPeriodeLectiu();

    private int dl, dt, dc, dj, dv;
    private char quadrimestre;
    private Date any;

    private InfoPeriodeLectiu() {
        // inicialitzar del fitxer de dades o servei extern
    }

    /**
     * Obté la informació del periode lectiu actual
     * @return Informacio periode lectiu
     */
    public static InfoPeriodeLectiu getInstance() {
        return ourInstance;
    }

    /**
     * Obté el nombre de dilluns
     * @return nombre de dilluns
     */
    public int getDl() {
        return dl;
    }

    /**
     * Obté el nombre de dimarts
     * @return nombre de dimarts
     */
    public int getDt() {
        return dt;
    }

    /**
     * Obté el nombre de dimecres
     * @return nombre de dimecres
     */
    public int getDc() {
        return dc;
    }

    /**
     * Obté el nombre de dijous
     * @return nombre de dijous
     */
    public int getDj() {
        return dj;
    }

    /**
     * Obté el nombre de divendres
     * @return nombre de divendres
     */
    public int getDv() {
        return dv;
    }

    /**
     * Obté el quadrimestre actual
     * @return quadrimestre actual
     */
    public char getQuadrimestre() {
        return quadrimestre;
    }

    /**
     * Obté l'any actual
     * @return any actual
     */
    public Date getAny() {
        return any;
    }
}
