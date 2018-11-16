package drivers;


import model.Horari;
import model.*;
import controllers.*;

import java.util.HashMap;
import java.util.Scanner;

public class DriverHorari {


    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Horari");
        System.out.println("2: Consultar Horari");
        System.out.println("3: Sortir");
    }

    public static Horari creador() {
        assignatures = new HashMap<>();
        plaEstudis = new HashMap<>();
        aules = new HashMap<>();
        resCorr = new RestriccioCorrequisit();
        resNiv = new RestriccioNivell();
        resAul = new RestriccioAula();
        resTeo = new RestriccioGrupTeo();
        resSub = new RestriccioSubgrupLab();
        resAulDia = new ArrayList<>();
        resAulaHora = new ArrayList<>();
        resMatiTarda = new ArrayList<>();

        CtrlIO c = new CtrlIO.getInstance();
        assignatures = c.carregaAssignatures("assigtest.json");
        aules = c.carregaAules("aulestest.json");
        Horari newhorari = new Horari(false, assignatures, aules, resCorr, resNiv, resAul, resTeo, resSub, resAulDia, resAulaHora, resMatiTarda);
        return newhorari;
    }


    public static void mostra(Horari g, Scanner s) {
        System.out.println(g.getHorari());
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        boolean creat = false;

        HashMap<String, Assignatura> assignatures;
        HashMap<String, PlaEstudis> plaEstudis;
        HashMap<String, Aula> aules;
        RestriccioCorrequisit resCorr;
        RestriccioNivell resNiv;
        RestriccioAula resAul;
        RestriccioGrupTeo resTeo;
        RestriccioSubgrupLab resSub;
        ArrayList<RestriccioAulaDia> resAulDia;
        ArrayList<RestriccioAulaHora> resAulaHora;
        ArrayList<RestriccioAssigMatiTarda> resMatiTarda;

        option = scan.nextInt();

        while (option != 3) {
            mostraopcions();
            Horari horari = null;
            switch (option) {
                case 1:
                    horari = creador();
                    creat = true;
                    break;

                case 2:
                    if (!creat) {
                        System.out.println("Error: no hi ha un horari creat");
                        System.out.println("");
                    } else {
                        mostra(horari, scan);
                    }
                    break;

                default:
                    System.out.println("Opcio invalida");
                    break;
            }
            option = scan.nextInt();
        }
    }
}