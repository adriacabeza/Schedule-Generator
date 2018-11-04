package model;

import java.util.Scanner;

public class DriverSubgrup {
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
        System.out.println("Introdueix el numero del grup");
        num = s.nextInt();
        System.out.println("Introdueix la capacitat del grup");
        capacitat = s.nextInt();
        System.out.println("Introdueix el nombre de subgrups que vols");
        subgrups = s.nextInt();
        return new Grup(num, capacitat, subgrups);
    }

    public static void mostra(Grup g, Scanner s){
        int opt = 0;
        while(opt != 4){
            System.out.println("Escull que vols consultar:");
            System.out.println("1: numero del grup");
            System.out.println("2: capacitat del grup");
            System.out.println("3: subgrups");
            System.out.println("4: sortir");
            opt = s.nextInt();
            switch(opt){
                case 1:
                    System.out.println(g.getNum());
                    break;

                case 2:
                    System.out.println(g.getCapacitat());
                    break;

                case 3:
                    g.getSubgrups().forEach((key,value) -> System.out.println(key));
                    break;

                case 4:
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }
    }

    public static void modifica(Grup g, Scanner s){
        int opt = 0;
        int aux;
        while(opt != 3) {
            System.out.println("Escull que vols modificar:");
            System.out.println("1: numero del grup");
            System.out.println("2: capacitat del grup");
            System.out.println("3: sortir");
            opt = s.nextInt();
            switch(opt){
                case 1:
                    System.out.println("Introdueix el nou numero de grup");
                    aux = s.nextInt();
                    g.setNum(aux);
                    break;

                case 2:
                    System.out.println("Introdueix la nova capacitat del grup");
                    aux = s.nextInt();
                    g.setCapacitat(aux);
                    break;

                case 3:
                    break;

                default:
                    opcioinavalida();
                    break;

            }
        }
    }

    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        int option = 0;
        boolean creat = false;
        Grup g = new Grup(0,0,0);
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
