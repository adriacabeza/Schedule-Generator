package model;

public abstract class InfoSessions {
    private int numSessions;
    private int duracioSessions;

    public InfoSessions(int numSessions, int duracioSessions) {
        this.numSessions = numSessions;
        this.duracioSessions = duracioSessions;
    }

    public int getNumSessions() {
        return numSessions;
    }

    public void setNumSessions(int numSessions) {
        this.numSessions = numSessions;
    }

    public int getDuracioSessions() {
        return duracioSessions;
    }

    public void setDuracioSessions(int duracioSessions) {
        this.duracioSessions = duracioSessions;
    }
}
