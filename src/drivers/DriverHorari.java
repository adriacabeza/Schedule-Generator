package drivers;


import model.Horari;
import stubs.*;

import java.util.HashMap;
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
        RestriccioAssigMatiTarda resmati = new RestriccioAssigMatiTarda(0,"AC",false);
        RestriccioAulaHora resaulaHora = new RestriccioAulaHora(0,0,new Aula("A",0,1, Aula.TipusAula.NORMAL,50));
        HashMap<String, Aula> aules = new HashMap<String, Aula>();
        HashMap<String, Assignatura> assignatures = new HashMap<>();
        assignatures.put("AC",new Assignatura("AC",2));
        aules.put("A01",new Aula("A",0,1, Aula.TipusAula.NORMAL,50));
        return new Horari(b,assignatures, aules,resCorreq,resNivell,resaula,resgrupTeo,resauladia,resaulaHora,resmati);
    }


    public static void mostra(Horari g, Scanner s) {
        System.out.println(g.getHorari());
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        boolean creat = false;

        while (option != 4) {
            mostraopcions();
            Horari horari = null;
            option = scan.nextInt();
            switch (option) {
                case 1:
                    //horari = creador(scan);
                    creat = true;
                    break;

                case 2:
                    if (!creat) {
                        System.out.println("Error: no hi ha un horari creat");
                        System.out.println("");
                    } else {
                        mostra(horari, scan);
                    }
                    break;

                case 3:
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }
    }

}