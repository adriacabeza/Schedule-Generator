package model;

import java.util.HashMap;
import java.util.Scanner;

public class DriverGrup {
    public static void mostraopcions(){
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Grup");
        System.out.println("2: Consultar atributs");
        System.out.println("3: Modificar atributs");
        System.out.println("4: Sortir");
    }
    public static void opcioinavalida(){
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }

    public static Grup creador(Scanner s){
        int capacitat, num, subgrups;
        System.out.print("Introdueix el numero del grup");
        num = s.nextInt();
        System.out.print("Introdueix la capacitat del grup");
        capacitat = s.nextInt();
        System.out.print("Introdueix el nombre de subgrups que vols");
        subgrups = s.nextInt();
        return new Grup(num, capacitat, subgrups);
    }

    public static void mostra(Grup g, Scanner s){

    }

    public static void modifica(Grup g, Scanner s){

    }

    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        int option = 0;
        boolean creat = false;
        Grup g;
        while(option != 4) {
            mostraopcions();
            option = scan.nextInt();
            switch (option){
                case 1:
                    g = creador(scan);
                    creat = true;
                    break;

                case 2:
                    if(!creat){
                        System.out.println("Error: no hi ha un grup creat");
                        System.out.println("");
                    }
                    else{
                        mostra(g,scan);
                    }
                    break;

                case 3:
                    if(!creat){
                        System.out.println("Error: no hi ha un grup creat");
                        System.out.println("");
                    }
                    else{
                        modifica(g,scan);
                    }
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
