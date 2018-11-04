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

    public static Subgrup creador(Scanner s){
        int capacitat, num;
        System.out.println("Introdueix el numero del subgrup");
        num = s.nextInt();
        System.out.println("Introdueix la capacitat del subgrup");
        capacitat = s.nextInt();
        return new Subgrup(num, capacitat, 0, null);
    }

    public static void mostra(Subgrup g, Scanner s){
        int opt = 0;
        while(opt != 4){
            System.out.println("Escull que vols consultar:");
            System.out.println("1: numero del subgrup");
            System.out.println("2: capacitat del subgrup");
            System.out.println("3: sortir");
            opt = s.nextInt();
            switch(opt){
                case 1:
                    System.out.println(g.getNum());
                    break;

                case 2:
                    System.out.println(g.getCapacitat());
                    break;


                case 3:
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
            System.out.println("1: numero del subgrup");
            System.out.println("2: capacitat del subgrup");
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
        Subgrup sg = new Subgrup(0,0,0,null);
        while(option != 4) {
            mostraopcions();
            option = scan.nextInt();
            switch (option){
                case 1:
                    sg = creador(scan);
                    creat = true;
                    break;

                case 2:
                    if(!creat){
                        System.out.println("Error: no hi ha un grup creat");
                        System.out.println("");
                    }
                    else{
                        mostra(sg,scan);
                    }
                    break;

                case 3:
                    if(!creat){
                        System.out.println("Error: no hi ha un grup creat");
                        System.out.println("");
                    }
                    else{
                        modifica(sg,scan);
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
