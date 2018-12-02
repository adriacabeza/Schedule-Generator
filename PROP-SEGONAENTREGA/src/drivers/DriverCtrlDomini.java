/**
 * @author Aina Garcia
 */

package drivers;

import controllers.CtrlDomini;
import controllers.CtrlIO;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.Aula;
import model.Horari;

import java.io.IOException;
import java.util.Scanner;

public class DriverCtrlDomini {

    public static void menu() {
        System.out.println("Opcio 0: Mostra menu");
        System.out.println("\t input: 0");
        System.out.println("Opcio 1: Crea Pla d'Estudis");
        System.out.println("\t input: 1 <nom pla>");
        System.out.println("Opcio 2: Esborra Pla d'Estudis");
        System.out.println("\t input: 2 <nom pla>");
        System.out.println("Opcio 3: Consultar Pla d'Estudis");
        System.out.println("\t input: 3 <nom pla>");
        System.out.println("Opcio 4: Afegir assignatura pla");
        System.out.println("\t input: 4 <nom pla> <nom assignatura>");
        System.out.println("Opcio 5: Esborrar assignatura pla");
        System.out.println("\t input: 5 <nom pla> <nom assignatura>");
        System.out.println("Opcio 6: Consultar assignatures pla");
        System.out.println("\t input: 6 <nom pla>");
        System.out.println("Opcio 7: Crear assignatura");
        System.out.println("\t input: 7 <nom assignatura> <quadrimestre>");
        System.out.println("Opcio 8: Consultar assignatura");
        System.out.println("\t input: 8 <nom assignatura>");
        System.out.println("Opcio 9: Esborrar assignatura");
        System.out.println("\t input: 9 <nom assignatura>");
        System.out.println("Opcio 10: Modifica informació teoria d'una assignatura");
        System.out.println("\t input: 10 <nom assignatura> <duracio sessions> <#sessions> <tipus aula>");
        System.out.println("Opcio 11: Modifica informació laboratori d'una assignatura");
        System.out.println("\t input: 11 <nom assignatura> <duracio sessions> <#sessions> <tipus aula>");
        System.out.println("Opcio 12: Modifica grups d'una assignatura");
        System.out.println("\t input: 12 <nom assignatura> <num grups> <capacitat> <num subgrups>");
        System.out.println("Opcio 13: Afegeix correquisits entre assignatures");
        System.out.println("\t input: 13 <nom assignatura A> <nom assignatura B>");
        System.out.println("Opcio 14: Esborra correquisits entre assignatures");
        System.out.println("\t input: 14 <nom assignatura A> <nom assignatura B>");
        System.out.println("Opcio 15: Crea Aula");
        System.out.println("\t input: 15 <edifici> <planta> <numero aula> <capacitat> <tipus aula>");
        System.out.println("Opcio 16: Esborra Aula");
        System.out.println("\t input: 16 <edifici> <planta> <numero aula>");
        System.out.println("Opcio 17: Modifica Aula");
        System.out.println("\t input: 17 <nom complet aula> <capacitat> <tipus aula>");
        System.out.println("Opcio 18: Consulta Aula");
        System.out.println("\t input: 18 <nom complet aula>");
        System.out.println("Opcio 19: Crea i mostra Horari");
        System.out.println("\t input: 19 <1:bt/2:bt cronologic>");
        System.out.println("Opcio 20: Reset");
        System.out.println("\t input: 20");
        System.out.println("Opcio 21: Surt");
        System.out.println("\t input: 21");
    }

    public static void main(String[] args) {
        CtrlDomini c = CtrlDomini.getInstance();
        menu();
        Scanner scan = new Scanner(System.in);

        /* variables pels inputs, se que hi ha moltes pero es per aclarar-me al fer lectures */
        String nomP, nomA1, nomA2, nomA, edifici, taula;
        int planta, aula, any, quadrimestre, duracio, nums, option, numgrups, numsubgrups, capacitat, capgrups;
        Horari h;

        option = scan.nextInt();

        while (option != 21) {
            switch (option) {
                case 0:
                    menu();
                    break;
                case 1:
                    nomP = scan.next();
                    any = scan.nextInt();
                    try {
                        c.crearPlaEstudis(nomP, any);
                    } catch (RestriccioIntegritatException e) {
                        System.out.println("Error, ja existeix el pla d'estudis");
                    }
                    break;
                case 2:
                    nomP = scan.next();
                    try {
                        c.esborrarPlaEstudis(nomP);
                    } catch (NotFoundException e) {
                        System.out.println("Error, no existeix un pla d'estudis amb aquest nom");
                    } catch (RestriccioIntegritatException e) {
                        System.out.println("No es pot  borrar un pla d'estudis no obsolet");
                    }
                    break;
                case 3:
                    nomP = scan.next();
                    try {
                        c.consultarPlaEsudis(nomP);
                    } catch (NotFoundException e) {
                        System.out.println("Error, no existeix un pla d'estudis amb aquest nom");
                    }
                    break;
                case 4:
                    nomP = scan.next();
                    nomA = scan.next();
                    try {
                        c.afegirAssignaturaPla(nomP, nomA);
                    } catch (NotFoundException e) {
                        System.out.println("Error, l'assignatura o el pla no existeixen");
                    } catch (RestriccioIntegritatException e) {
                        System.out.println("Error, l'assignatura ja esta assignada a  un altre pla");
                    }
                    break;
                case 5:
                    nomP = scan.next();
                    nomA = scan.next();
                    try {
                        c.esborrarAssignaturaPla(nomP, nomA);
                    } catch (NotFoundException e) {
                        System.out.println("Error, l'assignatura no esta en el pla o aquest no existeix");
                    }
                    break;
                case 6:
                    nomP = scan.next();
                    try {
                        c.consultarAssignaturesPlaEstudis(nomP);
                    } catch (NotFoundException e) {
                        System.out.println("Error, el pla amb aquest nom no existeix");
                    }
                    break;
                case 7:
                    nomA1 = scan.next();
                    quadrimestre = scan.nextInt();
                    try {
                        c.crearAssignatura(nomA1, quadrimestre);
                    } catch (RestriccioIntegritatException e) {
                        System.out.println("Ja existeix una assignatura amb aquest nom");
                    }
                    break;
                case 8:
                    nomA1 = scan.next();
                    try {
                        c.consultarAssignatura(nomA1);
                    } catch (NotFoundException e) {
                        System.out.println("Error, l'assignatura amb aquest nom no existeix");
                    }
                    break;
                case 9:
                    nomA1 = scan.next();
                    try {
                        c.esborrarAssignatura(nomA1);
                    } catch (NotFoundException e) {
                        System.out.println("Error, l'assignatura amb aquest nom no existeix");
                    }
                    break;
                case 10:
                    nomA1 = scan.next();
                    duracio = scan.nextInt();
                    nums = scan.nextInt();
                    taula = scan.next();
                    Aula.TipusAula t = Aula.stringToTipusAula(taula);

                    try {
                        c.modificaInformacioTeoria(nomA1, duracio, nums, t);
                    } catch (NotFoundException e) {
                        System.out.println("Error, l'assignatura amb aquest nom no existeix");
                    }
                    break;
                case 11:
                    nomA1 = scan.next();
                    duracio = scan.nextInt();
                    nums = scan.nextInt();
                    taula = scan.next();
                    t = Aula.stringToTipusAula(taula);
                    try {
                        c.modificaInformacioLaboratori(nomA1, duracio, nums, t);
                    } catch (NotFoundException e) {
                        System.out.println("Error, l'assignatura amb aquest nom no existeix");
                    }
                    break;
                case 12:
                    nomA1 = scan.next();
                    numgrups = scan.nextInt();
                    capgrups = scan.nextInt();
                    numsubgrups = scan.nextInt();
                    try {
                        c.modificarGrups(nomA1, numgrups, capgrups, numsubgrups);
                    } catch (NotFoundException e) {
                        System.out.println("No existeix cap assignatura amb aquest nom");
                    }
                    break;
                case 13:
                    nomA1 = scan.next();
                    nomA2 = scan.next();
                    try {
                        c.afegeixCorrequisit(nomA1, nomA2);
                    } catch (NotFoundException e) {
                        System.out.println("Alguna de les dues assignatures no existeix");
                    } catch (RestriccioIntegritatException e) {
                        System.out.println("No formen part del mateix quadrimestre o son la mateixa assignatura");
                    }
                    break;
                case 14:
                    nomA1 = scan.next();
                    nomA2 = scan.next();
                    try {
                        c.esborraCorrequisit(nomA1, nomA2);
                    } catch (NotFoundException e) {
                        System.out.println("Alguna de les dues assignatures no existeix");
                    }
                    break;
                case 15:
                    edifici = scan.next();
                    planta = scan.nextInt();
                    aula = scan.nextInt();
                    capacitat = scan.nextInt();
                    taula = scan.next();
                    t = Aula.stringToTipusAula(taula);
                    try {
                        c.creaAula(edifici, planta, aula, capacitat, t);
                    } catch (RestriccioIntegritatException e) {
                        System.out.println("Error, ja existeix una aula amb aquest nom");
                    }
                    break;
                case 16:
                    edifici = scan.next();
                    planta = scan.nextInt();
                    aula = scan.nextInt();
                    try {
                        c.esborrarAula(Aula.crearkey(edifici, planta, aula));
                    } catch (NotFoundException e) {
                        System.out.println("Error, no hi ha cap aula amb aquest nom");
                    }
                    break;
                case 17:
                    edifici = scan.next();
                    planta = scan.nextInt();
                    aula = scan.nextInt();
                    capacitat = scan.nextInt();
                    taula = scan.next();
                    t = Aula.stringToTipusAula(taula);
                    try {
                        c.modificarAula(Aula.crearkey(edifici, planta, aula), capacitat, t);
                    } catch (NotFoundException e) {
                        System.out.println("Error, no existeix una aula amb aquest nom");
                    }
                    break;
                case 18:
                    edifici = scan.next();
                    planta = scan.nextInt();
                    aula = scan.nextInt();
                    try {
                        c.consultarAula(Aula.crearkey(edifici, planta, aula));
                    } catch (NotFoundException e) {
                        System.out.println("Error, no existeix una aula amb aquest nom");
                    }
                    break;
                case 19:
                    int opcio = scan.nextInt();

                    if (opcio == 1) {
                        try {
                            h = c.crearHorari();
                            CtrlIO.getInstance().guardaHorari(h, "horariexemple.json");
                            System.out.println("Trobaras l'horari generat a horariexemple.json");
                        } catch (IOException e) {
                            System.out.println("Error al guardar horari");
                        }

                    } else {
                        h = c.crearHorari2();
                        try {
                            CtrlIO.getInstance().guardaHorari(h, "horariexemple2.json");
                            System.out.println("Trobaras l'horari generat a horariexemple2.json");
                        } catch (IOException e) {
                            System.out.println("Error al guardar horari");
                        }
                    }
                    break;
                case 20:
                    c.reload();
                    break;
                default:
                    break;
            }
            option = scan.nextInt();
        }
    }
}
