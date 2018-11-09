/**
 * @author Aina Garcia
 */

package model;

public abstract class InfoSessions {
    protected String assignatura;
    protected int numSessions;
    protected int duracioSessions;
    protected Aula.TipusAula tAula;

    /**
     * Crea una nova instancia d'informació de sessions i li assigna els seus atributs
     *
     * @param numSessions     numero de sessions setmanals del tipus especificat
     * @param duracioSessions duracio de cada sessio
     */
    public InfoSessions(int numSessions, int duracioSessions, Aula.TipusAula tAula, String assignatura) {
        this.assignatura = assignatura;
        this.numSessions = numSessions;
        this.duracioSessions = duracioSessions;
        this.tAula = tAula;
    }

    /**
     * Retorna el numero de sessions setmanals del tipus especificat
     *
     * @return numero de sessions
     */
    public int getNumSessions() {
        return numSessions;
    }

    /**
     * Assigna un nou numero de sessions setmanals
     *
     * @param numSessions numero de sessions
     */
    public void setNumSessions(int numSessions) {
        this.numSessions = numSessions;
    }

    /**
     * Retorna la duracio del es sessions del tipus especificat
     *
     * @return duracio de les sessions
     */
    public int getDuracioSessions() {
        return duracioSessions;
    }

    /**
     * Assigna una nova duracio a les sessions
     *
     * @param duracioSessions duracio de les sessions
     */
    public void setDuracioSessions(int duracioSessions) {
        this.duracioSessions = duracioSessions;
    }

    /**
     * Assigna un nou tipus d'aula requerit per dur a terme aquest tipus de sessions
     * @param tAula tipus de l'aula
     */
    public void settAula(Aula.TipusAula tAula) {
        this.tAula = Aula.TipusAula.NORMAL;
    }

    /**
     * Consulta el tipus d'aula requerit per el tipus de sessio
     * @return tipus d'aula
     */
    public Aula.TipusAula gettAula(){
        return tAula;
    }

}
