package drivers;

import model.Assignatura;
import model.RestriccioAssigMatiTarda;

import java.util.Scanner;

public class DriverRestriccioAssigMatiTarda {
    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear una restriccio");
        System.out.println("2: Consultar atributs");
        System.out.println("3: comprobar restriccio");
        System.out.println("4: Sortir");
    }
    public static RestriccioAssigMatiTarda creador (Scanner s){
        System.out.println("introdueix el nom de la assignatura");
        String nom = s.next();
        System.out.println("introdueix un 1 si es de mati, qualsevol altre numero si es de tardes");
        int mati = s.nextInt();
        return new RestriccioAssigMatiTarda(nom, (mati == 1));
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String aux;
        int hora;
        RestriccioAssigMatiTarda rest = null;
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
                        System.out.println("Nom de la assignatura: " + rest.getAssig());
                        if(rest.getMati()) System.out.println("Es de matins");
                        else System.out.println("Es de tardes");
                    }
                    break;

                case 3:
                    if(rest == null) System.out.println("no has creat una restriccio");
                    else {
                        System.out.println("Introdueix la assignatura que vols mirar");
                        aux = scan.next();
                        System.out.println("Introdueix la hora");
                        hora = scan.nextInt();
                        if(rest.isAble(new Assignatura(aux,0),hora)) System.out.println("aquesta assignatura es pot assignar a aquesta hora");
                        else System.out.println("Error: aquesta assignatura viola la seva assignacio horaria");
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
