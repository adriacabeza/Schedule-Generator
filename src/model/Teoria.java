/**
 * @author Aina Garcia
 */

package model;

public class Teoria extends InfoSessions {

    /**
     * Crea una nova instancia de informació de sessions de teoria i li assigna la informació necessària
     *
     * @param numSessions     número de sessions de teoria
     * @param duracioSessions duració de les sessions de teoria
     */
    public Teoria(int numSessions, int duracioSessions, Aula.TipusAula tAula) {
        super(numSessions, duracioSessions, tAula);
    }
}
