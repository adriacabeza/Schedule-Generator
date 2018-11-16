/**
 * @Author Adria Cabeza
 */
package drivers;

import model.*;

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
    public static Aula creaAula(Scanner s){
        String  tipusaula;
        Aula.TipusAula tAula;
        System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
        tipusaula = s.next();
        if (tipusaula.equalsIgnoreCase("pcs")) {
            tAula = Aula.TipusAula.PCS;
        } else if (tipusaula.equalsIgnoreCase("laboratori")) {
            tAula = Aula.TipusAula.LABORATORI;
        } else
            tAula = Aula.TipusAula.NORMAL;


        return new Aula(null, 0, 0, tAula, 0);
    }

    public static SessioGrup creaAssig(Scanner s){
        String tipusaula;
        Aula.TipusAula tAula;
        InfoSessions ses = null;
        System.out.println("Introdueix el tipus d'aula de la sessio, en cas d'input erroni sera una aula normal");
        tipusaula = s.next();
        if (tipusaula.equalsIgnoreCase("pcs")) {
            tAula = Aula.TipusAula.PCS;
        } else if (tipusaula.equalsIgnoreCase("laboratori")) {
            tAula = Aula.TipusAula.LABORATORI;
        } else
            tAula = Aula.TipusAula.NORMAL;
        ses = new Laboratori(0,0,tAula);

        return new SessioGrup(null,ses,null,null,0);
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        RestriccioAula rest = null;
        int opt = 0;
        while(opt != 3){
            mostraopcions();
            opt = scan.nextInt();
            switch(opt){

                case 1:
                    rest = creador(scan);
                    break;

                case 2:
                    if(rest == null) System.out.println("Error no s'ha creat una restriccio");
                    else{
                       if(rest.isAble(creaAula(scan),creaAssig(scan))) System.out.println("Tipus iguals");
                       else System.out.println("tipus diferents");
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
