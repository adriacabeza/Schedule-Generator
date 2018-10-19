package model;

public class GestorSessions {

    //CODI PERQUE ÉS UN SINGLETON
    private static GestorSessions ourInstance = new GestorSessions();

    public static GestorSessions getInstance() {
        return ourInstance;
    }

    //constructora privada
    private GestorSessions() {
    }

    //funcions etc
}
