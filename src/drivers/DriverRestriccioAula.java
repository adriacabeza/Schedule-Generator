package drivers;

import model.*;

import java.util.Scanner;

public class DriverRestriccioAula {

    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear una restriccio");
        System.out.println("2: consultar tipus d'aula");
        System.out.println("3: modificar tipus d'aula");
        System.out.println("4: comprobar restriccio");
        System.out.println("5: Sortir");
    }
    public static RestriccioAula creador (Scanner s){
        return new RestriccioAula();
    }
    public static Aula creaAula(Scanner s){
        String edifici, tipusaula;
        int planta, aula, capacitat;
        Aula.TipusAula tAula;

        System.out.println("Introdueix el nom de l'edifici");
        edifici = s.next();
        System.out.println("Introdueix el la planta en la que es situa l'aula");
        planta = s.nextInt();
        System.out.println("Introdueix el numero de l'aula");
        aula = s.nextInt();
        System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
        tipusaula = s.next();
        if (tipusaula.equalsIgnoreCase("pcs")) {
            tAula = Aula.TipusAula.PCS;
        } else if (tipusaula.equalsIgnoreCase("laboratori")) {
            tAula = Aula.TipusAula.LABORATORI;
        } else
            tAula = Aula.TipusAula.NORMAL;

        System.out.println("Introdueix la capacitat de l'aula");
        capacitat = s.nextInt();

        return new Aula(edifici, planta, aula, tAula, capacitat);
    }

    public static SessioGrup creaAssig(Scanner s){
        String nom,tipusaula;
        int aux, numses,durses,capacitat, num, subgrups;
        Aula.TipusAula tAula;
        InfoSessions ses = null;
        Assignatura ass;
        Grup grup = null;
        Subgrup sub = null;
        System.out.println("Introdueix el nom i el quadrimestre de la assignatura");
        nom = s.next();
        aux = s.nextInt();
        ass = new Assignatura(nom,aux);
        System.out.println("Introdueix el numero i duracio de les sessions");
        numses = s.nextInt();
        durses = s.nextInt();
        System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
        tipusaula = s.next();
        if (tipusaula.equalsIgnoreCase("pcs")) {
            tAula = Aula.TipusAula.PCS;
        } else if (tipusaula.equalsIgnoreCase("laboratori")) {
            tAula = Aula.TipusAula.LABORATORI;
        } else
            tAula = Aula.TipusAula.NORMAL;

        System.out.println("Introdueix un 1 si la sessio sera de teoria, qualsevol altre numero per a laboratori");
        if(s.nextInt() == 1){
            ses = new Teoria(numses,durses,tAula);
            System.out.println("Introdueix el numero del grup");
            num = s.nextInt();
            System.out.println("Introdueix la capacitat del grup");
            capacitat = s.nextInt();
            System.out.println("Introdueix el nombre de subgrups que vols");
            subgrups = s.nextInt();
            grup =  new Grup(num,capacitat,subgrups);
        }
        else {
            ses = new Laboratori(numses,durses,tAula);
            System.out.println("Introdueix el numero del subgrup");
            num = s.nextInt();
            System.out.println("Introdueix la capacitat del subgrup");
            capacitat = s.nextInt();
            sub = new Subgrup(num, capacitat, 0);
        }
        System.out.println("introdueix el valor que li vols posar a la sessio");
        return new SessioGrup(ass,ses,grup,sub,s.nextInt());
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String aux;
        int hora;
        RestriccioAula rest = null;
        Aula aula = null;
        SessioGrup assig = null;
        int opt = 0;
        while(opt != 5){
            mostraopcions();
            opt = scan.nextInt();
            switch(opt){

                case 1:
                    rest = creador(scan);
                    aula =creaAula(scan);
                    assig =creaAssig(scan);
                    break;

                case 2:
                    if(rest == null) System.out.println("Error no s'ha creat una restriccio");
                    else{
                        System.out.println("El tipus d'aula es: " + aula.getTipusAula());
                        System.out.println("El tipus d'aula de la sessio es: " + assig.getSessio().gettAula());
                    }
                    break;

                case 3:
                    if(rest == null) System.out.println("Error no s'ha creat una restriccio");
                    else {
                        Aula.TipusAula tAula;
                        int op;
                        String tipusaula;
                        System.out.println("prem 1 per a modificar el tipus d'aula de la aula qualsevol altre numero per modificar el de sesio");
                        op = scan.nextInt();
                        System.out.println("Introdueix el tipus d'aula, en cas d'input erroni sera una aula normal");
                        tipusaula = scan.next();
                        if (tipusaula.equalsIgnoreCase("pcs")) {
                            tAula = Aula.TipusAula.PCS;
                        } else if (tipusaula.equalsIgnoreCase("laboratori")) {
                            tAula = Aula.TipusAula.LABORATORI;
                        } else
                            tAula = Aula.TipusAula.NORMAL;
                        if(op == 1) aula.setTipusAula(tAula);
                        else assig.getSessio().settAula(tAula);
                    }
                    break;

                case 4:
                    if(rest == null) System.out.println("Error no s'ha creat una restriccio");
                    else {
                        if(rest.isAble(aula,assig)) System.out.println("es pot assignar aquesta sesio en aquesta aula");
                        else System.out.println("no es pot assignar aquesta sessio en aquesta aula");
                        System.out.println("El tipus d'aula es: " + aula.getTipusAula());
                        System.out.println("El tipus d'aula de la sessio es: " + assig.getSessio().gettAula());
                    }

                    break;

                case 5:
                    break;

                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;
            }
        }
    }
}
