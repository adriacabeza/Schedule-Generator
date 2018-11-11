package drivers;

import exceptions.RestriccioIntegritatException;
import model.AssignacioT;
import model.Assignatura;
import model.Aula;
import model.Grup;

import java.util.Scanner;

public class DriverAssignacioT {

    private static void mostraopcions(){
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Assginacio");
        System.out.println("2: Consultar atributs");
        System.out.println("3: Modificar atributs");
        System.out.println("4: Sortir");
    }

    private static void opcioinavalida(){
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }

    private static AssignacioT creador(Scanner s){
        String dia;
        Aula.TipusAula tipusaula;
        int hora,opt;
        Aula aula;
        System.out.println("Introdueix el dia de la setmana");
        dia = s.next();
        System.out.println("Introdueix la hora");
        hora = s.nextInt();
        System.out.println("Ara indicarem l'aula");
        aula = creaAula(s);
        Grup grup;
        System.out.println("Ara indicarem el grup:");
        grup = creaGrup(s);
        Assignatura assig;
        System.out.println("Ara indicarem la assignatura:");
        assig = creaAssignatura(s);
        AssignacioT assign = new AssignacioT(dia, hora, aula ,assig, grup);
        return assign;


    }

    private static void mostra(AssignacioT a, Scanner s){
        int opt = 0;
        while(opt != 6){
            System.out.println("Escull que vols consultar.");
            System.out.println("1: Per el dia de la setmana");
            System.out.println("2: Per la hora");
            System.out.println("3: Per la aula");
            System.out.println("4: Per el grup");
            System.out.println("5: Per la assignatura");
            System.out.println("6: per sortir");
            opt = s.nextInt();
            switch(opt){
                case 1:
                    System.out.println(a.getDiaSetmana());
                    break;

                case 2:
                    System.out.println(a.getHora());
                    break;

                case 3:
                    System.out.println(a.getAula().getAula());  //TODO: printear todos los atributos?
                    break;
                case 4:
                    System.out.println(a.getGrup().getNum());
                    break;

                case 5:
                    System.out.println("Assignatura: " + a.getAssignatura().getNom());
                    System.out.println("Quadrimestre: " + String.valueOf(a.getAssignatura().getQuadrimestre()));
                    break;

                case 6: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }
    }

    private static Grup creaGrup(Scanner s){
        int opt;
        System.out.println("Introdueix el numero de subgrup");
        opt = s.nextInt();
        return new Grup(opt, 0 , 0 );
    }

    private static Assignatura creaAssignatura(Scanner s){
        System.out.println("Introdueix el nom de la assignatura");
        String nom = s.next();
        System.out.println("Introdueix el numero de quadrimestre de la assignatura");
        int quad = s.nextInt();
        return new Assignatura(nom, quad);
    }

    private static Aula creaAula(Scanner s){
        String edifici, tipusaula;
        int planta, aula, capacitat;
        Aula.TipusAula tAula;
        System.out.println("Introdueix el nom de l'edifici");
        edifici = s.next();
        System.out.println("Introdueix el la planta en la que es situa l'aula");
        planta = s.nextInt();
        System.out.println("Introdueix el numero de l'aula");
        aula = s.nextInt();
        System.out.println("Introdueix la capacitat de l'aula");
        capacitat = s.nextInt();
        tAula = Aula.TipusAula.NORMAL;
        return new Aula(edifici, planta, aula, tAula, capacitat);
    }


    private static void modificaAssignacio(AssignacioT a, Scanner s){
        int opt = 0;
        String auxs;
        int auxi;
        while(opt != 6){
            System.out.println("Escull que vols modificar.");
            System.out.println("1: Per el dia de la setmana");
            System.out.println("2: Per la hora");
            System.out.println("3: Per la aula");
            System.out.println("4: Per el grup");
            System.out.println("5: Per la assignatura");
            System.out.println("6: per sortir");
            opt = s.nextInt();
            switch(opt){
                case 1:
                    System.out.println("Introdueix el nou dia");
                    auxs = s.next();
                    a.setDiaSetmana(auxs);
                    break;

                case 2:
                    System.out.println("Introdueix la nova hora");
                    auxi = s.nextInt();
                    a.setHora(auxi);
                    break;

                case 3:
                    modificaAula(s, a.getAula());
                    break;
                case 4:
                    System.out.println("Introdueix el nou numero de grup");
                    auxi = s.nextInt();
                    a.getGrup().setNum(auxi);
                    break;

                case 5:
                    System.out.println("Introdueix el nou nom de la assignatura");
                    auxs = s.next();
                    a.getAssignatura().setNom(auxs);            //TODO: no hauria d'haber una fucnio per a poder cambiar el nom d'una assignatura?
                    System.out.println("Introdueix el nou numero de quadrimestre");
                    auxi = s.nextInt();
                    try {
                        a.getAssignatura().setQuadrimestre(auxi);
                    }
                    catch(RestriccioIntegritatException e){
                        e.printStackTrace();
                    }


                case 6: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }

    }

    private static void modificaAula(Scanner s , Aula a){
        int opt = 0;
        int aux;
        String auxs;
        while(opt != 5){
            System.out.println("Escull que vols modificar:");
            System.out.println("1: Per el nom de l'edifici");
            System.out.println("2: Per la planta");
            System.out.println("3: Per la aula");
            System.out.println("4: Per la capacitat de l'aula");
            System.out.println("5: per sortir");
            opt = s.nextInt();
            switch(opt) {
                case 1:
                    System.out.println("Introdueix el nou edifici");
                    auxs = s.next();
                    a.setEdifici(auxs);
                    break;

                case 2:
                    System.out.println("Introdueix la nova planta");
                    aux = s.nextInt();
                    a.setPlanta(aux);
                    break;

                case 3:
                    System.out.println("Introdueix la nova aula");
                    aux = s.nextInt();
                    a.setAula(aux);
                    break;

                case 4:
                    System.out.println("Introdueix la nova capacitat");
                    aux = s.nextInt();
                    a.setCapacitat(aux);
                    break;

                case 5:
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
        AssignacioT assig = new AssignacioT(null,0,null,null,null);
        while(option != 4){
            mostraopcions();
            option = scan.nextInt();
            switch(option){
                case 1: //creem una asignació
                    assig = creador(scan);
                    creat = true;
                    break;

                case 2: //consultem els atributs
                    if(!creat){
                        System.out.println("Error: no hi ha una Assignació creada");
                        System.out.println("");
                    }
                    else{
                        mostra(assig,scan);
                    }
                    break;

                case 3: //modifiquem
                    if(!creat){
                        System.out.println("Error: no hi ha una Assignació creada");
                        System.out.println(""); //TODO: System.lineSeparator() mirate esto TONI
                    }
                    else{
                        modificaAssignacio(assig,scan);
                    }
                    break;

                case 4: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }

        }
    }
}
