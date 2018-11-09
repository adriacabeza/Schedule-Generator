package controllers;

public class CtrInputOutput {
    private static CtrInputOutput ourInstance;
    private CtrInputOutput() {

    }

    public static CtrInputOutput getInstance() {
        if(ourInstance == null){
            ourInstance = new CtrInputOutput();
        }
        return ourInstance;
    }

    /* LECTURA DE FITXERS */

    public void carregaAules(String filepath){

    }

    public void carregaPlansDEstudi(String filepath) {

    }

    public void carregaAssignatures(String filepath){

    }

    public void carregaHorari(String filepath) {

    }


    /* ESCRIPTURA DE FITXERS */

    public void guardaHorari(Horari horari){

    }

}
