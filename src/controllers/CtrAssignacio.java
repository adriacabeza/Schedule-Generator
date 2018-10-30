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

    public void crearAssignacio(Aula aula, Assignatura assignatura, int grup, int hora, int dia, boolean teoria){
        Assignacio a;
        if (teoria) {
            a = new AssignacioT(dia, hora, aula, assignatura, grup);
        } else {
            a = new AssignacioL(dia, hora, aula, assignatura, grup); //on grup es un subgrup
        }
        assignacions.add(a);
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
