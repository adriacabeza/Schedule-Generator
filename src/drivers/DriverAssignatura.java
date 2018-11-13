package drivers;

import controllers.CtrlIO;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.Assignatura;
import model.Aula;
import model.Grup;
import model.Subgrup;

import java.io.IOException;
import java.util.*;

public class DriverAssignatura {

    public static void menu() {
        System.out.println("Driver Assignatura");
        System.out.println("Opcions:");
        System.out.println("\t 0 - Mostra opcions");
        System.out.println("\t    input: 0");
        System.out.println("\t 1 - Crea Assignatura");
        System.out.println("\t    input: 1 <Nom> <quadrimestre>");
        System.out.println("\t 2 - Modifica grups de l'assignatura");
        System.out.println("\t    input: 2 <nom assignatura> <numero de grups> <capacitat del grup> <numero de subgrups per grup>");
        System.out.println("\t 3 - Consulta tota la informació de l'assignatura");
        System.out.println("\t    input: 3 <nom assignatura>");
        System.out.println("\t 4 - Consulta correquisits assignatura");
        System.out.println("\t    input: 4 <nom assignatura>");
        System.out.println("\t 5 - Afegeix correquisit assignatura");
        System.out.println("\t    input: 5 <nom assignatura A> <nom assignatura B>");
        System.out.println("\t 6 - Esborra correquisit assignatura");
        System.out.println("\t    input: 6 <nom assignatura A> <nom assignatura B>");
        System.out.println("\t 7 - Es correquisit");
        System.out.println("\t    input: 7 <nom assignatura A> <nom assignatura B>");
        System.out.println("\t 8 - Assigna informacio sessions teoria");
        System.out.println("\t    input: 8 <nom assignatura> <numero de sessions setmanals> <duracio de les sessions> <tipus aula requerit>");
        System.out.println("\t 9 - Assigna informacio sessions laboratori");
        System.out.println("\t    input: 9 <nom assignatura> <numero de sessions setmanals> <duracio de les sessions> <tipus aula requerit>");
        System.out.println("\t 10 - Sortir");
        System.out.println("\t    input: 10");
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        HashMap<String, Assignatura> assignatures = new HashMap<>();

        int option;

        menu();

        option = scan.nextInt();

        while (option != 10) {

            String nomA;
            String nomB;
            String tipusAula;
            int ng, cap, nsg, ns, d;
            Aula.TipusAula tAula;
            int quadrimestre;

            switch (option) {
                case 0:
                    menu();
                    try {
                        CtrlIO.getInstance().guardaAssignatures(assignatures, "assignatures.json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //assignatures = CtrlIO.getInstance().carregaAssignatures("assignatures.json");
                    break;
                case 1:
                    nomA = scan.next();
                    quadrimestre = scan.nextInt();
                    assignatures.put(nomA, new Assignatura(nomA, quadrimestre));
                    break;
                case 2:
                    nomA = scan.next();
                    ng = scan.nextInt();
                    cap = scan.nextInt();
                    nsg = scan.nextInt();
                    assignatures.get(nomA).modificarGrups(ng, cap, nsg);
                    break;
                case 3:
                    nomA = scan.next();
                    System.out.println(assignatures.get(nomA).toString());
                    try {
                        Map<Integer, Grup> grups = assignatures.get(nomA).getGrups();
                        Iterator<Grup> it = grups.values().iterator();
                        while (it.hasNext()) {
                            Grup g = it.next();
                            Iterator<Subgrup> sit = g.getSubgrups().values().iterator();
                            System.out.println(g.toString());
                            while (sit.hasNext()) {
                                System.out.println("\t " + sit.next().toString());
                            }
                        }
                    } catch (NotFoundException n) {
                    }
                    try {
                        System.out.println(assignatures.get(nomA).getTeoria().toString());
                    } catch (NotFoundException e) {
                    }
                    try {
                        System.out.println(assignatures.get(nomA).getLaboratori().toString());
                    } catch (NotFoundException e) {
                    }
                    break;
                case 4:
                    nomA = scan.next();
                    try {
                        ArrayList<String> a = assignatures.get(nomA).getCorrequisits();
                        for (String assig : a) {
                            System.out.println(assig);
                        }

                    } catch (NotFoundException n) {
                        System.out.println(n.getMessage());
                    }

                    break;
                case 5:
                    nomA = scan.next();
                    nomB = scan.next();

                    try {
                        assignatures.get(nomA).afegeixCorrequisit(assignatures.get(nomB));
                        assignatures.get(nomB).afegeixCorrequisit(assignatures.get(nomA));
                    } catch (RestriccioIntegritatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    nomA = scan.next();
                    nomB = scan.next();
                    try {
                        assignatures.get(nomA).esborraCorrequisit(nomB);
                        assignatures.get(nomB).esborraCorrequisit(nomA);
                    } catch (NotFoundException n) {
                        System.out.println(n.getMessage());
                    }
                    break;
                case 7:
                    nomA = scan.next();
                    nomB = scan.next();
                    System.out.println(assignatures.get(nomA).esCorrequisit(nomB));
                    break;
                case 8:
                    nomA = scan.next();
                    ns = scan.nextInt();
                    d = scan.nextInt();
                    tipusAula = scan.next();

                    tAula = Aula.stringToTipusAula(tipusAula);
                    if (tAula != null) {
                        assignatures.get(nomA).setTeoria(ns, d, tAula);
                    } else {
                        System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                    }
                    break;
                case 9:
                    nomA = scan.next();
                    ns = scan.nextInt();
                    d = scan.nextInt();
                    tipusAula = scan.next();

                    tAula = Aula.stringToTipusAula(tipusAula);
                    if (tAula != null) {
                        assignatures.get(nomA).setLaboratori(ns, d, tAula);
                    } else {
                        System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                    }
                    break;

                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;
            }
            System.out.println("Que vols fer a continuació? Prem 0 per mostrar les opcions");
            option = scan.nextInt();
        }
    }
}
