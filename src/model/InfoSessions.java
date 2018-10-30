package model;

public abstract class InfoSessions {
    private int numSessions;
    private int duracioSessions;

    /**
     * Crea una nova instancia d'informació de sessions i li assigna els seus atributs
     * @param numSessions numero de sessions setmanals del tipus especificat
     * @param duracioSessions duracio de cada sessio
     */
    public InfoSessions(int numSessions, int duracioSessions) {
        this.numSessions = numSessions;
        this.duracioSessions = duracioSessions;
    }

    /**
     * Retorna el numero de sessions setmanals del tipus especificat
     * @return numero de sessions
     */
    public int getNumSessions() {
        return numSessions;
    }

    /**
     * Assigna un nou numero de sessions setmanals
     * @param numSessions numero de sessions
     */
    public void setNumSessions(int numSessions) {
        this.numSessions = numSessions;
    }

    /**
     * Retorna la duracio del es sessions del tipus especificat
     * @return duracio de les sessions
     */
    public int getDuracioSessions() {
        return duracioSessions;
    }

    /**
     * Assigna una nova duracio a les sessions
     * @param duracioSessions duracio de les sessions
     */
    public void setDuracioSessions(int duracioSessions) {
        this.duracioSessions = duracioSessions;
    }
}
