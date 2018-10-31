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

    public void modificarAula(String edifici, int planta, int aula)//PUTO PALO CONTINUAR
    //TODO: preguntar si realment fa falta posar tots els atributs que es poden canviar
    //des de la plantilla

    public HashMap<String,Aula> getAules(){
        return aules;
    }
}
