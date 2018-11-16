package drivers;

import model.*;

import java.awt.*;
import java.util.Scanner;

public class DriverSessioGrup {
    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear SessioGrup");
        System.out.println("2: Consultar atributs");
        System.out.println("3: Modificar atributs");
        System.out.println("4: Sortir");
    }

    public static SessioGrup crea(Scanner s){
        String nom,tipusaula;
        int aux,numg,nums,cap,numses,durses;
        Aula.TipusAula tAula;
        InfoSessions ses = null;
        Assignatura ass;
        Grup grup = null;
        Subgrup sub = null;
        System.out.println("Introdueix el nom i el quadrimestre de la assignatura");
        nom = s.next();
        aux = s.nextInt();
        ass = new Assignatura(nom,aux);
        System.out.println("Introdueix el numero del grup, la seva capacitat i el numero de subgrup");
        numg = s.nextInt();
        cap = s.nextInt();
        nums = s.nextInt();
        System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
        tipusaula = s.next();
        if (tipusaula.equalsIgnoreCase("pcs")) {
            tAula = Aula.TipusAula.PCS;
        } else if (tipusaula.equalsIgnoreCase("laboratori")) {
            tAula = Aula.TipusAula.LABORATORI;
        } else
            tAula = Aula.TipusAula.NORMAL;
        System.out.println("introdueix el numero i duracio de les sesios");
        numses = s.nextInt();
        durses = s.nextInt();
        System.out.println("introdueix un 1 per a crear la sesio de teoria qualsevol altre numero per a que sigui de lab");
        if(s.nextInt() == 1){
            ses = new Teoria(numses,durses,tAula);
        }
        else {
            ses = new Laboratori(numses,durses,tAula);
        }
        System.out.println("Introdueix el valor que tindra");
        return new SessioGrup(ass,ses,new Grup(numg,cap,1),new Subgrup(nums,1,1),s.nextInt());
    }

    public static void main(String[] args) {
        int opt = 0;
        Scanner s = new Scanner(System.in);
        SessioGrup ses = null;
        String tipus;
        while (opt != 4){
            mostraopcions();
            opt = s.nextInt();
            switch (opt){
                case 1:
                    ses = crea(s);
                    break;

                case 2:
                    if(ses == null) System.out.println("error no hi ha una SessioGrup creada");
                    else {
                        System.out.println("Assignatura amb nom " + ses.getAssig().getNom());
                        System.out.println("Amb grup " + ses.getGrup().getNum());
                        System.out.println("Amb subgrup " + ses.getSub().getNum());
                        if(ses.getSessio().getClass() == Laboratori.class) tipus = "Laboratori";
                        else tipus = "Teoria";
                        System.out.println("Amb una sessio de tipus " + tipus);
                        System.out.println("Amb un valor de " + ses.getValor());
                    }
                    break;

                case 3:
                    if(ses == null) System.out.println("error no hi ha una SessioGrup creada");
                    else {
                        String nom,tipusaula;
                        int aux,numg,nums,cap,numses,durses;
                        Aula.TipusAula tAula;
                        InfoSessions infses = null;
                        Assignatura ass;
                        Grup grup = null;
                        Subgrup sub = null;
                        System.out.println("escull que vols modificar");
                        System.out.println("1: Assignatura");
                        System.out.println("2: Sessio");
                        System.out.println("3: Grup");
                        System.out.println("4: Subgrup");
                        System.out.println("5: valor");
                        int op = s.nextInt();
                        switch(op){
                            case 1:
                                System.out.println("Introdueix el nom i el quadrimestre de la assignatura");
                                nom = s.next();
                                aux = s.nextInt();
                                ass = new Assignatura(nom,aux);
                                ses.setAssig(ass);
                                break;
                            case 2:
                                System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
                                tipusaula = s.next();
                                if (tipusaula.equalsIgnoreCase("pcs")) {
                                    tAula = Aula.TipusAula.PCS;
                                } else if (tipusaula.equalsIgnoreCase("laboratori")) {
                                    tAula = Aula.TipusAula.LABORATORI;
                                } else
                                    tAula = Aula.TipusAula.NORMAL;
                                System.out.println("introdueix el numero i duracio de les sesios");
                                numses = s.nextInt();
                                durses = s.nextInt();
                                System.out.println("introdueix un 1 per a crear la sesio de teoria qualsevol altre numero per a que sigui de lab");
                                if(s.nextInt() == 1){
                                    infses = new Teoria(numses,durses,tAula);
                                }
                                else {
                                    infses = new Laboratori(numses,durses,tAula);
                                }
                                ses.setSessio(infses);
                                break;
                            case 3:
                                System.out.println("Introdueix el numero del grup i la seva capacitat");
                                numg = s.nextInt();
                                cap = s.nextInt();
                                ses.setGrup(new Grup(numg,cap,1));
                                break;
                            case 4:
                                System.out.println("Introdueix el numero del subgrup");
                                nums = s.nextInt();
                                ses.setSubgrup(new Subgrup(nums,1,1));
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("opcio incorrecta");
                                break;
                        }
                    }
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Opcio incorrecta");
                    break;
            }
        }

    }
}
