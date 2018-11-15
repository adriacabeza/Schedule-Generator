package drivers;

import model.AssignaturaMonosessio;
import model.Aula;
import model.RestriccioAula;

import java.util.Scanner;

public class DriverRestriccioAula {

    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear una restriccio");
        System.out.println("2: comprobar restriccio");
        System.out.println("3: Sortir");
    }
    public static RestriccioAula creador (Scanner s){
        return new RestriccioAula();
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String aux;
        int hora;
        RestriccioAula rest = null;
        Aula aula = null;
        AssignaturaMonosessio assig = null;
        int opt = 0;
        while(opt != 3){
            mostraopcions();
            opt = scan.nextInt();
            switch(opt){

                case 1:
                    rest = creador(scan);
                    aula = new Aula("A",1,1,null,20);
                    assig = new AssignaturaMonosessio(null,null,null,null,0);
                    break;

                case 2:
                    /*
                    System.out.println("Introdueix el nom de l'edifici");
                    edifici = scan.next();
                    System.out.println("Introdueix el la planta en la que es situa l'aula");
                    planta = scan.nextInt();
                    System.out.println("Introdueix el numero de l'aula");
                    aula = scan.nextInt();
                    System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
                    tipusaula = scan.next();
                    if (tipusaula.equalsIgnoreCase("pcs")) {
                        tAula = Aula.TipusAula.PCS;
                    } else if (tipusaula.equalsIgnoreCase("laboratori")) {
                        tAula = Aula.TipusAula.LABORATORI;
                    } else
                        tAula = Aula.TipusAula.NORMAL;

                    System.out.println("Introdueix la capacitat de l'aula");
                    capacitat = scan.nextInt();
                    new Aula(edifici, planta, aula, tAula, capacitat);
                    if (aula == null) System.out.println("Error no s'ha creat cap aula");
                    else System.out.println("tipus aula actual" + aula.getTipusAula());*/
                    if(rest == null) System.out.println("Error no s'ha creat una restriccio");
                    else {
                        if(rest.isable(aula,assig)) System.out.println("es pot assignar aquesta sesio en aquesta aula");
                        else System.out.println("no es pot assignar aquesta sessio en aquesta aula");
                        System.out.println("El tipus d'aula es: " + aula.getTipusAula());
                        System.out.println("El tipus de sessio es: " + assig.getSessio().gettAula());
                    }

                    break;

                case 3:
                    break;
                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;
            }
        }
    }
}
