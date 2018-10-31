package controllers;

import model.*;

import java.util.ArrayList;

public class CtrAssignacio {
    private static CtrAssignacio ourInstance = new CtrAssignacio();

    public static CtrAssignacio getInstance() {
        return ourInstance;
    }

    private ArrayList<Assignacio> assignacions = new ArrayList<>();

    private CtrAssignacio() {

    }



    /**
     *
     * @param aula
     * @param assignatura
     * @param grup
     * @param hora
     * @param dia
     * @param teoria
     */
    public void modificaAssignacio(Aula aula, Assignatura assignatura, int grup, int hora, int dia, boolean teoria){

    }
}
