/**
 * @Author Adria Cabeza
 */
package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class Horari {

    private Assignacio[][][] horari;

    /**
     * Obtenir l'horari
     *
     * @return horari
     */
    public Assignacio[][][] getHorari() {
        return horari;
    }


    /**
     * Construeix l'horari
     *
     * @param assignatures conjunt d'assignatures del pla d'estudis
     * @param aules        conjunt d'aules
     * @param resCorr      conjunt de restriccions de correquisit
     * @param resNiv       restricció de nivell
     * @param resAul       restricció d'aula
     * @param resTeo       restricció de sessió de teoria
     * @param resSub       restricció de de sessió de laboratori
     * @param resAulDia    conjunt de restriccions d'una aula en un dia
     * @param resAulaHora  conjunt de restriccions d'una aula en una hora
     * @param resMatiTarda conjunt de restriccions d'una assignatura de matins o tardes
     */
    //TODO horari creadora sin llamar a backtracking, solo lo crea vacio
    /*
    rollo que no se si se entiende: cuando se llame a generar horari, se hara sobre el horario vacio, es decir, haced una funcion aqui que sea
    del estilo bool generarHorari(assignatures, aules, ...) que es la que se llamara desde el ctrlDomini sobre el
    horario vacio anteriormente creado. La constructora no deberia tener ningun parametro

    TLDR: - constructora sin parametros pls
          - funcion generar horario que haga lo que hay en la constructora y haga return de un bool si se ha podido generar o no
          - las restricciones deberian ser un atributo del horario, no pasadas cada vez como parametro, desde dominio te pasare
            los bools de las restricciones que se tengan que crear (leidos desde la view) y tu te puedes hacer una funcion que te
            las de de alta todas para tenerlas preparadas para generar horario mas adelante
     */
    public Horari( HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                  RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora, ArrayList<RestriccioAssigMatiTarda> resMatiTarda, RestriccioCapacitatAula resCapAul, RestriccioLimits resLim ) {

        Algorismes algoritme = new Backtracking2(assignatures,aules,resCorr,resNiv,resAul,resTeo,resSub,resAulDia,resAulaHora,resMatiTarda,resCapAul,resLim);
        try {
            boolean b = ((Backtracking2) algoritme).generaHorari();
            if (!b) System.out.println("No es pot fer l'horari");
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        horari = algoritme.getHorari();
    }
}


