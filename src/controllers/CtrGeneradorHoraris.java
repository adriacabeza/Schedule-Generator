package controllers;

import model.*;

import java.util.HashMap;

public class CtrGeneradorHoraris {
    private static CtrGeneradorHoraris ourInstance = new CtrGeneradorHoraris();

    public static CtrGeneradorHoraris getInstance() {
        return ourInstance;
    }



    public void CtrGeneradorHoraris() {
        //carrega l'horari de donde sea


    }

    public void forwardchecking(LOQUSEA assignacions){
        //esta función lo que hace es imposibilitar que tal valor tenga x opciones
        //por ejemplo si necesito dos horas de tal asignatura por su duración y el martes solo queda una hora pues
        //imposibilito que pueda ni siquiera observar el martes



    }

    public void checkConsistency(){
        //returns true if the constraint is satisfied with "these" values

    }


    public Boolean isComplete(){
        //returns true if all the subjects are already assigned
    }


    public void creaHorari(){

        //pillar totes les assignatures o la seva infosessió tant de laboratori com de teoria
        //HashMap<String, Assignatura> assignatures = CtrAssignatura.getAssignatures();


        //HashMap<String,InfoSessions> infolab = CtrlSessions.getSessionsLab();
        //HashMap<String,InfoSessions> infoteo = CtrlSessions.getSessionsTeo();
        //TODO: aclarar lo de si necessito dos sessions lab i teoria, aclarar també des de on es modifiquen les coses si al
        //controller d'aula o al de sessions, s'han de tenir totes les classes fetes per a començar a picar codi bé

        //necessito ara pillar totes les restriccions que s'hagin seleccionat

        //CtrAssignacio.crearAssignacio(Aula aula, Assignatura assignatura, int grup, int hora, int dia, boolean teoria)

        solve();
    }

    public void backtracking_search(){
        //aixo pilla una variable que no esta assignada
        // mires si es consistent i si no fas backtracking sino es el resultat
    }


    public void solve() {
        //removes unary constraints and then uses backtracking
        if (! remove_unary());
    }

    public Boolean remove_unary(){
        //removes unary constraints and will return False if failure
    }

    public void setAssignacio(){
        //et pilla ja l assignatura i l'assignació que li tocaria i els junta
    }

    public void modificaHorari(){
        //coge las assignacions ya hechas
        //LOQUESEA assignacionsLab = CtrAssignacio.getAssignacionsLab();
        //LOQUESEA assignacionsTeo = CtrAssignacio.getAssignacionsTeo();



    }

    public void getHorari(){

    }



}
