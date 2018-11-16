package drivers;


import stubs.*;

import java.util.Scanner;

public class DriverHorari {


    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Horari");
        System.out.println("2: Consultar Horari");
        System.out.println("3: Sortir");
    }


    public static void opcioinavalida() {
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }

    public static Horari creador(Scanner s) {
        int num;
        boolean b;
        System.out.println("Introdueix 0 si vols fer l'horari sense forward checking i 1 si el vols fer servir");
        b= s.nextBoolean();
        RestriccioGrupTeo resgrupTeo = new stubs.RestriccioGrupTeo();
        RestriccioAula resaula = new RestriccioAula();
        RestriccioAulaDia resauladia = new RestriccioAulaDia(3,new Aula("A",0,1, Aula.TipusAula.NORMAL,50));
        RestriccioNivell resNivell = new RestriccioNivell();
        RestriccioCorrequisit resCorreq = new RestriccioCorrequisit();
        RestriccioAssigMatiTarda resmati = new RestriccioAssigMatiTarda("AC",false);
        RestriccioAulaHora resaulaHora = new RestriccioAulaHora(0,0,new Aula("A",0,1, Aula.TipusAula.NORMAL,50));

        return new Horari(b,resCorreq,resNivell,resaula,resauladia,resaulaHora,resmati);
    }

    public static void mostra(Horari g, Scanner s) {
        int opt = 0;
        while (opt != 4) {
            System.out.println("Escull que vols consultar:");
            System.out.println("1: numero del grup");
            System.out.println("2: capacitat del grup");
            System.out.println("3: subgrups");
            System.out.println("4: sortir");
            opt = s.nextInt();
            switch (opt) {
                case 1:
                    System.out.println(g.getNum());
                    break;

                case 2:
                    System.out.println(g.getCapacitat());
                    break;

                case 3:
                    g.getSubgrups().forEach((key, value) -> System.out.println(key));
                    break;

                case 4:
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }
    }


}