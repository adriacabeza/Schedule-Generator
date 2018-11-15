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

    /**
     * Construeix un horari un buit amb totes les dades que es necessitarien per a generar-lo
     *  @param assignatures assignatures que tindrà que tenir l'horari
     * @param aules        aules que tindrà que tenir l'horari
     * @param resCorr      restricció de correquísit
     * @param resNiv       restricció de nivell
     * @param resAul       restricció d'aules
     * @param resTeo       restricció de les sessions de teoria
     * @param resSub       restricció de les sessions de laboratori
     * @param resAulDia    restricció de dia i aula
     * @param resAulaHora  restricció d'aula i hora
     * @param resMatiTarda restriccio de assignatures de matins i tardes
     */
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



    /**
     * Obté l'horari
     * @return l'horari
     */
    public Assignacio[][][] getHorari() {
        return horari;
    }



    /**
     * Converteix un enter que representa un número en el seu pertinent dia en string
     *
     * @param dia enter que representa el dia
     * @return string que representa el dia
     */
    private String fromInt2dia(int dia) {
        return null;
    }

    /**
     * Printa per consola tot l'horari
     */

    public void printarHoraritot() {

    }

    /**
     * Converteix un string que representa un dia a enter
     *
     * @param dia string que representa un dia
     * @return enter que representa el dia
     */
    private int fromDia2int(String dia) {
        return 1;
    }

    /**
     * Converteix un número que representa una hora a l'hora que representa
     *
     * @param hora enter que representa una hora
     * @return hora que representa
     */
    private int getHora(int hora) {

        return 19;
    }


    /**
     * Comprova que a l'hora de fer una assignació no es passi dels límits de l'horari
     *
     * @param posaula enter que representa l'aula de l'assignació
     * @param dia     enter que representa el dia de l'assignació
     * @param hora    enter que representa l'hora de l'assignació
     * @param assig   enter que representa l'assignatura de l'assignació
     * @param duracio duració de la sessió que es vol assignar
     * @return
     */
    private boolean checkBoundaries(int posaula, int dia, int hora, model.SessioGrup assig, int duracio) {
        return true;
    }


    /**
     * S'encarrega de comprovar totes les restriccions per a fer assignacions correctes a l'horari
     *
     * @param aula   aula de l'assignació
     * @param dia     enter que representa el dia de l'assignació
     * @param hora    enter que representa l'hora de l'assignació
     * @param assig   assignatura de l'assignació
     * @param duracio duració de la sessió que es vol assignar
     * @param posaula enter que representa l'aula de l'assignació
     * @return true si es pot realitzar l'assignació
     */
    private boolean comprovarRestriccions(Aula aula, int dia, int hora, model.SessioGrup assig, int duracio, int posaula) {
        return true;
    }

    /**
     * Aquesta funció s'encarrega de fer una poda inicial de les possibilitats de cada assignació que haurem de realitzar
     * @param possibilitats les possibles aules en un determinat dia i hora que podrien ser assignades per cada sessió
     * @return true si ja hi ha un cas que no es pot realitzar false si totes tenen possibles solucions
     *
     */

    public boolean filtraRestriccions(HashMap<model.SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) {
        return false;
    }

    /**
     * Aquesta funció propaga les restriccions per a efectuar forward checking i fer la poda de domini
     * @param aula aula que ha de tenir en compte per a podar
     * @param dia dia que ha de de tenir en compte per a podar
     * @param hora hora que ha de tenir en compte per a podar
     */

    public boolean propagarPossibilitats(int aula, int dia, int hora, model.SessioGrup sessio, HashMap<model.SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibles ) {
        return true;
    }

    /**
     * Genera l'horari
     * @param i és l'ièssim que indica de quina sessió estem parlant
     * @param horari és l'horari que portem assignat
     * @param possibilitats son el conjunt de possibilitats de les sessions que no hem assignat encara
     * @return retorna true si ha pogut fer l'horari i si no ha pogut false
     */

    private boolean creaHorari(int i, Assignacio[][][] horari, HashMap<model.SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> possibilitats) {
        return true;
    }

    /**
     * Genera un horari
     * @return true si s'ha pogut realitzar l'horari i false si no s'ha pogut
     */
    public boolean generaHorari() {
        return true;

    }


    private ArrayList<model.SessioGrup> creaSessions(ArrayList<model.Assignatura> assignatures) throws NotFoundException {
        return null;
    }
}
