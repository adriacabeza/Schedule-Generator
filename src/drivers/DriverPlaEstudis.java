package drivers;

import model.PlaEstudis;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverPlaEstudis {
    public static void mostraopcions(){
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Pla d'Estudis");
        System.out.println("2: Consultar atributs");
        System.out.println("3: Modificar atributs");
        System.out.println("4: Sortir");
    }

    public static void opcioinavalida(){
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }

    public static PlaEstudis creador(Scanner s) {
        String nom;
        int any, obs;
        boolean obsolet = false;
        System.out.println("Introdueix el nom i l'any de creació del pla d'estudis");
        nom = s.next();
        any = s.nextInt();
        System.out.println("introdueix un 1 si el pla es obsolet, qualsevol altre numero en cas contrari");
        obs = s.nextInt();
        if(obs == 1) obsolet = true;
        PlaEstudis pla = new PlaEstudis(nom,any,obsolet);
        creaAssig(s, pla);
        return pla;
    }



    public static void mostra(Scanner s, PlaEstudis p) {
        int opt = 0;
        while (opt != 4) {
            System.out.println("Escull que vols consultar:");
            System.out.println("1: nom del pla d'estudis");
            System.out.println("2: any de creacio del pla d'estudis");
            System.out.println("3: consultar si el pla es obsolet");
            System.out.println("4: assignatures del pla d'estudis");
            System.out.println("5: sortir");
            opt = s.nextInt();
            switch (opt) {
                case 1:
                    System.out.println(p.getNomTitulacio());
                    break;

                case 2:
                    System.out.println(p.getAny());
                    break;

                case 3:
                    if (p.isObsolet()) System.out.println("Pla d'estudis obsolet");
                    else System.out.println("pla d'estudis vigent");
                    break;

                case 4:
                    ArrayList<String> assig = p.getAssignatures();
                    if (assig.size() == 0) System.out.println("no hi han assignatures");
                    else {
                        for (String a : assig) {
                            System.out.println("Assignatura: " + a);
                        }
                    }
                    break;

                case 5:
                    break;
                default:
                    opcioinavalida();
                    break;
            }
        }
    }

    public static void modifica(Scanner s, PlaEstudis p) {
        int opt = 0;
        int opaux = 0;
        String nom;
        int any;
        while (opt != 4) {
            System.out.println("Escull que vols modificar:");
            System.out.println("1: nom del pla d'estudis");
            System.out.println("2: any de creacio del pla d'estudis");
            System.out.println("3: modificar la obsolecencia del pla");
            System.out.println("4: assignatures del pla d'estudis");
            System.out.println("5: sortir");
            opt = s.nextInt();
            switch (opt) {
                case 1:
                    System.out.println("Introdueix el nou nom");
                    nom = s.next();
                    p.setNomTitulacio(nom);
                    break;

                case 2:
                    System.out.println("Introdueix el nou any");
                    any = s.nextInt();
                    p.setAny(any);
                    break;

                case 3:
                    System.out.println("Introdueix un 1 per a fer que el pla d'estudis sigui vigent, qualsevol altre numero en cas contrari");
                    any = s.nextInt();
                    p.setObsolet(any == 1);
                    break;

                case 4:
                   while(opaux != 3) {
                       System.out.println("Introdueix un 1 per a afegir assignatures");
                       System.out.println("Introdueix un 2 per a eliminar assignatures");
                       System.out.println("Introdueix un 3 per a sortir");
                       opaux = s.nextInt();
                       switch (opaux) {
                           case 1:
                               System.out.println("Introdueix el nom de la assignatura que vols afegir");
                               nom = s.next();
                               p.afegirAssignatura(nom);
                               break;

                           case 2:
                               System.out.println("Introdueix el nom de la assignatura que vols eliminar");
                               nom = s.next();
                               p.esborrarAssignatura(nom);
                               break;

                           case 3:
                               break;

                           default:
                               opcioinavalida();
                               break;
                       }
                   }
                    break;

                case 5:
                    break;
                default:
                    opcioinavalida();
                    break;
            }
        }
    }

    public static void creaAssig(Scanner s, PlaEstudis p){
        ArrayList<String> res = new ArrayList<>();
        String nom;
        int more;
        System.out.println("Introdueix un 1 si vols afegir assignatures");
        more = s.nextInt();
        while (more == 1) {
            System.out.println("Introdueix el nom de la assignatura");
            nom = s.next();
            p.afegirAssignatura(nom);
        }
    }
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        int option = 0;
        PlaEstudis pe = null;
        while(option != 4) {
            mostraopcions();
            option = scan.nextInt();
            switch (option){
                case 1:
                    pe = creador(scan);
                    break;

                case 2:
                    if(pe == null){
                        System.out.println("Error: no hi ha un pla d'estudis creat");
                        System.out.println("");
                    }
                    else{
                        mostra(scan, pe);
                    }
                    break;

                case 3:
                    if(pe == null){
                        System.out.println("Error: no hi ha un pla d'estudis creat");
                        System.out.println("");
                    }
                    else{
                        modifica(scan, pe);
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
