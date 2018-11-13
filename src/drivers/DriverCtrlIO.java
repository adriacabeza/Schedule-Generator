/**
 * @author Aina Garcia
 */

package drivers;

import controllers.CtrlIO;
import exceptions.RestriccioIntegritatException;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DriverCtrlIO {
    public static void menu() {
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
            if (i > 0 && i < 8) System.out.println(" <filepath>");
            else System.out.println();
            i++;
        }

        System.out.print("> ");
    }

    public static void main(String[] args) {
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
        Horari horari = null;

        //IO TESTING
        HashMap<String, PlaEstudis> pst1 = new HashMap<>();
        HashMap<String, Assignatura> at1 = new HashMap<>();
        HashMap<String, Aula> ast1 = new HashMap<>();

        pst1.put("Informatica", new PlaEstudis("Informatica", 2010, false));
        pst1.put("Fisica", new PlaEstudis("Fisica", 2012, false));
        at1.put("AC", new Assignatura("AC", 1));
        at1.put("ER", new Assignatura("ER", 1));

        try {
            at1.get("ER").afegeixCorrequisit(at1.get("AC"));
            at1.get("AC").afegeixCorrequisit(at1.get("ER"));
        } catch (RestriccioIntegritatException ignored) {
        }

        at1.get("AC").modificarGrups(2, 10, 2);
        at1.get("AC").setTeoria(2, 2, Aula.TipusAula.NORMAL);
        at1.get("AC").setLaboratori(3, 4, Aula.TipusAula.LABORATORI);

        at1.get("ER").modificarGrups(2, 10, 2);
        at1.get("ER").setTeoria(2, 2, Aula.TipusAula.NORMAL);
        at1.get("ER").setLaboratori(3, 4, Aula.TipusAula.LABORATORI);

        pst1.get("Informatica").afegirAssignatura("AC");
        ast1.put("a5002", new Aula("a5", 0, 2, Aula.TipusAula.NORMAL, 60));
        ast1.put("a5003", new Aula("a5", 0, 3, Aula.TipusAula.LABORATORI, 60));


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
                    while (incorrect) {
                        try {
                            horari = c.carregaHorari(filepath);
                            incorrect = false;
                        } catch (IOException e) {
                            System.out.print("introdueix un path correcte \n> ");
                            filepath = scan.next();
                        }
                    }
                    break;
                case 5:
                    filepath = scan.next(); //TODO

                    try {
                        c.guardaAssignatures(a, filepath);
                    } catch (IOException ignored) {
                    }
                    break;
                case 6:
                    filepath = scan.next(); //TODO
                    try {
                        c.guardaPlaDEstudis(ps, filepath);
                    } catch (IOException ignored) {
                    }
                    break;
                case 7:
                    try {
                        filepath = scan.next(); //TODO
                        c.guardaAules(as, filepath);
                    } catch (IOException ignored) {
                    }
                    break;
                case 8: // TODO
                    try {
                        filepath = scan.next(); //TODO
                        if (horari != null) c.guardaHorari(horari, filepath);
                    } catch (IOException ignored) {
                    }
                    break;
                case 9:
                    HashMap<String, Assignatura> at2 = new HashMap<>();
                    try {
                        c.guardaAssignatures(at1, "assigtest.json");
                        at2 = c.carregaAssignatures("assigtest.json");
                        if (at1.equals(at2)) System.out.println("OK");
                        else System.out.println("KO");
                    } catch (IOException e) {
                        System.out.println("this shouldn't show");
                    }
                    break;
                case 10:
                    HashMap<String, PlaEstudis> pst2 = new HashMap<>();
                    try {
                        c.guardaPlaDEstudis(pst1, "plaestudistest.json");
                        pst2 = c.carregaPlansDEstudi("plaestudistest.json");
                        if (pst1.equals(pst2)) System.out.println("OK");
                        else System.out.println("KO");
                    } catch (IOException e) {
                        System.out.println("this shouldn't show");
                    }
                    break;
                case 11:
                    HashMap<String, Aula> ast2 = new HashMap<>();
                    try {
                        c.guardaAules(ast1, "aulestest.json");
                        ast2 = c.carregaAules("aulestest.json");
                        if (ast1.equals(ast2)) System.out.println("OK");
                        else System.out.println("KO");
                    } catch (IOException e) {
                        System.out.println("this shouldn't show");
                    }
                    break;
                case 12: //TODO
                    try {
                        a = c.carregaAssignatures("assigtest.json");
                        ps = c.carregaPlansDEstudi("plaestudistest.json");
                        as = c.carregaAules("aulestest.json");
                        horari = new Horari(true, a, as, new RestriccioCorrequisit(), new RestriccioNivell(), new RestriccioAula(), new RestriccioGrupTeo(), new RestriccioSubgrupLab(), new ArrayList<>(), new ArrayList<>());
                        c.guardaHorari(horari, "horaritest.json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            option = scan.nextInt();
        }
    }
}
