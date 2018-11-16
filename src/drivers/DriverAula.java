/**
 * @Author Antoni Rambla
 */
package drivers;

import model.Aula;

import java.util.Scanner;

public class DriverAula {

    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Aula");
        System.out.println("2: Consultar atributs");
        System.out.println("3: Modificar atributs");
        System.out.println("4: Sortir");
    }


    public static void opcioinavalida() {
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }

    public static Aula creador(Scanner s) {
        String edifici, tipusaula;
        int planta, aula, capacitat;
        Aula.TipusAula tAula;

        System.out.println("Introdueix el nom de l'edifici");
        edifici = s.next();
        System.out.println("Introdueix el la planta en la que es situa l'aula");
        planta = s.nextInt();
        System.out.println("Introdueix el numero de l'aula");
        aula = s.nextInt();
        System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
        tipusaula = s.next();
        if (tipusaula.equalsIgnoreCase("pcs")) {
            tAula = Aula.TipusAula.PCS;
        } else if (tipusaula.equalsIgnoreCase("laboratori")) {
            tAula = Aula.TipusAula.LABORATORI;
        } else
            tAula = Aula.TipusAula.NORMAL;

        System.out.println("Introdueix la capacitat de l'aula");
        capacitat = s.nextInt();

        return new Aula(edifici, planta, aula, tAula, capacitat);
    }

    public static void mostra(Aula a, Scanner s) {
        int opt = 0;
        int aux;
        while (opt != 7) {
            System.out.println("Escull que vols consultar:");
            System.out.println("1: Per el nom de l'edifici");
            System.out.println("2: Per la planta");
            System.out.println("3: Per la aula");
            System.out.println("4: Pel tipus d'aula");
            System.out.println("5: Per la capacitat de l'aula");
            System.out.println("6: per la clau identificadora de l'aula");
            System.out.println("7: per sortir");
            opt = s.nextInt();
            switch (opt) {
                case 1:
                    System.out.println(a.getEdifici());
                    break;

                case 2:
                    System.out.println(a.getPlanta());
                    break;

                case 3:
                    System.out.println(a.getAula());
                    break;

                case 4:
                    System.out.println(a.getTipusAula());
                    break;
                case 5:
                    System.out.println(a.getCapacitat());
                    break;
                case 6:
                    System.out.println(a.getKey());
                    break;
                case 7:
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }
    }

    public static void modifica(Aula a, Scanner s) {
        int opt = 0;
        int aux;
        String auxs;
        while (opt != 6) {
            System.out.println("Escull que vols modificar:");
            System.out.println("1: Per el nom de l'edifici");
            System.out.println("2: Per la planta");
            System.out.println("3: Per la aula");
            System.out.println("4: Pel tipus d'aula");
            System.out.println("5: per sortir");
            opt = s.nextInt();
            switch (opt) {
                case 1:
                    System.out.println("Introdueix el nou edifici");
                    auxs = s.next();
                    a.setEdifici(auxs);
                    break;

                case 2:
                    System.out.println("Introdueix la nova planta");
                    aux = s.nextInt();
                    a.setPlanta(aux);
                    break;

                case 3:
                    System.out.println("Introdueix la nova aula");
                    aux = s.nextInt();
                    a.setAula(aux);
                    break;

                case 4:
                    System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
                    String tipusaula;
                    tipusaula = s.next();
                    if (tipusaula.equalsIgnoreCase("pcs")) {
                        a.setTipusAula(Aula.TipusAula.PCS);
                    } else if (tipusaula.equalsIgnoreCase("laboratori")) {
                        a.setTipusAula(Aula.TipusAula.LABORATORI);
                    } else
                        a.setTipusAula(Aula.TipusAula.NORMAL);

                    break;

                case 5:
                    break;
                default:
                    opcioinavalida();
                    break;
            }

        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        boolean creat = false;
        Aula aula = new Aula(null, 0, 0, null, 0);
        while (option != 4) {
            mostraopcions();
            option = scan.nextInt();
            switch (option) {
                case 1: //creem una aula
                    aula = creador(scan);
                    creat = true;
                    break;

                case 2: //consultem els atributs
                    if (!creat) {
                        System.out.println("Error: no hi ha una Aula creada");
                        System.out.println("");
                    } else {
                        mostra(aula, scan);
                    }
                    break;

                case 3: //modifiquem
                    if (!creat) {
                        System.out.println("Error: no hi ha una aula creada");
                        System.out.println("");
                    } else {
                        modifica(aula, scan);
                    }
                    break;

                case 4: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }

        }
    }
}
