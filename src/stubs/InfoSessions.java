package stubs;

import model.Aula;

public abstract class InfoSessions {
    private int numSessions;
    private int duracioSessions;
    private Aula.TipusAula tAula;

    public InfoSessions(){
        numSessions = 2;
        duracioSessions = 2;
        tAula = Aula.TipusAula.NORMAL;
    }
}
