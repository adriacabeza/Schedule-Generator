package model;

public class GestorSessions {

    //CODI PERQUE ÉS UN SINGLETON
    private static GestorSessions ourInstance = new GestorSessions();

    public static GestorSessions getInstance() {
        return ourInstance;
    }


    //TODO: agafar la info de l'assignatura amb el seu nom i pensar si el seu únic atribut es una infosessió
    public InfoSessions getInfoSessioLab(String nomAss){

    }

    public InfoSessions getSessioTeo(String nomAss){

    }
}
