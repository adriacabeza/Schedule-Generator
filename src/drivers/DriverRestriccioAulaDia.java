package drivers;

import model.Aula;
import model.RestriccioAulaDia;

import java.util.Scanner;

public class DriverRestriccioAulaDia {
    private static int fromDia2int(String dia) {
        switch (dia) {
            case "Dilluns":
                return 0;
            case "Dimarts":
                return 1;
            case "Dimecres":
                return 2;
            case "Dijous":
                return 3;
            default:
                return 4;
        }
    }

    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear una restriccio");
        System.out.println("2: Consultar atributs");
        System.out.println("3: comprobar restriccio");
        System.out.println("4: Sortir");
    }
    public static RestriccioAulaDia creador (Scanner s){
        Aula aula = creaAula(s);
        System.out.println("Ara escriu el dia de la setmana");
        return new RestriccioAulaDia(fromDia2int(s.next()), aula);
    }

    public static Aula creaAula(Scanner s){
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

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Aula aula;
        int hora;
        RestriccioAulaDia rest = null;
        int opt = 0;
        while(opt != 4){
            mostraopcions();
            opt = scan.nextInt();
            switch(opt){

                case 1:
                    rest = creador(scan);
                    break;

                case 2:
                    if(rest == null) System.out.println("no has creat una restriccio");
                    else{
                        System.out.println("Identificador de la aula: " + rest.getAula().getKey());
                        System.out.println("Dia: " + rest.getDia());
                    }
                    break;

                case 3:
                    if(rest == null) System.out.println("no has creat una restriccio");
                    else {
                        aula = creaAula(scan);
                        System.out.println("Introdueix el dia");
                        if(rest.isAble(aula, fromDia2int(scan.next()))) System.out.println("aquesta aula es pot usar en aquest dia");
                        else System.out.println("Error: aquesta aula no se li poden fer assignacions aquest dia");
                    }
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;
            }
        }
    }
}
