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

    public int getNumSessions() {
        return 2;
    }

    public int getDuracioSessions(){
        return 2;
    }

    public Aula.TipusAula gettAula() {
        return Aula.TipusAula.NORMAL;
    }

    @Override
    public String toString() {
        return "Sample Info";
    }
}
