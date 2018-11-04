package model;

import exceptions.RestriccioIntegritatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DriverAssignacioL {            //TODO: necessitariem creadors buits per a poderlos usar aqui ja que si no hem de ficar tot el relacionat amb un assignatura, grup, aula i a mes a mes el de assignacio


    public static void mostraopcions(){
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Assginacio");
        System.out.println("2: Consultar atributs");
        System.out.println("3: Modificar atributs");
        System.out.println("4: Sortir");
    }

    public static void opcioinavalida(){
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }

    public static AssignacioL creador(Scanner s){
        String dia, tipusaula;
        int hora,opt;
        Aula aula;
        System.out.println("Introdueix el dia de la setmana");
        dia = s.next();
        System.out.println("Introdueix la hora");
        hora = s.nextInt();
        System.out.println("Introdueix el tipus d'Aula");
        tipusaula = s.next();
        System.out.println("Ara indicarem l'aula");
        aula = creaAula(s, tipusaula);
        Subgrup sub;
        System.out.println("Ara indicarem el subgrup:");
        sub = creaSubgrup(s);
        Assignatura assig;
        System.out.println("Ara indicarem la assignatura:");
        assig = creaAssignatura(s);
        AssignacioL assign = new AssignacioL(dia, hora, aula, tipusaula, assig, sub);
        return assign;


    }

    public static void mostra(AssignacioL a, Scanner s){
        int opt = 0;
        while(opt != 7){
            System.out.println("Escull que vols consultar.");
            System.out.println("1: Per el dia de la setmana");
            System.out.println("2: Per la hora");
            System.out.println("3: Pel tipus d'aula");
            System.out.println("4: Per la aula");
            System.out.println("5: Per el subgrup");
            System.out.println("6: Per la assignatura");
            System.out.println("7: per sortir");
            opt = s.nextInt();
            switch(opt){
                case 1:
                    System.out.println(a.getDiaSetmana());
                    break;

                case 2:
                    System.out.println(a.getHora());
                    break;

                case 3:
                    System.out.println(a.getTipusAula());
                    break;

                case 4:
                    System.out.println(a.getAula().getAula());  //TODO: printear todos los atributos?
                    break;
                case 5:
                    System.out.println(a.getSubgrup().getNum());
                    break;

                case 6:
                    System.out.println("Assignatura: " + a.getAssignatura().getNom());
                    System.out.println("Quadrimestre: " + String.valueOf(a.getAssignatura().getQuadrimestre()));
                    break;

                case 7: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }
    }

    public static Subgrup creaSubgrup(Scanner s){
        int opt;
        System.out.println("Introdueix el numero de subgrup");
        opt = s.nextInt();
        return new Subgrup(opt, 0 , 0 , null );
    }

    public static Assignatura creaAssignatura(Scanner s){
        System.out.println("Introdueix el nom de la assignatura");
        String nom = s.next();
        System.out.println("Introdueix el numero de quadrimestre de la assignatura");
        int quad = s.nextInt();
        return new Assignatura(nom, quad, null, null );
    }
    public static Aula creaAula(Scanner s, String tipusaula){
        String edifici;
        int planta, aula;
        System.out.println("Introdueix el nom de l'edifici");
        edifici = s.next();
        System.out.println("Introdueix el la planta en la que es situa l'aula");
        planta = s.nextInt();
        System.out.println("Introdueix el numero de l'aula");
        aula = s.nextInt();
        return new Aula(edifici, planta, aula, tipusaula, null);
    }


    public static void modificaAssignacio(AssignacioL a, Scanner s){
        int opt = 0;
        String auxs;
        int auxi;
        while(opt != 7){
            System.out.println("Escull que vols modificar.");
            System.out.println("1: Per el dia de la setmana");
            System.out.println("2: Per la hora");
            System.out.println("3: Pel tipus d'aula");
            System.out.println("4: Per la aula");
            System.out.println("5: Per el subgrup");
            System.out.println("6: Per la assignatura");
            System.out.println("7: per sortir");
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
                    System.out.println("Introdueix el nou tipus d'aula");
                    auxs = s.next();
                    a.setTipusAula(auxs);
                    break;

                case 4:
                   modificaAula(s, a.getAula());
                    break;
                case 5:
                    System.out.println("Introdueix el nou numero de subgrup");
                    auxi = s.nextInt();
                    a.getSubgrup().setNum(auxi);
                    break;

                case 6:
                    System.out.println("Introdueix el nou nom de la assignatura");
                    auxs = s.next();
                    a.getAssignatura().setNom(auxs);            //TODO: no hauria d'haber una fucnio per a poder cambiar el nom d'una assignatura?
                    System.out.println("Introdueix el nou numero de quadrimestre");
                    auxi = s.nextInt();
                    try {
                        a.getAssignatura().setQuadrimestre(auxi);
                    }
                    catch(RestriccioIntegritatException e){

                    }


                case 7: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }

    }

    public static void modificaAula(Scanner s , Aula a){
        int opt = 0;
        int aux;
        String auxs;
        while(opt != 6){
            System.out.println("Escull que vols modificar:");
            System.out.println("1: Per el nom de l'edifici");
            System.out.println("2: Per la planta");
            System.out.println("3: Per la aula");
            System.out.println("4: per sortir");
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
        AssignacioL assig = new AssignacioL(null,0,null,null,null,null);
        while(option != 4){
            mostraopcions();
            option = scan.nextInt();
            switch(option){
                case 1: //creem una asignació
                    AssignacioL assig2 = creador(scan);             //Usem un assig auxiliar per si l'usuari s'ha equivocat
                    assig = assig2;
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
                        System.out.println("");
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
