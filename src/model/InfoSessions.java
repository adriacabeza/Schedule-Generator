package model;

public abstract class InfoSessions {
    private int numSessions;
    private int duracioSessions;
    private String tipusSessio;

    /**
     * Crea una nova informació de sessió d'un tipus concret de sessió
     * @param numSessions numero de sessions
     * @param duracioSessions duració de cada sessió
     */
    InfoSessions(int numSessions, int duracioSessions){
        this.numSessions =  numSessions;
        this.duracioSessions = duracioSessions;
        if (this.getClass().isInstance(Teoria.class)) tipusSessio = "teoria";
        else if (this.getClass().isInstance(Laboratori.class)) tipusSessio = "laboratori";
        // else throw new exception
    }

    /**
     * Obté la duració de les sessions
     * @return duració de les sessions
     */
    public int getDuracioSessions() {
        return duracioSessions;
    }

    /**
     * Retorna el número de sessions d'un tipus concret d'una assignatura
     * @return número de sessions
     */
    public int getNumSessions() {
        return numSessions;
    }

    /**
     * Obté el tipus de sessió
     * @return tipus de sessió
     */
    public String getTipusSessio() {
        return tipusSessio;
    }

    /**
     * Modifica el número de sessions d'un tipus concret que ha de tenir una assignatura
     * @param numSessions número de sessions nou
     */
    public void setNumSessions(int numSessions) {
        this.numSessions = numSessions;
    }

    /**
     * Modifica la duració de les sessions d'un tipus concret d'una assignatura
     * @param duracioSessions nova duració de sessions
     */
    public void setDuracioSessions(int duracioSessions) {
        this.duracioSessions = duracioSessions;
    }
}
