/**
 * @author Aina Garcia
 */

package test;

import controllers.CtrlDomini;
import controllers.CtrlSerDes;

import java.io.IOException;
import java.util.Scanner;

public class DriverCtrlDomini {

    public static void menu() {
        System.out.println("Opcio 0: Mostra menu");
        System.out.println("\t input: 0");
        System.out.println("Opcio 19: Crea i mostra Horari");
        System.out.println("\t input: 19 <1:bt/2:bt cronologic>");
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
        String h;

        option = scan.nextInt();

        while (option != 21) {
            switch (option) {
                case 19:
                    try {
                            c.carrega();
                            h = c.generaHorari();
                            CtrlSerDes.getInstance().guardaHorari(h , "horariexemple.json");
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
