/**
 * @Author Antoni Rambla
 */
package stubs;

import exceptions.NotFoundException;
import model.*;
import model.Assignatura;
import model.Aula;
import model.Grup;
import model.Laboratori;
import model.SessioGrup;
import model.Subgrup;
import model.Teoria;

import java.util.*;

public class Backtracking2 {


    private Assignacio[][][] horari;
    private ArrayList<model.SessioGrup> sessions;
    private ArrayList<model.Assignatura> assignatures;
    private ArrayList<model.Aula> aules;
    private RestriccioCorrequisit resCorr;
    private RestriccioNivell resNiv;
    private RestriccioAula resAul;
    private RestriccioGrupTeo resTeo;
    private RestriccioSubgrupLab resSub;
    private ArrayList<RestriccioAulaDia> resAulDia;
    private ArrayList<RestriccioAulaHora> resAulaHora;
    private ArrayList<RestriccioAssigMatiTarda> resMatiTarda;


    public Backtracking2(HashMap<String, model.Assignatura> assignatures, HashMap<String, model.Aula> aules, RestriccioCorrequisit resCorr, RestriccioNivell resNiv, RestriccioAula resAul, RestriccioGrupTeo resTeo,
                         RestriccioSubgrupLab resSub, ArrayList<RestriccioAulaDia> resAulDia, ArrayList<RestriccioAulaHora> resAulaHora, ArrayList<RestriccioAssigMatiTarda> resMatiTarda) {
        this.assignatures = new ArrayList<>();
        this.aules = new ArrayList<>();
        this.horari = new Assignacio[12][5][2];
        this.resCorr = resCorr;
        this.resNiv = resNiv;
        this.resAul = resAul;
        this.resTeo = resTeo;
        this.resSub = resSub;
        this.resAulDia = resAulDia;
        this.resAulaHora = resAulaHora;
        this.resMatiTarda = resMatiTarda;
    }

    public Assignacio[][][] getHorari() {
        return horari;
    }

    private String fromInt2dia(int dia) {
        return null;
    }

    public void printarHoraritot() {}

    private int fromDia2int(String dia) {
        return 1;
    }

    private int getHora(int hora) {
        return 19;
    }

    private boolean checkBoundaries(int posaula, int dia, int hora, model.SessioGrup assig, int duracio) {
        return true;
    }


    private boolean comprovarRestriccions(Aula aula, int dia, int hora, model.SessioGrup assig, int duracio, int posaula) {
        return true;
    }

    public boolean filtraRestriccions(HashMap<model.SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) {
        return false;
    }


    public boolean propagarPossibilitats(int aula, int dia, int hora, model.SessioGrup sessio, HashMap<model.SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibles ) {
        return true;
    }


    private boolean creaHorari(int i, Assignacio[][][] horari, HashMap<model.SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) {
        return true;
    }


    public boolean generaHorari() {
        return true;
    }


    private ArrayList<model.SessioGrup> creaSessions(ArrayList<model.Assignatura> assignatures) throws NotFoundException {
        return null;
    }
}
