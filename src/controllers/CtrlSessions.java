package controllers;

import model.*;

import java.util.HashMap;

public class CtrlSessions {

    //CODI PERQUE ÉS UN SINGLETON
    private static CtrlSessions ourInstance = new CtrlSessions();

    public static CtrlSessions getInstance() {
        return ourInstance;
    }


    private HashMap<String, InfoSessions> sessionsL;
    private HashMap<String, InfoSessions> sessionsT;


    private CtrlSessions(){
        //DUNNO si s'ha de fer servir
    }


    /**
     * Et retorna una infosessió amb tota la informació de la sessió de laboratori de l'assignatura
     * @param nomAss
     * @return 
     */
    public InfoSessions getInfoSessioLab(String nomAss){
        return sessionsL.get(nomAss);

    }

    /**
     * Et retorna una infosessió amb tota la informació de la sessió de teoria de l'assignatura
     * @param nomAss
     * @return
     */

    public InfoSessions getSessioTeo(String nomAss){
        return sessionsT.get(nomAss);
    }


    /**
     * Permet modificar la informació sobre el número de sessions de teoria d'una assignatura
     * @param nomAss Nom de l'assignatura
     * @param num Número de les sessions de laboratori
     */


    public void modificaNumSessiosL(String nomAss,int num){
        InfoSessions sessio = sessionsL.get(nomAss);
        sessio.setNumSessions(num);
        sessionsL.put(nomAss, sessio);
    }

    /**
     * Permet modificar la informació sobre el número de sessions de teoria d'una assignatura
     * @param nomAss Nom de l'assignatura
     * @param num Duració de les sessions de teoria
     */

    public void modificaNumSessiosT(String nomAss,int num){
        InfoSessions sessio = sessionsT.get(nomAss);
        sessio.setNumSessions(num);
        sessionsT.put(nomAss, sessio);
    }


    /**
     * Permet modificar la informació sobre les sessions de teoria d'una assignatura
     * @param nomAss Nom de l'assignatura
     * @param num Duració de les sessions de teoria
     */

    public void modificaDuracióSessioT(String nomAss,int num){
        InfoSessions sessio = sessionsT.get(nomAss);
        sessio.setDuracioSessions(num);
        sessionsL.put(nomAss, sessio);

    }

    /**
     * Permet modificar la informació sobre les sessions de laboratori d'una assignatura
     * @param nomAss Nom de l'assignatura
     * @param num Duració de les sessions de laboratori
     */

    public void modificaDuracióSessioL(String nomAss,int num) {
        InfoSessions sessio = sessionsL.get(nomAss);
        sessio.setDuracioSessions(num);
        sessionsL.put(nomAss, sessio);
    }

    /**
     *Retorna totes les sessions de laboratori que hi ha de cada assignatura
     * @return sessionsSL
     */
    public HashMap<String,InfoSessions> getSessionsLab(){
        return sessionsL;
    }

    /**
     * Retorna totes les sessions de teoria que hi ha de cada assignatura
     * @return sessionsT
     */
    public HashMap<String,InfoSessions> getSessionsTeo(){
        return sessionsT;
    }

}
