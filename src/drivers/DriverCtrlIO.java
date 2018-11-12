package drivers;

import controllers.CtrlIO;
import model.Assignatura;
import model.Aula;
import model.PlaEstudis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DriverCtrlIO {
    public static void menu(){
        ArrayList<String> opcions = new ArrayList<>();
        opcions.add("Consulta menu");

        opcions.add("Carrega assignatures");
        opcions.add("Carrega plans d'estudi");
        opcions.add("Carrega Aules");
        opcions.add("Carrega Horaris");

        opcions.add("Guarda assignatures");
        opcions.add("Guarda plans d'estudi");
        opcions.add("Guarda Aules");
        opcions.add("Guarda Horaris");

        opcions.add("IO Assignatures");
        opcions.add("IO Plans Estudi");
        opcions.add("IO Aules");
        opcions.add("IO Horari");

        opcions.add("Sortir");

        int i = 0;
        for (String op : opcions) {
            System.out.println("Opcio " + i + ": " + op);
            System.out.print("\t input: " + i);
            if (i > 0 && i < 5) System.out.println(" <filepath>");
            else System.out.println();
            i++;
        }

        System.out.print("> ");
    }

    public static void main (String[] args) {
        CtrlIO c = CtrlIO.getInstance();
        menu();

        Scanner scan = new Scanner(System.in);

        int option;
        option = scan.nextInt();
        String filepath;
        boolean incorrect = true;

        //individual testing
        HashMap<String, PlaEstudis> ps = new HashMap<>();
        HashMap<String, Assignatura> a = new HashMap<>();
        HashMap<String, Aula> as = new HashMap<>();

        //IO TESTING
        HashMap<String, PlaEstudis> pst1 = new HashMap<>();
        HashMap<String, Assignatura> at1 = new HashMap<>();
        HashMap<String, Aula> ast1 = new HashMap<>();

        while (option != 13) {
            switch (option) {
                case 0:
                    menu();
                    break;
                case 1:
                    filepath = scan.next();
                    while (incorrect) {
                        try {
                            a = c.carregaAssignatures(filepath);
                            incorrect = false;
                        } catch (IOException e) {
                            System.out.print("introdueix un path correcte \n> ");
                            filepath = scan.next();
                        }
                    }
                    break;
                case 2:
                    filepath = scan.next();
                    while (incorrect) {
                        try {
                            ps = c.carregaPlansDEstudi(filepath);
                            incorrect = false;
                        } catch (IOException e) {
                            System.out.print("introdueix un path correcte \n> ");
                            filepath = scan.next();
                        }
                    }
                    break;
                case 3:
                    filepath = scan.next();
                    while (incorrect) {
                        try {
                            as = c.carregaAules(filepath);
                            incorrect = false;
                        } catch (IOException e) {
                            System.out.print("introdueix un path correcte \n> ");
                            filepath = scan.next();
                        }
                    }
                    break;
                case 4:
                    filepath = scan.next(); //TODO
                    break;
                case 5:
                    try {
                        c.guardaAssignatures(a);
                    } catch (IOException ignored) {
                    }
                    break;
                case 6:
                    try {
                        c.guardaPlaDEstudis(ps);
                    } catch (IOException ignored) {
                    }
                    break;
                case 7:
                    try {
                        c.guardaAules(as);
                    } catch (IOException ignored) {
                    }
                    break;
                case 8: // TODO
                    break;
                case 9:
                    HashMap<String, Assignatura> at2 = new HashMap<>();
                    try {
                        at2 = c.carregaAssignatures("assigtest.json");
                    } catch (IOException e) {
                        System.out.println("this shouldn't show");
                    }
                    break;
                case 10:
                    HashMap<String, PlaEstudis> pst2 = new HashMap<>();
                    try {
                        pst2 = c.carregaPlansDEstudi("plaestudistest.json");
                    } catch (IOException e) {
                        System.out.println("this shouldn't show");
                    }
                    break;
                case 11:
                    HashMap<String, Aula> ast2 = new HashMap<>();
                    try {
                        ast2 = c.carregaAules("aulestest.json");
                    } catch (IOException e) {
                        System.out.println("this shouldn't show");
                    }
                    break;
                case 12: //TODO
                    break;
                default: break;
            }
            option = scan.nextInt();
        }


    }
}
