package controllers;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrAula {
    private static CtrAula ourInstance = new CtrAula();

    public static CtrAula getInstance() {
        return ourInstance;
    }

    private HashMap<String, Aula> aules;

    private CtrAula() {
    }

    public void creaAula(String edifici, int planta, int aula, String tipusAula, ArrayList<Assignacio> assignacions){
        aules.put(edifici + String.valueOf(planta) + String.valueOf(aula),new Aula(edifici, planta,aula,tipusAula,assignacions));
    }

    public void esborrarAula(String edifici, int planta, int aula){
        aules.remove(edifici + String.valueOf(planta) + String.valueOf(aula));
    }

    public void modificarAulaEdifici(String edifici) {
        //TODO: preguntar si realment fa falta posar tots els atributs que es poden canviar
        //des de la plantilla

    }

    public void modificarAulaPlanta(int planta, String edifici, int aula, int plantan){
        Aula a = aules.get(edifici + String.valueOf(planta) + String.valueOf(aula);
        a.setPlanta(plantan);
        aules.remove(edifici + String.valueOf(planta) + String.valueOf(aula));
        aules.put(edifici + String.valueOf(plantan) + String.valueOf(aula),a);

    }
    public void modificarAulaaula(int planta, String edifici, int aula, int aulan ){
        Aula a = aules.get(edifici + String.valueOf(planta) + String.valueOf(aula);
        a.setAula(aulan);
        aules.remove(edifici + String.valueOf(planta) + String.valueOf(aula));
        aules.put(edifici + String.valueOf(planta) + String.valueOf(aulan),a);

    }
    public void modificarAulaTipusAula(int planta, String edifici, int aula, String tipusN ) {
        Aula a = aules.get(edifici + String.valueOf(planta) + String.valueOf(aula);
        a.setTipusAula(tipusN);
        aules.put(edifici + String.valueOf(planta) + String.valueOf(aula),a);
    }

   //TODO: no stoy seguro public void modificarAulaAssignacions()//PUTO PALO CONTINUAR

    public HashMap<String,Aula> getAules(){
        return aules;
    }
}
