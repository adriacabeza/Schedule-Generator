package drivers;


import controllers.CtrlIO;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
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
        HashMap<String, Assignatura> assignatures;
        HashMap<String, Aula> aules;

        CtrlIO c = CtrlIO.getInstance();
        try {
            assignatures = c.carregaAssignatures("assigtest.json");
            aules = c.carregaAules("aulestest.json");
            Horari newhorari;
            newhorari = new Horari(true, assignatures, aules, new RestriccioCorrequisit(), new RestriccioNivell(), new RestriccioAula(), new RestriccioGrupTeo(), new RestriccioSubgrupLab(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            c.guardaHorari(newhorari, "horaritest.json");

            return newhorari;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void mostra(Horari g, Scanner s) {
        System.out.println(g.getHorari());
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        boolean creat = false;

        mostraopcions();
        Horari horari = null;

        option = scan.nextInt();

        while (option != 3) {
            mostraopcions();
            switch (option) {
                case 1:
                    horari = creador();
                    creat = true;
                    break;

                case 2:
                    if (!creat) {
                        System.out.println("Error: no hi ha un horari creat");
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