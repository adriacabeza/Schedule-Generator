package drivers;


import controllers.CtrlIO;
import model.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    //TODO Mostrar l'horari i no un toString generic.
    public static void mostra(Horari g, String filepath) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(filepath));
            System.out.println(new String(encoded, Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        mostra(horari, "horaritest.json");
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