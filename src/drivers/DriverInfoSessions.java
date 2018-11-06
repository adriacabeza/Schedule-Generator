package drivers;

import model.Aula;
import model.Laboratori;
import model.Teoria;

import java.util.Scanner;

/**
 * Aquest driver preten probar Teoria i Laboratori a traves de les mateixes funcionalitats
 */
public class DriverInfoSessions {
    public static void menu() {
        System.out.println("Driver InfoSessions");
        System.out.println("Opcions:");

        System.out.println("\t 0 - Mostra opcions");
        System.out.println("\t    input: 0");
        System.out.println("\t 1 - Consulta tota la informació de Teoria");
        System.out.println("\t    input: 1");
        System.out.println("\t 2 - Consulta tota la informació de Laboratori");
        System.out.println("\t    input: 2");
        System.out.println("\t 3 - Modifica numero sessions");
        System.out.println("\t    input: 3 <nou numero sessions> <L/T>");
        System.out.println("\t 4 - Modifica duracio de les sessions");
        System.out.println("\t    input: 4 <duracio de les sessions> <L/T>");
        System.out.println("\t 5 - Modifica informacio tipus Aula");
        System.out.println("\t    input: 5 <tipus aula requerit> <L/T>");
        System.out.println("\t 6 - Crea informacio sessions teoria");
        System.out.println("\t    input: 6 <numero de sessions setmanals> <duracio de les sessions> <tipus aula requerit>");
        System.out.println("\t 7 - Crea informacio sessions laboratori");
        System.out.println("\t    input: 7 <numero de sessions setmanals> <duracio de les sessions> <tipus aula requerit>");
        System.out.println("\t 8 - Sortir");
        System.out.println("\t    input: 8");
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Teoria t = null;
        Laboratori l = null;
        int duracio, numses;
        Aula.TipusAula tAula;
        String tipusAula;
        String tipusSessio;

        int option;

        menu();
        option = scan.nextInt();

        while (option != 8) {
            switch (option) {
                case 0:
                    menu();
                    break;
                case 1:
                    if (t != null) {
                        System.out.println(t.toString());
                    } else {
                        System.out.println("Crea abans una infoSessio de Teoria");
                    }
                    break;
                case 2:
                    if (l != null) {
                        System.out.println(l.toString());
                    } else {
                        System.out.println("Crea abans una infoSessio de Laboratori");
                    }
                    break;
                case 3:
                    numses = scan.nextInt();
                    tipusSessio = scan.next();

                    if (tipusSessio.equalsIgnoreCase("T")) {
                        if (t != null) {
                            t.setNumSessions(numses);
                        } else {
                            System.out.println("Crea abans una infoSessio de Teoria");
                        }
                    } else if (tipusSessio.equalsIgnoreCase("L")) {
                        if (l != null) {
                            l.setNumSessions(numses);
                        } else {
                            System.out.println("Crea abans una infoSessio de Laboratori");
                        }
                    } else {
                        System.out.println("InfoSessions pot ser L o T");
                    }

                    break;
                case 4:
                    duracio = scan.nextInt();
                    tipusSessio = scan.next();

                    if (tipusSessio.equalsIgnoreCase("T")) {
                        if (t != null) {
                            t.setDuracioSessions(duracio);
                        } else {
                            System.out.println("Crea abans una infoSessio de Teoria");
                        }
                    } else if (tipusSessio.equalsIgnoreCase("L")) {
                        if (l != null) {
                            l.setDuracioSessions(duracio);
                        } else {
                            System.out.println("Crea abans una infoSessio de Laboratori");
                        }
                    } else {
                        System.out.println("InfoSessions pot ser L o T");
                    }
                    break;
                case 5:
                    tipusAula = scan.next();
                    tipusSessio = scan.next();

                    tAula = Aula.stringToTipusAula(tipusAula);
                    if (tAula != null) {
                        if (tipusSessio.equalsIgnoreCase("T")) {
                            if (t != null) {
                                t.settAula(tAula);
                            } else {
                                System.out.println("Crea abans una infoSessio de Teoria");
                            }
                        } else if (tipusSessio.equalsIgnoreCase("L")) {
                            if (l != null) {
                                l.settAula(tAula);
                            } else {
                                System.out.println("Crea abans una infoSessio de Laboratori");
                            }
                        } else {
                            System.out.println("InfoSessions pot ser L o T");
                        }
                    } else {
                        System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                    }
                    break;
                case 6:
                    numses = scan.nextInt();
                    duracio = scan.nextInt();
                    tipusAula = scan.next();

                    tAula = Aula.stringToTipusAula(tipusAula);
                    if (tAula != null) {
                        if (t == null) {
                            t = new Teoria(numses, duracio, tAula);
                        } else {
                            System.out.println("Ja hi ha una InfoTeoria creada, proba a modificar-la");
                        }
                    } else {
                        System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                    }

                    break;
                case 7:
                    numses = scan.nextInt();
                    duracio = scan.nextInt();
                    tipusAula = scan.next();

                    tAula = Aula.stringToTipusAula(tipusAula);
                    if (tAula != null) {
                        if (l == null) {
                            l = new Laboratori(numses, duracio, tAula);
                        } else {
                            System.out.println("Ja hi ha una InfoTeoria creada, proba a modificar-la");
                        }
                    } else {
                        System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                    }
                    break;
                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;
            }
            option = scan.nextInt();
        }
    }
}
