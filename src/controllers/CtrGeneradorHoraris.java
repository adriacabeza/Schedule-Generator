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
        //pillar totes les restriccions NOT YET IMPLEMENTED
        LOQUESEA restriccions = CtrRestriccions.getRestriccions();


    }

    public void creaHorari(){

        //pillar totes les assignatures o la seva infosessió tant de laboratori com de teoria
        HashMap<String, Assignatura> assignatures = CtrAssignatura.getAssignatures();
        HashMap<String,InfoSessions> infolab = CtrlSessions.getSessionsLab();
        HashMap<String,InfoSessions> infoteo = CtrlSessions.getSessionsTeo();





    }

    public void modificaHorari(){
        //coge las assignacions ya hechas
        LOQUESEA assignacionsLab = CtrAssignacio.getAssignacionsLab();
        LOQUESEA assignacionsTeo = CtrAssignacio.getAssignacionsTeo();



    }

    public void getHorari(){

    }



}
