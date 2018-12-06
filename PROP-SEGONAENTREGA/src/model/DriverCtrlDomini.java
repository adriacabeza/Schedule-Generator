/**
 * @author Aina Garcia
 */

package model;

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
                case 19:
                        try {
                            h = c.crearHorari();
                            CtrlIO.getInstance().guardaHorari(h, "horariexemple.json");
                            System.out.println("Trobaras l'horari generat a horariexemple.json");
                        } catch (IOException e) {
                            System.out.println("Error al guardar horari");
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
